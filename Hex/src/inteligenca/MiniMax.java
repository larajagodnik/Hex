package inteligenca;
import java.util.List;
import logika.Igra;
import logika.Igralec;
import logika.Polje;
import splosno.Koordinati;

public class MiniMax extends Inteligenca {
	
	private static final int ZMAGA = Integer.MAX_VALUE; // vrednost zmage
	private static final int ZGUBA = -ZMAGA;  // vrednost izgube
	private int globina;
	
	public MiniMax () {
		super("MiniMax"); 
		this.globina = 3;
	}
	
	public MiniMax (int globina) {
		super("MiniMax " + globina); 
		this.globina = globina;
	}

	// izbere najboljso potezo
	public Koordinati izberiPotezo (Igra igra) {
		return alphabetaPoteze(igra, this.globina, ZGUBA, ZMAGA, igra.naPotezi()).poteza;
	}

	public static OcenjenaPoteza alphabetaPoteze(Igra igra, int globina, int alpha, int beta, Igralec jaz) {
		int ocena;
		// Če sem računalnik, maksimiramo oceno z začetno oceno ZGUBA
		// Če sem pa človek, minimiziramo oceno z začetno oceno ZMAGA
		if (igra.naPotezi() == jaz) {
			ocena = ZGUBA;
		}
		else {
			ocena = ZMAGA;
		}

		List<Koordinati> moznePoteze = igra.moznePoteze();
		Koordinati kandidat = moznePoteze.get(0);
		
		for (Koordinati p: moznePoteze) {
			Igra kopijaIgre = new Igra(igra);
			if (jaz == Igralec.rdeci) {
				kopijaIgre.plosca.plosca[p.getX()][p.getY()]=Polje.rdece;
			}
			else {
				kopijaIgre.plosca.plosca[p.getX()][p.getY()]=Polje.modro;
			}
			int ocenap;
			
			switch (kopijaIgre.stanje()) {
			case zmaga_rdeci: ocenap = (jaz == Igralec.rdeci ? ZMAGA : ZGUBA); break;
			case zmaga_modri: ocenap = (jaz == Igralec.modri ? ZMAGA : ZGUBA); break;
			default:
				// Nekdo je na potezi
				if (globina == 1) ocenap = OceniPozicijo.oceniPozicijo(kopijaIgre, jaz);
				else ocenap = alphabetaPoteze (kopijaIgre, globina-1, alpha, beta, jaz).ocena;
			}
			

			if (igra.naPotezi() == jaz) { // Maksimiramo oceno
				if (ocenap > ocena) { // mora biti > namesto >=
					ocena = ocenap;
					kandidat = p;
					alpha = Math.max(alpha, ocena);
				}
			}
			else { // igra.naPotezi() != jaz, torej minimiziramo oceno
				if (ocenap < ocena) { // mora biti < namesto <=
					ocena = ocenap;
					kandidat = p;
					beta = Math.min(beta, ocena);					
				}	
			}
			if (alpha >= beta) // Izstopimo iz "for loop", saj ostale poteze ne pomagajo
				return new OcenjenaPoteza (kandidat, ocena);
		}
		return new OcenjenaPoteza (kandidat, ocena);
		
	}
	
}
