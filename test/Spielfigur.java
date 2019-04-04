/**
 * Definition der Klasse Spielfigur.
 * Die Klasse Spielfigur enthält die Spielfigur als Array von Pixeln 
 * mit Methoden zur Bearbeitung und Ausgabe.
 * @author Rene Hippe 2018WiSe-2019WiSe
 * @version 1
 */
class Spielfigur
{
	/**
     * Zeilenkoordinate der Spielfigur.
     * Die Koordinate legt die Position der obersten Zeile der 
     * Spielfigur im Spielfeld fest.
     * Wertebereich ab 0
     */
	protected int zeile = 0;

	/**
     * Spaltenkoordinate der Spielfigur.
     * Die Koordinate legt die Position der ersten Spalte von 
     * links der Spielfigur im Spielfeld fest.
     * Wertebereich ab 0
     */
	protected int spalte = 0;

	/**
     * Rotationswinkel der Spielfigur.
     * Der Rotationswinkel legt die Lage der Spielfigur bezogen
     * auf das Spielfeld fest.
     * Wertebereich: {0°, 90°, 180°, 270°} erlaubt
     */
	 protected int rotation = 0;

	/**
     * Pixels der Spielfigur als 2D-Array von char. 
     * Wertebereich: {I, L, O, S, T, Z} erlaubt
     */
	 protected char[][] figurine = { 
			{ 'A', 'B', 'C' }, 
			{ 'D', 'E', 'F' },
			{ 'G', 'H', 'I' } };

	/**
 	 * speichern, ob eine Spielfigur beweglich ist, er also ohne Kollision in das 
 	 * Spielfeld gesetzt werden kann.
 	 */
	protected boolean spielfigurIstBeweglich = true;


