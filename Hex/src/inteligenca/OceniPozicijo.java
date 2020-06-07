package inteligenca;

import java.util.LinkedList;
import java.util.Queue;

import logika.Igra;
import logika.Igralec;
import logika.Plosca;
import logika.Polje;
import logika.Stanje;
import splosno.Koordinati;


public class OceniPozicijo {
	
	// Metoda oceniPozicijo za igro TicTacToe
	public static int oceniPozicijo(Igra igra, Igralec igralec) {

		
		int ocenap = 0;
		
		if (igra.stanje() == Stanje.zmaga_rdeci) {
			if(igralec == Igralec.rdeci) {
				ocenap = Integer.MAX_VALUE;
			}
			else {
				ocenap = Integer.MIN_VALUE;
			}
		}
		
		else if (igra.stanje() == Stanje.zmaga_modri) {
			if(igralec == Igralec.modri) {
				ocenap = Integer.MAX_VALUE;
			}
			else {
				ocenap = Integer.MIN_VALUE;
			}
		}
		else {
			for (int i = 0; i < Plosca.N; i++) {
				
				
				int razdalja_rdeci = bfs(igra, Igralec.rdeci, new Koordinati(i, 0));
				int razdalja_modri = bfs(igra, Igralec.modri, new Koordinati(0, i));		
				
			//	System.out.println("razdalja rdec " + razdalja_rdeci);
			//	System.out.println("razdalja moder " + razdalja_modri);
			
				ocenap = (razdalja_rdeci - razdalja_modri);
				
		//		System.out.println("rdec-moder " + ocenap);
			}
			
			if (igralec == Igralec.rdeci) {
				return ocenap;
			}
			else if (igralec == Igralec.modri) {
				return -ocenap;
			}			
		}
		
		/**
		switch (igra.stanje()) {
			case zmaga_rdeci: ocenap += (igralec == Igralec.rdeci ? Integer.MAX_VALUE : Integer.MIN_VALUE); break;
			case zmaga_modri: ocenap += (igralec == Igralec.modri ? Integer.MAX_VALUE :Integer.MIN_VALUE); break;
			
			default:

				for (int i = 0; i < Plosca.N; i++) {
					int razdalja_rdeci = bfs(igra, Igralec.rdeci, new Koordinati(i, 0));
					int razdalja_modri = bfs(igra, Igralec.modri, new Koordinati(0, i));			
				
					ocenap = (razdalja_rdeci - razdalja_modri);
				}
				
				if (igralec == Igralec.rdeci) {
					return ocenap;
				}
				else if (igralec == Igralec.modri) {
					return -ocenap;
				}			
		}
		**/
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
			return Integer.MAX_VALUE;
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


