package logika;

import java.util.*;

import splosno.Koordinati;

public class Igra {
	public Plosca plosca;
	public Igralec naPotezi;
	public ArrayList<Koordinati> potezeSwap = new ArrayList<Koordinati>();
	public LinkedList<Koordinati> odigranePoteze = new LinkedList<Koordinati>();
	public ArrayList<Koordinati> vrsta = new ArrayList<Koordinati>();
	public ArrayList<Koordinati> obiskani = new ArrayList<Koordinati>();	
	public List<Koordinati> zmagovalnaVrsta  = new ArrayList<Koordinati>();


	/**
	 * konstruktor za igro
	 */
	public Igra() {
		plosca = new Plosca();
		plosca.prazna();
		naPotezi = Igralec.rdeci; // zacne rdeci
	}
	
	/**
	 * kopija igre
	 * @param igra
	 */
	public Igra(Igra igra) {
		this.plosca = new Plosca();
		for (int x = 0; x < Plosca.N; x++) {
			for (int y = 0; y < Plosca.N; y++) {
				this.plosca.plosca[x][y] = igra.plosca.plosca[x][y];
			}
		}
		this.naPotezi = igra.naPotezi;
		this.odigranePoteze = igra.odigranePoteze;
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
	
	/**
	 * @return seznam moznih potez
	 */
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

	// ce je mozno odigrati potezo se ta doda v seznam poteze, na vrsti pa bo nasprotnik
	/**
	 * 
	 * @param p (koordinati)
	 * @return true ali false, ce je potezo mozno odigati ali ne
	 * zraven se shranjujejo se odigrane poteze
	 */
	public boolean odigraj(Koordinati p) {
		int x = p.getX();
		int y = p.getY();
		if (plosca.plosca[x][y] == Polje.prazno) {
			plosca.plosca[x][y] = naPotezi.getPolje();
			zmagovalnaVrsta = zmagovalnaVrsta(naPotezi);
			naPotezi = naPotezi.nasprotnik();
			odigranePoteze.add(p);
			potezeSwap.add(p);
			return true;
		}
		else {return false;}
	}
	
	/**
	 * razveljavi zadnjo potezo
	 */
	public void razveljavi() {
		Koordinati zadnjaPoteza = odigranePoteze.get(odigranePoteze.size()-1);
		plosca.plosca[zadnjaPoteza.getX()][zadnjaPoteza.getY()] = Polje.prazno;
		odigranePoteze.remove(odigranePoteze.size()-1);
		naPotezi = naPotezi.nasprotnik();

	}
	
	/**
	 * swap rule
	 * drugi igralec lahko izbrise prvi zeton igralca ki je odprl igro in postavi svojega
	 */
	public void swap() {
		Koordinati zadnjaPoteza = odigranePoteze.get(odigranePoteze.size()-1);
		plosca.plosca[zadnjaPoteza.getX()][zadnjaPoteza.getY()] = Polje.prazno;
	}
	
	
	/**
	 * 
	 * @return stanje igre; vrne zmagovalce ali igra v teku
	 */
	public Stanje stanje() {
		// Ali imamo zmagovalca?
		
		//ce na vrsti rdeci in zmaga, v resnici zmaga modri, ker odigraj(p) zamenjal kdo je naPotezi
		
		
		if (naPotezi == Igralec.rdeci) {
			if (vodja.Vodja.zmaga == true) {
				return Stanje.zmaga_rdeci;
			}
		}	
		
		else if (naPotezi == Igralec.modri) {
			if (vodja.Vodja.zmaga == true) {
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
		if (naPotezi == Igralec.rdeci) {
			return Stanje.zmaga_rdeci;
		}
		else {
			return Stanje.zmaga_modri;
		}
	}
	


/**
 * kako najti zmagovalno vrsto
 * vrsto isces za vsako zacetno vozlisce posebej rdec ce je y=0 in moder ce je x=0
 * za vsako vozlisce si shranis starsa iz kje je prisel in potem jo za nazaj konstruiras
 * ce obstaja zmagovalna vrsta potem imamo zmagovalca in to je tisti, ki je bil ravno na vrsti
 */

	
	/**
	 * 
	 * @param igralec
	 * @return zmagovalna vrsta
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
							ArrayList<Koordinati> zmagovalnaVrsta = new ArrayList<>();

							while (stars.get(polje) != polje){
								Koordinati s = stars.get(polje);
								zmagovalnaVrsta.add(polje);
								polje = s;
							}
							vodja.Vodja.zmaga = true;
							//append začetnega torej (y=0, x=...)
							zmagovalnaVrsta.add(zacetek);
							//poskrbimo, da le pot od y=0 do y=N-1 
							boolean bool = true;
							int k = 0;
							while(bool) {
								if(zmagovalnaVrsta.get(k).getY()==0) {bool = false;}
								k++;
							}
							return zmagovalnaVrsta.subList(0, k);
						}

						int[][] smeri = { {0,1}, {0,-1}, {-1,0}, {-1,1}, {1,-1}, {1,0} };
						for (int j=0; j<smeri.length; j++) {
							int sosedx = x + smeri[j][0];
							int sosedy = y + smeri[j][1];
							Koordinati sosed = new Koordinati(sosedx, sosedy);
							if (sosedx>=0 && sosedy >= 0 && sosedx<Plosca.N && sosedy<Plosca.N) {
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
							ArrayList<Koordinati> zmagovalnaVrsta = new ArrayList<>();
							while (stars.get(polje) != polje ){
								Koordinati s = stars.get(polje);
								zmagovalnaVrsta.add(polje);
								polje = s;
							}
							vodja.Vodja.zmaga = true;
							zmagovalnaVrsta.add(zacetek);
							boolean bool = true;
							int k = 0;
							while(bool) {
								if(zmagovalnaVrsta.get(k).getX()==0) {bool = false;}
								k++;
							}
							return zmagovalnaVrsta.subList(0, k);
						}
						int[][] smeri = { {0,1}, {0,-1}, {-1,0}, {-1,1}, {1,-1}, {1,0} };
						for (int j=0; j<smeri.length; j++) {
							int sosedx = x + smeri[j][0];
							int sosedy = y + smeri[j][1];
							Koordinati sosed = new Koordinati(sosedx, sosedy);
							if (sosedx>=0 && sosedy >= 0 && sosedx<Plosca.N && sosedy<Plosca.N) {
								if (plosca.plosca[sosedx][sosedy] == Polje.modro && !visited[sosedx][sosedy]) {
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
}
	

