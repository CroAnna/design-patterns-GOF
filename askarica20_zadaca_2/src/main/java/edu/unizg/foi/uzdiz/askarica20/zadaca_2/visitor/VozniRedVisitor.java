package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComposite;

public interface VozniRedVisitor {
  // Visitor ima visitElement za svaki tip konkretne klase u Compositeu

  void posjetiElement(VozniRedComposite vozniRedComposite);

  void posjetiElement(VlakComposite vlakComposite);

  void posjetiElement(EtapaLeaf etapaLeaf);

}
