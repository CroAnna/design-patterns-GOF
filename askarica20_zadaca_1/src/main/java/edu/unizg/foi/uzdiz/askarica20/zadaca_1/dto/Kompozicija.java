package edu.unizg.foi.uzdiz.askarica20.zadaca_1.dto;

/**
 *
 * @author Ana Skarica
 */
public class Kompozicija {
  private int id;
  private int oznaka;
  private String oznakaPrijevoznogSredstva;
  private String uloga;

  public Kompozicija() {
    super();
  }

  public Kompozicija(int id, int oznaka, String oznakaPrijevoznogSredstva, String uloga) {
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

  public int getOznaka() {
    return oznaka;
  }

  public void setOznaka(int oznaka) {
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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\nOznaka kompozicije: ").append(oznaka).append("\nOznaka prijevoznog sredstva: ")
        .append(oznakaPrijevoznogSredstva).append("\nUloga: ").append(uloga);
    return sb.toString();
  }
}
