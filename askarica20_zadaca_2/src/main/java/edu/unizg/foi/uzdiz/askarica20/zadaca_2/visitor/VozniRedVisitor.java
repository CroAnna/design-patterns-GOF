package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComposite;

public interface VozniRedVisitor {

  // void posjetiElement(VozniRedBaseComposite vozniRedBaseComposite);


  void posjetiElement(VozniRedComposite vozniRedComposite);

  void posjetiElement(VlakComposite vlakComposite);

  void posjetiElement(EtapaLeaf etapaLeaf);


}
