package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import java.util.ArrayList;
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
      return -1; // vlak ne staje na stanici
    }
  }

  @Override
  public void posjetiElement(EtapaLeaf etapaLeaf) {
    if (etapaLeaf.getOznakaVlaka().equals(oznakaVlaka)) {
      List<Stanica> staniceEtape = new ArrayList<>(etapaLeaf.getListaStanicaEtape());

      // System.out.println("lista stanica etape ima " + staniceEtape.size());

      System.out.println("smjer " + etapaLeaf.getSmjer());

      if (etapaLeaf.getSmjer().equals("O")) {
        System.out.println("okretanje smjera");
        Collections.reverse(staniceEtape);
      }

      Integer vrijeme = 0, index = 0, vrijemeZaIspis = 0;
      Stanica prethodna = null;
      for (Stanica s : staniceEtape) {
        // System.out.println("stanica " + s.getNazivStanice() + ", udaljenost:" + s.getDuzina());

      }


      for (Stanica s : staniceEtape) {

        // System.out.println("vrijeme " + vrijeme);

        String smjer = etapaLeaf.getSmjer();

        if (smjer.equals("N")) {
          if (!(index == 0 && s.getDuzina() != 0)) {
            udaljenost = udaljenost + s.getDuzina();
          }
          int dohvacenoVrijeme = dohvatiVrijeme(vlak.getVrstaVlaka(), s);
          // System.out.println("dohvacenoVrijeme " + dohvacenoVrijeme);
          if (dohvacenoVrijeme >= 0) {
            vrijeme = vrijeme + dohvacenoVrijeme;
          } else {
            // ak je dohvacen -1 koji znaci da ne staje na toj stanici nemoj dodat taj -1, a ne mogu
            // vracat 0 jer to ne znaci da ne staje na toj stanici...
            vrijeme = vrijeme + dohvacenoVrijeme + 1;
          }
          // System.out.println("vrijeme " + vrijeme);
          vrijemeZaIspis = etapaLeaf.getVrijemePolaskaUMinutama() + vrijeme;
          // System.out.println("vrijemeZaIspis " + vrijemeZaIspis);
        } else if (smjer.equals("O")) {
          if (index > 0) { // if not the first station
            // Add distance from previous station
            udaljenost = udaljenost + staniceEtape.get(index - 1).getDuzina();
            // Add time from previous station
            // vrijeme = vrijeme + dohvatiVrijeme(vlak.getVrstaVlaka(), staniceEtape.get(index -
            // 1));


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


        // System.out
        // .println(prethodna != null && s.getNazivStanice().equals(prethodna.getNazivStanice()));
        // System.out.println(index == staniceEtape.size() - 1);

        if (prethodna != null && smjer.equals("N") && vlak.getVrstaVlaka().equals("N")
            && s.getNazivStanice().equals(prethodna.getNazivStanice())
            && index == staniceEtape.size() - 1) {
          // ne ispisuj ak je smjer N i ak su 2 iste za redom i ak je to zadnja stanica etape i ak
          // je vlak normalni N
        } else if (vlak.getVrstaVlaka().equals("B") && smjer.equals("N")
            && dohvatiVrijeme("B", s) == -1) {
          // brzi i normalni smjer i tu nema vrijednosti za vrijeme (znaci da ne staje na toj
          // stanici)
          System.out.println("ne staje na " + s.getNazivStanice());

        } else if (vlak.getVrstaVlaka().equals("U") && smjer.equals("N")
            && dohvatiVrijeme("U", s) == -1) {
          // brzi i normalni smjer i tu nema vrijednosti za vrijeme (znaci da ne staje na toj
          // stanici)
          System.out.println("ne staje na " + s.getNazivStanice());

        } else if (vlak.getVrstaVlaka().equals("B") && smjer.equals("O")
            && dohvatiVrijeme("B", s) == -1) {
          // brzi i obrnuti smjer i tu nema vrijednosti za vrijeme (znaci da ne staje na toj
          // stanici)
          System.out.println("ne staje na " + s.getNazivStanice());

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
