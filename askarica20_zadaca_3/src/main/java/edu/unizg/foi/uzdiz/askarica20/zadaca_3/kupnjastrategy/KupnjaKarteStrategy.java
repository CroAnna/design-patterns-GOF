package edu.unizg.foi.uzdiz.askarica20.zadaca_3.kupnjastrategy;

import java.time.LocalDateTime;

public interface KupnjaKarteStrategy {
	double[] izracunajCijenuOvisnoOVrsti(String vrstaVlaka, LocalDateTime datumVoznje, int udaljenost,
			double cijenaNormalni, double cijenaUbrzani, double cijenaBrzi, double popustVikendom);
}