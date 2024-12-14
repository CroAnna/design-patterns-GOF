package edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto;

public class Korisnik {
  private int id;
  private String ime;
  private String prezime;

  public Korisnik() {
    super();
  }

  public Korisnik(int id, String ime, String prezime) {
    super();
    this.id = id;
    this.ime = ime;
    this.prezime = prezime;
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

}
