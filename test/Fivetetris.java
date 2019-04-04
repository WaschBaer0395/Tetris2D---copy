/**
 * 
 *
 * @author rene-
 */
class Fivetetris
{
	/**
	 * Fordert den Benutzer zu einer Eingabe auf,
     * mit Erläuterung der gültigen Eingaben
     * solange bis eine gültige Eingabe erfolgt ist.
     * @since 13.0.5
	 * @return gültige Zeichen a, d, s, w, q zur Spielsteuerung
	 */
	private String naechsterSpielzug() {
		String eingabe = "";

		// solange Benutzerabfrage, welche Bewegung durchgeführt werden
		// soll, bis eine gültige Antwort zurück gegeben wird
		do {
			System.out.println("\nGeben Sie Ihren Zug ein:\n"
                    + "a - Spielfigur nach links bewegen\n"
					+ "d - Spielfigur nach rechts bewegen\n"
					+ "s - Spielfigur nach unten bewegen\n"
					+ "w - Spielfigur um 90° im Uhrzeigersinn drehen\n"
					+ "q - Spiel beenden\n"
                	+ " Mit Return abschließen\n");
			eingabe = Tastatur.tastatur().naechsteZeile();

		} while (!(eingabe.equals("a") || eingabe.equals("d")
				|| eingabe.equals("s") || eingabe.equals("w")
				|| eingabe.equals("q")));

		return eingabe;
	}
	/**
	 * erzeugt eine Spielfigur anhand einer zufalls funktion
     * mögliche figuren sind I, O, S, T, Z, L, J.
     *
     * @param meinSpielFeld instanz des aktuellen Spielfeldes
     * @param figur Instanz der aktuellen Spielfigur
	 * @return figur random erstellte Spielfigur
	 */
public Spielfigur newRandomFigur(Spielfeld meinSpielFeld,Spielfigur figur){
   int zufall=(int)Math.round(Math.random()*7);
    
    switch (zufall){
        case 0:
            return figur = new ISpielfigur(meinSpielFeld,0,2,0) ;
        case 1:
            return figur = new OSpielfigur(meinSpielFeld,0,2,0) ;
        case 2:
            return figur = new SSpielfigur(meinSpielFeld,0,2,0) ;
        case 3:
            return figur = new TSpielfigur(meinSpielFeld,0,2,0) ;
        case 4:
            return figur = new ZSpielfigur(meinSpielFeld,0,2,0) ;
        case 5:
            return figur = new LSpielfigur(meinSpielFeld,0,2,0) ;
        case 6:
            return figur = new JSpielfigur(meinSpielFeld,0,2,0) ;
    }
   return figur = new ISpielfigur(meinSpielFeld,0,2,0);
}
	/**
	 * Haupt Spielfunktion, inkl willkommens nachricht und 
     * und schleife über das laufende Spiel.
     *
	 */


public void playGame(){
    Spielfeld meinSpielFeld = new Spielfeld();
	String spielZug = "s";
    boolean roundActive = true;

    
    System.out.println("Willkommen bei Fivetetris");
    System.out.println("Im folgendem Spiel werden nach und nach"+
                        "figuren erscheinen, die vom benutzer jeweils gedreht,"+ 
                        "nach unten, links oder rechts bewegt werden können."+
                        "besteht eine zeile nur aus blöcken von Spielfiguren, so wird diese gelöscht"+
                        "Das Spiel ist zuende, wenn die oberste Zeile, belegt ist und kein neuer"+
                        "Stein mehr spawnen kann.");
    //meinSpielFeld.printField();

    // Schleife über aktives Spiel
    while(roundActive){
        
        // aufruf der funktion über die runden
        round(spielZug, meinSpielFeld);
        
        // abfrage ob neugestartet werden soll oder nicht, nach manueller beendigung oder GameOver
        System.out.println("zum neustarten, bitte s drücken, zum beenden w, a, d oder q drücken");
        spielZug = naechsterSpielzug();
        
        // neustart oder nicht 
        if(spielZug.equals("s")){
			roundActive = true;
        }
        else roundActive = false;
	}
}


	/**
	 * Funktion inkl schleife über die zu spielende runde
     * beinhalted, erzeugung der Zufalls Figur, sowie des Spielfeldes
     * und überprüfung der beweglichkeit der erzeugten figur
     * sowie das löschen vollendeter Zeilen im Spielfeld.
     *
     * @param meinSpielFeld klasse des Spielfeldes
     * @param spielZug der Erste Spielzug als string übergeben (s)
	 */

public void round(String spielZug,Spielfeld meinSpielFeld){
    meinSpielFeld = new Spielfeld();
    boolean currentRound = true;
    boolean currentFigurine = true;
    
    // schleife über die runden
    while(currentRound){
        Spielfigur figur = new Spielfigur(meinSpielFeld,0,0,0);
       	// erzeugung der Zufallsfigur
        figur = (newRandomFigur(meinSpielFeld, figur));

		// testen auf beweglichkeit am startpunkt im Spielfeld
        if(!figur.spielfigurIstBeweglich){
            currentRound = false;
            System.out.println("Game Over :(");
        }

        else{
            //aufruf funktion über die aktuelle Figur 
            //rückgabe bestimmt ob runde vorbei ist.
            currentRound = currentFig(spielZug,figur,meinSpielFeld);
        }
        
        // löschen der vollen zeilen
        meinSpielFeld.loescheAlleVolleZeilen();
        meinSpielFeld.printField();
    }
}
	/**
	 * main Funktion, enthält den aufruf des Hauptspieles, sowie
     * die initialisierung der Fivetetris klasse als five.
	 * @param args start argument
	 */
public static void main(String[] args){
    Fivetetris five = new Fivetetris();
    five.playGame();
}
	/**
	 * funktion inkl schleife über die aktuelle figur, 
     * beinhaltet beweung der figur, sowie überprüfung ob abgebrochen werden soll
     * oder eine kollision auftrit im Spielfeld.
     * 
     * @param _meinSpielFeld klasse des Spielfeldes.
     * @param _figur Klasse der Spielfigur 
     * @param spielZug String mit eingabe für den ersten Spielzug
	 * @return currentRound Boolean false, wenn runde abgebrochen wird, oder kollision auftrit 
	 */

public boolean currentFig(String spielZug,Spielfigur _figur,Spielfeld _meinSpielFeld){
	boolean currentFigurine = true;
   	boolean currentRound = true;
    
    // aktuelle figur 
    while(currentFigurine){
        //abfrage nach dem Spielzug
        spielZug = naechsterSpielzug();
        
        // falls abgebrochen werden soll
        if(spielZug.equals("q")){
            System.out.println("vom user abgebrochen!");
            currentRound = false;
            currentFigurine = false;
        }
        else{
            // bewegen der Spielfigur, sowie drucken des Spielfeldes
            _figur.bewegeSpielfigurKomplett(spielZug);
            _meinSpielFeld.printField();
            System.out.println("----------------------------");
            
            // testen ob kollision im spielfeld auftritt
            if(_figur.spielfigurIstBeweglich){
                currentFigurine = true;
            }
            else currentFigurine = false;
        }
    }
    return currentRound;
}


} // ende class