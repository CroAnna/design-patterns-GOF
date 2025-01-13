package edu.unizg.foi.uzdiz.askarica20.zadaca_3.visitor;

import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.VozniRedBaseComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.VozniRedComponent;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.VozniRedComposite;

public class IspisVlakovaVisitor implements VozniRedVisitor {
  private boolean jePrviProlaz = true;

  @Override
  public void posjetiElement(VozniRedBaseComposite vozniRedBaseComposite) {
    if (vozniRedBaseComposite instanceof VozniRedComposite) {
      if (jePrviProlaz) {
        jePrviProlaz = false;
        System.out.println("\n\n--------------------------------- ISPIS VLAKOVA "
            + " ---------------------------------\n");
        System.out.printf("%-10s %-23s %-23s %-8s %-8s %-5s%n", "Oznaka", "Polazna stanica",
            "Odredišna stanica", "Polazak", "Dolazak", "Km");
        System.out.println(
            "----------------------------------------------------------------------------------");
        for (VozniRedComponent dijete : vozniRedBaseComposite.dohvatiDjecu()) {
          dijete.prihvati(this);
        }
      }
    } else if (vozniRedBaseComposite instanceof VlakComposite) {
      VlakComposite vlak = (VlakComposite) vozniRedBaseComposite;
      vlak.izracunajUkupnePodatke();

      System.out.printf("%-10s %-23s %-23s %-8s %-8s %-5d%n", vlak.getOznakaVlaka(),
          vlak.getPocetnaStanica(), vlak.getZavrsnaStanica(),
          pretvoriMinuteUVrijeme(vlak.getVrijemePolaska()),
          pretvoriMinuteUVrijeme(vlak.getVrijemeDolaska()), vlak.getUkupniKilometri());

      for (VozniRedComponent dijete : vozniRedBaseComposite.dohvatiDjecu()) {
        dijete.prihvati(this);
      }

    }
  }

  @Override
  public void posjetiElement(EtapaLeaf etapaLeaf) {

  }

  private String pretvoriMinuteUVrijeme(int minute) {
    int sati = minute / 60;
    int preostaleMinute = minute % 60;
    return String.format("%02d:%02d", sati, preostaleMinute);
  }
}
