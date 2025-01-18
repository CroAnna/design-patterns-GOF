package edu.unizg.foi.uzdiz.askarica20.zadaca_3.command;

import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.VlakComposite;

public class VlakReceiver {
	private VlakComposite vlak;

	public VlakReceiver(VlakComposite vlak) {
		this.vlak = vlak;
	}

	public void potrubi() {
		System.out.println("Vlak " + vlak.getOznakaVlaka() + " - TUUUT TUUUUUT!");
	}

	public void upaliKlimu() {
		System.out.println("Vlak " + vlak.getOznakaVlaka() + " - klima uključena u svim vagonima");
	}

	public void upaliSvjetla() {
		System.out.println("Vlak " + vlak.getOznakaVlaka() + " - svjetla upaljena");
	}
}