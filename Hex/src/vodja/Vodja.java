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
	public static GlavnoOkno okno;
	public static Igra igra = null;
	public static boolean clovekNaVrsti = false;
		
	
	public static void igramoNovoIgro () {
		igra = new Igra ();
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
