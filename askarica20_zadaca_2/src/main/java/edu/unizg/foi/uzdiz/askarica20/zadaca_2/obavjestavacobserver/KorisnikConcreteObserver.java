package edu.unizg.foi.uzdiz.askarica20.zadaca_2.obavjestavacobserver;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Korisnik;

public class KorisnikConcreteObserver implements ZeljeznickiObserver {
  private Korisnik korisnik;
  private String stanicaZaPracenje;

  public KorisnikConcreteObserver(Korisnik korisnik, String stanica) {
    this.korisnik = korisnik;
    this.stanicaZaPracenje = stanica;
  }

  @Override
  public void azuriraj(String poruka) {
    if (stanicaZaPracenje == null || poruka.contains(stanicaZaPracenje)) {
      System.out.println(
          "Obavijest za " + korisnik.getIme() + " " + korisnik.getPrezime() + ": " + poruka);
    }
  }
}
