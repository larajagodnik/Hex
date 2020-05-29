package inteligenca;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import logika.Igra;
import logika.Igralec;
import logika.Plosca;
import logika.Polje;
import splosno.Koordinati;


public class OceniPozicijo {
	
	// Metoda oceniPozicijo za igro TicTacToe
	public static int oceniPozicijo(Igra igra, Igralec igralec) {
		int ocenap = 0;
		switch (igra.stanje()) {
			case zmaga_rdeci: ocenap += (igralec == Igralec.rdeci ? Integer.MAX_VALUE : Integer.MIN_VALUE); break;
			case zmaga_modri: ocenap += (igralec == Igralec.modri ? Integer.MAX_VALUE :Integer.MIN_VALUE); break;
			
			default:

				for (int vrstica = 0; vrstica < Plosca.N; vrstica++) {
					int razdalja_rdeci = bfs(igra, Igralec.rdeci, new Koordinati(vrstica, 0));
					int razdalja_modri = bfs(igra, Igralec.modri, new Koordinati(0, vrstica));			
				
					ocenap = (razdalja_rdeci - razdalja_modri);
				}
				
				if (igralec == Igralec.rdeci) {
					return ocenap;
				}
				else if (igralec == Igralec.modri) {
					return -ocenap;
				}			
		}
		return Integer.MAX_VALUE;

		
	}
	
	public static int bfs(Igra igra, Igralec igralec, Koordinati k) {
		int[][] smeri = { {0,1}, {0,-1}, {-1,0}, {-1,1}, {1,-1}, {1,0} };
		boolean[][] visited = new boolean[Plosca.N][Plosca.N];
		int [][] dolzina = new int[Plosca.N][Plosca.N];
		
		for (int i=0; i<Plosca.N; i++) {
			for (int j=0; j<Plosca.N; j++) {
				visited[i][j] = false;
				dolzina[i][j] = Integer.MAX_VALUE;
			}
		}
		
		if (igralec == Igralec.rdeci) {
			Queue<Koordinati> queue = new LinkedList<>();
			dolzina[k.getX()][k.getY()] = 0;
			visited[k.getX()][k.getY()] = true;
			queue.add(k);
			 
			while (queue.size() > 0) {
				Koordinati vozlisce = queue.remove();
				int vozliscex = vozlisce.getX();
				int vozliscey = vozlisce.getY();
				for (int j=0; j<smeri.length; j++) {
					int sosedx = vozliscex + smeri[j][0];
					int sosedy = vozliscey+ smeri[j][1];
					Koordinati sosed = new Koordinati(sosedx, sosedy);
					if (sosedx>=0 && sosedy >= 0 && sosedx<Plosca.N && sosedy<Plosca.N && !visited[sosedx][sosedy]) {
						visited[sosedx][sosedy] = true;
						if (igra.plosca.plosca[sosedx][sosedy] == Polje.rdece) {
							dolzina[sosedx][sosedy] = Math.min(dolzina[sosedx][sosedy], dolzina[vozliscex][vozliscey]);
	                        queue.add(sosed);
						}
						else if (igra.plosca.plosca[sosedx][sosedy] == Polje.prazno) {
							dolzina[sosedx][sosedy] = Math.min(dolzina[sosedx][sosedy], dolzina[vozliscex][vozliscey] + 1);
	                        queue.add(sosed);
						}
						else {
							dolzina[sosedx][sosedy] = Integer.MAX_VALUE;
						}
						
						if (sosedy == Plosca.N - 1) {
							return dolzina[sosedx][sosedy];
						}
					}
				}	 
			}		
		}
		else {
			Queue<Koordinati> queue = new LinkedList<>();
			dolzina[k.getX()][k.getY()] = 0;
			visited[k.getX()][k.getY()] = true;
			queue.add(k);
			 
			while (queue.size() > 0) {
				Koordinati vozlisce = queue.remove();
				int vozliscex = vozlisce.getX();
				int vozliscey = vozlisce.getY();
				for (int j=0; j<smeri.length; j++) {
					int sosedx = vozliscex + smeri[j][0];
					int sosedy = vozliscey+ smeri[j][1];
					Koordinati sosed = new Koordinati(sosedx, sosedy);
					if (sosedx>=0 && sosedy >= 0 && sosedx<Plosca.N && sosedy<Plosca.N && !visited[sosedx][sosedy]) {
						visited[sosedx][sosedy] = true;
						if (igra.plosca.plosca[sosedx][sosedy] == Polje.modro) {
							dolzina[sosedx][sosedy] = Math.min(dolzina[sosedx][sosedy], dolzina[vozliscex][vozliscey]);
	                        queue.add(sosed);
						}
						else if (igra.plosca.plosca[sosedx][sosedy] == Polje.prazno) {
							dolzina[sosedx][sosedy] = Math.min(dolzina[sosedx][sosedy], dolzina[vozliscex][vozliscey] + 1);
	                        queue.add(sosed);
						}
						else {
							dolzina[sosedx][sosedy] = Integer.MAX_VALUE;
						}
						
						if (sosedx == Plosca.N - 1) {
							return dolzina[sosedx][sosedy];
						}
					}
				}	 
			}
		}		
		return Integer.MAX_VALUE;
	}
	
}


