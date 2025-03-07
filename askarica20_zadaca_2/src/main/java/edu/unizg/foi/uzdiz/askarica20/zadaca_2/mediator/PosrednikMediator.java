package edu.unizg.foi.uzdiz.askarica20.zadaca_2.mediator;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Korisnik;

public interface PosrednikMediator {
  void prijaviNadenPredmet(Korisnik korisnik, String predmet, String opis);

  void prijaviIzgubljenPredmet(Korisnik korisnik, String predmet, String opis);
}
