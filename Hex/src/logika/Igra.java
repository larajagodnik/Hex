package logika;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import splosno.Koordinati;

public class Igra {
	public Plosca plosca;
	public Igralec naPotezi;
	public ArrayList<Koordinati> poteze = new ArrayList<Koordinati>();

	// konstuktor za igro
	public Igra() {
		plosca = new Plosca();
		for (int x = 0; x > Plosca.N; x++) {
			for (int y = 0; y < Plosca.N; y++) {
				plosca.plosca[x][y] = this.plosca.plosca[x][y];
			}
		}
		
		naPotezi = Igralec.rdeci; // zacne rdeci
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
	
	
	public boolean odigraj(Koordinati p) {
		if (plosca.plosca[p.getX()][p.getY()] == Polje.prazno) {
			plosca.plosca[p.getX()][p.getY()] = naPotezi.getPolje();
			naPotezi = naPotezi.nasprotnik();
			poteze.add(p);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void razveljavi() {
		Koordinati zadnjaPoteza = poteze.get(poteze.size()-1);
		plosca.plosca[zadnjaPoteza.getX()][zadnjaPoteza.getY()] = Polje.prazno;
		poteze.remove(poteze.size()-1);
		naPotezi = naPotezi.nasprotnik();
	}
	
/**	
 * vrne nam barvo zmagovalca ali null ce zmagovalca se ni
 * z bfs-jem
 * visited nastavis na false, nato vsa polja ki so ob robu in so rdeca nastavis na true
 * in ta polja dodas v vrsto
 * dokler ni prazna vrsta, iz vrste das polje, ce je to polje na drugi strani (N-1) potem koncas
 * sicer umes gledas sosede (6-sosednost), cese niso obiskani ali so rdeci
 * smeri= { {0,1}, {0,-1}, {-1,0}, {-1,1}, {1,-1}, {1,0} }
 * ce se ni obiskan, potem polje das v vrsto in v visited
**/	
	
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
					if (plosca.plosca[x + smeri[j][0]][y + smeri[j][1]] == Polje.rdece && !visited[x + smeri[j][0]][y + smeri[j][1]]) {
						queue.add(new Koordinati(x + smeri[j][0], y + smeri[j][1]));
						visited[x + smeri[j][0]][y + smeri[j][1]] = true;
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
					if (plosca.plosca[x + smeri[j][0]][y + smeri[j][1]] == Polje.modro && !visited[x + smeri[j][0]][y + smeri[j][1]]){
						queue.add(new Koordinati(x + smeri[j][0], y + smeri[j][1]));
						visited[x + smeri[j][0]][y + smeri[j][1]] = true;
					}
				
				}
			}
			return null;
		
		}
		

	}
	
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
		
		// ce ni nobenega polja vec zmaga tisti ki je postaviu zadni zeton
		if (igralec == Igralec.rdeci) {
			return Stanje.zmaga_rdeci;
		}
		else {
			return Stanje.zmaga_rdeci;
		}		
	}
	
}