	/**
	 * Spielfigur erzeugen mit Koordinaten und Winkel.
	 * Constructor MusterSpielfigur
     * @param feld das Spielfeld, auf dem die Spielfigur abgebildet wird
     * @param Zeile Angabe der Zeile der Spielfigur auf dem Spielfeldes
	 * @param Spalte Angabe der Spalte der Spielfigur auf dem Spielfeldes 
	 * @param Winkel Rotation der Spielfigur im Spielfeld
	 */

Spielfigur(Spielfeld feld, int Zeile, int Spalte,int Winkel){
    this.ordneSpielfeldZu(feld);
    
    this.positioniereAuf(Zeile,Spalte);
    this.dreheAufWinkel(Winkel);
    
    if(kollisionImSpielfeld())
        this.spielfigurIstBeweglich=false;
}



/**
 * ausgabe der variable in die Konsole
 *
 * @return String mit informationen zur Pos.
 */
public String toString(){
	/*for (int x=0;x<this.figurine.length;x++) {
    	for (int y=0;y<this.figurine[x].length;y++) {
        	System.out.print(this.figurine[x][y]+" ");
      	}
        System.out.println();
    }*/  
    return "Die Figur ist um: "+this.rotation+" graht gedreht"+"und befindet sich in Zeile "+this.zeile+" und Spalte "+this.spalte + "\n";
}
/**
	 * Ist die Position gültig ?
	 * Prüft, ob die uebergebenen Koordinaten im positiven Bereich liegen.
	 *               Dabei prüfen wir nun auch, ob die Koordinaten im Feld liegen.
	 *               Und berücksichtigen dabei auch die Drehung
	 * @param _zeile Spalten Koordinate
	 * @param _spalte Zeilen Koordinate
	 * @return true, wenn beide Werte gültig, sonst false
	 */
	public boolean positionIstGueltig(int _zeile, int _spalte) {
        
        // Anzahl Zeilen -1 = max. zeile
		int maxZeileSpielfigur = this.figurine.length - 1; 		

        // Anzahl der Spalten in der 1.Zeile = max. spalte
        int maxSpalteSpielfigur = this.figurine[0].length - 1; 	

		boolean gueltig = false;

		if (rotation == 0 || rotation == 180) // -> alles wie bisher
			gueltig = (
				// oberste Zeile
				this.meinSpielFeld.zeileIstGueltig(_zeile)
				// linke Spalte
				& this.meinSpielFeld.spalteIstGueltig(_spalte)
				// unterste Zeile
				& this.meinSpielFeld.zeileIstGueltig(_zeile + maxZeileSpielfigur)
				// rechte Spalte
				& this.meinSpielFeld.spalteIstGueltig(_spalte + maxSpalteSpielfigur));
		else // 11 Aufgabe 2.3) Gültige Position inklusive Drehung 
             // 90 oder 270 Grad -> höhe und breite sind vertauscht
             // entsprechend muss anders addiert und geprüft werden:
			gueltig = (
				// oberste Zeile
				this.meinSpielFeld.zeileIstGueltig(_zeile)
				// linke Spalte
				& this.meinSpielFeld.spalteIstGueltig(_spalte)
				// unterste Zeile -> höhe und breite sind vertauscht
				& this.meinSpielFeld.zeileIstGueltig(_zeile + maxSpalteSpielfigur)  
				// rechte Spalte -> höhe und breite sind vertauscht
				& this.meinSpielFeld.spalteIstGueltig(_spalte + maxZeileSpielfigur) 
			);
		return gueltig;
	}
/** 
 * checks , if the entered value for Winkel
 * is in range. possible, 0, 90, 180, 270, 360.
 *
 * @param r Rotation value
 * @return Either True if the value is allowed 		or False
 **/
public boolean rotationIstGueltig(int r){
    if(r==0 || r==90 || r==180 || r==270 || r==360){
        return true;
    }
    else
        return false;
}
/**   
 * Position the figurine on the passed coordinates
 * for Spalte and Zeile after checking, if they are in range.
 * 
 *
 * @param _zeile used to position the figurine
 * @param _spalte used to position the figurine
 */

public void positioniereAuf(int _zeile, int _spalte){
	if(this.positionIstGueltig(_zeile, _spalte)){
     	spalte=_spalte;
        zeile=_zeile;
    }
}


/**   
 * rotates the figurine by the passed amount of Winkel
 * after checking the entered range, and Value.
 * 
 *
 * @param winkel the desired degree of rotation
 */
public void dreheAufWinkel(int winkel){
    if(this.rotationIstGueltig(winkel)){
		rotation = winkel;
    }
}

/**   */
protected Spielfeld meinSpielFeld;


/**   
*	passes a reference of Spielfeld to Spielfigur for usage in Spielfigur.
*
* @param s Spielfeld class reference named s
**/

void ordneSpielfeldZu(Spielfeld s){
	this.meinSpielFeld = s;  
}
/* 
 * counting each pixel of figurine and checking, if the 
 * pixel is allowed to be printed into the fied ( !='.')
 * and passing it over to uebertragePixelWertInsSpielfeld
 *
 */

void uebertrageInsSpielfeld(){
    // counting the rows
    for(int z=0;z<this.figurine.length;z++){
        //counting the colums
    	for(int s=0;s<this.figurine[z].length;s++){
            //char of current pixel
            char pixelWert=this.figurine[z][s];
            // passing the char of that pixel over to next function
            if(pixelwertIstUebertragbar(pixelWert)){
				this.uebertragePixelWertInsSpielfeld(z,s,this.figurine[z][s]);
            }
         }
     }

}     
/*
 * rotating the Figurines, according to set rotation
 * value,(90, 180, 270, 0) and passing the newly calculated final
 * x and y coordinates of the pixels
 * over to setzePixelwertAn
 *
 * @param z Zeile
 * @param s Spalte
 * @param _pixelWert Charachter of that pixel
 */
private void uebertragePixelWertInsSpielfeld(int z, int s, char _pixelWert){
    
    int neuZeile=z;
    int neuSpalte=s;

    int maxZeile = this.figurine.length-1;
    int maxSpalte = this.figurine[0].length-1;

    
    switch(this.rotation){
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
   this.meinSpielFeld.setzePixelwertAn(this.zeile+neuZeile,this.spalte+neuSpalte,_pixelWert);
   
}


	/**
 	 * 11 Aufgabe 2.5) Nur die richtigen Pixel übertragen
	 * Ein Pixelwert ist übertragbar, wenn er kein Leerzeichen ist
     * Für das Leerzeichen wird hier zum Test ein '.' verwendet.
	 * @param _pixelwert Zeichen des zu testenden Pixels
	 * @return true, wenn das Pixel kein Leerzeichen ist
	 */
	private boolean pixelwertIstUebertragbar(char _pixelwert) {
		return _pixelwert != '.';
	}
	/**
	 * Testet, ob eine Spielfigur ohne Kollision ins Spielfeld übertragbar ist.
	 * @return true, wenn Kollision vorhanden, sonst false
     */
public boolean kollisionImSpielfeld(){
    // counting the rows
    for(int z=0;z<this.figurine.length;z++){
        //counting the colums
    	for(int s=0;s<this.figurine[z].length;s++){
            //char of current pixel
            char pixelWert=this.figurine[z][s];
            if(this.pixelwertIstUebertragbar(pixelWert)){
				 if( this.pixelWertKollidiertImSpielfeld(z,s)){
                     return true;
            	}
         	}
     	}
    }
    return false;
}

/**
	 * Testet, ob das zu setzende Pixel der Spielfigur im Spielfeld eine Kollision auslöst.
	 *
	 * Für jeden der möglichen 4 Winkel 0, 90°, 180° und 270° wird eine passende
	 * Fallunterscheidung durchgeführt.
     * 
     * Die Struktur ist gleich der der Methode uebertragePixelWertInsSpielfeld()
	 * @param _zeile Koordinate des Pixels in der Spielfigur
     * @param _spalte Koordinate des Pixels in der Spielfigur
	 * @return true, wenn das Ziel-Pixel im Spielfeld bereits belegt ist, sonst false.
	 */
private boolean pixelWertKollidiertImSpielfeld(int _zeile, int _spalte) {

        int zeileSpielfigur = _zeile;
		int spalteSpielfigur = _spalte;
        
        // Anzahl Zeilen/Spalten -1 = maximaler Zeilen/Spalten-Index der Spielfigur
		int maxZeileSpielfigur = this.figurine.length - 1; 	
		int maxSpalteSpielfigur = this.figurine[0].length - 1; 
		
		// Fallunterscheidung nach den 4 erlaubten Winkel-Werten
		// für die Berechnung der gedrehten Koordinaten zeileSpielfigur und spalteSpielfigur
		switch (this.rotation) {
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
		return this.meinSpielFeld.pixelWertKollidiertAn(
                                              	this.zeile + zeileSpielfigur,
                                            	this.spalte + spalteSpielfigur);
}

/**   
 * Löscht die alte figur pixel für pixel aus dem Spielfeld
 */

 void loescheImSpielfeld(){
         // counting the rows
    for(int z=0;z<this.figurine.length;z++){
        //counting the colums
    	for(int s=0;s<this.figurine[z].length;s++){
            //char of current pixel
            char pixelWert=this.figurine[z][s];
            // passing the char of that pixel over to next function
            if(pixelwertIstUebertragbar(pixelWert)){
				this.uebertragePixelWertInsSpielfeld(z,s,'.');
            }
     	}
    }
 }

/**
 * Testet, ob der sich die Spielfigur noch im Spielfeld befindet 
 * oder ob sie mit einem anderen Spielfigur bei der Bewegung nach unten
 * kollidiert.
 * @param eingabe Eingabe von der Tastatur zur Steuerung der Spielfigur
             */
private void testeBeweglichkeit(String input) {

    if(input.equals("s"))		// Bewegung nach unten

        // wenn die neue Position nicht mehr im Feld ist (unterer Rand)
        if (!this.positionIstGueltig(this.zeile, this.spalte))		
            // wenn verschieben nach unten nicht mehr möglich ist, ist der Stein
            // nicht mehr beweglich
            spielfigurIstBeweglich = false;

    	else
        // oder es bei einer gültigen Position eine Kollision im
        // Spielfeld gibt.
        if (this.kollisionImSpielfeld())		
            // wenn verschieben nach unten nicht mehr möglich ist, ist der Stein
            // nicht mehr beweglich
            spielfigurIstBeweglich = false;

    // sonst ist weiteres Verschieben erlaubt.

}
/**
 * Bewegt einen Spielfigur je nach Benutzereingabe
 * @param input kleiner Buchstabe als Benutzereingabe
 * "a" - Spielfigur nach links verschieben
 * "d" - Spielfigur nach rechts verschieben
 * "s" - Spielfigur nach unten verschieben
 * "w" - Spielfigur im Uhrzeigersinn um 90 Grad drehen
 */
void bewegeSpielfigurEinfach(String input){

    switch(input){

        case "a":
            this.spalte--; 
            break;

        case "d":
            this.spalte++;
            break;

        case "s":
            this.zeile++;
            break;

        case "w":            
            if(rotation<270)
                rotation+=90;
            else
                rotation=0;
            break;    
    }       

}
/**
 * Bewegt eine Spielfigur abhängig von der Nutzereingabe
 * darf nur aufgerufen werden, wenn spielfigurIstBeweglich == true ist !!
 * @param input steuert die Benutzereingabe mit a, d, s, w
 */
void bewegeSpielfigurKomplett(String input) {
    // Aktuelle Position bzw. Winkel merken
    int altZeile = this.zeile;
    int altSpalte = this.spalte;
    int altWinkel = this.rotation;

    // Spielfigur an der aktuellen Position aus dem Spielfeld löschen
    this.loescheImSpielfeld();

    // Spielfigur  auf neue Werte für Position, bzw. neuen Winkel setzen
    this.bewegeSpielfigurEinfach(input);

    // Die Beweglichkeit der Spielfigur an der neuen Position feststellen
    this.testeBeweglichkeit(input);

        // Falls die neue Position nicht gültig ist, wieder die vorherigen 
        // Werte für Position bzw. Winkel setzen
		if (!(this.positionIstGueltig(this.zeile, this.spalte)&& this.rotationIstGueltig(rotation))) {
			this.zeile = altZeile;
            this.spalte = altSpalte;
			this.rotation = altWinkel;
		} else {
			// Falls doch, aber es dabei eine Kollision gegeben hat, wieder 
            // die vorherigen Werte für Position bzw. Winkel setzen
			if (this.kollisionImSpielfeld()) {
				this.zeile = altZeile;
            	this.spalte = altSpalte;
				this.rotation = altWinkel;
			}
		}
    // Spielfigur mit passenden Werten ins Spielfeld übertragen
    this.uebertrageInsSpielfeld();
}

	/**  
    * Ist die Spielfigur beweglich?
    * @return true, wenn die Spielfigur noch beweglich ist, sonst false
    */

	boolean istBeweglich(){
        
        return this.spielfigurIstBeweglich;
    }
} // ende class