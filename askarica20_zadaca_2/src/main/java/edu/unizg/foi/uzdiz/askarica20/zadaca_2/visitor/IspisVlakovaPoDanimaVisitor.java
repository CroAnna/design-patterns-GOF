package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import java.util.ArrayList;
import java.util.List;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedBaseComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComponent;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComposite;

public class IspisVlakovaPoDanimaVisitor implements VozniRedVisitor {
  private String trazeniDani;
  private List<VlakComposite> vlakoviKojiVoze = new ArrayList<>(); // TODO probaj slozit ovo bez
                                                                   // pomocne liste

  public IspisVlakovaPoDanimaVisitor(String dani) {
    this.trazeniDani = dani;
  }

  @Override
  public void posjetiElement(VozniRedBaseComposite vozniRedBaseComposite) {
    if (vozniRedBaseComposite instanceof VozniRedComposite) {
      // Prvi prolaz - skupi sve vlakove koji voze u tražene dane
      for (VozniRedComponent dijete : vozniRedBaseComposite.dohvatiDjecu()) {
        dijete.prihvati(this);
      }
      ispisiRezultate();
    } else if (vozniRedBaseComposite instanceof VlakComposite) {
      VlakComposite vlak = (VlakComposite) vozniRedBaseComposite;
      boolean sveEtapeVoze = true;

      // Provjeri sve etape vlaka
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
  public void posjetiElement(EtapaLeaf etapaLeaf) {
    // ne radi nis za ovu komandu
  }

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
    System.out.println("\nVLAKOVI KOJI VOZE NA DANE: " + trazeniDani);
    System.out.printf("%-8s %-8s %-23s %-23s %-8s %-8s %-15s%n", "Vlak", "Pruga", "Polazna",
        "Odredišna", "Polazak", "Dolazak", "Dani");
    System.out.println("-".repeat(95));
    int brojac = 0;
    for (VlakComposite vlak : vlakoviKojiVoze) {
      for (VozniRedComponent etapa : vlak.dohvatiDjecu()) {
        if (etapa instanceof EtapaLeaf) {
          EtapaLeaf e = (EtapaLeaf) etapa;
          System.out.printf("%-8s %-8s %-23s %-23s %02d:%02d %02d:%02d %-15s%n",
              vlak.getOznakaVlaka(), e.getOznakaPruge(), e.getPocetnaStanica(),
              e.getZavrsnaStanica(), e.getVrijemePolaskaUMinutama() / 60,
              e.getVrijemePolaskaUMinutama() % 60, e.getVrijemeDolaskaUMinutama() / 60,
              e.getVrijemeDolaskaUMinutama() % 60, e.getOznakaDana());
          brojac++;
        }
      }
    }
    System.out.println("Ukupno vozi vlakova: " + brojac);
  }
}
