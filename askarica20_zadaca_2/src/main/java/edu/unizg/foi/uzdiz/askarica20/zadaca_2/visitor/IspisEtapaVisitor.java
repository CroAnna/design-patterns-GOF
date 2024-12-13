package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComponent;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComposite;

public class IspisEtapaVisitor implements VozniRedVisitor {
  private String oznakaVlaka;


  public IspisEtapaVisitor(String oznakaVlaka) {
    this.oznakaVlaka = oznakaVlaka;
  }

  @Override
  public void posjetiElement(VozniRedComposite vozniRedComposite) {
    if (!vozniRedComposite.postojiVlak(oznakaVlaka)) {
      System.out.println("\nVlak s oznakom " + oznakaVlaka + " ne postoji u voznom redu.");
      return;
    }
  }

  @Override
  public void posjetiElement(VlakComposite vlakComposite) {
    if (vlakComposite.getOznakaVlaka().equals(oznakaVlaka)) {
      System.out.println("\n\n---------------------------------------------- ETAPE VLAKA "
          + oznakaVlaka + " ----------------------------------------------\n");
      System.out.printf("%-13s %-13s %-23s %-23s %-8s %-8s %-4s %-12s%n", "Oznaka vlaka",
          "Oznaka pruge", "Polazna stanica", "Odredišna stanica", "Polazak", "Dolazak", "Km",
          "Dani");
      System.out.println(
          "--------------------------------------------------------------------------------------------------------------");
      // TODO dodaj da se brisu etape ako nisu dobre - pitanje s foruma

      for (VozniRedComponent dijete : vlakComposite.djeca) {
        dijete.prihvati(this);
      }
      System.out.println(
          "\n--------------------------------------------------------------------------------------------------------------\n");
    }
    // TODO dodat provjeru ak uopce ne postoji vlak s tom oznakom
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

  public String getOznakaVlaka() {
    return oznakaVlaka;
  }

}
