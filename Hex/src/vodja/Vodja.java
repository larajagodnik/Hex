package vodja;

import java.util.Map;

import logika.Igralec;
import logika.KdoIgra;
import logika.Koordinati;
import splosno.Igra;

public class Vodja {

	public static Map<Igralec,VrstaIgralca> vrstaIgralca;
	public static Map<Igralec,KdoIgra> kdoIgra;
	public static Igra igra = null;
	public static gui.GlavnoOkno okno;
	public static boolean clovekNaVrsti = false;
	public static boolean zmaga = false;
	
	public static void igramoNovoIgro () {
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

					//ko clovek na vrsti pocakaj na input in ga preveri					
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
