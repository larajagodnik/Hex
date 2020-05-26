package inteligenca;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import logika.Igra;
import logika.Igralec;
import splosno.KdoIgra;
import splosno.Koordinati;

public class AlphaBeta extends KdoIgra {
	
	private static final int ZMAGA = Integer.MAX_VALUE; // vrednost zmage
	private static final int ZGUBA = -ZMAGA;  // vrednost izgube

	private int globina;
	
	public AlphaBeta() {
		super("alphabeta");
		this.globina = 4;
	}
/**	
	public AlphaBeta (int globina) {
		super("alphabeta globina " + globina);
		this.globina = globina;
	}
**/
	public Koordinati izberiPotezo (Igra igra) {
		// Na začetku alpha = ZGUBA in beta = ZMAGA
		return alphabetaPoteze(igra, this.globina, ZGUBA, ZMAGA, igra.naPotezi()).poteza;
	}
	
	public static OcenjenaPoteza alphabetaPoteze(Igra igra, int globina, int alpha, int beta, Igralec jaz) {
		int ocena;
		// Če sem računalnik, maksimiramo oceno z začetno oceno ZGUBA
		// Če sem pa človek, minimiziramo oceno z začetno oceno ZMAGA
		if (igra.naPotezi() == jaz) {ocena = ZGUBA;} else {ocena = ZMAGA;}
		List<Koordinati> moznePoteze = igra.moznePoteze();
		Koordinati kandidat = moznePoteze.get(0); // Možno je, da se ne spremini vrednost kanditata. Zato ne more biti null.
		for (Koordinati p: moznePoteze) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj (p);
			
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
					alpha = Math.max(alpha,ocena);
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
