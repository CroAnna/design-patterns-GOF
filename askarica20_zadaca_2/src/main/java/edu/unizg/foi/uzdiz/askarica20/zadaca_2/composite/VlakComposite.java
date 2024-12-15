package edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.ZeljeznickiSustav;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.OznakaDana;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Stanica;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.VozniRedVisitor;

public class VlakComposite extends VozniRedBaseComposite {
  private String oznakaVlaka;
  private String vrstaVlaka; // U, B ili N
  private String pocetnaStanica = "", zavrsnaStanica = "";
  private int vrijemePolaska = Integer.MAX_VALUE, vrijemeDolaska = 0, ukupniKilometri = 0;

  public VlakComposite(String oznakaVlaka, String vrstaVlaka) {
    this.oznakaVlaka = oznakaVlaka;
    this.vrstaVlaka = vrstaVlaka;
  }

  // ne znam jel ove metode moraju tu biti ili u etapi
  public boolean vlakDosaoNaStanicu(Stanica stanica) {
    System.out.println("vlakDosaoNaStanicu u VlakComposite 1");

    this.obavijestiObservere("poruka o stanici " + stanica.getNazivStanice());
    return true;
  }

  public boolean vlakDosaoNaStanicu(String oznakaVlaka, Stanica stanica) {
    System.out.println("vlakDosaoNaStanicu u VlakComposite 2");

    this.obavijestiObservere(
        "poruka o vlaku " + oznakaVlaka + " na stanici " + stanica.getNazivStanice());
    return true;
  }

  @Override
  public boolean dodaj(VozniRedComponent component) {
    if (component instanceof EtapaLeaf) {
      int index = 0;
      for (VozniRedComponent postojecaEtapa : djeca) {
        if (postojecaEtapa instanceof EtapaLeaf) {
          EtapaLeaf etapa = (EtapaLeaf) postojecaEtapa;
          if (((EtapaLeaf) component).getVrijemePolaskaUMinutama() < etapa
              .getVrijemePolaskaUMinutama()) {
            break;
          }
        }
        index++;
      }

      djeca.add(index, component);
      return true;
    } else {
      djeca.add(component);
      return true;
    }
  }

  @Override
  public VozniRedComponent dohvatiDijete(String oznakaPruge) {
    for (VozniRedComponent etapa : djeca) {
      if (etapa instanceof EtapaLeaf && ((EtapaLeaf) etapa).getOznakaPruge().equals(oznakaPruge)) {
        return etapa;
      }
    }
    return null;
  }

  public void izracunajUkupnePodatke() {
    ukupniKilometri = 0;
    pocetnaStanica = "";
    zavrsnaStanica = "";
    vrijemePolaska = Integer.MAX_VALUE;
    vrijemeDolaska = 0;

    for (VozniRedComponent komponenta : djeca) {
      if (komponenta instanceof EtapaLeaf) {
        EtapaLeaf etapa = (EtapaLeaf) komponenta;

        if (pocetnaStanica.isEmpty() || etapa.getVrijemePolaskaUMinutama() < vrijemePolaska) {
          vrijemePolaska = etapa.getVrijemePolaskaUMinutama();
          pocetnaStanica = etapa.getPocetnaStanica();
        }

        if (zavrsnaStanica.isEmpty() || etapa.getVrijemeDolaskaUMinutama() > vrijemeDolaska) {
          vrijemeDolaska = etapa.getVrijemeDolaskaUMinutama();
          zavrsnaStanica = etapa.getZavrsnaStanica();
        }
        ukupniKilometri = ukupniKilometri + etapa.getUdaljenost();
      }
    }
  }

  @Override
  public boolean postojiLi(String oznakaDana) {
    // provjerava postoji li oznaka dana po toj oznaci
    for (OznakaDana dan : ZeljeznickiSustav.dohvatiInstancu().dohvatiListuOznakaDana()) {
      if (dan.getDaniVoznje().equals(oznakaVlaka)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void prihvati(VozniRedVisitor visitor) {
    visitor.posjetiElement(this);
  }

  public String getVrstaVlaka() {
    return vrstaVlaka;
  }

  public void setVrstaVlaka(String vrstaVlaka) { // nez jel mi treba setter igdje
    this.vrstaVlaka = vrstaVlaka;
  }

  public String getOznakaVlaka() {
    return oznakaVlaka;
  }

  public void setOznakaVlaka(String oznakaVlaka) {
    this.oznakaVlaka = oznakaVlaka; // nez jel mi treba setter igdje
  }

  public String getPocetnaStanica() {
    return pocetnaStanica;
  }

  public String getZavrsnaStanica() {
    return zavrsnaStanica;
  }

  public int getVrijemePolaska() {
    return vrijemePolaska;
  }

  public int getVrijemeDolaska() {
    return vrijemeDolaska;
  }

  public int getUkupniKilometri() {
    return ukupniKilometri;
  }
}
