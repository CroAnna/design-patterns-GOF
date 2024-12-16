package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import java.util.Collections;
import java.util.List;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedBaseComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComponent;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Stanica;

public class IspisVoznogRedaVisitor implements VozniRedVisitor {
  // za IVRV - pregled voznog reda vlaka

  private String oznakaVlaka;
  int udaljenost = 0;
  VlakComposite vlak = null;

  public IspisVoznogRedaVisitor(String oznakaVlaka) {
    this.oznakaVlaka = oznakaVlaka;
  }

  @Override
  public void posjetiElement(VozniRedBaseComposite vozniRedBaseComposite) {
    if (vozniRedBaseComposite instanceof VozniRedComposite) {
      if (!vozniRedBaseComposite.postojiLi(oznakaVlaka)) {
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
    // System.out.println("tip vlaka " + tipVlaka);
    if (tipVlaka.equals("N")) {
      return stanica.getVrNorm();
    } else if (tipVlaka.equals("U")) {
      return stanica.getVrUbrz();
    } else if (tipVlaka.equals("B")) {
      return stanica.getVrBrzi();
    }
    return 0;
  }

  @Override
  public void posjetiElement(EtapaLeaf etapaLeaf) {
    if (etapaLeaf.getOznakaVlaka().equals(oznakaVlaka)) {
      List<Stanica> staniceEtape = etapaLeaf.getListaStanicaEtape();

      System.out.println("lista stanica etape ima " + staniceEtape.size());

      System.out.println("smjer " + etapaLeaf.getSmjer());

      if (etapaLeaf.getSmjer().equals("O")) {
        System.out.println("okretanje smjera");
        Collections.reverse(staniceEtape);
      }

      Integer vrijeme = 0, index = 0, vrijemeZaIspis = 0;
      Stanica prethodna = null;
      for (Stanica s : staniceEtape) {
        System.out.println("stanica " + s.getNazivStanice() + ", udaljenost:" + s.getDuzina());

      }


      for (Stanica s : staniceEtape) {

        // System.out.println("vrijeme " + vrijeme);

        String smjer = etapaLeaf.getSmjer();

        if (smjer.equals("N")) {
          udaljenost = udaljenost + s.getDuzina();
          vrijeme = vrijeme + dohvatiVrijeme(vlak.getVrstaVlaka(), s);
          vrijemeZaIspis = etapaLeaf.getVrijemePolaskaUMinutama() + vrijeme;
        } else if (smjer.equals("O")) {
          if (index > 0) { // if not the first station
            // Add distance from previous station
            udaljenost = udaljenost + staniceEtape.get(index - 1).getDuzina();
            // Add time from previous station
            vrijeme = vrijeme + dohvatiVrijeme(vlak.getVrstaVlaka(), staniceEtape.get(index - 1));
          }
          vrijemeZaIspis = etapaLeaf.getVrijemePolaskaUMinutama() + vrijeme;
        }


        // System.out
        // .println(prethodna != null && s.getNazivStanice().equals(prethodna.getNazivStanice()));
        // System.out.println(index == staniceEtape.size() - 1);

        if (prethodna != null && smjer.equals("N")
            && s.getNazivStanice().equals(prethodna.getNazivStanice())
            && index == staniceEtape.size() - 1) {
          // ne ispisuj ak je smjer N i ak su 2 iste za redom i ak je to zadnja stanica etape
        } else {
          System.out.printf("%-13s %-13s %-30s %-8s %-18s%n", etapaLeaf.getOznakaVlaka(),
              etapaLeaf.getOznakaPruge(), s.getNazivStanice(),
              pretvoriMinuteUVrijeme(vrijemeZaIspis), udaljenost);
        }

        // if (index != 0) {
        prethodna = s;
        // }
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
