package edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite;

import java.util.ArrayList;
import java.util.List;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.ZeljeznickiSustav;

public class VozniRedComposite extends VozniRedComponent {
  public List<VozniRedComponent> vlakovi = new ArrayList<>();

  @Override
  public void prikaziDetalje() {
    ZeljeznickiSustav.dohvatiInstancu().dohvatiIspisnik().ispisiVlakove(vlakovi);
  }

  @Override
  public boolean dodaj(VozniRedComponent component) {
    this.vlakovi.add(component);
    return true;
  }

  @Override
  public VozniRedComponent dohvatiDijete(int index) {
    return vlakovi.get(index);
  }

  @Override
  public VozniRedComponent dohvatiDijete(String oznaka) {
    for (VozniRedComponent vlak : vlakovi) {
      if (vlak instanceof VlakComposite && ((VlakComposite) vlak).getOznakaVlaka().equals(oznaka)) {
        return vlak;
      }
    }
    return null;
  }
}
