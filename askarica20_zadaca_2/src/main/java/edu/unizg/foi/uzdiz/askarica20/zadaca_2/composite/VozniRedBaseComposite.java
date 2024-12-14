package edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite;

import java.util.ArrayList;
import java.util.List;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.VozniRedVisitor;

public abstract class VozniRedBaseComposite extends VozniRedComponent {
  public List<VozniRedComponent> djeca = new ArrayList<>();

  @Override
  public boolean dodaj(VozniRedComponent component) {
    this.djeca.add(component);
    return true;
  }

  @Override
  public VozniRedComponent dohvatiDijete(int index) {
    return djeca.get(index);
  }

  @Override
  public void prihvati(VozniRedVisitor visitor) {
    visitor.posjetiElement(this);
  }

  public List<VozniRedComponent> dohvatiDjecu() {
    return djeca;
  }

  public abstract boolean postojiLi(String oznaka);

}
