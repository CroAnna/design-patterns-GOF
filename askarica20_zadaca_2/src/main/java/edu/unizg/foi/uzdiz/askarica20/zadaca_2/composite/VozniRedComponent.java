package edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite;

public abstract class VozniRedComponent {
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
