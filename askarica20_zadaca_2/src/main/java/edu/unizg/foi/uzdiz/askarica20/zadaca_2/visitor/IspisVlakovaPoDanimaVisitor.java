package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedBaseComposite;

public class IspisVlakovaPoDanimaVisitor implements VozniRedVisitor {
  // za IEVD - pregled vlakova za određene dane

  private String dani;

  public IspisVlakovaPoDanimaVisitor(String dani) {
    this.dani = dani;
  }

  @Override
  public void posjetiElement(VozniRedBaseComposite vozniRedBaseComposite) {
    if (vozniRedBaseComposite instanceof VlakComposite) {
      VlakComposite vlak = (VlakComposite) vozniRedBaseComposite;
      // provjera i ispis vlakova koji voze na zadane dane
    }
  }

  @Override
  public void posjetiElement(EtapaLeaf etapaLeaf) {
    // ispis podataka o etapi ako vlak vozi na zadane dane
  }
}
