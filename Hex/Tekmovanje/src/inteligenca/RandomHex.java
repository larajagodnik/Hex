package inteligenca;

import java.util.LinkedList;
import java.util.Random;

import logika.Igra;
import logika.Plosca;
import logika.Polje;
import splosno.KdoIgra;
import splosno.Koordinati;

public class RandomHex extends KdoIgra {

	public RandomHex() {
		super("random");
	}
	
	boolean prazen = true;
	
	public Koordinati izberiPotezo(Igra igra) {
		for (int i=0;i<Plosca.N;i++) {
			for (int j=0; j<Plosca.N;j++) {
				if (igra.plosca.plosca[i][j]!=Polje.prazno) {
					prazen = false;
				}
			}
		}
		if (prazen) {
			if(Plosca.N%2==0) {
				Koordinati p = new splosno.Koordinati((Plosca.N-2)/2,(Plosca.N-2)/2);
				prazen = true;
				return p;
			}
			else{
				Koordinati p = new Koordinati((Plosca.N-1)/2,(Plosca.N-1)/2);
				prazen = true;
				return p;
			}
		}
		else {
			Random r = new Random();
			LinkedList<Koordinati> poteze = (LinkedList<Koordinati>) igra.moznePoteze();
			Koordinati p = poteze.get(r.nextInt(poteze.size()));
			prazen = true;
			return p;
		}
	}
}
