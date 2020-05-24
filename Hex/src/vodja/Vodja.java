package vodja;

import java.util.Map;

import gui.GlavnoOkno;
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
	
//	public static void igrajClovekovoPotezo() {
//		clovekNaVrsti = true;
//		igra.naPotezi = igra.naPotezi.nasprotnik();
//		clovekNaVrsti = false;
//		igramo();
//	}


	// potrebno se napisat!
	public static void igrajRacunalnikovoPotezo() {
		
	}
}
