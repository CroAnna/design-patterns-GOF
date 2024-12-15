package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import java.util.List;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedBaseComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComponent;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.SimulatorVremena;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Stanica;

public class IspisSimulacijeVisitor implements VozniRedVisitor {
  private String oznakaVlaka;
  private String oznakaDana;
  private int koeficijent;
  private VlakComposite vlak = null;
  private SimulatorVremena simulator;

  public IspisSimulacijeVisitor(String oznakaVlaka, String oznakaDana, int koeficijent) {
    this.oznakaVlaka = oznakaVlaka;
    this.oznakaDana = oznakaDana;
    this.koeficijent = koeficijent;
  }

  @Override
  public void posjetiElement(VozniRedBaseComposite vozniRedBaseComposite) {
    if (vozniRedBaseComposite instanceof VozniRedComposite) {
      if (!vozniRedBaseComposite.postojiLi(oznakaVlaka)) {
        System.out.println("\nVlak s oznakom " + oznakaVlaka + " ne postoji u voznom redu.");
        return;
      }

      // Pronađi vlak koji simuliramo
      for (VozniRedComponent dijete : vozniRedBaseComposite.dohvatiDjecu()) {
        if (dijete instanceof VlakComposite) {
          VlakComposite trenutniVlak = (VlakComposite) dijete;
          if (trenutniVlak.getOznakaVlaka().equals(oznakaVlaka)) {
            vlak = trenutniVlak;
            // Dohvati vrijeme polaska iz prve etape
            EtapaLeaf prvaEtapa = (EtapaLeaf) vlak.dohvatiDjecu().get(0);
            int pocetnoVrijeme = prvaEtapa.getVrijemePolaskaUMinutama();
            simulator = new SimulatorVremena(pocetnoVrijeme, koeficijent);
            break;
          }
        }
      }

      if (vlak != null) {
        for (VozniRedComponent dijete : vozniRedBaseComposite.dohvatiDjecu()) {
          if (dijete instanceof VlakComposite
              && ((VlakComposite) dijete).getOznakaVlaka().equals(oznakaVlaka)) {
            dijete.prihvati(this);
          }
        }

        System.out.println("\nPočetak simulacije vožnje vlaka " + oznakaVlaka);
        simulator.pokreniSimulaciju();
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

    List<Stanica> staniceEtape = etapaLeaf.getListaStanicaEtape();
    int virtualnoVrijeme = etapaLeaf.getVrijemePolaskaUMinutama();

    for (int i = 0; i < staniceEtape.size(); i++) {
      Stanica trenutnaStanica = staniceEtape.get(i);

      simulator.dodajDogadaj(virtualnoVrijeme, vlak, trenutnaStanica.getNazivStanice(),
          etapaLeaf.getOznakaPruge());

      if (i < staniceEtape.size() - 1) {
        virtualnoVrijeme += dohvatiVrijeme(vlak.getVrstaVlaka(), trenutnaStanica);
      }
    }
  }

  private int dohvatiVrijeme(String tipVlaka, Stanica stanica) {
    switch (tipVlaka) {
      case "N":
        return stanica.getVrNorm();
      case "U":
        return stanica.getVrUbrz();
      case "B":
        return stanica.getVrBrzi();
      default:
        return 0;
    }
  }
}

