package inteligenca;

import java.util.LinkedList;
import java.util.Random;

import logika.Igra;
import logika.Plosca;
import splosno.Koordinati;

public class RandomHex  extends Inteligenca {
	
	public RandomHex() {
		super("Random");
	}
	
	// prvi zeton postavi na sredino, naprej pa random
	public Koordinati izberiPotezo(Igra igra) {
		if (igra.odigranePoteze.size() == 0) {
			if(Plosca.N%2==0) {
				Koordinati p = new Koordinati((Plosca.N-2)/2,(Plosca.N-2)/2);
				return p;
			}
			else{
				Koordinati p = new Koordinati((Plosca.N-1)/2,(Plosca.N-1)/2);
				return p;
			}
		}
		else {
			Random r = new Random();
			LinkedList<Koordinati> poteze = (LinkedList<Koordinati>) igra.moznePoteze();
			Koordinati p = poteze.get(r.nextInt(poteze.size()));
			return p;
		}
	}
}


