package splosno;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import logika.Igralec;
import logika.Koordinati;
import logika.Plosca;
import logika.Polje;
import logika.Stanje;

public class Igra {
	public logika.Plosca plosca;
	public Igralec naPotezi;
	public ArrayList<Koordinati> poteze = new ArrayList<Koordinati>();

// konstuktor za igro
	public Igra() {
		plosca = new logika.Plosca();
		plosca.prazna();
		naPotezi = Igralec.rdeci; // zacne rdeci
	}
	
// Kopija igre --> Zakaj?
	public Igra(Igra igra) {
		this.plosca = new Plosca();
		for (int x = 0; x > Plosca.N; x++) {
			for (int y = 0; y < Plosca.N; y++) {
				this.plosca.plosca[x][y] = plosca.plosca[x][y];
			}
		}
		this.naPotezi = igra.naPotezi;
	}
	
	/**
	 * @return igralna plosca
	 */
	public Polje[][] getPlosca () {
		return plosca.plosca;
	}
	
	/**
	 * @return igralec, ki je na potezi
	 */
	public Igralec naPotezi() {
		return naPotezi;
	}
	

	//seznam moznih potez
	public List<Koordinati> moznePoteze() {
		LinkedList<Koordinati> poteze = new LinkedList<Koordinati>();
		for (int i = 0; i < Plosca.N; i++) {
			for (int j = 0; j < Plosca.N; j++) {
				if (plosca.plosca[i][j] == Polje.prazno) {
					poteze.add(new Koordinati(i, j));
				}
			}
		}
		return poteze;
	}

	// ali je poteza veljavna
	public boolean veljavnaPoteza(int x, int y) {
		if(plosca.plosca[x][y] == Polje.prazno) {return true;}
		else {return false;}
	}
	
	// ce je mozno odigrati potezo se ta doda v seznam poteze, na vrsti pa bo nasprotnik
	public boolean odigraj(Koordinati p) {
		int x = p.getX();
		int y = p.getY();
		if (veljavnaPoteza(x, y)) {
			if (plosca.plosca[x][y] == Polje.prazno) {
				plosca.plosca[x][y] = naPotezi.getPolje();
				naPotezi = naPotezi.nasprotnik();
				poteze.add(p);
				return true;
			}
			else {return false;}
		}
		return false;	
	}
	
