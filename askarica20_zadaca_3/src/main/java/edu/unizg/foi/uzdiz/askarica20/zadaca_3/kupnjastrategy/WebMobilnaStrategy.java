package edu.unizg.foi.uzdiz.askarica20.zadaca_3.kupnjastrategy;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.VlakComposite;

public class WebMobilnaStrategy implements KupnjaKarteStrategy {
	double popustWeb;

	public WebMobilnaStrategy(double popustWeb) {
		super();
		this.popustWeb = popustWeb;
	}

	@Override
	public double izracunajCijenuOvisnoOVrsti(VlakComposite vlak, LocalDateTime datumVoznje, double cijenaNormalni,
			double cijenaUbrzani, double cijenaBrzi, double popustVikendom) {
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

		cijena = cijena * (1 - popustWeb / 100);

		if (datumVoznje.getDayOfWeek() == DayOfWeek.SATURDAY || datumVoznje.getDayOfWeek() == DayOfWeek.SUNDAY) {
			cijena = cijena * (1 - popustVikendom / 100);
		}

		return cijena;
	}
}
