package inteligenca;


import java.util.List;

import logika.Igra;

import logika.Polje;
import splosno.Koordinati;
import splosno.KdoIgra;


public class MiniMax extends KdoIgra {
	public static Koordinati poteza;
	
	public MiniMax () {
		super("Skupina instruktorjev"); 
	}
	
	public MiniMax (int globina) {
		super("Skupina instruktorjev");
	}
	
	// izbere najboljso potezo
	public Koordinati izberiPotezo (Igra igra) {
		int najbol = -Integer.MAX_VALUE;
		List<Koordinati> poteze = igra.moznePoteze();
		for(int i=0; i<poteze.size();i++) {
			igra.plosca.plosca[poteze.get(i).getX()][poteze.get(i).getY()]=Polje.rdece;
			int vrednost = oceni(igra, 0, false);
			igra.plosca.plosca[poteze.get(i).getX()][poteze.get(i).getY()]=Polje.prazno;
			if(vrednost > najbol) {
				najbol = vrednost;
				poteza = poteze.get(i);
			}
		}
		return poteza;
	}
	

	public static int oceni(Igra igra, int globina, boolean isceMax) {
		if(igra.zmagovalnaVrsta != null) {
			if(!isceMax) {
				return 1;
			}
			else {return -1;}
		}

		if(isceMax) {
			int vrednostOpt = -Integer.MAX_VALUE;
			List<Koordinati> poteze = igra.moznePoteze();
			for(int i=0; i<poteze.size();i++) {
				igra.plosca.plosca[poteze.get(i).getX()][poteze.get(i).getY()]=Polje.rdece;
				int vrednost = oceni(igra, globina+1, false);
				igra.plosca.plosca[poteze.get(i).getX()][poteze.get(i).getY()]=Polje.prazno;
				
				vrednostOpt = Math.max(vrednost, vrednostOpt);
			}
			return vrednostOpt;
		}
		else {
			int vrednostOpt = Integer.MAX_VALUE;
			List<Koordinati> poteze = igra.moznePoteze();
			for(int i=0; i<poteze.size();i++) {
				igra.plosca.plosca[poteze.get(i).getX()][poteze.get(i).getY()]=Polje.modro;
				int vrednost = oceni(igra, globina+1, true);
				igra.plosca.plosca[poteze.get(i).getX()][poteze.get(i).getY()]=Polje.prazno;
	
				vrednostOpt = Math.min(vrednost, vrednostOpt);
			}
			return vrednostOpt;
		}
	}
	
}