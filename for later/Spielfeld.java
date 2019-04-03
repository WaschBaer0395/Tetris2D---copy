package sample;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Das Spielfeld.
 * Die Klasse Spielfeld enthält ein Array als Spielfeld mit zugehoerigen
 * Methoden zur Bearbeitung und Ausgabe.
 *
 * @author Rene Hippe 2018WiSe-2019WiSe
 * @version 1
 */
class Spielfeld extends Controller {
    public static final Rectangle empty = new Rectangle(33, 33, Color.TRANSPARENT);
/**   */
    /**
     * Constructor, der das Spielfeld mit dem Zeichen '.' füllt
     */

    Spielfeld() {
    }

    /**
     * method to initialize the playfield
     * with dots for better visualisation
     **/

    void initializeField() {
        for (int z = 0; z < gamePane.getRowCount(); z++) {
            for (int s = 0; s < gamePane.getColumnCount(); s++) {
                gamePane.add(empty, posY+z,posX+s);
            }
        }
    }

    /**
     * writes a pixel to a chosen row and colum
     *
     * @param figZeile  current row of figure
     * @param figSpalte current colum of field
     * @param pixelWert character at figure pos figX and figY (pixel)
     **/

    void setzePixelwertAn(int figZeile, int figSpalte, Rectangle pixelWert) {
        gamePane.getChildren().remove(figZeile,figSpalte);
        gamePane.add(empty,figZeile,figSpalte);
    }

    /**
     * checks, if the chosen row is valid
     * and inside the playfield
     *
     * @param zeile chosen row of figure
     * @return true when inside the playfields max and min row otherwhise false
     **/
    boolean zeileIstGueltig(int zeile) {
        return (zeile >= 0 & zeile < gamePane.getRowCount());
    }

    /**
     * checks, if the chosen colum is valid
     * and inside the playfield
     *
     * @param spalte chosen colum of figure
     * @return true when inside the playfields min and max colum otherwhise false
     **/
    boolean spalteIstGueltig(int spalte) {
        return (spalte < gamePane.getColumnCount() & spalte >= 0);

    }

    /**
     * checks, for colision between other figurines
     *
     * @param zeile  current row of figure
     * @param spalte current colum of figure
     * @return true when hitting anything other than a dot, otherwhise false
     **/
    boolean pixelWertKollidiertAn(int zeile, int spalte) {
        if(getNodeFromGridPane(gamePane,spalte,zeile) != empty) return false;
        else return true;
    }


    /**
     * Prüft, ob eine Zeile mit Spielfiguren vollständig gefüllt ist.
     *
     * @param _zeile Nr. der Zeile (0 bis length-1)
     * @return true, wenn Zeile voll ist, sonst false
     * @since 13.0.5
     */
    private boolean zeileIstVoll(int _zeile) {

        // Über alle Spalten der Zeile iterieren
        for (int spalte = 0; spalte < gamePane.getColumnCount(); spalte++) {

            // wenn ein leeres Pixel in der Zeile gefunden wird, abbrechen
            if (getNodeFromGridPane(gamePane,spalte,_zeile)==empty) return false;
        }
        return true;
    }

    /**
     * Löscht eine Zeile aus pixels. Dabei werden alle Zeilen mit kleinerer
     * Zeilennummer dann auf die nächst größere Zeile kopiert.
     * Als letzte Zeile wird oben eine leere Zeile eingefügt.
     * <p>
     * z.B. lösche Zeile 2
     * 0 ....			.... 	(leerzeichen überschrieben)
     * 1 .x.x	|		....  	(alte Zeile 0)
     * 2 xx.x	V	- 	.x.x  	(alte Zeile 1)
     * 3 xxxx			xxxx	(alte Zeile 3)
     *
     * @param _zeile zu löschende Zeile (Array Index)
     * @since 13.0.5
     */
    void loescheZeile(int _zeile) {

        // zuerst jede über der zu löschenden Zeile liegenden Zeile
        // in die jeweils darunter liegende Zeile kopieren
        this.kopiereAlleZeilenNachUntenAb(_zeile);

        // danach die oberste Zeile voller Leerzeichen schreiben
        this.obersteZeileMitLeerzeichenFuellen(_zeile);
    }

    /**
     * Kopiert eine Zeile des 2D-Arrays auf die nächst kleinere Zeile(nach
     * unten), die gelöscht werden soll.
     *
     * @param _zeile Zeile (Array Index), die durch die mit der nächst
     *               kleineren Zeilennummer ersetzt werden soll, minimal == 1
     * @since 13.0.5
     */
    private void kopiereZeile(int _zeile) {

        // Kopiert spaltenweise einen Wert nach dem anderen in die zu löschende
        // Zeile aus der nächst kleineren Zeile.
        // Über alle Spalten des Arrays iterieren, die sich in der betreffenden
        // Zeile und bzw. darunter befinden.
        for (int spalte = 0; spalte < gamePane.getColumnCount(); spalte++){
            gamePane.getChildren().remove(_zeile, spalte);
        // Wert kopieren
            gamePane.add(getNodeFromGridPane(gamePane, _zeile - 1, spalte), _zeile, spalte);
        }
    }


    /**
     * Kopiert ab der zu löschenden Zeile alle darübergeordneten Zeilen eine Zeile
     * weiter nach unten. Als Erstes wird die zu löschende Zeile ersetzt, dann
     * die darüber.
     *
     * @param _zeile zu löschende Zeile (Array Index)
     * @since 13.0.5
     */
    private void kopiereAlleZeilenNachUntenAb(int _zeile) {

        // alle Zeilen des Arrays oberhalb der zu löschenden Zeile nach
        // unten verschieben
        for (int zeile = _zeile; zeile > 0; zeile--)

            // Zeile kopieren
            this.kopiereZeile(zeile);
    }

    /**
     * Schreibt die oberste Zeile voller Leerzeichen.
     * Oben ist im Array die Zeile mit der Zeilennummer 0
     *
     * @since 13.0.5
     */
    private void obersteZeileMitLeerzeichenFuellen(int _zeile) {

        // Ã¼ber alle Spalten der obersten Zeile iterieren
        for (int spalte = 0; spalte < gamePane.getColumnCount(); spalte++) {

            // Feld mit Leerzeichen füllen
            gamePane.getChildren().remove(_zeile, spalte);
            // Wert kopieren
            gamePane.add(empty, _zeile, spalte);
        }
    }

    /**
     * Löscht alle vollen Zeilen im Spielfeld von oben nach unten
     * (zeilen von 0 bis pixels.length-1). Das hat den Vorteil, das nur nicht volle Zeilen
     * weiter nach unten kopiert werden und für die Zeile kein erneutes löschen
     * notwendig ist, wie bei der umgekehrten Reihenfolge.
     */
    void loescheAlleVolleZeilen() {
        // Über alle Zeilen iterieren
        for (int zeile = 0; zeile < gamePane.getRowCount(); zeile++) {

            // wenn Zeile voll ist
            if (this.zeileIstVoll(zeile))

                // Zeile löschen
                this.loescheZeile(zeile);
        }
    }
} // ende class