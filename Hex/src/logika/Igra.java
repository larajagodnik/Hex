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
 * da je crta neke barve povezana moramo preverjati 6 smeri
 * z bfs-jem
 * 
**/	
/**	
	public boolean obstajaZamagovalec(Koordinati p) {
		
		boolean[][] visited = new boolean[Plosca.N][Plosca.N];
		for (int i=0; i<Plosca.N; i++) {
			for (int j=0; j<Plosca.N; j++) {
				visited[i][j] = false;
			}
		}
		
		Polje barva = plosca.plosca[p.getX()][p.getY()];
		
		LinkedList<Koordinati> queue = new LinkedList<Koordinati>();
		queue.add(p);
		visited[p.getX()][p.getY()] = true;
		while(!queue.isEmpty()) {
			Koordinati current = queue.pop();
			visited[current.getX()][current.getY()] = true;
			
		}
		
	}
		
**/
	
}
