package edu.unizg.foi.uzdiz.askarica20.zadaca_1.dto;

/**
 *
 * @author Ana Skarica
 */
public class Kompozicija {
  private int id;
  private String oznaka;
  private String oznakaPrijevoznogSredstva;
  private String uloga;

  public Kompozicija() {
    super();
  }

  public Kompozicija(int id, String oznaka, String oznakaPrijevoznogSredstva, String uloga) {
    super();
    this.id = id;
    this.oznaka = oznaka;
    this.oznakaPrijevoznogSredstva = oznakaPrijevoznogSredstva;
    this.uloga = uloga;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getOznaka() {
    return oznaka;
  }

  public void setOznaka(String oznaka) {
    this.oznaka = oznaka;
  }

  public String getOznakaPrijevoznogSredstva() {
    return oznakaPrijevoznogSredstva;
  }

  public void setOznakaPrijevoznogSredstva(String oznakaPrijevoznogSredstva) {
    this.oznakaPrijevoznogSredstva = oznakaPrijevoznogSredstva;
  }

  public String getUloga() {
    return uloga;
  }

  public void setUloga(String uloga) {
    this.uloga = uloga;
  }
}
