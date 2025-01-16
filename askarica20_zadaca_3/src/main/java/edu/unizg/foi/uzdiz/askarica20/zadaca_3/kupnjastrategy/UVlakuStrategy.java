package edu.unizg.foi.uzdiz.askarica20.zadaca_3.kupnjastrategy;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.VlakComposite;

public class UVlakuStrategy implements KupnjaKarteStrategy {
	private final double cijenaNormalni;
	private final double cijenaUbrzani;
	private final double cijenaBrzi;
	private final double popustVikendom;
	private final double uvecanje;

	public UVlakuStrategy(double cijenaNormalni, double cijenaUbrzani, double cijenaBrzi, double popustVikendom,
			double uvecanje) {
		this.cijenaNormalni = cijenaNormalni;
		this.cijenaUbrzani = cijenaUbrzani;
		this.cijenaBrzi = cijenaBrzi;
		this.popustVikendom = popustVikendom;
		this.uvecanje = uvecanje;
	}

	@Override
	public double izracunajCijenu(VlakComposite vlak, LocalDateTime datumVoznje) {
		double cijena;
		switch (vlak.getVrstaVlaka()) {
		case "U":
			cijena = vlak.getUkupniKilometri() * cijenaUbrzani;
			break;
		case "B":
			cijena = vlak.getUkupniKilometri() * cijenaBrzi;
			break;
		default:
			cijena = vlak.getUkupniKilometri() * cijenaNormalni;
		}

		cijena = cijena * (1 + uvecanje / 100);

		if (datumVoznje.getDayOfWeek() == DayOfWeek.SATURDAY || datumVoznje.getDayOfWeek() == DayOfWeek.SUNDAY) {
			cijena = cijena * (1 - popustVikendom / 100);
		}

		return cijena;
	}
}