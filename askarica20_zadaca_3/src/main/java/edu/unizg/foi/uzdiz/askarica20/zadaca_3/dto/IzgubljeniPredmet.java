package edu.unizg.foi.uzdiz.askarica20.zadaca_3.dto;

import java.time.LocalDateTime;

public class IzgubljeniPredmet {
  private Korisnik korisnik;
  private String predmet;
  private String opis;
  private LocalDateTime vrijeme;

  public IzgubljeniPredmet(Korisnik korisnik, String predmet, String opis) {
    this.korisnik = korisnik;
    this.predmet = predmet;
    this.opis = opis;
    this.vrijeme = LocalDateTime.now();
  }

  public Korisnik getKorisnik() {
    return korisnik;
  }

  public String getPredmet() {
    return predmet;
  }

  public String getOpis() {
    return opis;
  }

  public LocalDateTime getVrijeme() {
    return vrijeme;
  }

  @Override
  public String toString() {
    return "Predmet: " + predmet + ", Opis: " + opis + ", Prijavio: " + korisnik.getIme() + " "
        + korisnik.getPrezime();
  }
}
