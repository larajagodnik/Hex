package inteligenca;


import java.util.List;

import logika.Igra;
import logika.Igralec;
import logika.Stanje;
import splosno.Koordinati;
import splosno.KdoIgra;


public class MiniMax extends KdoIgra {
	
	private static final int ZMAGA = Integer.MAX_VALUE; // vrednost zmage
	private static final int ZGUBA = -ZMAGA;  // vrednost izgube


	private int globina;
	
	public MiniMax () {
		super("Skupina instruktorjev"); 
		this.globina = 4;
	}
	
	public MiniMax (int globina) {
		super("Skupina instruktorjev" + globina); 
		this.globina = globina;
	}
	
	public Koordinati izberiPotezo (Igra igra) {
		System.out.println("odigral sem " + alphabetaPoteze(igra, this.globina, ZGUBA, ZMAGA, igra.naPotezi()).poteza);
		return alphabetaPoteze(igra, this.globina, ZGUBA, ZMAGA, igra.naPotezi()).poteza;
	}
	
	public static OcenjenaPoteza alphabetaPoteze(Igra igra, int globina, int alpha, int beta, Igralec jaz) {
		int ocena;
		
		System.out.println("globina " + globina);
		
		// Če sem računalnik, maksimiramo oceno z začetno oceno ZGUBA
		// Če sem pa človek, minimiziramo oceno z začetno oceno ZMAGA
		System.out.println("na potezi " + igra.naPotezi() );
		if (igra.naPotezi() == jaz) {
			ocena = ZGUBA;
		}
		else {
			ocena = ZMAGA;
		}
		
		List<Koordinati> moznePoteze = igra.moznePoteze();
		Koordinati kandidat = moznePoteze.get(0);
		
		for (Koordinati p: moznePoteze) {
			System.out.println("p " + p);
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj(p);
			
			System.out.println("kopija na potezi " + kopijaIgre.naPotezi() );
			int ocenap;
			
		  // napisala brez switch da vidim kaj se dogaja
			
			System.out.println("stanje " + igra.stanje());
			System.out.println("kopija stanje " + kopijaIgre.stanje());
			
			if (kopijaIgre.stanje() == Stanje.zmaga_rdeci) {
				if(jaz == Igralec.rdeci) {
					ocenap = ZMAGA;
				}
				else {
					ocenap = ZGUBA;
				}
				
				System.out.println("ocenap " + ocenap);
			}
			
			else if (kopijaIgre.stanje() == Stanje.zmaga_modri) {
				
				System.out.println("zmaga modri");
				
				if(jaz == Igralec.modri) {
					ocenap = ZMAGA;
				}
				else {
					ocenap = ZGUBA;
				}
			}
			else {
				if (globina == 1) {
					System.out.println("ocenjujem1");
					
					 ocenap = OceniPozicijo.oceniPozicijo(kopijaIgre, jaz);
				}
				else {
					System.out.println("ocenjujem2");
					ocenap = alphabetaPoteze (kopijaIgre, globina-1, alpha, beta, jaz).ocena;
				}
			}
			
			/**
			switch (kopijaIgre.stanje()) {
			case zmaga_rdeci: ocenap = (jaz == Igralec.rdeci ? ZMAGA : ZGUBA); break;
			case zmaga_modri: ocenap = (jaz == Igralec.modri ? ZMAGA : ZGUBA); break;
			default:
				// Nekdo je na potezi
				if (globina == 1) ocenap = OceniPozicijo.oceniPozicijo(kopijaIgre, jaz);
				else ocenap = alphabetaPoteze (kopijaIgre, globina-1, alpha, beta, jaz).ocena;
			}
			**/
			
			if (igra.naPotezi() == jaz) { // Maksimiramo oceno
				if (ocenap > ocena) { // mora biti > namesto >=
					ocena = ocenap;
					kandidat = p;
					alpha = Math.max(alpha,ocena);
				}
			} else { // igra.naPotezi() != jaz, torej minimiziramo oceno
				if (ocenap < ocena) { // mora biti < namesto <=
					ocena = ocenap;
					kandidat = p;
					beta = Math.min(beta, ocena);					
				}	
			}
			if (alpha >= beta) // Izstopimo iz "for loop", saj ostale poteze ne pomagajo
				return new OcenjenaPoteza (kandidat, ocena);
			System.out.println("ocena " + ocena);
			System.out.println("ocenap " + ocenap);
		}
		return new OcenjenaPoteza (kandidat, ocena);
		
	}
	
}