	// razveljavi zadnjo potezo, in zamenja nasprotnika da bo spet isti kot pred razveljavitvijo
	//Čemu to obstaja?
	public void razveljavi() {
		Koordinati zadnjaPoteza = poteze.get(poteze.size()-1);
		plosca.plosca[zadnjaPoteza.getX()][zadnjaPoteza.getY()] = Polje.prazno;
		poteze.remove(poteze.size()-1);
		naPotezi = naPotezi.nasprotnik();
	}
	
/**	
 * vrne nam barvo zmagovalca ali null ce zmagovalca se ni
 * parameter igralec je igralec, ki je trenutno na potezi
 * z bfs-jem
 * visited nastavis na false, nato vsa polja ki so ob robu in so rdeca nastavis na true
 * in ta polja dodas v vrsto
 * dokler ni prazna vrsta, iz vrste das polje, ce je to polje na drugi strani (N-1) potem koncas
 * sicer umes gledas sosede (6-sosednost), cese niso obiskani ali so rdeci
 * smeri= { {0,1}, {0,-1}, {-1,0}, {-1,1}, {1,-1}, {1,0} }
 * ce se ni obiskan, potem polje das v vrsto in v visited
**/	

	
/**
 * 
 * brez zmagovalne vrste samo ali obstaja zmagovalec
 * in pripadajoče stanje igre
 * 
 *
	public Igralec zmagovalec(Igralec igralec) {
		
		boolean[][] visited = new boolean[Plosca.N][Plosca.N];
		for (int i=0; i<Plosca.N; i++) {
			for (int j=0; j<Plosca.N; j++) {
				visited[i][j] = false;
			}
		}
		
		LinkedList<Koordinati> queue = new LinkedList<Koordinati>();
		
		if (igralec == Igralec.rdeci) {
			for (int i=0; i<Plosca.N; i++) {
				if (plosca.plosca[i][0] == Polje.rdece) {
					queue.add(new Koordinati(i, 0));
					visited[i][0] = true;
				}
			}
		
			while(!queue.isEmpty()) {
				Koordinati polje = queue.pop();
				int x = polje.getX();
				int y = polje.getY();
				
				if (y == Plosca.N -1) {
					return Igralec.rdeci;
				}
				
				int[][] smeri = { {0,1}, {0,-1}, {-1,0}, {-1,1}, {1,-1}, {1,0} };
				for (int j=0; j<smeri.length; j++) {
					int sosedx = x + smeri[j][0];
					int sosedy = y + smeri[j][1];
					if (veljavnaPoteza(sosedx, sosedy)) {          // nisem sigurna ce rabi to preverjat
						if (plosca.plosca[sosedx][sosedy] == Polje.rdece && !visited[sosedx][sosedy]) {
							queue.add(new Koordinati(sosedx, sosedy));
							visited[sosedx][sosedy] = true;
						}		
					}
				}
			}
			return null;
		}
		
		else {
			for (int i=0; i<Plosca.N; i++) {
				if (plosca.plosca[0][i] == Polje.modro) {
					queue.add(new Koordinati(0, i));
					visited[0][i] = true;
				}
			}
		
			while(!queue.isEmpty()) {
				Koordinati polje = queue.pop();
				int x = polje.getX();
				int y = polje.getY();
				
				if (x == Plosca.N -1) {
					return Igralec.modri;
				}
				
				int[][] smeri = { {0,1}, {0,-1}, {-1,0}, {-1,1}, {1,-1}, {1,0} };
				for (int j=0; j<smeri.length; j++) {
					int sosedx = x + smeri[j][0];
					int sosedy = y + smeri[j][1];
					if (veljavnaPoteza(sosedx, sosedy)) {
						if (plosca.plosca[sosedx][sosedy] == Polje.modro && !visited[sosedx][sosedy]){
							queue.add(new Koordinati(sosedx, sosedy));
							visited[sosedx][sosedy] = true;
						}
					}
				}
			}
			return null;
		
		}
		

	}
	
	
	// vrne stanje igre
	public Stanje stanje() {
		// Ali imamo zmagovalca?
		Igralec igralec = naPotezi;
		Igralec zmagovalec = zmagovalec(igralec);
		if (zmagovalec == Igralec.rdeci) {
			return Stanje.zmaga_rdeci;
		}
		else if (zmagovalec == Igralec.modri) {
			return Stanje.zmaga_modri;
		}
		
		// ce je kaksno polje prazno je igra v teku
		for (int i=0; i<Plosca.N; i++) {
			for (int j=0; j<Plosca.N; j++) {
				if (plosca.plosca[i][j] == Polje.prazno) {
					return Stanje.v_teku;
				}
			}
		}
		
		// ce ni nobenega polja vec zmaga tisti ki je postavil zadni zeton
		if (igralec == Igralec.rdeci) {
			return Stanje.zmaga_rdeci;
		}
		else {
			return Stanje.zmaga_modri;
		}		
	}

**/


/**
 * kako najti zmagovalno vrsto
 * vrsto isces za v sako zacetno vozlisce posebej rdec ce je y=0 in moder ce je x=0
 * za vsako vozlisce si shranis starsa iz kje je prisel in potem jo za nazaj konstruiras
 * ce obstaja zmagovalna vrsta potem imamo zmagovalca in to je tisti, ki je bil ravno na vrsti
 */
	

