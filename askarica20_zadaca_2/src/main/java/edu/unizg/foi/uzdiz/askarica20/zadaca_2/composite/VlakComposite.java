package edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.VozniRedVisitor;

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
  public boolean dodaj(VozniRedComponent component) {
    if (component instanceof EtapaLeaf) {
      int index = 0;
      for (VozniRedComponent postojecaEtapa : djeca) {
        if (postojecaEtapa instanceof EtapaLeaf) {
          EtapaLeaf etapa = (EtapaLeaf) postojecaEtapa;
          if (((EtapaLeaf) component).getVrijemePolaskaUMinutama() < etapa
              .getVrijemePolaskaUMinutama()) {
            break;
          }
        }
        index++;
      }

      djeca.add(index, component);
      return true;
    } else {
      djeca.add(component);
      return true;
    }
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

  public String getOznakaVlaka() { // ne znam jel tu trebaju getteri i setteri...
    return oznakaVlaka;
  }

  public void setOznakaVlaka(String oznakaVlaka) {
    this.oznakaVlaka = oznakaVlaka;
  }

  public boolean postojiLi(String oznakaVlaka) {
    for (VozniRedComponent komponenta : djeca) {
      if (komponenta instanceof VlakComposite) {
        VlakComposite vlak = (VlakComposite) komponenta;
        if (vlak.getOznakaVlaka().equals(oznakaVlaka)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public void prihvati(VozniRedVisitor visitor) {
    visitor.posjetiElement(this);
  }
}
