package vodja;

import java.util.Map;
import javax.swing.SwingWorker;

import inteligenca.Inteligenca;
import inteligenca.MiniMax;
import inteligenca.RandomHex;
import logika.Igra;
import logika.Igralec;
import splosno.KdoIgra;
import splosno.Koordinati;

public class Vodja {

	public static Map<Igralec,VrstaIgralca> vrstaIgralca;
	public static Map<Igralec,KdoIgra> kdoIgra;
	public static Igra igra = null;
	public static gui.GlavnoOkno okno;
	public static boolean clovekNaVrsti = false;

	
	public static void igramoNovoIgro () {
		igra = new Igra();
		Igra.zmaga = false;
		igramo();
	}
	
	/**
	 * glede na stanje igre se le ta ustrezno nadaljuje s potezo cloveka
	 * ali racunalnika, oziroma se konca, ce imamo zmagovalca
	 */
	public static void igramo () {
		okno.osveziStanje();
		switch (igra.stanje()) {
			case zmaga_rdeci:
				return;
			case zmaga_modri: 
				return;
			case v_teku:
				Igralec igralec = igra.naPotezi();
				VrstaIgralca vrstaNaPotezi = vrstaIgralca.get(igralec);
				switch (vrstaNaPotezi) {
				case C: 
					clovekNaVrsti = true;
					break;
				case R:
					igrajRacunalnikovoPotezo ();
					break;
				}
		}
	}
	
	// poteze, ki jih lahko naredi clovek
	public static void igrajClovekovoPotezo(Koordinati p) {
		if(igra.odigraj(p)) clovekNaVrsti = false;
		igramo();
	}
	public static void razveljaviPotezo() {
		igra.razveljavi();	
	}

	public static void swapRule() {
		igra.swap();	
	}

	
	// racunalnikova igra - minimax z globino 3
	public static Inteligenca racunalnikovaInteligenca = new MiniMax(4);
	
	public static void igrajRacunalnikovoPotezo() {
		Igra zacetnaIgra = igra;
		SwingWorker<Koordinati, Void> worker = new SwingWorker<Koordinati, Void> () {
			@Override
			protected Koordinati doInBackground() {
				//try {TimeUnit.SECONDS.sleep(1);} catch (Exception e) {};
				Koordinati poteza = racunalnikovaInteligenca.izberiPotezo(igra);
				return poteza;
			}
			@Override
			protected void done () {
				Koordinati poteza = null;
				try {poteza = get();} catch (Exception e) {};
				if (igra == zacetnaIgra) {
					igra.odigraj(poteza);
					igramo();
				}
			}
		};
		worker.execute();
	}

}
