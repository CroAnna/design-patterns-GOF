package edu.unizg.foi.uzdiz.askarica20.zadaca_3.mediator;

public interface KorisnikColleague {
  void prijaviNadenPredmet(String predmet, String opis);

  void prijaviIzgubljenPredmet(String predmet, String opis);

  void obavijesti(String poruka);
}
