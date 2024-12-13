package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComposite;

public class IspisVlakovaPoDanimaVisitor implements VozniRedVisitor {
  // za IEVD - pregled vlakova za određene dane

  private String dani;

  public IspisVlakovaPoDanimaVisitor(String dani) {
    this.dani = dani;
  }

  @Override
  public void posjetiElement(VozniRedComposite vozniRedComposite) {
    // TODO: implementirati za IEVD
  }

  @Override
  public void posjetiElement(VlakComposite vlakComposite) {
    // TODO: implementirati za IEVD
  }

  @Override
  public void posjetiElement(EtapaLeaf etapaLeaf) {
    // TODO: implementirati za IEVD
  }
}
