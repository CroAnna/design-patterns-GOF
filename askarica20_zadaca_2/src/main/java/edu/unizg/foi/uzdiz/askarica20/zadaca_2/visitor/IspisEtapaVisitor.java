package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComponent;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComposite;

public class IspisEtapaVisitor implements VozniRedVisitor {
  // za IEV - pregled etapa vlaka

  private String oznakaVlaka;
  private boolean pronadenVlak = false;

  public IspisEtapaVisitor(String oznakaVlaka) {
    this.oznakaVlaka = oznakaVlaka;
  }

  @Override
  public void posjetiElement(VozniRedComposite vozniRedComposite) {
    // System.out.println("posjetiElement VozniRedComposite u IspisEtapaVisitor");
    // TODO: implementirati za IEV

  }

  @Override
  public void posjetiElement(VlakComposite vlakComposite) {
    if (vlakComposite.getOznakaVlaka().equals(oznakaVlaka)) {
      if (!pronadenVlak) { // Zaglavlje ispišemo samo jednom
        pronadenVlak = true;
        System.out.println("ETAPE VLAKA " + oznakaVlaka + ":");
        System.out.printf("%-10s %-10s %-20s %-20s %-8s %-8s %-5s%n", "Oznaka vlaka",
            "Oznaka pruge", "Polazna stanica", "Odredišna stanica", "Polazak", "Dolazak", "Km");
        System.out.println("-".repeat(75));
      }

      for (VozniRedComponent dijete : vlakComposite.djeca) {
        dijete.prihvati(this);
      }
    }
  }

  @Override
  public void posjetiElement(EtapaLeaf etapaLeaf) {
    if (pronadenVlak && etapaLeaf.getOznakaVlaka().equals(oznakaVlaka)) {
      System.out.printf("%-10s %-10s %-20s %-20s %-8s %-8s %-5d%n", etapaLeaf.getOznakaVlaka(),
          etapaLeaf.getOznakaPruge(), etapaLeaf.getPocetnaStanica(), etapaLeaf.getZavrsnaStanica(),
          pretvoriMinuteUVrijeme(etapaLeaf.getVrijemePolaskaUMinutama()),
          pretvoriMinuteUVrijeme(etapaLeaf.getVrijemeDolaskaUMinutama()),
          etapaLeaf.getUdaljenost());
    }
  }

  private String pretvoriMinuteUVrijeme(int minute) {
    int sati = minute / 60;
    int preostaleMinute = minute % 60;
    return String.format("%02d:%02d", sati, preostaleMinute);
  }

}
