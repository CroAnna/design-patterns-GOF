package edu.unizg.foi.uzdiz.askarica20.zadaca_2.obavjestavacobserver;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Korisnik;

public class KorisnikConcreteObserver implements ZeljeznickiObserver {
  private Korisnik korisnik;
  private String oznakaVlaka;
  private String stanicaZaPracenje;

  // tu bi navodno trebalo bit nekakvo stanje, ja nez kakvo...

  public KorisnikConcreteObserver(Korisnik korisnik, String oznakaVlaka, String stanica) {
    this.korisnik = korisnik;
    this.oznakaVlaka = oznakaVlaka;
    this.stanicaZaPracenje = stanica;
  }

  @Override
  public void azuriraj(String poruka) {
    // ima implementaciju za azuriranje
    System.out.println("azuriraj u KorisnikConcreteObserver");
    if (stanicaZaPracenje == null || poruka.contains(stanicaZaPracenje)) {
      System.out.println(
          "Obavijest za " + korisnik.getIme() + " " + korisnik.getPrezime() + ": " + poruka);
    }
  }
}
