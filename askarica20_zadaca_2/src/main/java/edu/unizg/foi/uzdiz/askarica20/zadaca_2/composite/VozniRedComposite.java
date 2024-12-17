package edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite;

public class VozniRedComposite extends VozniRedBaseComposite {
  @Override
  public VozniRedComponent dohvatiDijete(String oznaka) {
    // da se ne overridea dobil bi se stack overflow od roditelja
    for (VozniRedComponent vlak : djeca) {
      if (vlak instanceof VlakComposite && ((VlakComposite) vlak).getOznakaVlaka().equals(oznaka)) {
        return vlak;
      }
    }
    return null;
  }

  public boolean postojiLi(String oznakaVlaka) {
    // provjerava postoji li vlak po toj oznaci
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
