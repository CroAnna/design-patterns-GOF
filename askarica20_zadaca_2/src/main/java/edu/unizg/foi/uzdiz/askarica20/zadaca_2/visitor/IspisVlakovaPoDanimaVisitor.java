package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedBaseComposite;

public class IspisVlakovaPoDanimaVisitor implements VozniRedVisitor {
  // za IEVD - pregled vlakova za određene dane

  private String dani;

  public IspisVlakovaPoDanimaVisitor(String dani) {
    this.dani = dani;
  }

  @Override
  public void posjetiElement(VozniRedBaseComposite vozniRedBaseComposite) {
    System.out.println("posjetiElement VozniRedBaseComposite u IspisVlakovaPoDanimaVisitor");
    // TODO: implementirati za IEVD
  }

  @Override
  public void posjetiElement(EtapaLeaf etapaLeaf) {
    System.out.println("posjetiElement EtapaLeaf u IspisVlakovaPoDanimaVisitor");
    // TODO: implementirati za IEVD
  }
}
