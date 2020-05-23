package splosno;

import java.util.*;

import logika.Igralec;
import logika.Koordinati;
import logika.Plosca;
import logika.Polje;
import logika.Stanje;

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
				poteze.add(p);
				return true;
			}
			else {return false;}
		}
		return false;	
	}
	
	// razveljavi zadnjo potezo, in zamenja nasprotnika da bo spet isti kot pred razveljavitvijo
	// Čemu to obstaja? za Računalnik?
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
	public boolean zmagovalniBFS() {
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
		       	//sosedi kandidata, ki bodo šli v vrsto	
		       	sosed(kandidat.getX(),kandidat.getY());
		       	vrsta.remove(0);
			}
			for (int j=0; j < obiskani.size(); j++) {
	       		if(obiskani.get(j).getY()==Plosca.N-1 && katero == Polje.rdece) {
	       			System.out.println("we here boi");
	       			return true;}
	       		else if(obiskani.get(j).getX()==Plosca.N-1 && katero == Polje.modro) {return true;}
	       	}
		}
		return false;
	}

	//vrne stanje igre glede na metodo zmagovalna vrsta
	public Stanje stanje() {
		// Ali imamo zmagovalca?
		
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
	
	
}
