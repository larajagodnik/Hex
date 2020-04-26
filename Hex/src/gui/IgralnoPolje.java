package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import logika.Igra;

//
// treba je popravit zamik !
//

@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener {
	
	public IgralnoPolje() {
		setBackground(Color.WHITE);
		this.addMouseListener(this);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1200, 1200);
	}
	
	//sirina crt
	private final static double LINE_WIDTH = 0.08;
	
	/**
	 profesor: return Math.min(getWidth(), getHeight()) / Igra.N;
	 nocemo da je cisto do roba zato getWidth()* 0.8
	 imamo N sestkotnikov, po zamiku je njihova sirina = N+(N-1)/2
	 in dodat mores se enega
	 iz tega dobis a = sirina polja/(sqrt(3) * (N+(N-1)/2)+1) 
	 visina vseh polj skupaj = (N+1)*3a/2 + a/2 -> a = visina*a/(3(N+1)+1)
	**/
	private double stranica() {
		double stranica1 = getWidth()*0.8 / (Math.sqrt(3) * (Igra.N + (Igra.N -1)/2) + 1);
		double stranica2 = getHeight()*0.8 * 2 / (3*Igra.N + 1);
		return Math.min(stranica1, stranica2);
	}
	/**
	 metoda, ki nam vrne oglisca sestkotnika, glede na sredisce sestkotnika (x, y),
	 (po tem lahko ta oglisca povezemo s Polygon)
	 dolocili bomo kako bo zamaknjeno oglisce, glede na katerega risemo 
	 **/
	
	//oglisca (0 do 5) so v tabeli, najprej x nato y
	/**
	 x|0|1|2|3|4|5| tabela [][] { { },{ } } v prvem arrayu so x
	 y|0|1|2|3|4|5|
	   ... 
	**/
	public static int round(double x) {
		return (int)(x + 0.5);
	}
		
	private int[][] oglisca(double x, double y) {
		double a = stranica();
		int[][] tabela = new int[2][6];
		tabela[0][0] = round(x - Math.cos(Math.PI/6)*a); 
		tabela[0][1] = round(x);
		tabela[0][2] = round(x + Math.cos(Math.PI/6)*a);
		tabela[0][3] = round(x + Math.cos(Math.PI/6)*a);
		tabela[0][4] = round(x);
		tabela[0][5] = round(x - Math.cos(Math.PI/6)*a);
		tabela[1][0] = round(y + Math.sin(Math.PI/6)*a);
		tabela[1][1] = round(y + a);
		tabela[1][2] = round(y + Math.sin(Math.PI/6)*a);
		tabela[1][3] = round(y - Math.sin(Math.PI/6)*a);
		tabela[1][4] = round(y - a);
		tabela[1][5] = round(y - Math.sin(Math.PI/6)*a);
		
		return tabela;	
	}
	
	private double[] premik(int x, int y) {
		double a = stranica();
		double xPremik = 2 * Math.cos(Math.PI/6)*a;
		double yPremik = a * 3/2; // y se premakne za cel a in se polovica
		//vrstica se za y=2 in naprej usakic premakne za a+cos(30) vec
		double vrsticaPremik = (y-1) * a * Math.acos(Math.PI/6);
		
		double[] koordinate = new double[2];
		// koordinata za x = 1 bo na sirina / (N+(N-1)/2 +1)
		// x za 2 in naprej se premakne za (x-1)*premikx
		// ko se premakne se y se premakne tudi za vrstico
		koordinate[0] = getWidth()/(Igra.N+(Igra.N-1)/2 + 1) + (x-1)*xPremik + vrsticaPremik;
		// y zacne na visina (N+1); od 2 naprej se premika za premik y 
		koordinate[1] = getHeight()/(Igra.N+1) + (y-1)*yPremik;
		
		
		return koordinate;
		
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		double w = stranica();
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		
		for(int y = 1; y <= Igra.N; y++) {
			for (int x = 1; x <= Igra.N; x++) {
				double[] tocka = premik(x, y);
				
				//sredisca[x][y] = new double[]{round(tocka[0]), round(tocka[1])};
				int[][] tocke = oglisca(tocka[0], tocka[1]);
				g2.drawPolygon(tocke[0], tocke[1], 6);
				
				
			}	
		}
		

		
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
