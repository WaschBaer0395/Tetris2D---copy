package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;



public class Controller implements Initializable {

    @FXML
    private Button down;

    @FXML
    private Button up;

    @FXML
    private Button left;

    @FXML
    private Button right;

    @FXML
    private Text scoreField;

    @FXML
    private GridPane nextBlockPane;


    @FXML
    protected GridPane gamePane;

    final char[][] TSpielfigur = {{' ','T',' '}, {'T','T','T'}};
    final char[][] ISpielfigur = {{'I'}, {'I'}, {'I'},{'I'}};
    final char[][] OSpielfigur = {{'O','O'}, {'O','O'}};
    final char[][] ZSpielfigur = {{'Z','Z',' '}, {' ','Z','Z'}};
    final char[][] SSpielfigur = {{' ','S','S'}, {'S','S',' '}};
    final char[][] LSpielfigur = {{'L',' '}, {'L',' '}, {'L','L'}};
    final char[][] JSpielfigur = {{' ','J'}, {' ','J'}, {'J','J'}};


    protected int posY = 0;
    protected int posX = 3;

    protected char[][]field=new char[21][10];

    final static int maxR = 20;
    final static int maxC = 9;

    protected char[][]figur;
    protected char[][]nextFig;

    protected  int rotation = 0;

    protected boolean spielfigurIstBeweglich = true;

