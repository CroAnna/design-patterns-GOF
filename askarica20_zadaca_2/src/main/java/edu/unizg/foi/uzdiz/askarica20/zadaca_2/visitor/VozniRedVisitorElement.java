package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

public interface VozniRedVisitorElement {

  // element definira accept operaciju koja uzima Visitor-a kao argument

  void prihvati(IspisVlakovaVisitor visitor);

  void prihvati(IspisEtapaVisitor visitor);

  void prihvati(IspisVoznogRedaVisitor visitor);

  void prihvati(IspisVlakovaPoDanimaVisitor visitor);
}
