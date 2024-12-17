package edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.ZeljeznickiSustav;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.OznakaDana;
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

  @Override
  public boolean dodaj(VozniRedComponent komponenta) {
    if (komponenta instanceof EtapaLeaf) {
      int index = 0;
      for (VozniRedComponent postojecaEtapa : djeca) {
        if (postojecaEtapa instanceof EtapaLeaf) {
          EtapaLeaf etapa = (EtapaLeaf) postojecaEtapa;
          if (((EtapaLeaf) komponenta).getVrijemePolaskaUMinutama() < etapa
              .getVrijemePolaskaUMinutama()) {
            break;
          }
        }
        index++;
      }
      djeca.add(index, komponenta);

      if (imaNeispravneEtape()) {
        ZeljeznickiSustav.dohvatiInstancu().ukloniVlak(this);
        ZeljeznickiSustav.dohvatiInstancu().dodajGreskuUSustav();
        return false;
      } else if (imaNeispravneBrzine()) {
        ZeljeznickiSustav.dohvatiInstancu().ukloniVlak(this);
        ZeljeznickiSustav.dohvatiInstancu().dodajGreskuUSustav();
        return false;
      }

      return true;
    } else {
      djeca.add(komponenta);
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

  public boolean postojiLi(String oznakaDana) {
    // provjerava postoji li oznaka dana po toj oznaci
    for (OznakaDana dan : ZeljeznickiSustav.dohvatiInstancu().dohvatiListuOznakaDana()) {
      if (dan.getDaniVoznje().equals(oznakaVlaka)) {
        return true;
      }
    }
    return false;
  }

  public boolean imaNeispravneEtape() {
    if (djeca.size() < 2) {
      return false;
    }
    for (int i = 0; i < djeca.size() - 1; i++) {
      EtapaLeaf trenutnaEtapa = (EtapaLeaf) djeca.get(i);
      EtapaLeaf sljedecaEtapa = (EtapaLeaf) djeca.get(i + 1);
      if (trenutnaEtapa.getVrijemeDolaskaUMinutama() > sljedecaEtapa.getVrijemePolaskaUMinutama()) {
        System.out.println("Greška brzine - vlak " + trenutnaEtapa.getOznakaVlaka()
            + " ima neispravna vremena - dolazak prethodne je nakon polaska sljedeće");
        return true;
      }
      if (!trenutnaEtapa.getZavrsnaStanica().equals(sljedecaEtapa.getPocetnaStanica())) {
        System.out.println("Greška brzine - vlak " + trenutnaEtapa.getOznakaVlaka()
            + " ima nepoklapajuće stanice između etapa (presjedališta)");
        return true;
      }
    }
    return false;
  }

  public boolean imaNeispravneBrzine() {
    if (djeca.size() < 2) {
      return false;
    }

    String brzinaPrethodne = null;

    for (VozniRedComponent komponenta : djeca) {
      EtapaLeaf etapa = (EtapaLeaf) komponenta;
      String trenutnaBrzina = etapa.getVrstaVlaka().isEmpty() ? "N" : etapa.getVrstaVlaka();

      if (brzinaPrethodne == null) {
        brzinaPrethodne = trenutnaBrzina;
      } else if (!trenutnaBrzina.equals(brzinaPrethodne)) {
        System.out.println("Greška brzine - vlak " + etapa.getOznakaVlaka()
            + " ima više brzina na razl. etapama (nedozvoljeno)");
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
