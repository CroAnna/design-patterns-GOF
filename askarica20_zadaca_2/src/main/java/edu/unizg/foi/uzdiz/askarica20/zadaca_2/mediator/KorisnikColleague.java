package edu.unizg.foi.uzdiz.askarica20.zadaca_2.mediator;

public interface KorisnikColleague {
  // colleague interface koji objasnjava kaj korisnik moze
  void prijaviNadenPredmet(String predmet, String opis);

  void prijaviIzgubljenPredmet(String predmet, String opis);

  void obavijesti(String poruka);
}
