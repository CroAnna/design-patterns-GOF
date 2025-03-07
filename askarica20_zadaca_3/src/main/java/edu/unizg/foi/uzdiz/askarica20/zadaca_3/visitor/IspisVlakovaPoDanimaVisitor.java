package edu.unizg.foi.uzdiz.askarica20.zadaca_3.visitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.VozniRedBaseComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.VozniRedComponent;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.VozniRedComposite;

public class IspisVlakovaPoDanimaVisitor implements VozniRedVisitor {
  private String trazeniDani;
  private List<VlakComposite> vlakoviKojiVoze = new ArrayList<>();

  public IspisVlakovaPoDanimaVisitor(String dani) {
    this.trazeniDani = dani;
  }

  @Override
  public void posjetiElement(VozniRedBaseComposite vozniRedBaseComposite) {
    if (vozniRedBaseComposite instanceof VozniRedComposite) {
      for (VozniRedComponent dijete : vozniRedBaseComposite.dohvatiDjecu()) {
        dijete.prihvati(this);
      }
      ispisiRezultate();
    } else if (vozniRedBaseComposite instanceof VlakComposite) {
      VlakComposite vlak = (VlakComposite) vozniRedBaseComposite;
      boolean sveEtapeVoze = true;

      for (VozniRedComponent etapa : vlak.dohvatiDjecu()) {
        if (etapa instanceof EtapaLeaf) {
          if (!voziLiEtapaUDane((EtapaLeaf) etapa)) {
            sveEtapeVoze = false;
            break;
          }
        }
      }

      if (sveEtapeVoze) {
        vlakoviKojiVoze.add(vlak);
      }
    }
  }

  @Override
  public void posjetiElement(EtapaLeaf etapaLeaf) {}

  private boolean voziLiEtapaUDane(EtapaLeaf etapa) {
    String daniVoznje = etapa.getOznakaDana();

    for (int i = 0; i < trazeniDani.length(); i += 2) {
      String dan = trazeniDani.substring(i, Math.min(i + 2, trazeniDani.length()));
      if (!daniVoznje.contains(dan)) {
        return false;
      }
    }
    return true;
  }

  private void ispisiRezultate() {
    System.out.println("\n------------------------------ VLAKOVI KOJI VOZE NA DANE: " + trazeniDani
        + " -------------------------------");
    System.out.printf("%-8s %-8s %-23s %-23s %-8s %-8s %-15s%n", "Vlak", "Pruga", "Polazna",
        "Odredišna", "Polazak", "Dolazak", "Dani");
    System.out.println(
        "--------------------------------------------------------------------------------------------");

    VlakComposite[] sortiraniVlakovi = vlakoviKojiVoze.toArray(new VlakComposite[0]);

    Arrays.sort(sortiraniVlakovi, (v1, v2) -> {
      EtapaLeaf e1 = (EtapaLeaf) v1.dohvatiDjecu().get(0);
      EtapaLeaf e2 = (EtapaLeaf) v2.dohvatiDjecu().get(0);
      return Integer.compare(e1.getVrijemePolaskaUMinutama(), e2.getVrijemePolaskaUMinutama());
    });

    int brojac = 0;
    for (VlakComposite vlak : sortiraniVlakovi) {
      for (VozniRedComponent etapa : vlak.dohvatiDjecu()) {
        if (etapa instanceof EtapaLeaf) {
          EtapaLeaf e = (EtapaLeaf) etapa;
          System.out.printf("%-8s %-8s %-23s %-25s %02d:%02d %02d:%02d %-15s%n",
              vlak.getOznakaVlaka(), e.getOznakaPruge(), e.getPocetnaStanica(),
              e.getZavrsnaStanica(), e.getVrijemePolaskaUMinutama() / 60,
              e.getVrijemePolaskaUMinutama() % 60, e.getVrijemeDolaskaUMinutama() / 60,
              e.getVrijemeDolaskaUMinutama() % 60, e.getOznakaDana());
          brojac++;
        }
      }
      System.out.println(
          "--------------------------------------------------------------------------------------------");
    }
  }
}
