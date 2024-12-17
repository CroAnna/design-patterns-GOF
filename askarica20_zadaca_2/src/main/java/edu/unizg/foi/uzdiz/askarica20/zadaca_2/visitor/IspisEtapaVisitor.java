package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedBaseComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComponent;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComposite;

public class IspisEtapaVisitor implements VozniRedVisitor {
  private String oznakaVlaka;

  public IspisEtapaVisitor(String oznakaVlaka) {
    this.oznakaVlaka = oznakaVlaka;
  }

  @Override
  public void posjetiElement(VozniRedBaseComposite vozniRedBaseComposite) {
    if (vozniRedBaseComposite instanceof VozniRedComposite) {
      VozniRedComposite red = (VozniRedComposite) vozniRedBaseComposite;
      if (!red.postojiLi(oznakaVlaka)) {
        System.out.println("\nVlak s oznakom " + oznakaVlaka + " ne postoji u voznom redu.");
        return;
      }
      for (VozniRedComponent dijete : vozniRedBaseComposite.dohvatiDjecu()) {
        dijete.prihvati(this);
      }
    } else if (vozniRedBaseComposite instanceof VlakComposite) {
      VlakComposite vlak = (VlakComposite) vozniRedBaseComposite;
      if (vlak.getOznakaVlaka().equals(oznakaVlaka)) {
        System.out.println("\n\n---------------------------------------------- ETAPE VLAKA "
            + oznakaVlaka + " ----------------------------------------------\n");
        System.out.printf("%-13s %-13s %-23s %-23s %-8s %-8s %-4s %-12s%n", "Oznaka vlaka",
            "Oznaka pruge", "Polazna stanica", "Odredišna stanica", "Polazak", "Dolazak", "Km",
            "Dani");
        System.out.println(
            "--------------------------------------------------------------------------------------------------------------");
        // TODO dodaj da se brisu etape ako nisu dobre - pitanje s foruma
        for (VozniRedComponent dijete : vozniRedBaseComposite.dohvatiDjecu()) {
          dijete.prihvati(this);
        }
        System.out.println(
            "\n--------------------------------------------------------------------------------------------------------------\n");
      }
    }
  }

  @Override
  public void posjetiElement(EtapaLeaf etapaLeaf) {
    if (etapaLeaf.getOznakaVlaka().equals(oznakaVlaka)) {
      System.out.printf("%-13s %-13s %-23s %-23s %-8s %-8s %-4s %-12s%n",
          etapaLeaf.getOznakaVlaka(), etapaLeaf.getOznakaPruge(), etapaLeaf.getPocetnaStanica(),
          etapaLeaf.getZavrsnaStanica(),
          pretvoriMinuteUVrijeme(etapaLeaf.getVrijemePolaskaUMinutama()),
          pretvoriMinuteUVrijeme(etapaLeaf.getVrijemeDolaskaUMinutama()), etapaLeaf.getUdaljenost(),
          etapaLeaf.getOznakaDana());
    }
  }

  private String pretvoriMinuteUVrijeme(int minute) {
    int sati = minute / 60;
    int preostaleMinute = minute % 60;
    return String.format("%02d:%02d", sati, preostaleMinute);
  }
  /*
   * public String getOznakaVlaka() { return oznakaVlaka; }
   */

}
