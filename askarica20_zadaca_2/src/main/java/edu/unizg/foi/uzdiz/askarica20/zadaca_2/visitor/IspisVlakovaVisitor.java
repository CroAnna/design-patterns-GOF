package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedBaseComposite;

public class IspisVlakovaVisitor implements VozniRedVisitor {
  // za IV - pregled vlakova

  @Override
  public void posjetiElement(VozniRedBaseComposite vozniRedBaseComposite) {
    // TODO Auto-generated method stub
    if (vozniRedBaseComposite instanceof VlakComposite) {
      VlakComposite vlak = (VlakComposite) vozniRedBaseComposite;
      // ispis podataka o vlaku (oznaka, polazna stanica, odredišna stanica,
      // vrijeme polaska, vrijeme dolaska, ukupan broj km)

      // prema prezi, tu se treba pozvat prihvati(this), navodno se u klijentu poziva objekt ove
      // klase concreteVisitor.posjetiElement
    }
  }

  @Override
  public void posjetiElement(EtapaLeaf etapaLeaf) {
    // TODO Auto-generated method stub
    // izračun podataka iz etape ako je potrebno
  }

  // mozda treba dodat posjeti element i za drukcije vrste koje se koriste u compositeu? prouci to
  // jos malo... kolko sam skuzila trebam koristit sve 4 concretevisitor klase
}
