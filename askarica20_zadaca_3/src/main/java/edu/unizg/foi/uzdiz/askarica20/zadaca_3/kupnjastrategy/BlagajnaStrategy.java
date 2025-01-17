package edu.unizg.foi.uzdiz.askarica20.zadaca_3.kupnjastrategy;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.VlakComposite;

public class BlagajnaStrategy implements KupnjaKarteStrategy {

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

		if (datumVoznje.getDayOfWeek() == DayOfWeek.SATURDAY || datumVoznje.getDayOfWeek() == DayOfWeek.SUNDAY) {
			cijena = cijena * (1 - popustVikendom / 100);
		}

		return cijena;
	}
}
