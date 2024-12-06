package edu.unizg.foi.uzdiz.askarica20.zadaca_2.vozilobuilder;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Vozilo;

public class VoziloConcreteBuilder implements VoziloBuilder {
  private Vozilo vozilo;

  public VoziloConcreteBuilder() {
    vozilo = new Vozilo();
  }

  @Override
  public Vozilo izgradi() {
    return vozilo;
  }

  @Override
  public VoziloBuilder setId(int id) {
    vozilo.setId(id);
    return this;
  }

  @Override
  public VoziloBuilder setOznaka(String oznaka) {
    vozilo.setOznaka(oznaka);
    return this;
  }

  @Override
  public VoziloBuilder setOpis(String opis) {
    vozilo.setOpis(opis);
    return this;
  }

  @Override
  public VoziloBuilder setBrojSjedecihMjesta(int brojSjedecihMjesta) {
    vozilo.setBrojSjedecihMjesta(brojSjedecihMjesta);
    return this;
  }

  @Override
  public VoziloBuilder setZapremnina(int zapremnina) {
    vozilo.setZapremnina(zapremnina);
    return this;
  }

  @Override
  public VoziloBuilder setProizvodac(String proizvodac) {
    vozilo.setProizvodac(proizvodac);
    return this;
  }

  @Override
  public VoziloBuilder setGodina(int godina) {
    vozilo.setGodina(godina);
    return this;
  }

  @Override
  public VoziloBuilder setNamjena(String namjena) {
    vozilo.setNamjena(namjena);
    return this;
  }

  @Override
  public VoziloBuilder setVrstaPrijevoza(String vrstaPrijevoza) {
    vozilo.setVrstaPrijevoza(vrstaPrijevoza);
    return this;
  }

  @Override
  public VoziloBuilder setVrstaPogona(String vrstaPogona) {
    vozilo.setVrstaPogona(vrstaPogona);
    return this;
  }

  @Override
  public VoziloBuilder setMaksimalnaBrzina(int maksimalnaBrzina) {
    vozilo.setMaksimalnaBrzina(maksimalnaBrzina);
    return this;
  }

  @Override
  public VoziloBuilder setMaksimalnaSnaga(double maksimalnaSnaga) {
    vozilo.setMaksimalnaSnaga(maksimalnaSnaga);
    return this;
  }

  @Override
  public VoziloBuilder setBrojStajacihMjesta(int brojStajacihMjesta) {
    vozilo.setBrojStajacihMjesta(brojStajacihMjesta);
    return this;
  }

  @Override
  public VoziloBuilder setBrojBicikala(int brojBicikala) {
    vozilo.setBrojBicikala(brojBicikala);
    return this;
  }

  @Override
  public VoziloBuilder setBrojKreveta(int brojKreveta) {
    vozilo.setBrojKreveta(brojKreveta);
    return this;
  }

  @Override
  public VoziloBuilder setBrojAutomobila(int brojAutomobila) {
    vozilo.setBrojAutomobila(brojAutomobila);
    return this;
  }

  @Override
  public VoziloBuilder setNosivost(double nosivost) {
    vozilo.setNosivost(nosivost);
    return this;
  }

  @Override
  public VoziloBuilder setPovrsina(double povrsina) {
    vozilo.setPovrsina(povrsina);
    return this;
  }

  @Override
  public VoziloBuilder setStatusVozila(String statusVozila) {
    vozilo.setStatusVozila(statusVozila);
    return this;
  }

}
