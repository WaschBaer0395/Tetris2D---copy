package sample;

import java.io.*;// diese Zeile muss ganz am Anfang des files stehen

/** ******************************************
* Die Klasse stellt die Tastatur für die Eingabe zur Verfügung
*
* Es darf nur ein Tastatur-Objekt pro Programm existieren !
* Andernfalls greifen mehrere Objekte auf die "echte" Tastatur zu, 
* was zu unerwarteten Ergebnissen führt, d.h. zu Fehlern. 
*
* An das Tastatur-Objekt kommt man daher _immer_ über die statische Methode
* public static Tastatur tastatur() 
*
* Da die Klasse Tastatur für alle hier definierten Klassen erreichbar ist,
* kann man auch immer diese Methode aufrufen.
*
* Diese Vorgehensweise entspricht dem Software-Entwurfsmuster "Singleton"
* das wir in OOP2 kennenlernen werden. 
*
* @author Dahm 2016WS
* @since 13.0.5
* @version 13.0.5
************************************************/

class Tastatur
{
	// P13 Aufgabe 2.2) Steuerung über die Tastatur
    // ###############################################

	/** ******************************************
 	 * Instanz-Variable
	 ******************************************** */

	private BufferedReader tast = null;
	static private Tastatur t = null;

	/** ******************************************
	* Beim Erzeugen einer Tastatur wird sie initialisiert.
	* Wenn das daneben geht, wird eine Meldung ausgegeben
	*
	* Es darf nur ein Tastatur-Objekt pro Programm existieren !
	* Daher bekommt man dieses eine Objekt nur über eine Methode
    * Der Constructor darf nicht verwendet werden, daher ist er private
    *
    * So wird in Java üblicherweise das Entwurfsmuster Singleton implementiert
    *
	************************************************/
	private Tastatur() {
		try {
			tast = new BufferedReader(new InputStreamReader(System.in));
		} catch (Exception e) {
			System.out.println("Fehler bei Init der Tastatur");
		}
	}

	/** ******************************************
 	 * Methode, über die man die einzige Instanz der Klasse Tastatur bekommt.
     * @return gibt eine Tastatur zur Eingabe zurück.
	************************************************/
	static Tastatur tastatur() {
		if (t == null)
			t = new Tastatur();
		return t;
	}

	/** ******************************************
	* Diese Methode liest eine Zeile von der Konsole ein
	* Eine Zeile wird immer durch die Eingabetaste "Return" abgeschlossen 
	*
	* @return	eingelesene Zeile als String
	************************************************/
	String naechsteZeile() {
		String line = "";
		try {
			line = tast.readLine();
		} catch (Exception e) {
			return "q ** Fehler bei der Eingabe";
		}
		return line;
	}


	/** ******************************************
	* Ein main() zum Testen der Tastatur
    * @param args - nicht benutzt
	************************************************/
	public static void main(String[] args) {
		String s = ""; // der eingelesene String
		Tastatur tastatur = Tastatur.tastatur(); // Ermittle die Tastatur
		long l;
		System.out.println("Geben Sie etwas ein und drücken Sie Return - Ende mit q");
		while (!s.equals("q")) {
			// jetzt lesen wir wirklich etwas von der Tastatur ein.
            s = tastatur.naechsteZeile(); // lese eine Zeile ein
            
            // hier probieren wir auch gleich mal Zufallszahlen aus:
			l = Math.round(Math.random() * 4);

            System.out.println(l + " --> " + s); // und gibs aus
		}
		System.out.println("Ende");
	}


} // ende class