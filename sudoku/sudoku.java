package sudokuJava;

public class sudoku {
	//Vermeide MAGIC NUMBERS! Deshalb:
	private static final int Feld_Startindex = 0;
	private static final int Feld_Breite_Hoehe = 9;
	private static final int MIN_Zahl = 1;
	private static final int MAX_Zahl = 9;
	
	//zu lösendes Feld
	int[][] feld = {
			  { 8, 0, 0, 0, 0, 0, 0, 0, 0 },
			  { 0, 0, 3, 6, 0, 0, 0, 0, 0 },
			  { 0, 7, 0, 0, 9, 0, 2, 0, 0 },
			  { 0, 5, 0, 0, 0, 7, 0, 0, 0 },
			  { 0, 0, 0, 0, 4, 5, 7, 0, 0 },
			  { 0, 0, 0, 1, 0, 0, 0, 3, 0 },
			  { 0, 0, 1, 0, 0, 0, 0, 6, 8 },
			  { 0, 0, 8, 5, 0, 0, 0, 1, 0 },
			  { 0, 9, 0, 0, 0, 0, 4, 0, 0 } 
			};
	
	int[][] feld254 = {
			  { 0, 1, 0, 5, 0, 0, 0, 0, 0 },
			  { 9, 0, 4, 0, 0, 0, 0, 0, 8 },
			  { 0, 0, 0, 4, 0, 0, 6, 7, 0 },
			  { 0, 0, 0, 6, 9, 0, 0, 0, 0 },
			  { 5, 8, 0, 0, 0, 0, 0, 9, 7 },
			  { 0, 0, 0, 0, 4, 7, 0, 0, 0 },
			  { 0, 5, 3, 0, 0, 4, 0, 0, 0 },
			  { 2, 0, 0, 0, 0, 0, 3, 0, 6 },
			  { 0, 0, 0, 0, 0, 1, 0, 8, 0 } 
			};

	public static void main(String[] args) {
		sudoku s = new sudoku();
		s.feldPrint(s.feld254);
		s.loeseSudoku(s.feld254);
		s.feldPrint(s.feld254);
	}
	
	//===================================================
	//Brute Force = Wir testen alle Zahlen für jedes Feld
	
	//Wir benötigen das Feld als Matrix und geben ein BOOLEAN zurück
	private boolean loeseSudoku(int[][] feld) {
		//Wir testen Zeile für Zeile
	    for (int zeile = Feld_Startindex; zeile < Feld_Breite_Hoehe; zeile++) {
	    	//Wir testen Spalte für spalte - in der jeweiligen Zeile
	        for (int spalte = Feld_Startindex; spalte < Feld_Breite_Hoehe; spalte++) {
	        	//Wir testen jede Zahl für jedes Zahlenfeld
	        	//Ist die Stelle feld[][] bereits gelöst?
	            if (feld[zeile][spalte] == 0) {
	            	//Falls nicht gelöst: Teste alle Zahlen
	                for (int k = MIN_Zahl; k <= MAX_Zahl; k++) {
	                    feld[zeile][spalte] = k;
	                    //Der eigentliche test mit pruefeZahl()
	                    //Tricky! Wenn Valid dann loeseSudoku() erneut ausführen mit bereits gelöstem Zahlenfeld. REKURSION!
	                    if (pruefeZahl(feld, zeile, spalte) && loeseSudoku(feld)) {
	                        return true;
	                    }
	                    //falls keine valide Zahl möglich:
	                    feld[zeile][spalte] = 0;
	                }
	                //keine valide Zahl gefunden: ERROR
	                return false;
	            }
	        }
	    }
	    //Feld gelöst
	    return true;
	}
	
	//=============
	//Sudoku Regeln
	//Pruefe Zahl an stelle (zeile, spalte)
	private boolean pruefeZahl(int[][] feld, int zeile, int spalte) {
		//Alle Regeln durch UND verknüpft
	    return (pruefeZeile(feld, zeile, spalte) && pruefeSpalte(feld, zeile, spalte) && pruefeKaestchen(feld, zeile, spalte));
	}

	private boolean pruefeKaestchen(int[][] feld, int zeile, int spalte) {
		//Zu pruefende Zahl
		int zahl = feld[zeile][spalte];
		
		//Kaestchen = Array der länge 9
	    int[] zahlenKaestchen = new int[MAX_Zahl];
	    
	    //Identifiziere Kaestchen in 3x3 matrix
	    int kZeileStart = (zeile / 3) * 3;
	    int kZeileEnde = kZeileStart + 3;
	    
	    int kSpalteStart = (spalte / 3) * 3;
	    int kSpalteEnde = kSpalteStart + 3;
	    
	    //Kopiere Kaestchen-Zahlen
	    int i = 0;
	    for (int z = kZeileStart; z < kZeileEnde; z++) {
	        for (int s = kSpalteStart; s < kSpalteEnde; s++) {
	            zahlenKaestchen[i] = feld[z][s];
	            i++;
	        }
	    }
	    return pruefeDuplikate(zahlenKaestchen, zahl);
	}

	private boolean pruefeSpalte(int[][] feld, int zeile, int spalte) {
		//Zu pruefende Zahl
		int zahl = feld[zeile][spalte];
		
		//Spalte = Array der länge 9
	    int[] zahlenSpalte = new int[MAX_Zahl];
	    
	    //Kopiere Spalte
	    for (int i = 0; i < MAX_Zahl; i++) {
			zahlenSpalte[i] = feld[i][spalte];
		}
	    
	    return pruefeDuplikate(zahlenSpalte, zahl);
	}

	private boolean pruefeZeile(int[][] feld, int zeile, int spalte) {
		//Zu pruefende Zahl
		int zahl = feld[zeile][spalte];
		
		//Zeile = Array der länge 9
	    int[] zahlenZeile = new int[MAX_Zahl];
	    
	    //Kopiere Zeile
	    for (int i = 0; i < MAX_Zahl; i++) {
			zahlenZeile[i] = feld[zeile][i];
		}
	    
	    return pruefeDuplikate(zahlenZeile, zahl);
	}
	
	private boolean pruefeDuplikate(int[] zahlen, int zahl) {
		int counter = 0;
		//Für jedes Element i in Zahlen:
		for (int i : zahlen) {
			if (i == zahl) {
				counter++;
				if (counter == 2) {
				return false;
				}
			}
		}
		return true;
	}
	
	private void feldPrint(int[][] feld) {
	    for (int zeile = Feld_Startindex; zeile < Feld_Breite_Hoehe; zeile++) {
	        for (int spalte = Feld_Startindex; spalte < Feld_Breite_Hoehe; spalte++) {
	            System.out.print(feld[zeile][spalte] + " ");
	        }
	        System.out.println();
	    }
        System.out.println("===================================");
	}
} //ENDE
