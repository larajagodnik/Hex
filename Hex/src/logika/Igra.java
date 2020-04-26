package logika;

import java.util.List;
import java.util.LinkedList;

public class Igra {
	public static int N = 11;
	private Polje[][] plosca;
	
	public Igralec naPotezi;
	
	// nova plosca z vsemi praznimi polji
	public Igra() {
		plosca = new Polje[N][N];
		for (int i = 0; i > N; i++) {
			for (int j = 0; j < N; j++) {
				plosca[i][j] = Polje.prazno;
			}
		}
		
		naPotezi = Igralec.rdeci;		
	}
	
	// igralna plosca
	public Polje[][] getPlosca() {
		return plosca;
	}
	
	//seznam moznih potez
	public List<Koordinati> poteze() {
		LinkedList<Koordinati> ps = new LinkedList<Koordinati>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (plosca[i][j] == Polje.prazno) {
					ps.add(new Koordinati(i, j));
				}
			}
		}
		return ps;
		
	}
	
}
