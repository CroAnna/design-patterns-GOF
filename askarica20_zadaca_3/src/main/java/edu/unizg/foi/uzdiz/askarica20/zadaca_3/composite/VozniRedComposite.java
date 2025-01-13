package edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite;

public class VozniRedComposite extends VozniRedBaseComposite {
  @Override
  public VozniRedComponent dohvatiDijete(String oznaka) {
    for (VozniRedComponent vlak : djeca) {
      if (vlak instanceof VlakComposite && ((VlakComposite) vlak).getOznakaVlaka().equals(oznaka)) {
        return vlak;
      }
    }
    return null;
  }

  public boolean postojiLi(String oznakaVlaka) {
    for (VozniRedComponent komponenta : djeca) {
      if (komponenta instanceof VlakComposite) {
        VlakComposite vlak = (VlakComposite) komponenta;
        if (vlak.getOznakaVlaka().equals(oznakaVlaka)) {
          return true;
        }
      }
    }
    return false;
  }
}
