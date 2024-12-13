package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedBaseComposite;

public class IspisVoznogRedaVisitor implements VozniRedVisitor {
  // za IVRV - pregled voznog reda vlaka

  private String oznakaVlaka;

  public IspisVoznogRedaVisitor(String oznakaVlaka) {
    this.oznakaVlaka = oznakaVlaka;
  }

  @Override
  public void posjetiElement(VozniRedBaseComposite vozniRedBaseComposite) {
    System.out.println("posjetiElement PomocniBazniComposite u IspisVoznogRedaVisitor");
    // TODO: implementirati za IVRV
  }

  @Override
  public void posjetiElement(EtapaLeaf etapaLeaf) {
    System.out.println("posjetiElement EtapaLeaf u IspisVoznogRedaVisitor");
    // TODO: implementirati za IVRV
  }
}
