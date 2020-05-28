package inteligenca;

import java.util.LinkedList;

import logika.Igra;
import logika.Igralec;
import logika.Plosca;
import logika.Polje;
import splosno.Koordinati;


public class OceniPozicijo {
	
	// Metoda oceniPozicijo za igro TicTacToe
	public static int oceniPozicijo(Igra igra, Igralec jaz) {
		int ocena = Integer.MAX_VALUE;
		return ocena;

	}

	
	
	public static int najkrajsaPotModra(Igra igra) {
		int min = Integer.MAX_VALUE;
		
		for (int n=0; n<Plosca.N; n++) {
			if (igra.plosca.plosca[Plosca.N-1][n] != Polje.rdece) {
				int trenutni = najkrajsaModra(igra, Plosca.N-1, n);
				if (trenutni < min) {
					min = trenutni;
				}
			}
		}
		return min;
	}
	
	public static int najkrajsaPotRdeca(Igra igra) {
		int min = Integer.MAX_VALUE;
		
		for (int n=0; n<Plosca.N; n++) {
			if (igra.plosca.plosca[n][0] != Polje.rdece) {
				int trenutni = najkrajsaRdeca(igra, n, 0);
				if (trenutni < min) {
					min = trenutni;
				}
			}
		}
		return min;
	}

	
	public static int najkrajsaModra(Igra igra, int x, int y){
		int[][] dolzina = new int[Plosca.N][Plosca.N];
		boolean[][] visited = new boolean[Plosca.N][Plosca.N];
		LinkedList<Koordinati> queue = new LinkedList<Koordinati>();
		
		for (int i=0; i<Plosca.N; i++) {
			for (int j=0; j<Plosca.N; j++) {
				dolzina[i][j] = Integer.MAX_VALUE;
				visited[i][j] = false;
				if (igra.plosca.plosca[x][y] != Polje.rdece) {
					queue.add(new Koordinati(x, y));
				}				
			}
		}
		dolzina[x][y] = 0;
		
		while(queue != null) {
			int min = Integer.MAX_VALUE;
			Koordinati voz = null;
			for (Koordinati u : queue) {
				if (dolzina[u.getX()][u.getY()] < min) {
					min = dolzina[u.getX()][u.getY()];
					voz = u;
				}
			}
			
			//Koordinati vozlisce = voz;
			queue.remove(voz);
			dolzina[voz.getX()][voz.getY()] = Integer.MAX_VALUE;
			
			int vozx = voz.getX();
			int vozy = voz.getY();
			int[][] smeri = { {0,1}, {0,-1}, {-1,0}, {-1,1}, {1,-1}, {1,0} };
			for (int j=0; j<smeri.length; j++) {
				int sosedx = x + smeri[j][0];
				int sosedy = y + smeri[j][1];
				
				Koordinati sosed = new Koordinati(sosedx, sosedy);
				if (queue.contains(sosed)){
					if (igra.plosca.plosca[sosedx][sosedy] == Polje.modro) {
						int razdalja = dolzina[vozx][vozy];
						if (razdalja < dolzina[sosedx][sosedy]) {
							dolzina[sosedx][sosedy] =  dolzina[vozx][vozy];
						}
					}
					else {
						int razdalja = dolzina[vozx][vozy] + 1;
						if (razdalja < dolzina[sosedx][sosedy]) {
							dolzina[sosedx][sosedy] =  dolzina[x][y] + 1;
							min++;
						}
					}
				}
				if (sosedx == 0) {
					System.out.println(min);
					return min;
				}
			}	
		}
		return Integer.MAX_VALUE;
	}
	
	public static int najkrajsaRdeca(Igra igra, int x, int y){
		int[][] dolzina = new int[Plosca.N][Plosca.N];
		boolean[][] visited = new boolean[Plosca.N][Plosca.N];
		LinkedList<Koordinati> queue = new LinkedList<Koordinati>();
		
		for (int i=0; i<Plosca.N; i++) {
			for (int j=0; j<Plosca.N; j++) {
				dolzina[i][j] = Integer.MAX_VALUE;
				visited[i][j] = false;
				if (igra.plosca.plosca[x][y] != Polje.rdece) {
					queue.add(new Koordinati(x, y));
				}				
			}
		}
		dolzina[x][y] = 0;
		
		while(queue != null) {
			int min = Integer.MAX_VALUE;
			Koordinati voz = null;
			for (Koordinati u : queue) {
				if (dolzina[u.getX()][u.getY()] < min) {
					min = dolzina[u.getX()][u.getY()];
					voz = u;
				}
			}
			
			//Koordinati vozlisce = voz;
			queue.remove(voz);
			dolzina[voz.getX()][voz.getY()] = Integer.MAX_VALUE;
			
			int vozx = voz.getX();
			int vozy = voz.getY();
			int[][] smeri = { {0,1}, {0,-1}, {-1,0}, {-1,1}, {1,-1}, {1,0} };
			for (int j=0; j<smeri.length; j++) {
				int sosedx = x + smeri[j][0];
				int sosedy = y + smeri[j][1];
				
				Koordinati sosed = new Koordinati(sosedx, sosedy);
				if (queue.contains(sosed)){
					if (igra.plosca.plosca[sosedx][sosedy] == Polje.modro) {
						int razdalja = dolzina[vozx][vozy];
						if (razdalja < dolzina[sosedx][sosedy]) {
							dolzina[sosedx][sosedy] =  dolzina[vozx][vozy];
						}
					}
					else {
						int razdalja = dolzina[vozx][vozy] + 1;
						if (razdalja < dolzina[sosedx][sosedy]) {
							dolzina[sosedx][sosedy] =  dolzina[x][y] + 1;
							min++;
						}
					}
				}
				if (sosedy == Plosca.N) {
					System.out.println(min);
					return min;
				}
			}	
		}
		return Integer.MAX_VALUE;
	}
	

}
