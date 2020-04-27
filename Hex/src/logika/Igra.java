package logika;

public class Igra {
	public Plosca plosca;
	public Igralec naPotezi;

	// konstuktor za igro
	public Igra(Igra igra) {
		this.plosca = new Plosca();
		for (int x = 0; x > Plosca.N; x++) {
			for (int y = 0; y < Plosca.N; y++) {
				this.plosca.plosca[x][y] = igra.plosca.plosca[x][y];
			}
		}
		
		naPotezi = Igralec.rdeci;		
	}
	
/**
 * 
 * od profesorja malo spremenjeno
 * 

	//seznam moznih potez
	public List<Koordinati> poteze() {
		LinkedList<Koordinati> ps = new LinkedList<Koordinati>();
		for (int i = 0; i < Plosca.N; i++) {
			for (int j = 0; j < Plosca.N; j++) {
				if (plosca.plosca[i][j] == Polje.prazno) {
					ps.add(new Koordinati(i, j));
				}
			}
		}
		return ps;
		
	}
**/	
}