    int score = 0;






    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeField();
        figur = randomizedFig();
        placeNewFigurine();
        drawField(field);
        scoreField.setText(String.valueOf(score));
        nextFig = randomizedFig();
        drawNext();
        registerKeyboardHandler(gamePane);
    }

    private void registerKeyboardHandler(Pane pane) {
        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                boolean consumed = false;
                if(!spielfigurIstBeweglich){
                    loescheAlleVolleZeilen();
                    scoreField.setText((String.valueOf(score)));
                    drawField(field);
                    figur = nextFig;
                    placeNewFigurine();
                    drawField(field);
                    nextFig = randomizedFig();
                    drawNext();
                    spielfigurIstBeweglich = true;
                }

                switch (keyEvent.getCode()) {
                    case DOWN:
                        System.out.println("Key Pressed: " + keyEvent.getCode());
                        bewegeSpielfigurKomplett("DOWN");
                        consumed = true;
                        break;
                    case LEFT:
                        System.out.println("Key Pressed: " + keyEvent.getCode());
                        bewegeSpielfigurKomplett("LEFT");
                        //evenT("LEFT");
                        consumed = true;
                        break;
                    case RIGHT:
                        System.out.println("Key Pressed: " + keyEvent.getCode());
                        bewegeSpielfigurKomplett("RIGHT");
                        //evenT("RIGHT");
                        consumed = true;
                        break;
                    case UP:
                        System.out.println("Key Pressed: " + keyEvent.getCode());
                        //bewegeSpielfigurKomplett("DOWN");
                        //evenT("UP");
                        consumed = true;
                        break;

                    case Q:
                        System.out.println("Key Pressed: " + keyEvent.getCode());
                        bewegeSpielfigurKomplett("Q");
                        //evenT("Q");
                        consumed = true;
                        break;

                    case E:
                        System.out.println("Key Pressed: " + keyEvent.getCode());
                        //evenT("E");
                        consumed = true;
                        break;

                    case ENTER:
                        System.out.println("Key Pressed: " + keyEvent.getCode());
                        //evenT("ENTER");
                        consumed = true;
                        break;

                    default:
                        break;
                }

                if (consumed) {
                    keyEvent.consume();
                }

                printFieldToConsole();
                drawField(field);
            }
        });
        pane.setFocusTraversable(true);
        System.out.println("test");
    }

    private void drawNext(){
        nextBlockPane.getChildren().clear();
        Rectangle rectangle;
        for(int y=0;y<nextFig.length;y++){
            for(int x=0;x<nextFig[0].length;x++){
                switch(nextFig[y][x]) {
                    case 'T':
                        rectangle = new Rectangle(29, 29, Color.PURPLE);
                        rectangle.setStroke(Color.BLACK);
                        rectangle.setArcWidth(10.0);
                        rectangle.setArcHeight(10.0);

                        break;
                    case 'I':
                        rectangle = new Rectangle(29, 29, Color.DEEPSKYBLUE);
                        rectangle.setStroke(Color.BLACK);
                        rectangle.setArcWidth(10.0);
                        rectangle.setArcHeight(10.0);

                        break;
                    case 'O':
                        rectangle = new Rectangle(29, 29, Color.YELLOW);
                        rectangle.setStroke(Color.BLACK);
                        rectangle.setArcWidth(10.0);
                        rectangle.setArcHeight(10.0);

                        break;
                    case 'S':
                        rectangle = new Rectangle(29, 29, Color.GREEN);
                        rectangle.setStroke(Color.BLACK);
                        rectangle.setArcWidth(10.0);
                        rectangle.setArcHeight(10.0);

                        break;
                    case 'Z':
                        rectangle = new Rectangle(29, 29, Color.RED);
                        rectangle.setStroke(Color.BLACK);
                        rectangle.setArcWidth(10.0);
                        rectangle.setArcHeight(10.0);

                        break;
                    case 'J':
                        rectangle = new Rectangle(29, 29, Color.BLUE);
                        rectangle.setStroke(Color.BLACK);
                        rectangle.setArcWidth(10.0);
                        rectangle.setArcHeight(10.0);

                        break;
                    case 'L':
                        rectangle = new Rectangle(29, 29, Color.ORANGE);
                        rectangle.setStroke(Color.BLACK);
                        rectangle.setArcWidth(10.0);
                        rectangle.setArcHeight(10.0);

                        break;

                    default:
                        rectangle = new Rectangle();
                        break;
                }
                GridPane.setRowIndex(rectangle, y);
                GridPane.setColumnIndex(rectangle, x);
                nextBlockPane.getChildren().addAll(rectangle);
            }
        }
    }

    public void drawField(char[][] _field){
        clearField();
        for(int y=0;y<=maxR;y++){
            for(int x=0;x<=maxC;x++){
                getTypeOfBlock(_field[y][x],y,x);
            }
        }
    }

    private void getTypeOfBlock(char block,int _y,int _x){
        Rectangle rectangle;

        switch(block){
            case 'T':
                rectangle = new Rectangle(33,33,Color.PURPLE);
                rectangle.setStroke(Color.BLACK);
                rectangle.setArcWidth(10.0);
                rectangle.setArcHeight(10.0);

                break;
            case 'I':
                rectangle = new Rectangle(33,33,Color.DEEPSKYBLUE);
                rectangle.setStroke(Color.BLACK);
                rectangle.setArcWidth(10.0);
                rectangle.setArcHeight(10.0);

                break;
            case 'O':
                rectangle = new Rectangle(33,33,Color.YELLOW);
                rectangle.setStroke(Color.BLACK);
                rectangle.setArcWidth(10.0);
                rectangle.setArcHeight(10.0);

                break;
            case 'S':
                rectangle = new Rectangle(33,33,Color.GREEN);
                rectangle.setStroke(Color.BLACK);
                rectangle.setArcWidth(10.0);
                rectangle.setArcHeight(10.0);

                break;
            case 'Z':
                rectangle = new Rectangle(33,33,Color.RED);
                rectangle.setStroke(Color.BLACK);
                rectangle.setArcWidth(10.0);
                rectangle.setArcHeight(10.0);

                break;
            case 'J':
                rectangle = new Rectangle(33,33,Color.BLUE);
                rectangle.setStroke(Color.BLACK);
                rectangle.setArcWidth(10.0);
                rectangle.setArcHeight(10.0);

                break;
            case 'L':
                rectangle = new Rectangle(33,33,Color.ORANGE);
                rectangle.setStroke(Color.BLACK);
                rectangle.setArcWidth(10.0);
                rectangle.setArcHeight(10.0);

                break;
            default:
                rectangle = new Rectangle(33,33,Color.TRANSPARENT);
                break;
        }
            placeNode(rectangle,_y,_x);
    }

    public void placeNode(Rectangle _rectangle,int _y,int _x){
        GridPane.setRowIndex(_rectangle, _y );
        GridPane.setColumnIndex(_rectangle, _x);
        gamePane.getChildren().addAll(_rectangle);
    }

    private void clearField(){
        Node node = gamePane.getChildren().get(0);
        gamePane.getChildren().clear();
        gamePane.getChildren().add(0,node);
    }

    @FXML
    private void buttonAction(ActionEvent e) {

        if (e.getSource() == down) {
        }

        if (e.getSource() == up) {
        }

        if (e.getSource() == left) {
        }

        if (e.getSource() == right) {
        }
    }

    protected void placeNewFigurine(){
        posY = 0;
        posX = 3;
        rotation = 0;

        for (int y = 0; y < figur.length; y++) {
            for (int x = 0; x < figur[y].length; x++) {
                if(figur[y][x]!=' ') {
                    field[posY+y][posX+x] = figur[y][x];
                }
            }
        }
    }

    public char[][] randomizedFig(){
        char[][] _figur;
        int zufall=(int)Math.round(Math.random()*7);

        switch (zufall){
            case 0:
                _figur = ISpielfigur;
                break;
            case 1:
                _figur = OSpielfigur;
                break;
            case 2:
                _figur = SSpielfigur;
                break;
            case 3:
                _figur = TSpielfigur;
                break;
            case 4:
                _figur = ZSpielfigur;
                break;
            case 5:
                _figur = LSpielfigur;
                break;
            case 6:
                _figur = JSpielfigur;
                break;
            default:
                _figur = OSpielfigur;
                break;
        }

        return _figur;
    }

    public boolean positionIstGueltig(int _zeile, int _spalte) {

        // Anzahl Zeilen -1 = max. zeile
        int maxZeileSpielfigur = figur.length-1;

        // Anzahl der Spalten in der 1.Zeile = max. spalte
        int maxSpalteSpielfigur = figur[0].length-1;

        boolean gueltig = false;

        if (rotation == 0 || rotation == 180) // -> alles wie bisher
            gueltig = (
                    // oberste Zeile
                    zeileIstGueltig(_zeile)
                            // linke Spalte
                            & spalteIstGueltig(_spalte)
                            // unterste Zeile
                            & zeileIstGueltig(_zeile + maxZeileSpielfigur)
                            // rechte Spalte
                            & spalteIstGueltig(_spalte + maxSpalteSpielfigur));
        else // 11 Aufgabe 2.3) Gültige Position inklusive Drehung
            // 90 oder 270 Grad -> höhe und breite sind vertauscht
            // entsprechend muss anders addiert und geprüft werden:
            gueltig = (
                    // oberste Zeile
                    zeileIstGueltig(_zeile)
                            // linke Spalte
                            & spalteIstGueltig(_spalte)
                            // unterste Zeile -> höhe und breite sind vertauscht
                            & zeileIstGueltig(_zeile + maxSpalteSpielfigur)
                            // rechte Spalte -> höhe und breite sind vertauscht
                            & spalteIstGueltig(_spalte + maxZeileSpielfigur)
            );
        return gueltig;
    }

    public boolean rotationIstGueltig(int r){
        if(r==0 || r==90 || r==180 || r==270 || r==360){
            return true;
        }
        else
            return false;
    }

    void uebertrageInsSpielfeld(){
        // counting the rows
        for(int y=0;y<figur.length;y++){
            //counting the colums
            for(int x=0;x<figur[y].length;x++){
                //char of current pixel
               char pixelWert=figur[y][x];
                // passing the char of that pixel over to next function
                if(pixelwertIstUebertragbar(pixelWert)){
                    uebertragePixelWertInsSpielfeld(y,x,figur[y][x]);
                }
            }
        }

    }

    private void uebertragePixelWertInsSpielfeld(int z, int s, char _pixelWert){

        int neuZeile=z;
        int neuSpalte=s;

        int maxZeile = figur.length-1;
        int maxSpalte = figur[0].length-1;


        switch(rotation){
            case 0:
                neuZeile = z;
                neuSpalte = s;
                break;

            case 90:
                neuZeile=s;
                neuSpalte=maxZeile -z;
                break;

            case 180:
                neuZeile=maxZeile -z;
                neuSpalte=maxSpalte -s;
                break;

            case 270:
                neuSpalte=z;
                neuZeile=maxSpalte -s;
                break;
        }
        setzePixelwertAn(posY+neuZeile,posX+neuSpalte,_pixelWert);


    }

    void setzePixelwertAn(int figZeile, int figSpalte,char pixelWert){
        field[figZeile][figSpalte] = pixelWert;
    }

    private void testeBeweglichkeit(String input) {

        if(input.equals("DOWN"))		// Bewegung nach unten

            // wenn die neue Position nicht mehr im Feld ist (unterer Rand)
            if (!positionIstGueltig(posY, posX))
                // wenn verschieben nach unten nicht mehr möglich ist, ist der Stein
                // nicht mehr beweglich
                spielfigurIstBeweglich = false;


            else
                // oder es bei einer gültigen Position eine Kollision im
                // Spielfeld gibt.
                if (kollisionImSpielfeld())
                    // wenn verschieben nach unten nicht mehr möglich ist, ist der Stein
                    // nicht mehr beweglich
                    spielfigurIstBeweglich = false;


        // sonst ist weiteres Verschieben erlaubt.

    }

    public boolean kollisionImSpielfeld(){
        // counting the rows
        for(int y=0;y<figur.length;y++){
            //counting the colums
            for(int x=0;x<figur[y].length;x++){
                //char of current pixel
                char pixelWert=figur[y][x];
                if(pixelwertIstUebertragbar(pixelWert)){
                    if( pixelWertKollidiertImSpielfeld(y,x)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean pixelwertIstUebertragbar(char _pixelwert) {
        return _pixelwert != ' ' ;
    }

    private boolean pixelWertKollidiertImSpielfeld(int _zeile, int _spalte) {

        int zeileSpielfigur = _zeile;
        int spalteSpielfigur = _spalte;

        // Anzahl Zeilen/Spalten -1 = maximaler Zeilen/Spalten-Index der Spielfigur
        int maxZeileSpielfigur = figur.length - 1;
        int maxSpalteSpielfigur = figur[0].length - 1;

        // Fallunterscheidung nach den 4 erlaubten Winkel-Werten
        // für die Berechnung der gedrehten Koordinaten zeileSpielfigur und spalteSpielfigur
        switch (rotation) {
            case 0: // abc
                // def -> Original-Array, keine Veränderung
                // Zeilen und Spalten unverändert übernehmen
                zeileSpielfigur = _zeile;
                spalteSpielfigur = _spalte;
                break;

            case 90: 	// da
                // eb
                // fc
                // Zeilen der Spielfigur invertieren
                // Zeilen und Spalten vertauschen
                spalteSpielfigur = maxZeileSpielfigur - _zeile;
                zeileSpielfigur = _spalte;
                break;

            case 180: 	// fed
                // cba
                // Zeilen und Spalten der Spielfigur invertieren
                zeileSpielfigur = maxZeileSpielfigur - _zeile;
                spalteSpielfigur = maxSpalteSpielfigur - _spalte;
                break;

            case 270: 	// cf
                // be
                // ad
                // Spalten der Spielfigur invertieren
                // Zeilen und Spalten vertauschen
                spalteSpielfigur = _zeile;
                zeileSpielfigur = maxSpalteSpielfigur - _spalte;
                break;
        }

        // zum Schluss die eigentliche Verschiebung des Punktes
        // zeileSpielfigur|spalteSpielfigur um die Position der
        // Spielfigur this.zeile|this.spalte im Spielfeld
        return pixelWertKollidiertAn(posY + zeileSpielfigur, posX + spalteSpielfigur);
    }

    boolean pixelWertKollidiertAn(int zeile, int spalte){
        return  field[zeile][spalte] != ' ';
    }

    void bewegeSpielfigurEinfach(String input){

        switch(input){

            case "LEFT":
                posX--;
                break;

            case "RIGHT":
                posX++;
                break;

            case "DOWN":
                posY++;
                break;

            case "Q":
                if(rotation<270)
                    rotation+=90;
                else
                    rotation=0;
                break;
        }

    }

    void loescheImSpielfeld(){
        // counting the rows
        for(int y=0;y<figur.length;y++){
            //counting the colums
            for(int x=0;x<figur[y].length;x++){
                //char of current pixel
                char pixelWert=figur[y][x];
                // passing the char of that pixel over to next function
                if(pixelwertIstUebertragbar(pixelWert)){
                    uebertragePixelWertInsSpielfeld(y,x,' ');
                }
            }
        }
    }

    void bewegeSpielfigurKomplett(String input) {
        // Aktuelle Position bzw. Winkel merken
        int altZeile = posY;
        int altSpalte = posX;
        int altWinkel = rotation;

        loescheImSpielfeld();

        // Spielfigur an der aktuellen Position aus dem Spielfeld löschen
        //loescheImSpielfeld();

        // Spielfigur  auf neue Werte für Position, bzw. neuen Winkel setzen
        bewegeSpielfigurEinfach(input);

        // Die Beweglichkeit der Spielfigur an der neuen Position feststellen
        testeBeweglichkeit(input);

        // Falls die neue Position nicht gültig ist, wieder die vorherigen
        // Werte für Position bzw. Winkel setzen
        if (!(positionIstGueltig(posY, posX)&& rotationIstGueltig(rotation))) {
            posY = altZeile;
            posX = altSpalte;
            rotation = altWinkel;
        } else {
            // Falls doch, aber es dabei eine Kollision gegeben hat, wieder
            // die vorherigen Werte für Position bzw. Winkel setzen
            if (kollisionImSpielfeld()) {
                posY = altZeile;
                posX = altSpalte;
                rotation = altWinkel;
            }
        }
        // Spielfigur mit passenden Werten ins Spielfeld übertragen
        uebertrageInsSpielfeld();
    }

    void loescheAlleVolleZeilen() {
        int deletedLines = 0;
        // Über alle Zeilen iterieren
        for (int y = 0; y < field.length; y++) {

            // wenn Zeile voll ist
            if (zeileIstVoll(y)) {
                deletedLines++;
                // Zeile löschen
                loescheZeile(y);
            }
        }
        if(deletedLines ==1 ) score += 100;
        else if(deletedLines ==2)score +=400;
        else if(deletedLines ==3)score +=900;
        else if(deletedLines ==4)score+=2000;
    }

    void loescheZeile(int _zeile) {

        // zuerst jede über der zu löschenden Zeile liegenden Zeile
        // in die jeweils darunter liegende Zeile kopieren
        kopiereAlleZeilenNachUntenAb(_zeile);

        // danach die oberste Zeile voller Leerzeichen schreiben
        obersteZeileMitLeerzeichenFuellen();
    }

    private void kopiereZeile(int _zeile) {

        // Kopiert spaltenweise einen Wert nach dem anderen in die zu löschende
        // Zeile aus der nächst kleineren Zeile.
        // Über alle Spalten des Arrays iterieren, die sich in der betreffenden
        // Zeile und bzw. darunter befinden.
        for (int spalte = 0; spalte < field[_zeile].length; spalte++)

            // Wert kopieren
            field[_zeile][spalte] = field[_zeile - 1][spalte];
    }

    private void kopiereAlleZeilenNachUntenAb(int _zeile) {

        // alle Zeilen des Arrays oberhalb der zu löschenden Zeile nach
        // unten verschieben
        for (int  zeile = _zeile; zeile > 0; zeile--)

            // Zeile kopieren
            kopiereZeile(zeile);
    }

    private void obersteZeileMitLeerzeichenFuellen() {

        // Ã¼ber alle Spalten der obersten Zeile iterieren
        for (int spalte = 0; spalte < field[0].length; spalte++) {

            // Feld mit Leerzeichen füllen
            field[0][spalte] = ' ';
        }
    }

    private boolean zeileIstVoll(int _zeile) {

        // Über alle Spalten der Zeile iterieren
        for (int spalte = 0; spalte < field[_zeile].length; spalte++) {

            // wenn ein leeres Pixel in der Zeile gefunden wird, abbrechen
            if (field[_zeile][spalte] == ' ')
                return false;
        }
        return true;
    }

    boolean zeileIstGueltig(int zeile){
        return(zeile>=0 & zeile < field.length);
    }

    boolean spalteIstGueltig(int spalte){
        return(spalte<field[0].length & spalte>=0);

    }

    public void printFieldToConsole(){
        for(int y=0;y<field.length;y++){
            System.out.println();
            for(int x=0;x<field[0].length;x++){
                if(field[y][x]==' ' || field[y][x]==0)System.out.print('.');
                else System.out.print(field[y][x]);
            }
        }
    }

    void initializeField(){
        for(int z=0;z<field.length;z++){
            for(int s=0;s<field[z].length;s++){
                field[z][s] = ' ';
            }
        }
    }
}
