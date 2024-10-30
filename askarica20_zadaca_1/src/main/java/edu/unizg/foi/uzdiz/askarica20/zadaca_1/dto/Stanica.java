package edu.unizg.foi.uzdiz.askarica20.zadaca_1.dto;

/**
 *
 * @author Ana Skarica
 */
public class Stanica {
  private int id;
  private String nazivStanice;
  private String oznakaPruge;
  private String statusStanice;
  private boolean putniciUlIz;
  private boolean robaUtIst;
  private String kategorijaPruge;
  private int brojPerona;
  private String vrstaPruge;
  private int brojKolosjeka;
  private double doPoOsovini;
  private double doPoDuznomM;
  private String statusPruge;
  private int duzina;

  public Stanica() {
    super();
  }

  public Stanica(int id, String nazivStanice, String oznakaPruge, String vrstaStanice,
      String statusStanice, boolean putniciUlIz, boolean robaUtIst, String kategorijaPruge,
      int brojPerona, String vrstaPruge, int brojKolosjeka, double doPoOsovini, double doPoDuznomM,
      String statusPruge, int duzina) {
    super();
    this.id = id;
    this.nazivStanice = nazivStanice;
    this.oznakaPruge = oznakaPruge;
    this.statusStanice = statusStanice;
    this.putniciUlIz = putniciUlIz;
    this.robaUtIst = robaUtIst;
    this.kategorijaPruge = kategorijaPruge;
    this.brojPerona = brojPerona;
    this.vrstaPruge = vrstaPruge;
    this.brojKolosjeka = brojKolosjeka;
    this.doPoOsovini = doPoOsovini;
    this.doPoDuznomM = doPoDuznomM;
    this.statusPruge = statusPruge;
    this.duzina = duzina;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNazivStanice() {
    return nazivStanice;
  }

  public void setNazivStanice(String nazivStanice) {
    this.nazivStanice = nazivStanice;
  }

  public String getOznakaPruge() {
    return oznakaPruge;
  }

  public void setOznakaPruge(String oznakaPruge) {
    this.oznakaPruge = oznakaPruge;
  }

  public String getStatusStanice() {
    return statusStanice;
  }

  public void setStatusStanice(String statusStanice) {
    this.statusStanice = statusStanice;
  }

  public boolean isPutniciUlIz() {
    return putniciUlIz;
  }

  public void setPutniciUlIz(boolean putniciUlIz) {
    this.putniciUlIz = putniciUlIz;
  }

  public boolean isRobaUtIst() {
    return robaUtIst;
  }

  public void setRobaUtIst(boolean robaUtIst) {
    this.robaUtIst = robaUtIst;
  }

  public String getKategorijaPruge() {
    return kategorijaPruge;
  }

  public void setKategorijaPruge(String kategorijaPruge) {
    this.kategorijaPruge = kategorijaPruge;
  }

  public int getBrojPerona() {
    return brojPerona;
  }

  public void setBrojPerona(int brojPerona) {
    this.brojPerona = brojPerona;
  }

  public String getVrstaPruge() {
    return vrstaPruge;
  }

  public void setVrstaPruge(String vrstaPruge) {
    this.vrstaPruge = vrstaPruge;
  }

  public int getBrojKolosjeka() {
    return brojKolosjeka;
  }

  public void setBrojKolosjeka(int brojKolosjeka) {
    this.brojKolosjeka = brojKolosjeka;
  }

  public double getDoPoOsovini() {
    return doPoOsovini;
  }

  public void setDoPoOsovini(double doPoOsovini) {
    this.doPoOsovini = doPoOsovini;
  }

  public double getDoPoDuznomM() {
    return doPoDuznomM;
  }

  public void setDoPoDuznomM(double doPoDuznomM) {
    this.doPoDuznomM = doPoDuznomM;
  }

  public String getStatusPruge() {
    return statusPruge;
  }

  public void setStatusPruge(String statusPruge) {
    this.statusPruge = statusPruge;
  }

  public int getDuzina() {
    return duzina;
  }

  public void setDuzina(int duzina) {
    this.duzina = duzina;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\nNaziv stanice: ").append(nazivStanice).append("\nOznaka pruge: ")
        .append(oznakaPruge).append("\nDuzina: ").append(duzina);
    return sb.toString();
  }
}
