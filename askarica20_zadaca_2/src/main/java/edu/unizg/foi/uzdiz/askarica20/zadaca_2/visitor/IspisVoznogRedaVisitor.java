package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComposite;

public class IspisVoznogRedaVisitor implements VozniRedVisitor {
  // za IVRV - pregled voznog reda vlaka

  private String oznakaVlaka;

  public IspisVoznogRedaVisitor(String oznakaVlaka) {
    this.oznakaVlaka = oznakaVlaka;
  }

  @Override
  public void posjetiElement(VozniRedComposite vozniRedComposite) {
    System.out.println("posjetiElement VozniRedComposite u IspisVoznogRedaVisitor");
    // TODO: implementirati za IVRV
  }

  @Override
  public void posjetiElement(VlakComposite vlakComposite) {
    System.out.println("posjetiElement VlakComposite u IspisVoznogRedaVisitor");
    // TODO: implementirati za IVRV
  }

  @Override
  public void posjetiElement(EtapaLeaf etapaLeaf) {
    System.out.println("posjetiElement EtapaLeaf u IspisVoznogRedaVisitor");
    // TODO: implementirati za IVRV
  }
}
