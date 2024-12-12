package edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite;

import java.util.ArrayList;
import java.util.List;

public class VlakComposite extends VozniRedComponent {
  private String oznakaVlaka;
  private String vrstaVlaka; // U, B ili prazno - prazno bi mogla pretvorit u npr slovo N (normalan
                             // pri citanju retka)

  public String getVrstaVlaka() {
    return vrstaVlaka;
  }

  public void setVrstaVlaka(String vrstaVlaka) {
    this.vrstaVlaka = vrstaVlaka;
  }

  public List<VozniRedComponent> etape = new ArrayList<>();
  // u GOF Composite (VlakComposite) treba imati kolekciju komponenti (etapa) --> etapa = dijete

  public VlakComposite(String oznakaVlaka, String vrstaVlaka) {
    this.oznakaVlaka = oznakaVlaka;
    this.vrstaVlaka = vrstaVlaka;
  }

  @Override
  public boolean dodaj(VozniRedComponent component) {
    this.etape.add(component);
    return true;
  }

  @Override
  public VozniRedComponent dohvatiDijete(int index) {
    return etape.get(index);
  }

  @Override
  public VozniRedComponent dohvatiDijete(String oznakaPruge) {
    for (VozniRedComponent etapa : etape) {
      if (etapa instanceof EtapaLeaf && ((EtapaLeaf) etapa).getOznakaPruge().equals(oznakaPruge)) {
        return etapa;
      }
    }
    return null;
  }

  @Override
  public void prikaziDetalje() {
    System.out.println("Vlak broj: " + oznakaVlaka);
    System.out.println("Etape vlaka:");
    for (VozniRedComponent etapa : etape) {
      etapa.prikaziDetalje();
    }
  }

  public String getOznakaVlaka() { // ne znam jel tu trebaju getteri i setteri...
    return oznakaVlaka;
  }

  public void setOznakaVlaka(String oznakaVlaka) {
    this.oznakaVlaka = oznakaVlaka;
  }
}
