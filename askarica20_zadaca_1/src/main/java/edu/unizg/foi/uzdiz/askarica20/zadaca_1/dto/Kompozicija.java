package edu.unizg.foi.uzdiz.askarica20.zadaca_1.dto;

import java.util.ArrayList;
import java.util.List;

public class Kompozicija {
  private int id;
  private int oznaka;
  private String uloga;
  private List<Vozilo> vozila;

  public Kompozicija() {
    super();
  }

  public Kompozicija(int id, int oznaka) {
    super();
    this.id = id;
    this.oznaka = oznaka;
    this.vozila = new ArrayList<>();
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

  public List<Vozilo> getVozila() {
    return vozila;
  }

  public void addVozilo(Vozilo vozilo) {
    vozila.add(vozilo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Kompozicija: ").append(oznaka).append(" ").append(uloga).append(" ");
    for (Vozilo vozilo : vozila) {
      sb.append(vozilo.toString());
    }
    sb.append("\n");
    return sb.toString();
  }
}
