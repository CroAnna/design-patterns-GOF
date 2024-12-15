package edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite;

import java.util.ArrayList;
import java.util.List;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.obavjestavacobserver.ZeljeznickiObserver;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.obavjestavacobserver.ZeljeznickiObserverSubject;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.VozniRedVisitor;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.VozniRedVisitorElement;

public abstract class VozniRedComponent
    implements VozniRedVisitorElement, ZeljeznickiObserverSubject {
  protected List<ZeljeznickiObserver> observeri = new ArrayList<>();

  public void prikvaciObservera(ZeljeznickiObserver zeljeznickiObserver) {
    System.out.println("prikvaciObservera u " + this.getClass().getSimpleName());
    this.observeri.add(zeljeznickiObserver);
  }

  public void otkvaciObservera(ZeljeznickiObserver zeljeznickiObserver) {
    System.out.println("otkvaciObservera");
    this.observeri.remove(zeljeznickiObserver);
  }

  public void obavijestiObservere(String poruka) {
    // System.out.println("obavijestiObservere u " + this.getClass().getSimpleName() + " - ima "
    // + this.observeri.size() + " observera");;
    for (ZeljeznickiObserver obs : this.observeri) {
      // System.out.println("obavijestiObservere petlja - " + poruka);
      obs.azuriraj(poruka);
    }
  }

  @Override
  public void prihvati(VozniRedVisitor visitor) {
    if (this instanceof VozniRedBaseComposite) {
      visitor.posjetiElement((VozniRedBaseComposite) this);
    } else if (this instanceof EtapaLeaf) {
      visitor.posjetiElement((EtapaLeaf) this);
    }
  }

  public boolean dodaj(VozniRedComponent komponenta) { // Add(Component)
    if (!(this instanceof VozniRedComponent)) {
      return false;
    }
    return this.dodaj(komponenta);
  }

  public boolean ukloni(VozniRedComponent komponenta) {
    if (!(this instanceof VozniRedComponent)) {
      return false;
    }
    return this.ukloni(komponenta);
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
