package edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto;

public class OznakaDana {
  private int id;
  private int oznakaDana;
  private String daniVoznje;

  public OznakaDana() {
    super();
  }

  public OznakaDana(int id, int oznakaDana, String daniVoznje) {
    super();
    this.id = id;
    this.oznakaDana = oznakaDana;
    this.daniVoznje = daniVoznje;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getOznakaDana() {
    return oznakaDana;
  }

  public void setOznakaDana(int oznakaDana) {
    this.oznakaDana = oznakaDana;
  }

  public String getDaniVoznje() {
    return daniVoznje;
  }

  public void setDaniVoznje(String daniVoznje) {
    this.daniVoznje = daniVoznje;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\nOznaka dana: ").append(oznakaDana).append("\nDani voznje: ").append(daniVoznje);
    return sb.toString();
  }
}
