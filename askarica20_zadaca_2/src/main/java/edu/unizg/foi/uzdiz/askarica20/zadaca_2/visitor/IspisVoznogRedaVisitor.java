package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

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
      for (Stanica s : staniceEtape) {
        int vrijeme = dohvatiVrijeme(vlak.getVrstaVlaka(), s);
        udaljenost = udaljenost + s.getDuzina();

        // TODO slozit da ak je ovakvo vrijeme, da se izbrise cijeli taj vlak jer postoji greska...
        // to se mora onda nekad pri ucitavanju, a ne tek sad...

        System.out.printf("%-13s %-13s %-30s %-8s %-18s%n", etapaLeaf.getOznakaVlaka(),
            etapaLeaf.getOznakaPruge(), s.getNazivStanice(),
            pretvoriMinuteUVrijeme(etapaLeaf.getVrijemePolaskaUMinutama() + vrijeme), udaljenost);
      }
    }
  }

  private String pretvoriMinuteUVrijeme(int minute) {
    int sati = minute / 60;
    int preostaleMinute = minute % 60;
    return String.format("%02d:%02d", sati, preostaleMinute);
  }
}
