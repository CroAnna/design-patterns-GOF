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
  public void prikaziDetalje() {
    System.out.println("Detalji voznog reda:");
    for (VozniRedComponent dijete : djeca) {
      dijete.prikaziDetalje();
    }
  }

  @Override
  public void prihvati(IspisVlakovaVisitor visitor) {
    visitor.posjetiElement(this);
    for (VozniRedComponent dijete : djeca) {
      dijete.prihvati(visitor);
    }
  }

  @Override
  public void prihvati(IspisEtapaVisitor visitor) {
    visitor.posjetiElement(this);
    for (VozniRedComponent dijete : djeca) {
      dijete.prihvati(visitor);
    }
  }

  @Override
  public void prihvati(IspisVlakovaPoDanimaVisitor visitor) {
    visitor.posjetiElement(this);
    for (VozniRedComponent dijete : djeca) {
      dijete.prihvati(visitor);
    }
  }

  @Override
  public void prihvati(IspisVoznogRedaVisitor visitor) {
    visitor.posjetiElement(this);
    for (VozniRedComponent dijete : djeca) {
      dijete.prihvati(visitor);
    }
  }
}
