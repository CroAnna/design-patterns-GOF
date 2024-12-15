package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import java.util.ArrayList;
import java.util.Collections;
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

      for (VozniRedComponent dijete : vozniRedBaseComposite.dohvatiDjecu()) {
        if (dijete instanceof VlakComposite) {
          VlakComposite trenutniVlak = (VlakComposite) dijete;
          if (trenutniVlak.getOznakaVlaka().equals(oznakaVlaka)) {
            vlak = trenutniVlak;
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
        simulator.pokreniSimulaciju(vlak);
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
    int virtualnoVrijeme = etapaLeaf.getVrijemePolaskaUMinutama();

    // Ako je smjer O (obrnuti), obrnemo redoslijed stanica
    if (etapaLeaf.getSmjer().equals("O")) {
      Collections.reverse(staniceEtape);
    }

    for (int i = 0; i < staniceEtape.size(); i++) {
      Stanica trenutnaStanica = staniceEtape.get(i);
      boolean zadnjaStanica = trenutnaStanica.getNazivStanice().equals(vlak.getZavrsnaStanica())
          && etapaLeaf.equals(vlak.dohvatiDjecu().get(vlak.dohvatiDjecu().size() - 1));

      // Dodaj događaj za trenutnu stanicu
      simulator.dodajDogadaj(virtualnoVrijeme, trenutnaStanica.getNazivStanice(),
          etapaLeaf.getOznakaPruge(), zadnjaStanica);

      // Ako nije zadnja stanica u etapi, dodaj vrijeme do sljedeće
      if (i < staniceEtape.size() - 1) {
        if (etapaLeaf.getSmjer().equals("O")) {
          // U obrnutom smjeru koristimo vrijeme sljedeće stanice
          virtualnoVrijeme += staniceEtape.get(i + 1).getVrNorm();
        } else {
          // U normalnom smjeru koristimo vrijeme trenutne stanice
          virtualnoVrijeme += trenutnaStanica.getVrNorm();
        }
      }
    }

    // Ako ovo nije zadnja etapa, postavi vrijeme na vrijeme polaska sljedeće etape
    if (!etapaLeaf.equals(vlak.dohvatiDjecu().get(vlak.dohvatiDjecu().size() - 1))) {
      int indexTrenutneEtape = vlak.dohvatiDjecu().indexOf(etapaLeaf);
      EtapaLeaf sljedecaEtapa = (EtapaLeaf) vlak.dohvatiDjecu().get(indexTrenutneEtape + 1);
      virtualnoVrijeme = sljedecaEtapa.getVrijemePolaskaUMinutama();
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
