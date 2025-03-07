package edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite;

import java.util.ArrayList;
import java.util.List;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Stanica;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.VozniRedVisitor;

public class EtapaLeaf extends VozniRedComponent {
  private List<Stanica> listaStanicaEtape = new ArrayList<>();

  private String oznakaPruge;
  private String oznakaVlaka;
  private String vrstaVlaka;
  private String pocetnaStanica;
  private String zavrsnaStanica;
  private int trajanjeVoznjeUMinutama;
  private int vrijemePolaskaUMinutama;
  private int vrijemeDolaskaUMinutama;
  private int udaljenost;
  private String smjer;
  private String oznakaDana;

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

  public List<Stanica> getListaStanicaEtape() {
    return listaStanicaEtape;
  }

  public void setListaStanicaEtape(List<Stanica> listaStanicaEtape) {
    this.listaStanicaEtape = listaStanicaEtape;
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
