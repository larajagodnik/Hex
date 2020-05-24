package inteligenca;

import java.util.LinkedList;
import java.util.Random;

import logika.Igra;
import splosno.KdoIgra;
import splosno.Koordinati;

public class RandomHex extends KdoIgra {

	public RandomHex() {
		super("random");
	}

	public Koordinati izberiPotezo(Igra igra) {
		Random r = new Random();
		LinkedList<Koordinati> poteze = (LinkedList<Koordinati>) igra.moznePoteze();
		Koordinati p = poteze.get(r.nextInt(poteze.size()));
		return p;
	}

}
