package edu.unizg.foi.uzdiz.askarica20.zadaca_3.visitor;

import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.VozniRedBaseComposite;

public interface VozniRedVisitor {
  void posjetiElement(VozniRedBaseComposite vozniRedBaseComposite);

  void posjetiElement(EtapaLeaf etapaLeaf);
}
