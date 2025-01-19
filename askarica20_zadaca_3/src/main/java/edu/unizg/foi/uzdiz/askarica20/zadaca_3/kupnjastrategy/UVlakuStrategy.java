package edu.unizg.foi.uzdiz.askarica20.zadaca_3.kupnjastrategy;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class UVlakuStrategy implements KupnjaKarteStrategy {
	double uvecanjeVlak;

	public UVlakuStrategy(double uvecanjeVlak) {
		super();
		this.uvecanjeVlak = uvecanjeVlak;
	}

	@Override
	public double[] izracunajCijenuOvisnoOVrsti(String vrstaVlaka, LocalDateTime datumVoznje, int udaljenost,
			double cijenaNormalni, double cijenaUbrzani, double cijenaBrzi, double popustVikendom) {

		double osnovna, konacna;

		switch (vrstaVlaka) {
		case "U":
			osnovna = udaljenost * cijenaUbrzani;
			break;
		case "B":
			osnovna = udaljenost * cijenaBrzi;
			break;
		default:
			osnovna = udaljenost * cijenaNormalni;
		}

		konacna = osnovna * (1 + uvecanjeVlak / 100);
		if (datumVoznje.getDayOfWeek() == DayOfWeek.SATURDAY || datumVoznje.getDayOfWeek() == DayOfWeek.SUNDAY) {
			konacna = konacna * (1 - popustVikendom / 100);
		}

		return new double[] { osnovna, konacna };
	}
}