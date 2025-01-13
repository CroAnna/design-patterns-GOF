package edu.unizg.foi.uzdiz.askarica20.zadaca_3.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.VozniRedBaseComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.VozniRedComponent;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.composite.VozniRedComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.dto.Stanica;

public class IspisVoznogRedaVisitor implements VozniRedVisitor {
  private String oznakaVlaka;
  int udaljenost = 0;
  VlakComposite vlak = null;

  public IspisVoznogRedaVisitor(String oznakaVlaka) {
    this.oznakaVlaka = oznakaVlaka;
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
        dijete.prihvati(this);
      }
    } else if (vozniRedBaseComposite instanceof VlakComposite) {
      vlak = (VlakComposite) vozniRedBaseComposite;
      if (vlak.getOznakaVlaka().equals(oznakaVlaka)) {
        System.out.println("\n\n----------------------------------- VOZNI RED VLAKA " + oznakaVlaka
            + " -----------------------------------\n");
        System.out.printf("%-13s %-13s %-23s %-8s %-18s%n", "Oznaka vlaka", "Oznaka pruge",
            "Željeznička stanica", "Vrijeme polaska", "Udaljenost od polazne");
        System.out.println(
            "--------------------------------------------------------------------------------------------");
        for (VozniRedComponent dijete : vozniRedBaseComposite.dohvatiDjecu()) {
          dijete.prihvati(this);
        }
        System.out.println(
            "\n-------------------------------------------------------------------------------------------\n");
      }
    }
  }

  private int dohvatiVrijeme(String tipVlaka, Stanica stanica) {
    try {
      switch (tipVlaka) {
        case "N":
          return stanica.getVrNorm();
        case "U":
          return stanica.getVrUbrz();
        case "B":
          return stanica.getVrBrzi();
        default:
          return -1;
      }
    } catch (NullPointerException e) {
      return -1;
    }
  }

  @Override
  public void posjetiElement(EtapaLeaf etapaLeaf) {
    if (etapaLeaf.getOznakaVlaka().equals(oznakaVlaka)) {
      List<Stanica> staniceEtape = new ArrayList<>(etapaLeaf.getListaStanicaEtape());

      System.out.println("");

      if (etapaLeaf.getSmjer().equals("O")) {
        Collections.reverse(staniceEtape);
      }

      Integer vrijeme = 0, index = 0, vrijemeZaIspis = 0;
      Stanica prethodna = null;

      for (Stanica s : staniceEtape) {
        String smjer = etapaLeaf.getSmjer();

        if (smjer.equals("N")) {
          if (!(index == 0 && s.getDuzina() != 0)) {
            udaljenost = udaljenost + s.getDuzina();
          }
          int dohvacenoVrijeme = dohvatiVrijeme(vlak.getVrstaVlaka(), s);
          if (dohvacenoVrijeme >= 0) {
            vrijeme = vrijeme + dohvacenoVrijeme;
          } else {
            vrijeme = vrijeme + dohvacenoVrijeme + 1;
          }
          vrijemeZaIspis = etapaLeaf.getVrijemePolaskaUMinutama() + vrijeme;
        } else if (smjer.equals("O")) {
          if (index > 0) {
            udaljenost = udaljenost + staniceEtape.get(index - 1).getDuzina();
            int dohvacenoVrijeme =
                dohvatiVrijeme(vlak.getVrstaVlaka(), staniceEtape.get(index - 1));
            if (dohvacenoVrijeme >= 0) {
              vrijeme = vrijeme + dohvacenoVrijeme;
            } else {
              vrijeme = vrijeme + dohvacenoVrijeme + 1;
            }
          }
          vrijemeZaIspis = etapaLeaf.getVrijemePolaskaUMinutama() + vrijeme;
        }

        if (prethodna != null && smjer.equals("N") && vlak.getVrstaVlaka().equals("N")
            && s.getNazivStanice().equals(prethodna.getNazivStanice())
            && index == staniceEtape.size() - 1) {
        } else if (vlak.getVrstaVlaka().equals("B") && smjer.equals("N")
            && dohvatiVrijeme("B", s) == -1) {
          // System.out.println("Ne staje na " + s.getNazivStanice());
        } else if (vlak.getVrstaVlaka().equals("U") && smjer.equals("N")
            && dohvatiVrijeme("U", s) == -1) {
          // System.out.println("Ne staje na " + s.getNazivStanice());

        } else if (vlak.getVrstaVlaka().equals("B") && smjer.equals("O")
            && dohvatiVrijeme("B", s) == -1) {
          // System.out.println("Ne staje na " + s.getNazivStanice());
        } else {
          System.out.printf("%-13s %-13s %-30s %-8s %-18s%n", etapaLeaf.getOznakaVlaka(),
              etapaLeaf.getOznakaPruge(), s.getNazivStanice(),
              pretvoriMinuteUVrijeme(vrijemeZaIspis), udaljenost);
        }
        prethodna = s;
        index++;
      }
    }
  }

  private String pretvoriMinuteUVrijeme(int minute) {
    int sati = minute / 60;
    int preostaleMinute = minute % 60;
    return String.format("%02d:%02d", sati, preostaleMinute);
  }
}
