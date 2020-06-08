package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;

import javax.swing.JButton;
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

	// glavno igralno polje
	public IgralnoPolje polje;
	
	//Statusna vrstica v spodnjem delu okna
	public JLabel status;
	public JLabel stpotez;
	
	// Izbire v menujih
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	private JMenuItem igraClovekClovek;
	private JMenuItem igraRacunalnikRacunalnik;
	
	private JMenuItem velikost5;
	private JMenuItem velikost11;
	
	// gumba
	private JButton razveljavi;
	private JButton swap;
	
	public GlavnoOkno() {
		this.setTitle("Hex");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());

        polje = new IgralnoPolje();
        status = new JLabel("Izberi igro");
        stpotez = new JLabel("Število odigranih potez je 0");
        
        // polje
		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(polje, polje_layout);
		
		
		// statusna vrstica za stevilo odigranih potez
		stpotez.setFont(new Font(status.getFont().getName(), status.getFont().getStyle(), 20));
		GridBagConstraints stpotez_layout = new GridBagConstraints();
		stpotez_layout.gridx = 0;
		stpotez_layout.gridy = 1;
		stpotez_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(stpotez, stpotez_layout);
		
		// statusna vrstica za sporocila
		status.setFont(new Font(status.getFont().getName(), status.getFont().getStyle(), 20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 2;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		// menu
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		JMenu igra_menu = new JMenu("Nova igra");
		menu_bar.add(igra_menu);

		igraClovekRacunalnik = new JMenuItem("Človek Računalnik");
		igra_menu.add(igraClovekRacunalnik);
		igraClovekRacunalnik.addActionListener(this);
		
		igraRacunalnikClovek = new JMenuItem("Računalnik Človek");
		igra_menu.add(igraRacunalnikClovek);
		igraRacunalnikClovek.addActionListener(this);
		
		igraClovekClovek = new JMenuItem("Človek Človek");
		igra_menu.add(igraClovekClovek);
		igraClovekClovek.addActionListener(this);
		
		igraRacunalnikRacunalnik = new JMenuItem("Računalnik Računalnik");
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
		
		//razveljavi in swap na voljo le, če igrata 2 človeka
		// razveljavi
		razveljavi = new JButton("Razveljavi zadnjo potezo");
		menu_bar.add(razveljavi);
		razveljavi.addActionListener(this);
		razveljavi.setVisible(false);
		
		// swap rule
		swap = new JButton("Uporabi swap pravilo");
		menu_bar.add(swap);
		swap.addActionListener(this);
		swap.setVisible(false);
	}

	
	/**
	 * akcije ob klikih na menuje in gumbe
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == igraClovekRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.rdeci, VrstaIgralca.C); 
			Vodja.vrstaIgralca.put(Igralec.modri, VrstaIgralca.R);
			Vodja.kdoIgra = new EnumMap<Igralec,KdoIgra>(Igralec.class);
			Vodja.kdoIgra.put(Igralec.rdeci, new KdoIgra("Človek")); 
			Vodja.kdoIgra.put(Igralec.modri, Vodja.racunalnikovaInteligenca);
			Vodja.igramoNovoIgro();
		} else if (e.getSource() == igraRacunalnikClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.rdeci, VrstaIgralca.R); 
			Vodja.vrstaIgralca.put(Igralec.modri, VrstaIgralca.C);
			Vodja.kdoIgra = new EnumMap<Igralec,KdoIgra>(Igralec.class);
			Vodja.kdoIgra.put(Igralec.rdeci, Vodja.racunalnikovaInteligenca);
			Vodja.kdoIgra.put(Igralec.modri, new KdoIgra("Človek")); 
			Vodja.igramoNovoIgro();
		}	else if (e.getSource() == igraRacunalnikRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.rdeci, VrstaIgralca.R); 
			Vodja.vrstaIgralca.put(Igralec.modri, VrstaIgralca.R);
			Vodja.kdoIgra = new EnumMap<Igralec,KdoIgra>(Igralec.class);
			Vodja.kdoIgra.put(Igralec.rdeci, Vodja.racunalnikovaInteligenca);
			Vodja.kdoIgra.put(Igralec.modri, Vodja.racunalnikovaInteligenca);
			Vodja.igramoNovoIgro();
		} else if (e.getSource() == igraClovekClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.rdeci, VrstaIgralca.C); 
			Vodja.vrstaIgralca.put(Igralec.modri, VrstaIgralca.C);
			Vodja.kdoIgra = new EnumMap<Igralec,KdoIgra>(Igralec.class);
			Vodja.kdoIgra.put(Igralec.rdeci, new KdoIgra("Človek")); 
			Vodja.kdoIgra.put(Igralec.modri, new KdoIgra("Človek"));
			Vodja.igramoNovoIgro();
	
		//klik v okno Velikost	
		} else if (e.getSource() == velikost5) {
			Plosca.N = 7;
			vodja.Vodja.okno.status.setText("Izberi igro");
			repaint();
			vodja.Vodja.clovekNaVrsti = false;
		} else if (e.getSource() == velikost11) {
			Plosca.N = 11;
			vodja.Vodja.okno.status.setText("Izberi igro");
			repaint();
			vodja.Vodja.clovekNaVrsti = false;
		}
		
		// klik na gumba razveljavi zadnjo potezo in uporabi swap pravilo
		else if (e.getSource() == razveljavi) {
			Vodja.razveljaviPotezo();
		} else if (e.getSource() == swap) {
			Vodja.swapRule();
		}
			
		osveziStanje();
	}
	
	/**
	 * osvezi stanje na plosci (ob vsakem kliku miske)
	 */
	public void osveziStanje() {
		// razveljavi in swap v splosnem nista vidna
		razveljavi.setVisible(false);
		swap.setVisible(false);
		
		// na zacetku igra ni v teku
		if (Vodja.igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch (Vodja.igra.stanje()) {
			case v_teku:
				// izpis igralca, ki je na potezi in njegova vrsta, za racunalnik metoda s katero igra
				Vodja.okno.stpotez.setText("Število odigranih potez je " + Vodja.igra.odigranePoteze.size());
				Vodja.okno.status.setText("Na potezi je " + Vodja.igra.naPotezi() + " - " + Vodja.kdoIgra.get(Vodja.igra.naPotezi()).ime());
				
				// gumb razveljavi postane viden ko je stevilo potez > 0
				if (Vodja.vrstaIgralca.get(Igralec.rdeci) == VrstaIgralca.C && 
						Vodja.vrstaIgralca.get(Igralec.modri) == VrstaIgralca.C &&
						Vodja.igra.odigranePoteze.size() >= 1) {
					razveljavi.setVisible(true);
				}
				// za gumb swap mora biti stevilo potez enako 1, viden samo ce igrata 2 cloveka
				if (Vodja.vrstaIgralca.get(Igralec.rdeci) == VrstaIgralca.C && 
						Vodja.vrstaIgralca.get(Igralec.modri) == VrstaIgralca.C && Vodja.igra.potezeSwap.size()==1) {
					swap.setVisible(true);
				}
				break;
				
			// izpis zmagovalca
			case zmaga_rdeci:
				Vodja.okno.stpotez.setText("Število odigranih potez je " + Vodja.igra.odigranePoteze.size());
				Vodja.okno.status.setText("Zmagal je " + Vodja.igra.naPotezi().nasprotnik() + " - " + Vodja.kdoIgra.get(Vodja.igra.naPotezi().nasprotnik()).ime());
				break;
			case zmaga_modri:
				Vodja.okno.stpotez.setText("Število odigranih potez je " + Vodja.igra.odigranePoteze.size());
				Vodja.okno.status.setText("Zmagal je " + Vodja.igra.naPotezi().nasprotnik() + " - " + Vodja.kdoIgra.get(Vodja.igra.naPotezi().nasprotnik()).ime());
				break;
			}
		}
	polje.repaint();
	}
}	

