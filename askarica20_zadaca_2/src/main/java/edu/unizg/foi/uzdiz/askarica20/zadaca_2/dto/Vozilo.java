package edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto;

public class Vozilo {
  private int id;
  private String oznaka;
  private String opis;
  private String proizvodac;
  private int godina;
  private String namjena;
  private String vrstaPrijevoza;
  private String vrstaPogona;
  private int maksimalnaBrzina;
  private double maksimalnaSnaga;
  private int brojSjedecihMjesta;
  private int brojStajacihMjesta;
  private int brojBicikala;
  private int brojKreveta;
  private int brojAutomobila;
  private double nosivost;
  private double povrsina;
  private int zapremnina;
  private String statusVozila;

  public Vozilo() {
    super();
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

  public String getOpis() {
    return opis;
  }

  public void setOpis(String opis) {
    this.opis = opis;
  }

  public String getProizvodac() {
    return proizvodac;
  }

  public void setProizvodac(String proizvodac) {
    this.proizvodac = proizvodac;
  }

  public int getGodina() {
    return godina;
  }

  public void setGodina(int godina) {
    this.godina = godina;
  }

  public String getNamjena() {
    return namjena;
  }

  public void setNamjena(String namjena) {
    this.namjena = namjena;
  }

  public String getVrstaPrijevoza() {
    return vrstaPrijevoza;
  }

  public void setVrstaPrijevoza(String vrstaPrijevoza) {
    this.vrstaPrijevoza = vrstaPrijevoza;
  }

  public String getVrstaPogona() {
    return vrstaPogona;
  }

  public void setVrstaPogona(String vrstaPogona) {
    this.vrstaPogona = vrstaPogona;
  }

  public int getMaksimalnaBrzina() {
    return maksimalnaBrzina;
  }

  public void setMaksimalnaBrzina(int maksimalnaBrzina) {
    this.maksimalnaBrzina = maksimalnaBrzina;
  }

  public double getMaksimalnaSnaga() {
    return maksimalnaSnaga;
  }

  public void setMaksimalnaSnaga(double maksimalnaSnaga) {
    this.maksimalnaSnaga = maksimalnaSnaga;
  }

  public int getBrojSjedecihMjesta() {
    return brojSjedecihMjesta;
  }

  public void setBrojSjedecihMjesta(int brojSjedecihMjesta) {
    this.brojSjedecihMjesta = brojSjedecihMjesta;
  }

  public int getBrojStajacihMjesta() {
    return brojStajacihMjesta;
  }

  public void setBrojStajacihMjesta(int brojStajacihMjesta) {
    this.brojStajacihMjesta = brojStajacihMjesta;
  }

  public int getBrojBicikala() {
    return brojBicikala;
  }

  public void setBrojBicikala(int brojBicikala) {
    this.brojBicikala = brojBicikala;
  }

  public int getBrojKreveta() {
    return brojKreveta;
  }

  public void setBrojKreveta(int brojKreveta) {
    this.brojKreveta = brojKreveta;
  }

  public int getBrojAutomobila() {
    return brojAutomobila;
  }

  public void setBrojAutomobila(int brojAutomobila) {
    this.brojAutomobila = brojAutomobila;
  }

  public double getNosivost() {
    return nosivost;
  }

  public void setNosivost(double nosivost) {
    this.nosivost = nosivost;
  }

  public double getPovrsina() {
    return povrsina;
  }

  public void setPovrsina(double povrsina) {
    this.povrsina = povrsina;
  }

  public int getZapremnina() {
    return zapremnina;
  }

  public void setZapremnina(int zapremnina) {
    this.zapremnina = zapremnina;
  }

  public String getStatusVozila() {
    return statusVozila;
  }

  public void setStatusVozila(String statusVozila) {
    this.statusVozila = statusVozila;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\nOznaka: ").append(oznaka).append("\nOpis: ").append(opis).append("\nProizvođač: ")
        .append(proizvodac).append("\nVrsta prijevoza: ").append(vrstaPrijevoza);
    return sb.toString();
  }
}
