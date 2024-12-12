package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedBaseComposite;

public class IspisEtapaVisitor implements VozniRedVisitor {
  // za IEV - pregled etapa vlaka

  private String oznakaVlaka;

  public IspisEtapaVisitor(String oznakaVlaka) {
    this.oznakaVlaka = oznakaVlaka;
  }

  @Override
  public void posjetiElement(VozniRedBaseComposite vozniRedBaseComposite) {
    // TODO Auto-generated method stub
    if (vozniRedBaseComposite instanceof VlakComposite) {
      VlakComposite vlak = (VlakComposite) vozniRedBaseComposite;
      if (vlak.getOznakaVlaka().equals(oznakaVlaka)) {
        // ispis podataka o etapama vlaka
      }
    }
  }

  @Override
  public void posjetiElement(EtapaLeaf etapaLeaf) {
    // TODO Auto-generated method stub
    // ispis podataka o etapi
  }

}
