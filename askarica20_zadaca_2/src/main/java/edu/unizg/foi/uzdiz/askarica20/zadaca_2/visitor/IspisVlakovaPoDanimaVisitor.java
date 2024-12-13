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
    System.out.println("posjetiElement VozniRedComposite u IspisVlakovaPoDanimaVisitor");
    // TODO: implementirati za IEVD
  }

  @Override
  public void posjetiElement(VlakComposite vlakComposite) {
    System.out.println("posjetiElement VlakComposite u IspisVlakovaPoDanimaVisitor");
    // TODO: implementirati za IEVD
  }

  @Override
  public void posjetiElement(EtapaLeaf etapaLeaf) {
    System.out.println("posjetiElement EtapaLeaf u IspisVlakovaPoDanimaVisitor");
    // TODO: implementirati za IEVD
  }
}
