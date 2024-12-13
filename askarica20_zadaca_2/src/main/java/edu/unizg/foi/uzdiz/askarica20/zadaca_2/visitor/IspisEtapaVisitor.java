package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComposite;

public class IspisEtapaVisitor implements VozniRedVisitor {
  // za IEV - pregled etapa vlaka

  private String oznakaVlaka;

  public IspisEtapaVisitor(String oznakaVlaka) {
    this.oznakaVlaka = oznakaVlaka;
  }

  @Override
  public void posjetiElement(VozniRedComposite vozniRedComposite) {
    // TODO: implementirati za IEV
  }

  @Override
  public void posjetiElement(VlakComposite vlakComposite) {
    // TODO: implementirati za IEV
  }

  @Override
  public void posjetiElement(EtapaLeaf etapaLeaf) {
    // TODO: implementirati za IEV
  }

}
