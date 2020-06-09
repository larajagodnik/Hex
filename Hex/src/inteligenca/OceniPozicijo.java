package inteligenca;

import java.util.LinkedList;
import java.util.Queue;
import logika.Igra;
import logika.Igralec;
import logika.Plosca;
import logika.Polje;

import splosno.Koordinati;
public class OceniPozicijo {
	
	// Metoda oceniPozicijo za igro Hex
	public static int oceniPozicijo(Igra igra, Igralec igralec) {
		int ocenap = 0;
		
		// najmanjso razdaljo, ki jo potrebujeta rdeci oz modri, da povezeta stranici dobimo z  bfs
		int razdalja_rdeci = bfs(igra, Igralec.rdeci);
		int razdalja_modri = bfs(igra, Igralec.modri);			

		ocenap = razdalja_rdeci - razdalja_modri;
		if (igralec == Igralec.rdeci) {
			return ocenap;
		}
		else {
			return -ocenap;
		}			
	}
	
	// modificiran bfs, ki poišče najmanjšo razdaljo
	public static int bfs(Igra igra, Igralec igralec) {
		int[][] smeri = { {0,1}, {0,-1}, {-1,0}, {-1,1}, {1,-1}, {1,0} };
		boolean[][] visited = new boolean[Plosca.N][Plosca.N];
		int [][] dolzina = new int[Plosca.N][Plosca.N];
		
		for (int i=0; i<Plosca.N; i++) {
			for (int j=0; j<Plosca.N; j++) {
				visited[i][j] = false;
				dolzina[i][j] = Integer.MAX_VALUE;
			}
		}

		// dolzina za rdecega igralca
		if (igralec == Igralec.rdeci) {
			Queue<Koordinati> queue = new LinkedList<>();
			for (int i=0; i<Plosca.N; i++) {
				dolzina[i][0] = 0;
				visited[i][0] = true;
				queue.add(new Koordinati(i, 0));
			}
			
			// dokler vrsta ni prazna pogleda sosede odstranjenega vozlisca in popravi njihovo dolzino, ce obstaja krajsa pot
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

						// ce je polje rdece, je dolzina minimum dolzine polja in soseda iz katerega smo prisli 
						if (igra.plosca.plosca[sosedx][sosedy] == Polje.rdece) {
							dolzina[sosedx][sosedy] = Math.min(dolzina[sosedx][sosedy], dolzina[vozliscex][vozliscey]);
	                        queue.add(sosed);
						}
						
						// ce je polje prazno, je dolzina minimum dolzine polja in soseda iz katerega smo prisli + 1 
						else if (igra.plosca.plosca[sosedx][sosedy] == Polje.prazno) {
							dolzina[sosedx][sosedy] = Math.min(dolzina[sosedx][sosedy], dolzina[vozliscex][vozliscey] + 1);
	                        queue.add(sosed);
						}

						if (sosedy == Plosca.N - 1) {
							// vrnemo dolzino zadnjega soseda, ki je prisel do druge strani
							return dolzina[sosedx][sosedy];
						}
					}
				}	 
			}
			return Integer.MAX_VALUE;
		}
		
		// dolzina za modrega igralca
		else {
			Queue<Koordinati> queue = new LinkedList<>();
			for (int i=0; i<Plosca.N; i++) {
				dolzina[0][i] = 0;
				visited[0][i] = true;
				queue.add(new Koordinati(0, i));
			}

			// podobno obravnavamo kot za rdecega igralca
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


	

