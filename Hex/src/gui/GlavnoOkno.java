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
import splosno.KdoIgra;

@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener {

	private IgralnoPolje polje;
	
	//Statusna vrstica v spodnjem delu okna
	private JLabel status;
	
	// Izbire v menujih
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	private JMenuItem igraClovekClovek;
	private JMenuItem igraRacunalnikRacunalnik;
	
	public GlavnoOkno() {
		super();
		this.setTitle("Hex");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());

		
		polje = new IgralnoPolje();
		
		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(polje, polje_layout);
		
		
		// menu
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		JMenu igra_menu = new JMenu("Nova igra");
		menu_bar.add(igra_menu);

		igraClovekRacunalnik = new JMenuItem("Človek – računalnik");
		igra_menu.add(igraClovekRacunalnik);
		igraClovekRacunalnik.addActionListener(this);
		
		igraRacunalnikClovek = new JMenuItem("Računalnik – človek");
		igra_menu.add(igraRacunalnikClovek);
		igraRacunalnikClovek.addActionListener(this);
		
		igraClovekClovek = new JMenuItem("Človek – človek");
		igra_menu.add(igraClovekClovek);
		igraClovekClovek.addActionListener(this);
		
		igraRacunalnikRacunalnik = new JMenuItem("Računalnik – računalnik");
		igra_menu.add(igraRacunalnikRacunalnik);
		igraRacunalnikRacunalnik.addActionListener(this);

		// igralno polje
		polje = new IgralnoPolje();
/**
		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(polje, polje_layout);
	**/	
		// statusna vrstica za sporočila
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
							    status.getFont().getStyle(),
							    20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		status.setText("Izberite igro!");
		
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == igraClovekRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.rdeci, VrstaIgralca.C); 
			Vodja.vrstaIgralca.put(Igralec.modri, VrstaIgralca.R);
			Vodja.kdoIgra = new EnumMap<Igralec,KdoIgra>(Igralec.class);
			Vodja.kdoIgra.put(Igralec.rdeci, new KdoIgra("Človek")); 
			//Vodja.kdoIgra.put(Igralec.modri, Vodja.racunalnikovaInteligenca);
			Vodja.igramoNovoIgro();
		} else if (e.getSource() == igraRacunalnikClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.rdeci, VrstaIgralca.R); 
			Vodja.vrstaIgralca.put(Igralec.modri, VrstaIgralca.C);
			Vodja.kdoIgra = new EnumMap<Igralec,KdoIgra>(Igralec.class);
			//Vodja.kdoIgra.put(Igralec.rdeci, Vodja.racunalnikovaInteligenca);
			Vodja.kdoIgra.put(Igralec.modri, new KdoIgra("Človek")); 
			Vodja.igramoNovoIgro();
		} else if (e.getSource() == igraClovekClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.rdeci, VrstaIgralca.C); 
			Vodja.vrstaIgralca.put(Igralec.modri, VrstaIgralca.C);
			Vodja.kdoIgra = new EnumMap<Igralec,KdoIgra>(Igralec.class);
			Vodja.kdoIgra.put(Igralec.rdeci, new KdoIgra("Človek")); 
			Vodja.kdoIgra.put(Igralec.modri, new KdoIgra("Človek"));
			//Vodja.igramoNovoIgro();
		}
		/**
		} else if (e.getSource() == igraRacunalnikRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.O, VrstaIgralca.R); 
			Vodja.vrstaIgralca.put(Igralec.X, VrstaIgralca.R);
			Vodja.kdoIgra = new EnumMap<Igralec,KdoIgra>(Igralec.class);
			Vodja.kdoIgra.put(Igralec.O, Vodja.racunalnikovaInteligenca);
			Vodja.kdoIgra.put(Igralec.X, Vodja.racunalnikovaInteligenca); 
			Vodja.igramoNovoIgro();
		}
		**/
	}		

	
	public void osveziGUI() {
		if (Vodja.igra == null) {
			System.out.println("Igra ni v teku");
			//status.setText("Igra ni v teku.");
		}
		else {
			switch(Vodja.igra.stanje()) {
			case v_teku: 
				System.out.println("Na potezi je " + Vodja.igra.naPotezi() +  " - " + Vodja.kdoIgra.get(Vodja.igra.naPotezi()).ime());
				//status.setText("Na potezi je " + Vodja.igra.naPotezi() + 
				//		" - " + Vodja.kdoIgra.get(Vodja.igra.naPotezi()).ime()); 
				break;
			case zmaga_rdeci: 
				System.out.println("Zmagal je RDEC - " + Vodja.igra.naPotezi() +  " - " + Vodja.kdoIgra.get(Vodja.igra.naPotezi()).ime());
				//status.setText("Zmagal je RDEC - " + 
				//		Vodja.kdoIgra.get(Vodja.igra.naPotezi().nasprotnik()).ime()); 
				break;
			case zmaga_modri: 
				System.out.println("Zmagal je MODER - " + Vodja.igra.naPotezi() +  " - " + Vodja.kdoIgra.get(Vodja.igra.naPotezi()).ime());
				//status.setText("Zmagal je MODER - " + 
				//		Vodja.kdoIgra.get(Vodja.igra.naPotezi().nasprotnik()).ime());
				break;
			}
		}
		polje.repaint();
	}

}	

