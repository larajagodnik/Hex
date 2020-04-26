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
// Ni se cisto na sredini
//

@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener {
	
	public IgralnoPolje() {
		setBackground(Color.WHITE);
		this.addMouseListener(this);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(900, 800);
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
		double stranica1 = getWidth()*0.9 / (Math.sqrt(3) * (Igra.N + (Igra.N -1)/2));
		double stranica2 = getHeight()*0.9 / (3*Igra.N/2 + 1/2);
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
		tabela[1][0] = round(y - Math.sin(Math.PI/6)*a);
		tabela[1][1] = round(y - a);
		tabela[1][2] = round(y - Math.sin(Math.PI/6)*a);
		tabela[1][3] = round(y + Math.sin(Math.PI/6)*a);
		tabela[1][4] = round(y + a);
		tabela[1][5] = round(y + Math.sin(Math.PI/6)*a);
		
		return tabela;	
	}
	
	private double[] premik(int x, int y) {
		double a = stranica();
		double xPremik = 2 * Math.cos(Math.PI/6)*a;
		double yPremik = a * 3/2; // y se premakne za cel a in se polovica
		//vrstica se za y=2 in naprej usakic premakne za a+cos(30) vec
		double vrsticaPremik = (y-1) * a * Math.cos(Math.PI/6);
		
		double[] koordinate = new double[2];

		double skupnaDolzina = 2*Igra.N*a*Math.cos(Math.PI/6)+(Igra.N-1)*a*Math.cos(Math.PI/6);
		double skupnaVisina = Igra.N *3.0*a/2.0 + a/2.0;
		
		koordinate[0] = (getWidth() - skupnaDolzina)/2.0 + a*Math.cos(Math.PI/6) + (x-1) * xPremik + vrsticaPremik;

		koordinate[1] = (getHeight() - skupnaVisina)/2.0 + a + (y-1)*yPremik;
		
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
				
				
				int[][] tocke = oglisca(tocka[0], tocka[1]);
				g2.drawPolygon(tocke[0], tocke[1], 6);
				
				if(y==1) {
					g2.setColor(Color.RED);
					g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH * 2)));
					g2.drawLine(tocke[0][0], tocke[1][0], tocke[0][1], tocke[1][1]);
					g2.drawLine(tocke[0][1], tocke[1][1], tocke[0][2], tocke[1][2]);
				}
				if(y==Igra.N) {
					g2.setColor(Color.RED);
					g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH * 2)));
					g2.drawLine(tocke[0][3], tocke[1][3], tocke[0][4], tocke[1][4]);
					g2.drawLine(tocke[0][4], tocke[1][4], tocke[0][5], tocke[1][5]);
				}
				if(x==1) {
					g2.setColor(Color.BLUE);
					g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH * 2)));
					g2.drawLine(tocke[0][0], tocke[1][0], tocke[0][5], tocke[1][5]);
					g2.drawLine(tocke[0][5], tocke[1][5], tocke[0][4], tocke[1][4]);
				}
				if(x==Igra.N) {
					g2.setColor(Color.BLUE);
					g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH * 2)));
					g2.drawLine(tocke[0][1], tocke[1][1], tocke[0][2], tocke[1][2]);
					g2.drawLine(tocke[0][2], tocke[1][2], tocke[0][3], tocke[1][3]);
				}
				g2.setColor(Color.BLACK);
				g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
				
				
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
