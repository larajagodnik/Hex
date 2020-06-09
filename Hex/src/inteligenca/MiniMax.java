package inteligenca;


import java.util.List;

import logika.Igra;
import logika.Igralec;
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
		List<Koordinati> poteze = igra.moznePoteze();
		Igralec igralec = igra.naPotezi;
		for(int i=0; i<poteze.size();i++) {
			int vrednost = OceniPozicijo.oceniPozicijo(igra, igralec);
			System.out.println(vrednost);
			if(vrednost==1 && igralec==Igralec.rdeci) {
				return(poteze.get(i));
			}
			else if(vrednost==-1 && igralec==Igralec.modri) {
				return(poteze.get(i));
			}
			else if(vrednost==0) {
				poteza = poteze.get(i);
			}
		}
		return poteza;
	}
	
}