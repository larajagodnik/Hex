package inteligenca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import logika.Igra;
import logika.Igralec;
import logika.Plosca;
import logika.Polje;
import splosno.Koordinati;
import splosno.KdoIgra;

import inteligenca.OceniPozicijo;

public class MiniMax extends KdoIgra {

	private int globina;
	
	public MiniMax () {
		super("Skupina instruktorjev"); 
		this.globina = 9;
	}
	
	public Koordinati izberiPotezo (Igra igra) {
		
		int globina = 1;
		Koordinati poteza = null;
		int najboljsaOcena = Integer.MIN_VALUE;
		for (Koordinati p : igra.moznePoteze()) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj(p);
			int oc = alphabetaPoteze(igra,globina, Integer.MIN_VALUE, Integer.MIN_VALUE, igra.naPotezi);
			if (oc > najboljsaOcena) {
				najboljsaOcena = oc;
				poteza = p;
			}
		
		}
		
		return poteza;
	}

	public static int alphabetaPoteze(Igra igra, int globina, int alpha, int beta, Igralec jaz) {
		
		
		if (igra.naPotezi == jaz) {
			int ocena = Integer.MIN_VALUE;
			for (Koordinati p :  igra.moznePoteze()) {
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.odigraj(p);
				ocena = Math.max(ocena,  alphabetaPoteze(igra, globina-1,alpha, beta, jaz.nasprotnik()));
				alpha = Math.max(alpha, ocena);
				if (alpha >= beta) {
                    break;
                }
			}
			return ocena;
		}
		else {
			int ocena = Integer.MAX_VALUE;
			for (Koordinati p :  igra.moznePoteze()) {
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.odigraj(p);
				ocena = Math.min(ocena,  alphabetaPoteze(igra, globina-1,alpha, beta, jaz.nasprotnik()));
				beta = Math.min(alpha, ocena);
				if (alpha >= beta) {
                    break;
                }
			}
			return ocena;
		}
		
		
	}
	
}