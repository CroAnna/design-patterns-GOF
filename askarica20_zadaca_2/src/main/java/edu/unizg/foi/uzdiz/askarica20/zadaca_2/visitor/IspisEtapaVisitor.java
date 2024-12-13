package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComponent;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComposite;

public class IspisEtapaVisitor implements VozniRedVisitor {
  private String oznakaVlaka;
  private boolean zaglavljeIspisano = false;

  public IspisEtapaVisitor(String oznakaVlaka) {
    this.oznakaVlaka = oznakaVlaka;
  }

  @Override
  public void posjetiElement(VozniRedComposite vozniRedComposite) {
    // Ne ispisujemo ništa na razini voznog reda
  }

  @Override
  public void posjetiElement(VlakComposite vlakComposite) {
    if (vlakComposite.getOznakaVlaka().equals(oznakaVlaka)) {
      if (!zaglavljeIspisano) {
        zaglavljeIspisano = true;
        System.out.println("\n\n------------------------------------ ETAPE VLAKA " + oznakaVlaka
            + " ------------------------------------\n");
        System.out.printf("%-12s %-12s %-22s %-22s %-7s %-7s %-3s%n", "Oznaka vlaka",
            "Oznaka pruge", "Polazna stanica", "Odredišna stanica", "Polazak", "Dolazak", "Km");
        System.out.println(
            "------------------------------------------------------------------------------------------");
      }
      // Prosljeđujemo visitor samo etapama ovog vlaka
      for (VozniRedComponent dijete : vlakComposite.djeca) {
        dijete.prihvati(this);
      }
      System.out.println(
          "\n------------------------------------------------------------------------------------------\n");
    }
    // TODO dodat provjeru ak uopce ne postoji vlak s tom oznakom
  }

  @Override
  public void posjetiElement(EtapaLeaf etapaLeaf) {
    if (etapaLeaf.getOznakaVlaka().equals(oznakaVlaka)) {
      System.out.printf("%-12s %-12s %-22s %-22s %-7s %-7s %-3s%n", etapaLeaf.getOznakaVlaka(),
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

  public String getOznakaVlaka() {
    return oznakaVlaka;
  }
}
