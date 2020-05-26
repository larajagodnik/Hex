package inteligenca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import logika.Igra;
import logika.Igralec;
import logika.Plosca;
import logika.Polje;
import splosno.Koordinati;
import vodja.Vodja;

public class OceniPozicijo {
	
	//public Plosca plosca;
	public static Polje[][] plosca;
	
	// Metoda oceniPozicijo za igro TicTacToe
	
	public static int oceniPozicijo(Igra igra, Igralec jaz) {
		int ocena = 0;		
		
		return ocena;
			
	}
	
	public static LinkedList<Koordinati> najkrajsaModra(Igra igra, int x, int y){
		int[][] dolzina = new int[Plosca.N][Plosca.N];
		boolean[][] visited = new boolean[Plosca.N][Plosca.N];
		HashMap<Koordinati, Koordinati> stars = new HashMap<Koordinati, Koordinati>();
		LinkedList<Koordinati> queue = new LinkedList<Koordinati>();
		
		for (int i=0; i<Plosca.N; i++) {
			for (int j=0; j<Plosca.N; j++) {
				dolzina[i][j] = Integer.MAX_VALUE;
				visited[i][j] = false;
				if (plosca[x][y] != Polje.modro) {
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
					int razdalja = dolzina[vozx][vozy] + 1;
					if (razdalja < dolzina[sosedx][sosedy]) {
						dolzina[sosedx][sosedy] =  dolzina[x][y] + 1;
						stars.put(sosed, voz);
						if (sosedx == 0) {
							
							LinkedList<Koordinati> vrsta = new LinkedList<Koordinati>();
							while (dolzina[stars.get(sosed).getX()][stars.get(sosed).getY()] != 0){
								Koordinati s = stars.get(sosed);
								vrsta.add(sosed);
								sosed = s;
							}
							return vrsta;
						}
					}
				}
			}	
		}
		return null;
	}

/**	
	public static List<Koordinati> veriga(Igra igra, Koordinati u, Igralec igralec){
		LinkedList<Koordinati> veriga = new LinkedList<Koordinati>();
		LinkedList<Koordinati> visited = new LinkedList<Koordinati>();
	
		
		if (igralec == igralec.rdeci) {
			visited.add(u);
			int x = u.getX();
			int y = u.getY();
			int[][] smeri = { {0,1}, {0,-1}, {-1,0}, {-1,1}, {1,-1}, {1,0} };
			for (int j=0; j<smeri.length; j++) {
				int sosedx = x + smeri[j][0];
				int sosedy = y + smeri[j][1];
				Koordinati sosed = new Koordinati(sosedx, sosedy);
				if (sosedx>=0 && sosedy >= 0 && sosedx<Plosca.N && sosedy<Plosca.N) {
					if (plosca[sosedx][sosedy] == Polje.rdece && !visited.contains(sosed)) {
						veriga.add(sosed);
						visited.add(sosed);
					}
				}
			}
		}
		else {
			visited.add(u);
			int x = u.getX();
			int y = u.getY();
			int[][] smeri = { {0,1}, {0,-1}, {-1,0}, {-1,1}, {1,-1}, {1,0} };
			for (int j=0; j<smeri.length; j++) {
				int sosedx = x + smeri[j][0];
				int sosedy = y + smeri[j][1];
				Koordinati sosed = new Koordinati(sosedx, sosedy);
				if (sosedx>=0 && sosedy >= 0 && sosedx<Plosca.N && sosedy<Plosca.N) {
					if (plosca[sosedx][sosedy] == Polje.modro && !visited.contains(sosed)) {
						veriga.add(sosed);
						visited.add(sosed);
					}
				}
			}
		}
		return veriga;
	}
		
		
	
	public static List<Koordinati> sosescina(Igra igra, Koordinati u, Igralec igralec){
		LinkedList<Koordinati> vsiSosedi = new LinkedList<Koordinati>();
		
		LinkedList<Koordinati> veriga = (LinkedList<Koordinati>) veriga(igra, u, igralec);
		for (Koordinati vozlisce : veriga) {
			int x = vozlisce.getX();
			int y = vozlisce.getY();
			int[][] smeri = { {0,1}, {0,-1}, {-1,0}, {-1,1}, {1,-1}, {1,0} };
			for (int j=0; j<smeri.length; j++) {
				int sosedx = x + smeri[j][0];
				int sosedy = y + smeri[j][1];
				Koordinati sosed = new Koordinati(sosedx, sosedy);
				if (sosedx>=0 && sosedy >= 0 && sosedx<Plosca.N && sosedy<Plosca.N) {
					if (plosca[sosedx][sosedy] == Polje.prazno && !vsiSosedi.contains(sosed)) {
						vsiSosedi.add(sosed);
					}
				}
			}		
		}
		System.out.println(vsiSosedi);
		return vsiSosedi;
	}
	
**/
}
