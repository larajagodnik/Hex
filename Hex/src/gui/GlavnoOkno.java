package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logika.Igralec;
import vodja.VrstaIgralca;
import vodja.Vodja;
import logika.Plosca;
import splosno.KdoIgra;

@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener {

	public IgralnoPolje polje;
	//Statusna vrstica v spodnjem delu okna
	public JLabel status;
	
	// Izbire v menujih
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	private JMenuItem igraClovekClovek;
	private JMenuItem igraRacunalnikRacunalnik;
	
	private JMenuItem velikost5;
	private JMenuItem velikost11;
	
	public GlavnoOkno() {
		this.setTitle("Hex");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());

        polje = new IgralnoPolje();
        status = new JLabel("Izberi igro");
        
        // polje
		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(polje, polje_layout);
		
		// statusna vrstica za sporoÄŤila
		status.setFont(new Font(status.getFont().getName(), status.getFont().getStyle(), 20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		// menu
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		JMenu igra_menu = new JMenu("Nova igra");
		menu_bar.add(igra_menu);

		igraClovekRacunalnik = new JMenuItem("ÄŚlovek â€“ raÄŤunalnik");
		igra_menu.add(igraClovekRacunalnik);
		igraClovekRacunalnik.addActionListener(this);
		
		igraRacunalnikClovek = new JMenuItem("RaÄŤunalnik â€“ ÄŤlovek");
		igra_menu.add(igraRacunalnikClovek);
		igraRacunalnikClovek.addActionListener(this);
		
		igraClovekClovek = new JMenuItem("ÄŚlovek â€“ ÄŤlovek");
		igra_menu.add(igraClovekClovek);
		igraClovekClovek.addActionListener(this);
		
		igraRacunalnikRacunalnik = new JMenuItem("RaÄŤunalnik â€“ raÄŤunalnik");
		igra_menu.add(igraRacunalnikRacunalnik);
		igraRacunalnikRacunalnik.addActionListener(this);

		// velikost plosce
		JMenu size_menu = new JMenu("Velikost");
		menu_bar.add(size_menu);
		
		velikost5 = new JMenuItem("N = 3");
		size_menu.add(velikost5);
		velikost5.addActionListener(this);
		
		velikost11 = new JMenuItem("N = 11");
		size_menu.add(velikost11);
		velikost11.addActionListener(this);	
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//klik v okno Nova igra
		if (e.getSource() == igraClovekRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.rdeci, VrstaIgralca.C); 
			Vodja.vrstaIgralca.put(Igralec.modri, VrstaIgralca.R);
			Vodja.kdoIgra = new EnumMap<Igralec,KdoIgra>(Igralec.class);
			Vodja.kdoIgra.put(Igralec.rdeci, new KdoIgra("ÄŚlovek")); 
			//Vodja.kdoIgra.put(Igralec.modri, Vodja.racunalnikovaInteligenca);
			Vodja.igramoNovoIgro();
			repaint();
		} else if (e.getSource() == igraRacunalnikClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.rdeci, VrstaIgralca.R); 
			Vodja.vrstaIgralca.put(Igralec.modri, VrstaIgralca.C);
			Vodja.kdoIgra = new EnumMap<Igralec,KdoIgra>(Igralec.class);
			//Vodja.kdoIgra.put(Igralec.rdeci, Vodja.racunalnikovaInteligenca);
			Vodja.kdoIgra.put(Igralec.modri, new KdoIgra("ÄŚlovek")); 
			Vodja.igramoNovoIgro();
			repaint();
		}	else if (e.getSource() == igraRacunalnikRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.rdeci, VrstaIgralca.R); 
			Vodja.vrstaIgralca.put(Igralec.modri, VrstaIgralca.R);
			Vodja.kdoIgra = new EnumMap<Igralec,KdoIgra>(Igralec.class);
			//Vodja.kdoIgra.put(Igralec.rdeci, Vodja.racunalnikovaInteligenca);
			//Vodja.kdoIgra.put(Igralec.modri, Vodja.racunalnikovaInteligenca);
			Vodja.igramoNovoIgro();
			repaint();
		} else if (e.getSource() == igraClovekClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.rdeci, VrstaIgralca.C); 
			Vodja.vrstaIgralca.put(Igralec.modri, VrstaIgralca.C);
			Vodja.kdoIgra = new EnumMap<Igralec,KdoIgra>(Igralec.class);
			Vodja.kdoIgra.put(Igralec.rdeci, new KdoIgra("ÄŚlovek")); 
			Vodja.kdoIgra.put(Igralec.modri, new KdoIgra("ÄŚlovek"));
			Vodja.igramoNovoIgro();
			Vodja.okno.repaint();
			
			
		//klik v okno Velikost	
		} else if (e.getSource() == velikost5) {
			Plosca.N = 3;
			vodja.Vodja.okno.status.setText("Izberi igro");
			repaint();
			vodja.Vodja.clovekNaVrsti = false;
		} else if (e.getSource() == velikost11) {
			Plosca.N = 11;
			vodja.Vodja.okno.status.setText("Izberi igro");
			repaint();
			vodja.Vodja.clovekNaVrsti = false;
		}
	}
	
	public void osveziStanje() {
		if (Vodja.igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch (Vodja.igra.stanje()) {
			case v_teku:
				vodja.Vodja.okno.status.setText("Na potezi je " + Vodja.igra.naPotezi() + " - " + Vodja.kdoIgra.get(Vodja.igra.naPotezi()).ime());
				break;
			case zmaga_rdeci:
				//pazi tukaj je trenutno nasprotnik zmagovalec ker odigraj(p) zamenja vloge POPRAVI
				vodja.Vodja.okno.status.setText("Zmagal je " + Vodja.igra.naPotezi().nasprotnik() + " - " + Vodja.kdoIgra.get(Vodja.igra.naPotezi()).ime());
				break;
			case zmaga_modri:
				vodja.Vodja.okno.status.setText("Zmagal je " + Vodja.igra.naPotezi().nasprotnik() + " - " + Vodja.kdoIgra.get(Vodja.igra.naPotezi()).ime());
				break;
			}
		}
	polje.repaint();
	}
}	

