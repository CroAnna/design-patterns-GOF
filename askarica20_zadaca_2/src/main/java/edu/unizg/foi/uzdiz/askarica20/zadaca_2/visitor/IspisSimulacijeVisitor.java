package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import java.util.List;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedBaseComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComponent;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Stanica;

public class IspisSimulacijeVisitor implements VozniRedVisitor {
  // za SVV - simulaciju voznje vlaka na odredeni dan u tjednu uz koeficijent za sekundu

  private String oznakaVlaka;
  private String oznakaDana;
  private int koeficijent;
  VlakComposite vlak = null;

  public IspisSimulacijeVisitor(String oznakaVlaka, String oznakaDana, int koeficijent) {
    this.oznakaVlaka = oznakaVlaka;
    this.oznakaDana = oznakaDana;
    this.koeficijent = koeficijent;
  }

  @Override
  public void posjetiElement(VozniRedBaseComposite vozniRedBaseComposite) {

    if (vozniRedBaseComposite instanceof VozniRedComposite) {
      System.out.println("posjetiElement(VozniRedBaseComposite");
      if (!vozniRedBaseComposite.postojiLi(oznakaVlaka)) {
        System.out.println("\nVlak s oznakom " + oznakaVlaka + " ne postoji u voznom redu.");
        return;
      }

      // TODO dodat provjeru jel oznaka dana valjana

      for (VozniRedComponent dijete : vozniRedBaseComposite.dohvatiDjecu()) {
        dijete.prihvati(this);
      }
    } else if (vozniRedBaseComposite instanceof VlakComposite) {
      System.out.println("posjetiElement(VlakComposite");

      vlak = (VlakComposite) vozniRedBaseComposite;
      if (vlak.getOznakaVlaka().equals(oznakaVlaka)) {
        for (VozniRedComponent dijete : vozniRedBaseComposite.dohvatiDjecu()) {
          dijete.prihvati(this);
        }
      }
      // TODO trebas nekak slozit da se uzme vrijeme polaska s polazne željezničke stanice
      // to se spremi u neku varijablu (virtualno vrijeme)
      // onda se napravi nekakava petlja di se spava svaku sekundu
      // u etapi se tek ispise ta stanica kad je vrijeme jednako tom vremenu u brojacu - tad se i
      // salje notifikacija observerima (koji su pretplaceni na tu stanicu ili taj vlak di je)
      // simulacija se izvrsava dok se ne dode do zadnje stanice ili dok se ne unese X
      // nez jel se treba ispisivat stanica na kojoj se je trenutno, al za pocetak bolje nek da



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
    System.out.println("posjetiElement(EtapaLeaf");

    if (etapaLeaf.getOznakaVlaka().equals(oznakaVlaka)) {
      List<Stanica> staniceEtape = etapaLeaf.getListaStanicaEtape();
      System.out.println("stanice " + staniceEtape.size());
      for (Stanica s : staniceEtape) {
        int vrijeme = dohvatiVrijeme(vlak.getVrstaVlaka(), s);
        try {
          System.out.println("ispis");
          Thread.sleep(1000);

        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        System.out.printf(" %-13s %-13s %-30s %-8s %-18s%n", "Trenutna stanica: ",
            etapaLeaf.getOznakaVlaka(), etapaLeaf.getOznakaPruge(), s.getNazivStanice(),
            pretvoriMinuteUVrijeme(etapaLeaf.getVrijemePolaskaUMinutama() + vrijeme));
      }
    }


  }

  private String pretvoriMinuteUVrijeme(int minute) {
    int sati = minute / 60;
    int preostaleMinute = minute % 60;
    return String.format("%02d:%02d", sati, preostaleMinute);
  }

  private void pokreniSimulaciju() {

  }

}
