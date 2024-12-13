package edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.VozniRedVisitor;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.VozniRedVisitorElement;

public abstract class VozniRedComponent implements VozniRedVisitorElement {

  @Override
  public void prihvati(VozniRedVisitor visitor) {
    if (this instanceof VozniRedBaseComposite) {
      visitor.posjetiElement((VozniRedBaseComposite) this);
    } else if (this instanceof EtapaLeaf) {
      visitor.posjetiElement((EtapaLeaf) this);
    }
  }

  public boolean dodaj(VozniRedComponent component) { // Add(Component)
    if (!(this instanceof VozniRedComponent)) {
      return false;
    }
    return this.dodaj(component);
  }

  public VozniRedComponent dohvatiDijete(int i) { // GetChild(int)
    if (!(this instanceof VozniRedComposite)) {
      return null;
    }
    return this.dohvatiDijete(i); // ovo stvara stackoverflow, a s preze je...
  }

  public VozniRedComponent dohvatiDijete(String oznaka) { // GetChild(String) - ja dodala
    if (!(this instanceof VozniRedComposite)) {
      return null;
    }
    return this.dohvatiDijete(oznaka);
  }
}
