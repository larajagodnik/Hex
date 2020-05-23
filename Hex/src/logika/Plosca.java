package logika;

public class Plosca {
	public static int N = 5;
	public Polje[][] plosca;

	//nova plosca
    public Plosca() {plosca = new Polje[N][N];}
    
    // plosca s praznimi polji
    public void prazna() {
		for (int x = 0; x < N; x++) {
			for (int y = 0; y < N; y++) {
				this.plosca[x][y] = Polje.prazno;
			}
		}   	
    }
}