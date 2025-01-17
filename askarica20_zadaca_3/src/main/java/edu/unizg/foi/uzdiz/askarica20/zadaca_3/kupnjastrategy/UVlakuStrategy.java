package edu.unizg.foi.uzdiz.askarica20.zadaca_3.kupnjastrategy;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.VlakComposite;

public class UVlakuStrategy implements KupnjaKarteStrategy {
	double uvecanjeVlak;

	public UVlakuStrategy(double uvecanjeVlak) {
		super();
		this.uvecanjeVlak = uvecanjeVlak;
	}

	@Override
	public double[] izracunajCijenuOvisnoOVrsti(VlakComposite vlak, LocalDateTime datumVoznje, double cijenaNormalni,
			double cijenaUbrzani, double cijenaBrzi, double popustVikendom) {
		double osnovna, konacna;

		switch (vlak.getVrstaVlaka()) {
		case "U":
			osnovna = vlak.getUkupniKilometri() * cijenaUbrzani;
			break;
		case "B":
			osnovna = vlak.getUkupniKilometri() * cijenaBrzi;
			break;
		default:
			osnovna = vlak.getUkupniKilometri() * cijenaNormalni;
		}

		konacna = osnovna * (1 + uvecanjeVlak / 100);

		if (datumVoznje.getDayOfWeek() == DayOfWeek.SATURDAY || datumVoznje.getDayOfWeek() == DayOfWeek.SUNDAY) {
			konacna = konacna * (1 - popustVikendom / 100);
		}

		return new double[] { osnovna, konacna };
	}
}