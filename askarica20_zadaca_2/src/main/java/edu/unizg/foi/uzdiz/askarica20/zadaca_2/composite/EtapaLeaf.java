package edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Stanica;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.VozniRedVisitor;

public class EtapaLeaf extends VozniRedComponent {
  private Map<Integer, List<Stanica>> mapaStanicaEtape = new HashMap<>();

  private String oznakaPruge; // etapa je poddio pruge
  private String oznakaVlaka; // ovo mozda ne treba pod etapu ic (jer je pod Vlak)
  private String vrstaVlaka; // ovo mozda ne treba pod etapu ic (jer je pod Vlak)
  private String pocetnaStanica; // ak nije definirano = pocetna stanica pruge (po oznaci pruge)
  private String zavrsnaStanica; // ak nije definirano = zavrsna stanica pruge (po oznaci pruge)
  private int trajanjeVoznjeUMinutama;
  private int vrijemePolaskaUMinutama; // od 00:00 u ponoci
  private int vrijemeDolaskaUMinutama; // na odredisnu stanicu = vrijeme polaska + trajanje
  private int udaljenost; // ukupan br. km od polazne stanice do zavrsne na toj etapi
  private String smjer; // O ili N
  private String oznakaDana; // nez kaj ce mi ovo iskreno

  @Override
  public void prihvati(VozniRedVisitor visitor) {
    visitor.posjetiElement(this);
  }

  public EtapaLeaf(String oznakaPruge, String oznakaVlaka, String vrstaVlaka, String pocetnaStanica,
      String zavrsnaStanica, int trajanjeVoznjeUMinutama, int vrijemePolaskaUMinutama,
      int vrijemeDolaskaUMinutama, int udaljenost, String smjer, String oznakaDana) {
    this.oznakaPruge = oznakaPruge;
    this.oznakaVlaka = oznakaVlaka;
    this.vrstaVlaka = vrstaVlaka;
    this.pocetnaStanica = pocetnaStanica;
    this.zavrsnaStanica = zavrsnaStanica;
    this.trajanjeVoznjeUMinutama = trajanjeVoznjeUMinutama;
    this.vrijemePolaskaUMinutama = vrijemePolaskaUMinutama;
    this.vrijemeDolaskaUMinutama = vrijemeDolaskaUMinutama;
    this.udaljenost = udaljenost;
    this.smjer = smjer;
    this.oznakaDana = oznakaDana;
  }

  public Map<Integer, List<Stanica>> getMapaStanicaEtape() {
    return mapaStanicaEtape;
  }

  public void setMapaStanicaEtape(Map<Integer, List<Stanica>> mapaStanicaEtape) {
    this.mapaStanicaEtape = mapaStanicaEtape;
  }

  public String getOznakaPruge() {
    return oznakaPruge;
  }

  public void setOznakaPruge(String oznakaPruge) {
    this.oznakaPruge = oznakaPruge;
  }

  public String getOznakaVlaka() {
    return oznakaVlaka;
  }

  public void setOznakaVlaka(String oznakaVlaka) {
    this.oznakaVlaka = oznakaVlaka;
  }

  public String getVrstaVlaka() {
    return vrstaVlaka;
  }

  public void setVrstaVlaka(String vrstaVlaka) {
    this.vrstaVlaka = vrstaVlaka;
  }

  public String getPocetnaStanica() {
    return pocetnaStanica;
  }

  public void setPocetnaStanica(String pocetnaStanica) {
    this.pocetnaStanica = pocetnaStanica;
  }

  public String getZavrsnaStanica() {
    return zavrsnaStanica;
  }

  public void setZavrsnaStanica(String zavrsnaStanica) {
    this.zavrsnaStanica = zavrsnaStanica;
  }

  public int getTrajanjeVoznjeUMinutama() {
    return trajanjeVoznjeUMinutama;
  }

  public void setTrajanjeVoznjeUMinutama(int trajanjeVoznjeUMinutama) {
    this.trajanjeVoznjeUMinutama = trajanjeVoznjeUMinutama;
  }

  public int getVrijemePolaskaUMinutama() {
    return vrijemePolaskaUMinutama;
  }

  public void setVrijemePolaskaUMinutama(int vrijemePolaskaUMinutama) {
    this.vrijemePolaskaUMinutama = vrijemePolaskaUMinutama;
  }

  public int getVrijemeDolaskaUMinutama() {
    return vrijemeDolaskaUMinutama;
  }

  public void setVrijemeDolaskaUMinutama(int vrijemeDolaskaUMinutama) {
    this.vrijemeDolaskaUMinutama = vrijemeDolaskaUMinutama;
  }

  public int getUdaljenost() {
    return udaljenost;
  }

  public void setUdaljenost(int udaljenost) {
    this.udaljenost = udaljenost;
  }

  public String getSmjer() {
    return smjer;
  }

  public void setSmjer(String smjer) {
    this.smjer = smjer;
  }

  public String getOznakaDana() {
    return oznakaDana;
  }

  public void setOznakaDana(String oznakaDana) {
    this.oznakaDana = oznakaDana;
  }

}
