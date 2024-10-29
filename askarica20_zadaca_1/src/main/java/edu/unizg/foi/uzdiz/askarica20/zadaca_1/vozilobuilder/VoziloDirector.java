package edu.unizg.foi.uzdiz.askarica20.zadaca_1.vozilobuilder;

import edu.unizg.foi.uzdiz.askarica20.zadaca_1.dto.Vozilo;

public class VoziloDirector {
  private VoziloBuilder voziloBuilder;

  public VoziloDirector(final VoziloBuilder voziloBuilder) {
    this.voziloBuilder = voziloBuilder;
  }

  public Vozilo konstruirajLokomotivu(int id, String oznaka, String opis, String proizvodac,
      int godina, String namjena, String vrstaPrijevoza, String vrstaPogona, int maksimalnaBrzina,
      double maksimalnaSnaga, String statusVozila) {
    return voziloBuilder.setId(id).setOznaka(oznaka).setOpis(opis).setProizvodac(proizvodac)
        .setGodina(godina).setNamjena(namjena).setVrstaPrijevoza(vrstaPrijevoza)
        .setVrstaPogona(vrstaPogona).setMaksimalnaBrzina(maksimalnaBrzina)
        .setMaksimalnaSnaga(maksimalnaSnaga).setStatusVozila(statusVozila).izgradi();
  }

  public Vozilo konstruirajPutnickiVagon(int id, String oznaka, String opis, String proizvodac,
      int godina, String namjena, String vrstaPrijevoza, String vrstaPogona, int maksimalnaBrzina,
      double maksimalnaSnaga, int brojSjedecihMjesta, int brojStajacihMjesta, int brojBicikala,
      int brojKreveta, String statusVozila) {
    return voziloBuilder.setId(id).setOznaka(oznaka).setOpis(opis).setProizvodac(proizvodac)
        .setGodina(godina).setNamjena(namjena).setVrstaPrijevoza(vrstaPrijevoza)
        .setVrstaPogona(vrstaPogona).setMaksimalnaBrzina(maksimalnaBrzina)
        .setMaksimalnaSnaga(maksimalnaSnaga).setBrojSjedecihMjesta(brojSjedecihMjesta)
        .setBrojStajacihMjesta(brojStajacihMjesta).setBrojBicikala(brojBicikala)
        .setBrojKreveta(brojKreveta).setStatusVozila(statusVozila).izgradi();
  }

  public Vozilo konstruirajTeretniAutomobilskiVagon(int id, String oznaka, String opis,
      String proizvodac, int godina, String namjena, String vrstaPrijevoza, String vrstaPogona,
      int maksimalnaBrzina, double maksimalnaSnaga, int brojAutomobila, double nosivost,
      double povrsina, String statusVozila) {
    return voziloBuilder.setId(id).setOznaka(oznaka).setOpis(opis).setProizvodac(proizvodac)
        .setGodina(godina).setNamjena(namjena).setVrstaPrijevoza(vrstaPrijevoza)
        .setVrstaPogona(vrstaPogona).setMaksimalnaBrzina(maksimalnaBrzina)
        .setMaksimalnaSnaga(maksimalnaSnaga).setBrojAutomobila(brojAutomobila).setNosivost(nosivost)
        .setPovrsina(povrsina).setStatusVozila(statusVozila).izgradi();
  }

  public Vozilo konstruirajTeretniKontejnerskiVagon(int id, String oznaka, String opis,
      String proizvodac, int godina, String namjena, String vrstaPrijevoza, String vrstaPogona,
      int maksimalnaBrzina, double maksimalnaSnaga, double nosivost, double povrsina,
      String statusVozila) {
    return voziloBuilder.setId(id).setOznaka(oznaka).setOpis(opis).setProizvodac(proizvodac)
        .setGodina(godina).setNamjena(namjena).setVrstaPrijevoza(vrstaPrijevoza)
        .setVrstaPogona(vrstaPogona).setMaksimalnaBrzina(maksimalnaBrzina)
        .setMaksimalnaSnaga(maksimalnaSnaga).setNosivost(nosivost).setPovrsina(povrsina)
        .setStatusVozila(statusVozila).izgradi();
  }

  public Vozilo konstruirajTeretniRobniRasutoVagon(int id, String oznaka, String opis,
      String proizvodac, int godina, String namjena, String vrstaPrijevoza, String vrstaPogona,
      int maksimalnaBrzina, double maksimalnaSnaga, double nosivost, double povrsina,
      int zapremnina, String statusVozila) {
    return voziloBuilder.setId(id).setOznaka(oznaka).setOpis(opis).setProizvodac(proizvodac)
        .setGodina(godina).setNamjena(namjena).setVrstaPrijevoza(vrstaPrijevoza)
        .setVrstaPogona(vrstaPogona).setMaksimalnaBrzina(maksimalnaBrzina)
        .setMaksimalnaSnaga(maksimalnaSnaga).setNosivost(nosivost).setPovrsina(povrsina)
        .setZapremnina(zapremnina).setStatusVozila(statusVozila).izgradi();
  }

  public Vozilo konstruirajTeretniRobniTekuceVagon(int id, String oznaka, String opis,
      String proizvodac, int godina, String namjena, String vrstaPrijevoza, String vrstaPogona,
      int maksimalnaBrzina, double maksimalnaSnaga, double nosivost, int zapremnina,
      String statusVozila) {
    return voziloBuilder.setId(id).setOznaka(oznaka).setOpis(opis).setProizvodac(proizvodac)
        .setGodina(godina).setNamjena(namjena).setVrstaPrijevoza(vrstaPrijevoza)
        .setVrstaPogona(vrstaPogona).setMaksimalnaBrzina(maksimalnaBrzina)
        .setMaksimalnaSnaga(maksimalnaSnaga).setNosivost(nosivost).setZapremnina(zapremnina)
        .setStatusVozila(statusVozila).izgradi();
  }
}
