package edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;

public class SimulatorVremena {
  private int virtualnoVrijeme;
  private final int koeficijent;
  private final Map<Integer, List<DogadajStanice>> rasporedDogadaja;
  private boolean simulacijaAktivna = true;

  public static class DogadajStanice {
    public final VlakComposite vlak;
    public final String nazivStanice;
    public final String oznakaPruge;

    public DogadajStanice(VlakComposite vlak, String nazivStanice, String oznakaPruge) {
      this.vlak = vlak;
      this.nazivStanice = nazivStanice;
      this.oznakaPruge = oznakaPruge;
    }
  }

  public SimulatorVremena(int pocetnoVrijeme, int koeficijent) {
    this.virtualnoVrijeme = pocetnoVrijeme;
    this.koeficijent = koeficijent;
    this.rasporedDogadaja = new TreeMap<>();
  }

  public void dodajDogadaj(int vrijeme, VlakComposite vlak, String nazivStanice,
      String oznakaPruge) {
    rasporedDogadaja.computeIfAbsent(vrijeme, k -> new ArrayList<>())
        .add(new DogadajStanice(vlak, nazivStanice, oznakaPruge));
  }

  public void pokreniSimulaciju() {
    System.out.println("\nPočetak simulacije u " + pretvoriMinuteUVrijeme(virtualnoVrijeme));

    while (!rasporedDogadaja.isEmpty() && simulacijaAktivna) {
      try {
        Thread.sleep(1000 * 60 / koeficijent);
        virtualnoVrijeme++;

        System.out.println("Virtualno vrijeme: " + pretvoriMinuteUVrijeme(virtualnoVrijeme));

        if (rasporedDogadaja.containsKey(virtualnoVrijeme)) {
          for (DogadajStanice dogadaj : rasporedDogadaja.get(virtualnoVrijeme)) {
            System.out.printf("Vlak %s stigao na stanicu %s (pruga %s) u %s%n",
                dogadaj.vlak.getOznakaVlaka(), dogadaj.nazivStanice, dogadaj.oznakaPruge,
                pretvoriMinuteUVrijeme(virtualnoVrijeme));

            dogadaj.vlak.obavijestiObservere(
                String.format("Vlak %s stigao na stanicu %s u %s", dogadaj.vlak.getOznakaVlaka(),
                    dogadaj.nazivStanice, pretvoriMinuteUVrijeme(virtualnoVrijeme)));
          }
          rasporedDogadaja.remove(virtualnoVrijeme);
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
