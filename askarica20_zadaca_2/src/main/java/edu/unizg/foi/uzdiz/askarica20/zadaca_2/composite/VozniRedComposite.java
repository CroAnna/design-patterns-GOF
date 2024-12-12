package edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.ZeljeznickiSustav;

public class VozniRedComposite extends VozniRedBaseComposite {

  @Override
  public void prikaziDetalje() {
    ZeljeznickiSustav.dohvatiInstancu().dohvatiIspisnik().ispisiVlakove(djeca);
  }

  @Override
  public VozniRedComponent dohvatiDijete(String oznaka) {
    for (VozniRedComponent vlak : djeca) {
      if (vlak instanceof VlakComposite && ((VlakComposite) vlak).getOznakaVlaka().equals(oznaka)) {
        return vlak;
      }
    }
    return null;
  }
}
