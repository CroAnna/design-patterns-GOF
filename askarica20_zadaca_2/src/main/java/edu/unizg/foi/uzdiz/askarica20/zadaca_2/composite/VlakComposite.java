package edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite;

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

  // u GOF Composite (VlakComposite) treba imati kolekciju komponenti (etapa) --> etapa = dijete

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
    System.out.println("Vlak broj: " + oznakaVlaka);
    System.out.println("Etape vlaka:");
    for (VozniRedComponent etapa : djeca) {
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
