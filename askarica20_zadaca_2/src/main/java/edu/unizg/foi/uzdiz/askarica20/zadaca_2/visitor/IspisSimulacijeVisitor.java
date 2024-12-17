package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
  private volatile boolean simulacijaAktivna = true;
  private VlakComposite vlak = null;
  private final Map<Integer, StanicniDogadaj> rasporedDogadaja;
  private Integer posljednjiDogadaj = null;
  private Thread inputThread;

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

  private boolean voziNaDan(String oznakaDanaIzEtape, String trazeniDan) {
    System.out.println("vozi na " + oznakaDanaIzEtape + ", a u komandi je " + trazeniDan);
    String prviDani = oznakaDanaIzEtape.split("\\s+")[0];
    return prviDani.contains(trazeniDan);
  }

  @Override
  public void posjetiElement(VozniRedBaseComposite vozniRedBaseComposite) {
    if (vozniRedBaseComposite instanceof VozniRedComposite) {
      VozniRedComposite red = (VozniRedComposite) vozniRedBaseComposite;
      if (!red.postojiLi(oznakaVlaka)) {
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

    if (etapaLeaf.getSmjer().equals("O")) {
      // Handle starting station
      Stanica prvaStanica = staniceEtape.get(0);
      boolean isStartingStationLastStation =
          prvaStanica.getNazivStanice().equals(vlak.getZavrsnaStanica())
              && etapaLeaf.equals(vlak.dohvatiDjecu().get(vlak.dohvatiDjecu().size() - 1));

      rasporedDogadaja.put(vrijeme, new StanicniDogadaj(prvaStanica.getNazivStanice(),
          etapaLeaf.getOznakaPruge(), isStartingStationLastStation));

      if (posljednjiDogadaj == null || vrijeme > posljednjiDogadaj) {
        posljednjiDogadaj = vrijeme;
      }

      for (int i = 0; i < staniceEtape.size() - 1; i++) {
        System.out.println("O trenutna " + staniceEtape.get(i));
      }

      // Handle subsequent stations
      for (int i = 0; i < staniceEtape.size() - 1; i++) {
        Stanica trenutnaStanica = staniceEtape.get(i);
        Stanica sljedecaStanica = staniceEtape.get(i + 1);
        System.out.println("tr " + trenutnaStanica.getNazivStanice()
            + pretvoriMinuteUVrijeme(trenutnaStanica.getVrNorm()));


        vrijeme += trenutnaStanica.getVrNorm();


        boolean zadnjaStanica = sljedecaStanica.getNazivStanice().equals(vlak.getZavrsnaStanica())
            && etapaLeaf.equals(vlak.dohvatiDjecu().get(vlak.dohvatiDjecu().size() - 1));

        rasporedDogadaja.put(vrijeme, new StanicniDogadaj(sljedecaStanica.getNazivStanice(),
            etapaLeaf.getOznakaPruge(), zadnjaStanica));

        if (vrijeme > posljednjiDogadaj) {
          posljednjiDogadaj = vrijeme;
        }
      }
    }

    // TODO isto za N
    if (etapaLeaf.getSmjer().equals("N")) {
      // Handle starting station
      Stanica prvaStanica = staniceEtape.get(0);
      Stanica zadnja = staniceEtape.get(staniceEtape.size() - 1);

      System.out.println("zadnja - " + zadnja.getNazivStanice());
      System.out.println("ova etapa je zadnja- "
          + etapaLeaf.equals(vlak.dohvatiDjecu().get(vlak.dohvatiDjecu().size() - 1)));
      System.out.println("ova etapa je prva - " + etapaLeaf.equals(vlak.dohvatiDjecu().get(0)));

      rasporedDogadaja.put(vrijeme,
          new StanicniDogadaj(prvaStanica.getNazivStanice(), etapaLeaf.getOznakaPruge(), false));

      if (posljednjiDogadaj == null || vrijeme > posljednjiDogadaj) {
        posljednjiDogadaj = vrijeme;
      }

      // Your debug output for stations
      for (int i = 0; i < staniceEtape.size(); i++) {
        System.out.println("N trenutna ");
        System.out.println("Naziv stanice: " + staniceEtape.get(i).getNazivStanice());
        System.out.println("Oznaka pruge: " + staniceEtape.get(i).getOznakaPruge());
        System.out.println("Duzina: " + staniceEtape.get(i).getDuzina());
      }

      // Handle middle stations
      for (int i = 0; i < staniceEtape.size(); i++) {
        Stanica trenutnaStanica = staniceEtape.get(i);
        // Stanica sljedecaStanica = staniceEtape.get(i + 1);

        System.out.println("tr " + trenutnaStanica.getNazivStanice()
            + pretvoriMinuteUVrijeme(trenutnaStanica.getVrNorm()));

        vrijeme += trenutnaStanica.getVrNorm();

        boolean isLastStation =
            etapaLeaf.equals(vlak.dohvatiDjecu().get(vlak.dohvatiDjecu().size() - 1))
                && i == staniceEtape.size() - 1
                && trenutnaStanica.getNazivStanice().equals(vlak.getZavrsnaStanica());

        /*
         * rasporedDogadaja.put(vrijeme, new StanicniDogadaj(sljedecaStanica.getNazivStanice(),
         * etapaLeaf.getOznakaPruge(), isLastStation));
         */
        rasporedDogadaja.put(vrijeme, new StanicniDogadaj(trenutnaStanica.getNazivStanice(),
            etapaLeaf.getOznakaPruge(), isLastStation));

        if (vrijeme > posljednjiDogadaj) {
          posljednjiDogadaj = vrijeme;
        }
      }
    }
  }

  private void pokreniSimulaciju() {
    System.out
        .println("\nPočetak simulacije vožnje vlaka (X + ENTER za prekid)" + vlak.getOznakaVlaka());
    System.out.println("Pritisnite 'X' za prekid simulacije");
    System.out.println("Virtualno vrijeme: " + pretvoriMinuteUVrijeme(virtualnoVrijeme));

    pokreniPracenjeUnosa();

    while (virtualnoVrijeme <= posljednjiDogadaj && simulacijaAktivna) {
      try {
        Thread.sleep(1000 * 60 / koeficijent);
        virtualnoVrijeme++;

        System.out.println("Virtualno vrijeme: " + pretvoriMinuteUVrijeme(virtualnoVrijeme));

        if (rasporedDogadaja.containsKey(virtualnoVrijeme)) {
          StanicniDogadaj dogadaj = rasporedDogadaja.get(virtualnoVrijeme);

          System.out.println("\n=== DOLAZAK NA STANICU === " + dogadaj.nazivStanice + " ("
              + dogadaj.oznakaPruge + ") u " + pretvoriMinuteUVrijeme(virtualnoVrijeme));

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

    zaustaviPracenjeUnosa();
    System.out.println("\nSimulacija završena.");
  }

  private void pokreniPracenjeUnosa() {
    inputThread = new Thread(() -> {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      try {
        while (simulacijaAktivna) {
          if (reader.ready()) {
            int input = reader.read();
            if (input == 'X' || input == 'x') {
              simulacijaAktivna = false;
              System.out.println("\nZaustavljanje simulacije...");
              break;
            }
          }
          Thread.sleep(100);
        }
      } catch (IOException | InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });
    inputThread.setDaemon(true);
    inputThread.start();
  }

  private void zaustaviPracenjeUnosa() {
    if (inputThread != null && inputThread.isAlive()) {
      inputThread.interrupt();
      try {
        inputThread.join(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  private String pretvoriMinuteUVrijeme(int minute) {
    int sati = minute / 60;
    int preostaleMinute = minute % 60;
    return String.format("%02d:%02d", sati, preostaleMinute);
  }
}
