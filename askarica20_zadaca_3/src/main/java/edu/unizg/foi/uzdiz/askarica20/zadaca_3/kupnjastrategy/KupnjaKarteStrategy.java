package edu.unizg.foi.uzdiz.askarica20.zadaca_3.kupnjastrategy;

import java.time.LocalDateTime;

import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.VlakComposite;

public interface KupnjaKarteStrategy {
	double izracunajCijenu(VlakComposite vlak, LocalDateTime datumVoznje);
}
