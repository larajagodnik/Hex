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
	public static boolean clovekNaVrsti = false;
	public static gui.GlavnoOkno okno;
	
	public static void igramoNovoIgro () {
		igra = new Igra();
		igramo();
	}
	
	public static void igramo () {
		okno.osveziGUI();
		switch (igra.stanje()) {
			case zmaga_rdeci: 
			case zmaga_modri: 
				return; // odhajamo iz metode igramo
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
	
	public static void igrajClovekovoPotezo(Koordinati poteza) {
		System.out.println(poteza);
		if (igra.odigraj(poteza)) clovekNaVrsti = false;
		igramo();
	}
	
	// potrebno se napisat!
	public static void igrajRacunalnikovoPotezo() {
		
	}
}
