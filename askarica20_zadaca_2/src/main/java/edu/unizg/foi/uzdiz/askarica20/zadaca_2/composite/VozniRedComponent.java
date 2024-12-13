package edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.IspisEtapaVisitor;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.IspisVlakovaPoDanimaVisitor;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.IspisVlakovaVisitor;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.IspisVoznogRedaVisitor;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.VozniRedVisitorElement;

public abstract class VozniRedComponent implements VozniRedVisitorElement {
  public abstract void prihvati(IspisVlakovaVisitor visitor); // za visitora

  public abstract void prihvati(IspisEtapaVisitor visitor); // za visitora

  public abstract void prihvati(IspisVlakovaPoDanimaVisitor visitor); // za visitora

  public abstract void prihvati(IspisVoznogRedaVisitor visitor); // za visitora

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
