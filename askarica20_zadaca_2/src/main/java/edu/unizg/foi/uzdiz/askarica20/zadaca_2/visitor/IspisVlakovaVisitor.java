package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComposite;

public class IspisVlakovaVisitor implements VozniRedVisitor {
  // za IV - pregled vlakova

  @Override
  public void posjetiElement(VozniRedComposite vozniRedComposite) {
    System.out.println("posjetiElement VozniRedComposite u IspisVlakovaVisitor");
    System.out.println("PREGLED VLAKOVA:");
    System.out.printf("%-10s %-20s %-20s %-8s %-8s %-5s%n", "Oznaka", "Polazna stanica",
        "Odredišna stanica", "Polazak", "Dolazak", "Km");
    System.out.println("-".repeat(75));
  }


  @Override
  public void posjetiElement(VlakComposite vlakComposite) {
    // Vlak se ne ispisuje direktno, nego kroz svoje etape
    System.out.println("posjetiElement VlakComposite u IspisVlakovaVisitor");
  }

  @Override
  public void posjetiElement(EtapaLeaf etapaLeaf) {
    System.out.println("posjetiElement EtapaLeaf u IspisVlakovaVisitor");
    System.out.printf("%-10s %-20s %-20s %-8s %-8s %-5d%n", etapaLeaf.getOznakaVlaka(),
        etapaLeaf.getPocetnaStanica(), etapaLeaf.getZavrsnaStanica(),
        pretvoriMinuteUVrijeme(etapaLeaf.getVrijemePolaskaUMinutama()),
        pretvoriMinuteUVrijeme(etapaLeaf.getVrijemeDolaskaUMinutama()), etapaLeaf.getUdaljenost());
  }

  private String pretvoriMinuteUVrijeme(int minute) {
    int sati = minute / 60;
    int preostaleMinute = minute % 60;
    return String.format("%02d:%02d", sati, preostaleMinute);
  }
}
