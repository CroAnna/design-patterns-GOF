package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComposite;

public interface VozniRedVisitor {
  // int visitElement(DivingComposite divingComposite); // TO JA NEMAM, JER JE OVO ONA DRUGA
  // APSTRAKTNA
  // KLASA...

  // da imam onu apstraktnu ostala bi 1 metoda za leaf i 1 za composite abstract
  void visitVozniRedComposite(VozniRedComposite vozniRed);

  void visitVlakComposite(VlakComposite vlak);

  void visitEtapaLeaf(EtapaLeaf etapa);
}
