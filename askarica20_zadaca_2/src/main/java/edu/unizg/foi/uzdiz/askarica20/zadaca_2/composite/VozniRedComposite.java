package edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite;

import java.util.ArrayList;
import java.util.List;

public class VozniRedComposite extends VozniRedComponent {
  public List<VozniRedComponent> vlakovi = new ArrayList<>();

  @Override
  public void prikaziDetalje() {
    System.out.println("VOZNI RED:");
    System.out.printf("%-10s %-20s %-20s %-10s %-10s %-10s%n", "Vlak", "Polazna stanica",
        "Odredišna stanica", "Polazak", "Dolazak", "Km");
    System.out.println("-".repeat(85));

    for (VozniRedComponent vlak : vlakovi) {
      vlak.prikaziDetalje();
    }

    if (vlakovi.isEmpty()) {
      System.out.println("Nema vlakova u voznom redu.");
    }
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
