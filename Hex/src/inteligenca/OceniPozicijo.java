package inteligenca;

import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

import logika.Igra;
import logika.Igralec;
import logika.Plosca;
import logika.Polje;
import logika.Stanje;
import splosno.Koordinati;


public class OceniPozicijo {
	
	public static int oceniPozicijo(Igra igra, Igralec igralec){

		Igra kopija = new Igra(igra);
		Polje polje;
		kopija.naPotezi = igralec.nasprotnik();
		List<Koordinati> poteze = kopija.moznePoteze();
		if(poteze.size()==0) {return 0;}
		for(int i=0; i<poteze.size();i++) {
			int x = poteze.get(i).getX();
			int y = poteze.get(i).getX();
			Polje[][] plosca = kopija.getPlosca();
			if(kopija.naPotezi == Igralec.rdeci) {polje = Polje.rdece;}
			else {polje = Polje.modro;}
			
			plosca[x][y]=polje;
			if(kopija.zmagovalnaVrsta != null) {
				if(igralec == igralec.rdeci) {return 1;}
				else {return -1;}
			}
			else {oceniPozicijo(kopija, igralec);}
		}
		return 0;
	}
}


