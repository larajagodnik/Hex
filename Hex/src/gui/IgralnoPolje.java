package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import logika.Plosca;
import vodja.Vodja;
import splosno.Koordinati;


@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener {
	
	// konstruktor za igralno polje
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
	 profesor: return Math.min(getWidth(), getHeight()) / Plosca.N;
	 nocemo da je cisto do roba zato getWidth()* 0.8
	 imamo N sestkotnikov, po zamiku je njihova sirina = N+(N-1)/2
	 in dodat mores se enega
	 iz tega dobis a = sirina polja/(sqrt(3) * (N+(N-1)/2)+1) 
	 visina vseh polj skupaj = (N+1)*3a/2 + a/2 -> a = visina*a/(3(N+1)+1)
	**/
	
	
	// kako dolga bo stranica glede na velikost polja
	private double stranica() {
		double stranica1 = getWidth()*0.9 / (Math.sqrt(3) * (Plosca.N + (Plosca.N -1)/2));
		double stranica2 = getHeight()*0.9 / (3*Plosca.N/2 + 1/2);
		return Math.min(stranica1, stranica2);
	}
	
	public static int round(double x) {
		return (int)(x + 0.5);
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
	
	// premik za x, y in vrstico
	// risali bomo najprej prvo vrstico kjer se sredisce premika za x, sredisce vrstic od 2 naprej se zamakne za nek y in x
	// dobimo koordniate sredisc
	private double[] premik(int x, int y) {
		double a = stranica();
		double xPremik = 2 * Math.cos(Math.PI/6)*a;
		double yPremik = a * 3/2; // y se premakne za cel a in se polovica
		//vrstica se za y=2 in naprej usakic premakne za a+cos(30) 
		double vrsticaPremik = (y-1) * a * Math.cos(Math.PI/6);
		
		double[] koordinate = new double[2];

		double skupnaDolzina = 2*Plosca.N*a*Math.cos(Math.PI/6)+(Plosca.N-1)*a*Math.cos(Math.PI/6);
		double skupnaVisina = Plosca.N *3.0*a/2.0 + a/2.0;
		
		koordinate[0] = (getWidth() - skupnaDolzina)/2.0 + a*Math.cos(Math.PI/6) + (x-1) * xPremik + vrsticaPremik;

		koordinate[1] = (getHeight() - skupnaVisina)/2.0 + a + (y-1)*yPremik;
		
		return koordinate;
		
	}
	
	//definiramo sredisca heksagonov
	//ker jih bomo dodali ko gradimo igralno polje (dvojni for loop) bodo narejena z dvema matrikama 11x11
	public static double[][] srediscax = new double[11][11];
	public static double[][] srediscay = new double[11][11];
	
	//narisemo obmocje igre, to je 11x11 heksagonov
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		double w = stranica();
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		
		for(int y = 1; y <= Plosca.N; y++) {
			for (int x = 1; x <= Plosca.N; x++) {
				double[] tocka = premik(x, y);
				
				
				//shranimo sredisca za kasneje, za barvanje ob kliku
				//imamo 2 matriki 11x11 kjer za vsak heksagon shranimo pozicijo po x in y
				srediscax[x-1][y-1] = tocka[0];
				srediscay[x-1][y-1] = tocka[1];
				
				// barvanje robov igralne plosce
				int[][] tocke = oglisca(tocka[0], tocka[1]);
				g2.drawPolygon(tocke[0], tocke[1], 6);
				
				if(y==1) {
					g2.setColor(Color.RED);
					g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH * 2)));
					g2.drawLine(tocke[0][0], tocke[1][0], tocke[0][1], tocke[1][1]);
					g2.drawLine(tocke[0][1], tocke[1][1], tocke[0][2], tocke[1][2]);
				}
				if(y==Plosca.N) {
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
				if(x==Plosca.N) {
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
	
	//poiscemo najblizje sredisce torej najblizji heksagon in shrani indeks najblizjega sredisca, ter razdaljo
	private int[] sredisce(int klikX, int klikY){
		int[] minim = new int[3];
		minim[0] = 0;
		minim[1] = 0;
		minim[2] = 0;
		
		double absX = 10000;
		double absY = 10000;
		
		for(int y = 0; y < Plosca.N; y++) {
			for (int x = 0; x < Plosca.N; x++) {
				//iskanje najmanjse dolzine v 2D
				double kandidatX = Math.pow(klikX-srediscax[x][y], 2);
				double kandidatY = Math.pow(klikY-srediscay[x][y], 2);
				//shranimo indeks ce razdalja do kandidata manjÅ¡a
				//nato zamenjamo kandidata s testiranjem (absX oziroma absY)
				double razdalja = Math.sqrt(kandidatX+kandidatY);
				if(razdalja < Math.sqrt(absX+absY)) {
					absX = kandidatX;
					absY = kandidatY;
					minim[0] = x;
					minim[1] = y;
					minim[2] = round(razdalja);
				}
			}
		}
		return minim;
	}

	
	//ker se bomo po njih sprehajali s stevcem, ki se poveca z novim pobarvanim heksagonom imamo 11**2
	public static double[][] pobarvani = new double[(int) Math.pow(11, 2)][2];
	public static int stevec = 1;
	public static boolean prvi = false;
	
	
	//igranje z barvami
	public Color color = Color.RED;
	
	protected void pobarvaj(Graphics g, int[] minim, int klikX, int klikY) {
		//nas poligon je dolocen z oglisci okoli sredsica
		int[][] poligon = oglisca(srediscax[minim[0]][minim[1]], srediscay[minim[0]][minim[1]]);
		g.fillPolygon(poligon[0], poligon[1], 6);
		
		//dodamo sredisce poligona ki smo ga pobarvali med pobarvane
		pobarvani[stevec][0] = minim[0];
		pobarvani[stevec][1] = minim[1];
		
		//stevilo pobarvanih se poveca
		stevec++;
		
		//test menjave barve
		if(color == Color.RED) {color = Color.BLUE;}
		else {color = Color.RED;}
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
		Graphics g = getGraphics();
		g.setColor(color);
		//if (Vodja.clovekNaVrsti) {
		
		//dobim pozicijo klika
		int klikX = e.getX();
		int klikY = e.getY();
		
		//poiscem indeks najblizjega sredisca
		int[] minim = sredisce(klikX, klikY);
		

		//da nismo kliknili izven igralnega polja
		if (minim[2] < stranica()) {
			//posebej obravnavamo prvega ker ima pobarvani začetno vrednost 0,0
			if (minim[0] == 0 && minim[1] == 0) {
				if(prvi == false) {
					int[][] poligon = oglisca(srediscax[0][0], srediscay[0][0]);
					g.fillPolygon(poligon[0], poligon[1], 6);
					prvi = true;
					if(color == Color.RED) {color = Color.BLUE;}
					else {color = Color.RED;}
				}
			}
			
			//preverim ostale, če so slučajno že pobarvani
			boolean pobarvan = false;
			for(int i = 1; i <= stevec; i++) {
				if(pobarvani[i][0] == minim[0] && pobarvani [i][1] == minim[1]) {pobarvan = true;}
			}
			
			//pobarvamo poligon
			if(pobarvan == false) {
				pobarvaj(g, minim, klikX, klikY);
				Vodja.igrajClovekovoPotezo(new Koordinati(minim[0], minim[1]));
				}
		}
		
		//}
		
	}

	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

}