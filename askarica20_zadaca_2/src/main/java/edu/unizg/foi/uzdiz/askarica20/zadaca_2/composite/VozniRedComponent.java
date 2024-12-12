package edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.VozniRedVisitor;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.VozniRedVisitorElement;

public abstract class VozniRedComponent implements VozniRedVisitorElement {
  public abstract void prihvati(VozniRedVisitor visitor); // za visitora

  public abstract void prikaziDetalje(); // Operation()

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
    return this.dohvatiDijete(i);
  }

  public VozniRedComponent dohvatiDijete(String oznaka) { // GetChild(String) - ja dodala
    if (!(this instanceof VozniRedComposite)) {
      return null;
    }
    return this.dohvatiDijete(oznaka);
  }
}
