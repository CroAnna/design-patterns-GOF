

package edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto;

import java.util.Map;
import java.util.TreeMap;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;

public class SimulatorVremena {
  private int virtualnoVrijeme;
  private final int koeficijent;
  private final Map<Integer, StanicniDogadaj> rasporedDogadaja;
  private boolean simulacijaAktivna = true;
  private Integer posljednjiDogadaj = null;

  public static class StanicniDogadaj {
    public final String nazivStanice;
    public final String oznakaPruge;
    public final boolean zadnjaStanica;

    public StanicniDogadaj(String nazivStanice, String oznakaPruge, boolean zadnjaStanica) {
      this.nazivStanice = nazivStanice;
      this.oznakaPruge = oznakaPruge;
      this.zadnjaStanica = zadnjaStanica;
    }
  }

  public SimulatorVremena(int pocetnoVrijeme, int koeficijent) {
    this.virtualnoVrijeme = pocetnoVrijeme;
    this.koeficijent = koeficijent;
    this.rasporedDogadaja = new TreeMap<>();
  }

  public void dodajDogadaj(int vrijeme, String nazivStanice, String oznakaPruge,
      boolean zadnjaStanica) {
    rasporedDogadaja.put(vrijeme, new StanicniDogadaj(nazivStanice, oznakaPruge, zadnjaStanica));
    if (posljednjiDogadaj == null || vrijeme > posljednjiDogadaj) {
      posljednjiDogadaj = vrijeme;
    }
  }

  public void pokreniSimulaciju(VlakComposite vlak) {
    System.out.println("\nPočetak simulacije vožnje vlaka " + vlak.getOznakaVlaka());
    System.out.println("Virtualno vrijeme: " + pretvoriMinuteUVrijeme(virtualnoVrijeme));

    while (virtualnoVrijeme <= posljednjiDogadaj && simulacijaAktivna) {
      try {
        Thread.sleep(1000 * 60 / koeficijent);
        virtualnoVrijeme++;

        System.out.println("Virtualno vrijeme: " + pretvoriMinuteUVrijeme(virtualnoVrijeme));

        if (rasporedDogadaja.containsKey(virtualnoVrijeme)) {
          StanicniDogadaj dogadaj = rasporedDogadaja.get(virtualnoVrijeme);

          System.out.println("\n=== DOLAZAK NA STANICU ===" + dogadaj.nazivStanice + " u "
              + pretvoriMinuteUVrijeme(virtualnoVrijeme));

          vlak.obavijestiObservere(
              String.format("Vlak %s stigao na stanicu %s u %s", vlak.getOznakaVlaka(),
                  dogadaj.nazivStanice, pretvoriMinuteUVrijeme(virtualnoVrijeme)));

          if (dogadaj.zadnjaStanica) {
            System.out.println("\nVlak stigao na odredišnu stanicu.");
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
