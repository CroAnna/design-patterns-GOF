package edu.unizg.foi.uzdiz.askarica20.zadaca_3.kupnjastrategy;

import java.time.LocalDateTime;

import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.VlakComposite;

public class IzracunCijeneContext {
	private KupnjaKarteStrategy strategija;

	private double cijenaNormalni;
	private double cijenaUbrzani;
	private double cijenaBrzi;
	private double popustVikendom;
	private double popustWeb;
	private double uvecanje;

	public IzracunCijeneContext() {
	}

	public void postaviCijene(double cijenaNormalni, double cijenaUbrzani, double cijenaBrzi, double popustVikendom,
			double popustWeb, double uvecanje) {
		this.cijenaNormalni = cijenaNormalni;
		this.cijenaUbrzani = cijenaUbrzani;
		this.cijenaBrzi = cijenaBrzi;
		this.popustVikendom = popustVikendom;
		this.popustWeb = popustWeb;
		this.uvecanje = uvecanje;
	}

	public void postaviNacinKupovine(String nacinKupovine) {
		switch (nacinKupovine) {
		case "B":
			this.strategija = new BlagajnaStrategy(cijenaNormalni, cijenaUbrzani, cijenaBrzi, popustVikendom);
			break;
		case "WM":
			this.strategija = new WebMobilnaStrategy(cijenaNormalni, cijenaUbrzani, cijenaBrzi, popustVikendom,
					popustWeb);
			break;
		case "V":
			this.strategija = new UVlakuStrategy(cijenaNormalni, cijenaUbrzani, cijenaBrzi, popustVikendom, uvecanje);
			break;
		default:
			throw new IllegalArgumentException("Nepoznat način kupovine: " + nacinKupovine);
		}
	}

	public double izracunajCijenu(VlakComposite vlak, LocalDateTime datumVoznje) {
		if (strategija == null) {
			throw new IllegalStateException("Strategija nije ispravno odabrana!");
		}
		return strategija.izracunajCijenu(vlak, datumVoznje);
	}
}
