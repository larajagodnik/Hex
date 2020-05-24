package logika;

import java.util.*;

import splosno.Koordinati;

public class Igra {
	public logika.Plosca plosca;
	private logika.Polje katero;
	public Igralec naPotezi;
	public ArrayList<Koordinati> poteze = new ArrayList<Koordinati>();
	public ArrayList<Koordinati> vrsta = new ArrayList<Koordinati>();
	public ArrayList<Koordinati> obiskani = new ArrayList<Koordinati>();

// konstuktor za igro
	public Igra() {
		plosca = new logika.Plosca();
		plosca.prazna();
		naPotezi = Igralec.rdeci; // zacne rdeci
	}
	
// Kopija igre
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

	
	// ce je mozno odigrati potezo se ta doda v seznam poteze, na vrsti pa bo nasprotnik
	public boolean odigraj(Koordinati p) {
		int x = p.getX();
		int y = p.getY();
		if (plosca.plosca[x][y] == Polje.prazno) {
			plosca.plosca[x][y] = naPotezi.getPolje();
			poteze.add(p);
			vodja.Vodja.zmaga = vodja.Vodja.igra.zmagovalniBfs();
			naPotezi = naPotezi.nasprotnik();
			return true;
		}
		else {return false;}
	}
	
	// razveljavi zadnjo potezo, in zamenja nasprotnika da bo spet isti kot pred razveljavitvijo
	public void razveljavi() {
		Koordinati zadnjaPoteza = poteze.get(poteze.size()-1);
		plosca.plosca[zadnjaPoteza.getX()][zadnjaPoteza.getY()] = Polje.prazno;
		poteze.remove(poteze.size()-1);
		naPotezi = naPotezi.nasprotnik();
	}
	
	public void sosed(int x, int y) {
	 	//y
		if(x+1 < Plosca.N) {
			if(plosca.plosca[x+1][y]==katero) {
				if(!vodja.Vodja.igra.obiskani.contains(new Koordinati(x+1,y))) {
					vodja.Vodja.igra.vrsta.add(new Koordinati(x+1,y));}
			}
		}
		if(x-1 >= 0) {
			if(plosca.plosca[x-1][y]==katero) {
				if(!vodja.Vodja.igra.obiskani.contains(new Koordinati(x-1,y))) {
					vodja.Vodja.igra.vrsta.add(new Koordinati(x-1,y));}
			}
		}
		
		//y+1
		if(y+1 < Plosca.N) {
			if(plosca.plosca[x][y+1]==katero) {
				if(!vodja.Vodja.igra.obiskani.contains(new Koordinati(x,y+1))) {
					vodja.Vodja.igra.vrsta.add(new Koordinati(x,y+1));}
			}
		}
		if(y+1 < Plosca.N && x-1 >= 0){
			if(plosca.plosca[x-1][y+1]==katero) {
				if(!vodja.Vodja.igra.obiskani.contains(new Koordinati(x-1,y+1))) {
					vodja.Vodja.igra.vrsta.add(new Koordinati(x+1,y+1));}
			}
		}
		
		//y-1
		if(y-1 >= 0) {
			if(plosca.plosca[x][y-1]==katero) {
				if(!vodja.Vodja.igra.obiskani.contains(new Koordinati(x,y-1))) {
					vodja.Vodja.igra.vrsta.add(new Koordinati(x,y-1));}
			}
		}
		if(y-1 >= 0 && x+1 < Plosca.N) {
			if(plosca.plosca[x+1][y-1]==katero) {
				if(!vodja.Vodja.igra.obiskani.contains(new Koordinati(x+1,y-1))) {
					vodja.Vodja.igra.vrsta.add(new Koordinati(x+1,y-1));}
			}
		}	       	
	}
	
	//preverimo ali je poteza zmagovalna
	public boolean zmagovalniBfs() {
		obiskani = new ArrayList<Koordinati>();
		vrsta = new ArrayList<Koordinati>();
		ArrayList<Koordinati> rob = new ArrayList<Koordinati>();
		
		if(naPotezi == Igralec.rdeci) {
			katero = Polje.rdece;
			for(int x=0; x < Plosca.N; x++) {
				if(plosca.plosca[x][0] == katero){
					if(x>0 && !rob.contains(new Koordinati(x-1,0))){
						rob.add(new Koordinati(x,0));}
					else {rob.add(new Koordinati(x,0));}
				}
			}
		}
		else {
			katero = Polje.modro;
			for(int y=0; y < Plosca.N; y++) {
				if(plosca.plosca[0][y] == katero) {
					if(y>0 && !rob.contains(new Koordinati(0,y-1))) {
					rob.add(new Koordinati(0,y));}
					else {rob.add(new Koordinati(0,y));}
				}
			}
		}
		
		if(rob.isEmpty()) {return false;}

		for (int i=0; i < rob.size(); i++) {
			vrsta.add(rob.get(i));
			
			while (vrsta.size() > 0) {
		    	Koordinati kandidat = vrsta.get(0);
		       	obiskani.add(kandidat);
		       	//sosedi kandidata, ki bodo Ĺˇli v vrsto	
		       	sosed(kandidat.getX(),kandidat.getY());
		       	vrsta.remove(0);
			}
			for (int j=0; j < obiskani.size(); j++) {
	       		if(obiskani.get(j).getY()==Plosca.N-1 && katero == Polje.rdece) {
	       			return true;}
	       		else if(obiskani.get(j).getX()==Plosca.N-1 && katero == Polje.modro) {return true;}
	       	}
		}
		return false;
	}

	//vrne stanje igre glede na metodo zmagovalna vrsta
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
			return null;
	}
}
	

