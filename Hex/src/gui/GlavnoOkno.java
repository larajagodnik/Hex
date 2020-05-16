package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

import vodja.Vodja;

@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame{

	private IgralnoPolje polje;
	
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
