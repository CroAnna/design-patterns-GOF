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
	private double uvecanjeVlak;

	public void setStrategija(KupnjaKarteStrategy strategija) {
		this.strategija = strategija;
	}

	public IzracunCijeneContext(double cijenaNormalni, double cijenaUbrzani, double cijenaBrzi, double popustVikendom,
			double popustWeb, double uvecanjeVlak) {
		this.cijenaNormalni = cijenaNormalni;
		this.cijenaUbrzani = cijenaUbrzani;
		this.cijenaBrzi = cijenaBrzi;
		this.popustVikendom = popustVikendom;
		this.popustWeb = popustWeb;
		this.uvecanjeVlak = uvecanjeVlak;
	}

	public double[] izracunajCijenu(VlakComposite vlak, LocalDateTime datumVoznje, String polaznaStanica,
			String odredisnaStanica) {
		if (strategija == null) {
			throw new IllegalStateException("Strategija nije ispravno odabrana!");
		}
		int stvarnaUdaljenost = vlak.izracunajUdaljenostIzmeduStanica(polaznaStanica, odredisnaStanica);

		return strategija.izracunajCijenuOvisnoOVrsti(vlak.getVrstaVlaka(), datumVoznje, stvarnaUdaljenost,
				cijenaNormalni, cijenaUbrzani, cijenaBrzi, popustVikendom);
	}

	public double getPopustWeb() {
		return popustWeb;
	}

	public double getUvecanjeVlak() {
		return uvecanjeVlak;
	}
}
