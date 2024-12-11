package edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite;

public abstract class VozniRedComponent {
  public abstract void prikaziDetalje(); // Operation()S

  // jel component moze imati listu composita?

  public boolean dodaj(VozniRedComponent component) { // Add(Component)
    if (!(this instanceof VozniRedComponent)) {
      return false;
    }
    return this.dodaj(component);
  }

  public VozniRedComponent dohvatiDijete(int i) { // GetChild(int)
    if (!(this instanceof VlakComposite)) {
      return null;
    }
    return this.dohvatiDijete(i);
  }
}
