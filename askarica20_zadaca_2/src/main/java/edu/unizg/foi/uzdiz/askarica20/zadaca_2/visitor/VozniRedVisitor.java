package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedBaseComposite;

public interface VozniRedVisitor {
  void posjetiElement(VozniRedBaseComposite vozniRedBaseComposite);

  void posjetiElement(EtapaLeaf etapaLeaf);
}
