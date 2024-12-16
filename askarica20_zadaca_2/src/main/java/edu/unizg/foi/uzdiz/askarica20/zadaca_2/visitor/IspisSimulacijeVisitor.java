package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedBaseComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComponent;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Stanica;

public class IspisSimulacijeVisitor implements VozniRedVisitor {
  private final String oznakaVlaka;
  private final String oznakaDana;
  private final int koeficijent;
  private int virtualnoVrijeme;
  private boolean simulacijaAktivna = true;
  private VlakComposite vlak = null;
  private final Map<Integer, StanicniDogadaj> rasporedDogadaja;
  private Integer posljednjiDogadaj = null;

  private static class StanicniDogadaj {
    final String nazivStanice;
    final String oznakaPruge;
    final boolean zadnjaStanica;

    StanicniDogadaj(String nazivStanice, String oznakaPruge, boolean zadnjaStanica) {
      this.nazivStanice = nazivStanice;
      this.oznakaPruge = oznakaPruge;
      this.zadnjaStanica = zadnjaStanica;
    }
  }

  public IspisSimulacijeVisitor(String oznakaVlaka, String oznakaDana, int koeficijent) {
    this.oznakaVlaka = oznakaVlaka;
    this.oznakaDana = oznakaDana;
    this.koeficijent = koeficijent;
    this.rasporedDogadaja = new TreeMap<>();
  }

  @Override
  public void posjetiElement(VozniRedBaseComposite vozniRedBaseComposite) {
    if (vozniRedBaseComposite instanceof VozniRedComposite) {
      if (!vozniRedBaseComposite.postojiLi(oznakaVlaka)) {
        System.out.println("\nVlak s oznakom " + oznakaVlaka + " ne postoji u voznom redu.");
        return;
      }

      for (VozniRedComponent dijete : vozniRedBaseComposite.dohvatiDjecu()) {
        if (dijete instanceof VlakComposite) {
          VlakComposite trenutniVlak = (VlakComposite) dijete;
          if (trenutniVlak.getOznakaVlaka().equals(oznakaVlaka)) {
            vlak = trenutniVlak;
            EtapaLeaf prvaEtapa = (EtapaLeaf) vlak.dohvatiDjecu().get(0);

            if (!voziNaDan(prvaEtapa.getOznakaDana(), oznakaDana)) {
              System.out.println("\nVlak " + oznakaVlaka + " ne vozi na dan " + oznakaDana);
              return;
            }

            virtualnoVrijeme = prvaEtapa.getVrijemePolaskaUMinutama();
            break;
          }
        }
      }

      if (vlak != null) {
        for (VozniRedComponent dijete : vlak.dohvatiDjecu()) {
          dijete.prihvati(this);
        }
        pokreniSimulaciju();
      }
    } else if (vozniRedBaseComposite instanceof VlakComposite) {
      vlak = (VlakComposite) vozniRedBaseComposite;
      for (VozniRedComponent dijete : vozniRedBaseComposite.dohvatiDjecu()) {
        dijete.prihvati(this);
      }
    }
  }

  private boolean voziNaDan(String oznakaDanaIzEtape, String trazeniDan) {
    String prviDani = oznakaDanaIzEtape.split("\\s+")[0];
    return prviDani.contains(trazeniDan);
  }

  @Override
  public void posjetiElement(EtapaLeaf etapaLeaf) {
    if (!etapaLeaf.getOznakaVlaka().equals(oznakaVlaka)) {
      return;
    }

    List<Stanica> staniceEtape = new ArrayList<>(etapaLeaf.getListaStanicaEtape());
    int vrijeme = etapaLeaf.getVrijemePolaskaUMinutama();

    if (etapaLeaf.getSmjer().equals("O")) {
      Collections.reverse(staniceEtape);
    }

    for (int i = 0; i < staniceEtape.size(); i++) {
      Stanica trenutnaStanica = staniceEtape.get(i);
      boolean zadnjaStanica = trenutnaStanica.getNazivStanice().equals(vlak.getZavrsnaStanica())
          && etapaLeaf.equals(vlak.dohvatiDjecu().get(vlak.dohvatiDjecu().size() - 1));

      // Dodajemo događaj za ovu stanicu
      rasporedDogadaja.put(vrijeme, new StanicniDogadaj(trenutnaStanica.getNazivStanice(),
          etapaLeaf.getOznakaPruge(), zadnjaStanica));

      if (posljednjiDogadaj == null || vrijeme > posljednjiDogadaj) {
        posljednjiDogadaj = vrijeme;
      }

      // Računamo vrijeme do sljedeće stanice
      if (i < staniceEtape.size() - 1) {
        if (etapaLeaf.getSmjer().equals("O")) {
          vrijeme += staniceEtape.get(i).getVrNorm();
        } else {
          vrijeme += trenutnaStanica.getVrNorm();
        }
      }
    }
  }

  private void pokreniSimulaciju() {
    System.out.println("\nPočetak simulacije vožnje vlaka " + vlak.getOznakaVlaka());
    System.out.println("Virtualno vrijeme: " + pretvoriMinuteUVrijeme(virtualnoVrijeme));

    while (virtualnoVrijeme <= posljednjiDogadaj && simulacijaAktivna) {
      try {
        Thread.sleep(1000 * 60 / koeficijent);
        virtualnoVrijeme++;

        System.out.println("Virtualno vrijeme: " + pretvoriMinuteUVrijeme(virtualnoVrijeme));

        if (rasporedDogadaja.containsKey(virtualnoVrijeme)) {
          StanicniDogadaj dogadaj = rasporedDogadaja.get(virtualnoVrijeme);
          System.out.println("\n=== DOLAZAK NA STANICU === " + dogadaj.nazivStanice + " ("
              + dogadaj.oznakaPruge + ") u " + pretvoriMinuteUVrijeme(virtualnoVrijeme));

          // Obavještavanje observera
          vlak.obavijestiObservere(
              String.format("Vlak %s stigao na stanicu %s u %s", vlak.getOznakaVlaka(),
                  dogadaj.nazivStanice, pretvoriMinuteUVrijeme(virtualnoVrijeme)));

          if (dogadaj.zadnjaStanica) {
            System.out.println("Vlak stigao na odredišnu stanicu.");
            break;
          }
        }
      } catch (InterruptedException e) {
        simulacijaAktivna = false;
        Thread.currentThread().interrupt();
      }
    }
    System.out.println("\nSimulacija završena.");
  }

  private String pretvoriMinuteUVrijeme(int minute) {
    int sati = minute / 60;
    int preostaleMinute = minute % 60;
    return String.format("%02d:%02d", sati, preostaleMinute);
  }
}
