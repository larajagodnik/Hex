package vodja;

import java.util.Map;

import java.util.concurrent.TimeUnit;

import javax.swing.SwingWorker;

import inteligenca.AlphaBeta;
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
	public static boolean zmaga;
	
	public static void igramoNovoIgro () {
		zmaga = false;
		igra = new Igra();
		igramo();
	}
	
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
	
	public static void igrajClovekovoPotezo(Koordinati p) {
		if(igra.odigraj(p)) clovekNaVrsti = false;
		igramo();
	}


	// potrebno se napisat!
	public static AlphaBeta racunalnikovaInteligenca = new AlphaBeta();
	
	public static void igrajRacunalnikovoPotezo() {
		Igra zacetkaIgra = igra;
		SwingWorker<Koordinati, Void> worker = new SwingWorker<Koordinati, Void> () {
			@Override
			protected Koordinati doInBackground() {
				try {TimeUnit.SECONDS.sleep(1);} catch (Exception e) {};
				Koordinati poteza = racunalnikovaInteligenca.izberiPotezo(igra);
				return poteza;
			}
			@Override
			protected void done () {
				Koordinati poteza = null;
				try {poteza = get();} catch (Exception e) {};
				if (igra == zacetkaIgra) {
					igra.odigraj(poteza);
					igramo();
				}
			}
		};
		worker.execute();
	}
}
