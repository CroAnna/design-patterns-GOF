package edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.IspisEtapaVisitor;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.IspisVlakovaPoDanimaVisitor;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.IspisVlakovaVisitor;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.IspisVoznogRedaVisitor;

public class VozniRedComposite extends VozniRedBaseComposite {

  @Override
  public VozniRedComponent dohvatiDijete(String oznaka) {
    for (VozniRedComponent vlak : djeca) {
      if (vlak instanceof VlakComposite && ((VlakComposite) vlak).getOznakaVlaka().equals(oznaka)) {
        return vlak;
      }
    }
    return null;
  }

  @Override
  public void prikaziDetalje() { // cak mislim da mi ova metoda nikad ne treba ni u jednoj klasi
                                 // vise...
    System.out.println("Detalji voznog reda:");
    for (VozniRedComponent dijete : djeca) {
      dijete.prikaziDetalje();
    }
  }

  @Override
  public void prihvati(IspisVlakovaVisitor visitor) {
    System.out.println("prihvati IspisVlakovaVisitor u VozniRedComposite");

    visitor.posjetiElement(this); // ispis headera
    for (VozniRedComponent dijete : djeca) {
      dijete.prihvati(visitor); // ispis podataka
    }
  }

  @Override
  public void prihvati(IspisEtapaVisitor visitor) {
    System.out.println("prihvati IspisEtapaVisitor u VozniRedComposite");

    visitor.posjetiElement(this);
    for (VozniRedComponent dijete : djeca) {
      dijete.prihvati(visitor);
    }
  }

  @Override
  public void prihvati(IspisVlakovaPoDanimaVisitor visitor) {
    System.out.println("prihvati IspisVlakovaPoDanimaVisitor u VozniRedComposite");

    visitor.posjetiElement(this);
    for (VozniRedComponent dijete : djeca) {
      dijete.prihvati(visitor);
    }
  }

  @Override
  public void prihvati(IspisVoznogRedaVisitor visitor) {
    System.out.println("prihvati IspisVoznogRedaVisitor u VozniRedComposite");

    visitor.posjetiElement(this);
    for (VozniRedComponent dijete : djeca) {
      dijete.prihvati(visitor);
    }
  }
}