	public List<Koordinati> zmagovalnaVrsta(Igralec igralec) {
		
		boolean[][] visited = new boolean[Plosca.N][Plosca.N];
		for (int i=0; i<Plosca.N; i++) {
			for (int j=0; j<Plosca.N; j++) {
				visited[i][j] = false;
			}
		}
		
		if (igralec == Igralec.rdeci) {
			for (int i=0; i<Plosca.N; i++) {
				LinkedList<Koordinati> queue = new LinkedList<Koordinati>();
				HashMap<Koordinati, Koordinati> stars = new HashMap<Koordinati, Koordinati>();
				
				if (plosca.plosca[i][0] == Polje.rdece) {
					Koordinati zacetek = new Koordinati(i, 0);
					queue.add(zacetek);
					visited[i][0] = true;
					stars.put(zacetek, zacetek);
				
					while(!queue.isEmpty()) {
						Koordinati polje = queue.pop();
						int x = polje.getX();
						int y = polje.getY();
						
						if (y == Plosca.N - 1) {
							List<Koordinati> zmagovalnaVrsta = new ArrayList<>();
							
							while (stars.get(polje) != polje ){
								Koordinati s = stars.get(polje);
								zmagovalnaVrsta.add(polje);
								polje = s;
							}
							return zmagovalnaVrsta;
						}
						
						int[][] smeri = { {0,1}, {0,-1}, {-1,0}, {-1,1}, {1,-1}, {1,0} };
						for (int j=0; j<smeri.length; j++) {
							int sosedx = x + smeri[j][0];
							int sosedy = y + smeri[j][1];
							Koordinati sosed = new Koordinati(sosedx, sosedy);
							if (veljavnaPoteza(sosedx, sosedy)) {          // nisem sigurna ce rabi to preverjat
								if (plosca.plosca[sosedx][sosedy] == Polje.rdece && !visited[sosedx][sosedy]) {
									queue.add(sosed);
									visited[sosedx][sosedy] = true;
									stars.put(sosed, polje);
								}		
							}
						}
					}
				}
			}
		}
		
		else {
			for (int i=0; i<Plosca.N; i++) {
				LinkedList<Koordinati> queue = new LinkedList<Koordinati>();
				HashMap<Koordinati, Koordinati> stars = new HashMap<Koordinati, Koordinati>();
				
				if (plosca.plosca[0][i] == Polje.modro) {
					Koordinati zacetek = new Koordinati(0, i);
					queue.add(zacetek);
					visited[0][i] = true;
					stars.put(zacetek, zacetek);
				
					while(!queue.isEmpty()) {
						Koordinati polje = queue.pop();
						int x = polje.getX();
						int y = polje.getY();
						
						if (x == Plosca.N - 1) {
							List<Koordinati> zmagovalnaVrsta = new ArrayList<>();
							
							while (stars.get(polje) != polje ){
								Koordinati s = stars.get(polje);
								zmagovalnaVrsta.add(polje);
								polje = s;
							}
							return zmagovalnaVrsta;
						}
						
						int[][] smeri = { {0,1}, {0,-1}, {-1,0}, {-1,1}, {1,-1}, {1,0} };
						for (int j=0; j<smeri.length; j++) {
							int sosedx = x + smeri[j][0];
							int sosedy = y + smeri[j][1];
							Koordinati sosed = new Koordinati(sosedx, sosedy);
							if (veljavnaPoteza(sosedx, sosedy)) {          // nisem sigurna ce rabi to preverjat
								if (plosca.plosca[sosedx][sosedy] == Polje.rdece && !visited[sosedx][sosedy]) {
									queue.add(sosed);
									visited[sosedx][sosedy] = true;
									stars.put(sosed, polje);
								}		
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	//vrne stanje igre glede na metodo zmagovalna vrsta
	public Stanje stanje() {
		// Ali imamo zmagovalca?
		Igralec igralec = naPotezi;
		
		if (igralec == Igralec.rdeci) {
			if (zmagovalnaVrsta(igralec) != null) {
				return Stanje.zmaga_rdeci;
			}
		}	
		
		else if (igralec == Igralec.modri) {
			if (zmagovalnaVrsta(igralec) != null) {
				return Stanje.zmaga_modri;
			}
		}
		
		// ce je kaksno polje prazno je igra v teku
		for (int i=0; i<Plosca.N; i++) {
			for (int j=0; j<Plosca.N; j++) {
				if (plosca.plosca[i][j] == Polje.prazno) {
					return Stanje.v_teku;
				}
			}
		}
		
		// ce ni nobenega polja vec zmaga tisti ki je postavil zadnji zeton
		if (igralec == Igralec.rdeci) {
			return Stanje.zmaga_rdeci;
		}
		else {
			return Stanje.zmaga_modri;
		}
	}
	
	
}
