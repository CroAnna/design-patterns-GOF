package edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.mediator.KorisnikColleague;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.mediator.PosrednikMediator;

public class Korisnik implements KorisnikColleague {
  // concrete colleague koja implementira korisnikove radnje
  private int id;
  private String ime;
  private String prezime;
  private PosrednikMediator mediator;

  public Korisnik() {
    super();
  }

  public Korisnik(int id, String ime, String prezime) {
    super();
    this.id = id;
    this.ime = ime;
    this.prezime = prezime;
  }

  public Korisnik(int id, String ime, String prezime, PosrednikMediator mediator) {
    super();
    this.id = id;
    this.ime = ime;
    this.prezime = prezime;
    this.mediator = mediator;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getIme() {
    return ime;
  }

  public void setIme(String ime) {
    this.ime = ime;
  }

  public String getPrezime() {
    return prezime;
  }

  public void setPrezime(String prezime) {
    this.prezime = prezime;
  }

  public PosrednikMediator getMediator() {
    return mediator;
  }

  public void setMediator(PosrednikMediator mediator) {
    this.mediator = mediator;
  }

  @Override
  public void prijaviNadenPredmet(String predmet, String opis) {
    if (mediator == null) {
      System.out.println("Korisnik nema pristup uredu izgubljeno-nadeno.");
      return;
    }
    mediator.prijaviNadenPredmet(this, predmet, opis);
  }

  @Override
  public void prijaviIzgubljenPredmet(String predmet, String opis) {
    if (mediator == null) {
      System.out.println("Korisnik nema pristup uredu izgubljeno-nadeno.");
      return;
    }
    mediator.prijaviIzgubljenPredmet(this, predmet, opis);
  }

  @Override
  public void pregledPredmeta() {
    if (mediator == null) {
      System.out.println("Korisnik nema pristup uredu izgubljeno-nadeno.");
      return;
    }
    mediator.pregledPredmeta(this);
  }

}
