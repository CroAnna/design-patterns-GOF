package edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.IspisEtapaVisitor;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.IspisVlakovaPoDanimaVisitor;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.IspisVlakovaVisitor;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.IspisVoznogRedaVisitor;

public class VlakComposite extends VozniRedBaseComposite {
  private String oznakaVlaka;
  private String vrstaVlaka; // U, B ili prazno - prazno bi mogla pretvorit u npr slovo N (normalan
                             // pri citanju retka)

  public String getVrstaVlaka() {
    return vrstaVlaka;
  }

  public void setVrstaVlaka(String vrstaVlaka) {
    this.vrstaVlaka = vrstaVlaka;
  }

  public VlakComposite(String oznakaVlaka, String vrstaVlaka) {
    this.oznakaVlaka = oznakaVlaka;
    this.vrstaVlaka = vrstaVlaka;
  }

  @Override
  public VozniRedComponent dohvatiDijete(String oznakaPruge) {
    for (VozniRedComponent etapa : djeca) {
      if (etapa instanceof EtapaLeaf && ((EtapaLeaf) etapa).getOznakaPruge().equals(oznakaPruge)) {
        return etapa;
      }
    }
    return null;
  }

  @Override
  public void prikaziDetalje() {
    System.out.println("stara metoda prikaziDetalje");
    // System.out.println("Vlak broj: " + oznakaVlaka);
    // System.out.println("Etape vlaka:");
    // for (VozniRedComponent etapa : djeca) {
    // etapa.prikaziDetalje();
    // }
  }

  public String getOznakaVlaka() { // ne znam jel tu trebaju getteri i setteri...
    return oznakaVlaka;
  }

  public void setOznakaVlaka(String oznakaVlaka) {
    this.oznakaVlaka = oznakaVlaka;
  }

  @Override
  public void prihvati(IspisVlakovaVisitor visitor) {
    System.out.println("prihvati IspisVlakovaVisitor u VlakComposite");

    visitor.posjetiElement(this);
    for (VozniRedComponent dijete : djeca) {
      dijete.prihvati(visitor);
    }
  }

  @Override
  public void prihvati(IspisEtapaVisitor visitor) {
    System.out.println("prihvati IspisEtapaVisitor u VlakComposite");

    visitor.posjetiElement(this);
    for (VozniRedComponent dijete : djeca) {
      dijete.prihvati(visitor);
    }
  }

  @Override
  public void prihvati(IspisVlakovaPoDanimaVisitor visitor) {
    System.out.println("prihvati IspisVlakovaPoDanimaVisitor u VlakComposite");

    visitor.posjetiElement(this);
    for (VozniRedComponent dijete : djeca) {
      dijete.prihvati(visitor);
    }
  }

  @Override
  public void prihvati(IspisVoznogRedaVisitor visitor) {
    System.out.println("prihvati IspisVoznogRedaVisitor u VlakComposite");
    visitor.posjetiElement(this);
    for (VozniRedComponent dijete : djeca) {
      dijete.prihvati(visitor);
    }
  }
}
