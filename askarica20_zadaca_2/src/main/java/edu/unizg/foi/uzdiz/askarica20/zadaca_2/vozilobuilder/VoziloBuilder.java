package edu.unizg.foi.uzdiz.askarica20.zadaca_2.vozilobuilder;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Vozilo;

public interface VoziloBuilder {
  Vozilo izgradi();

  VoziloBuilder setId(final int id);

  VoziloBuilder setOznaka(final String oznaka);

  VoziloBuilder setOpis(final String opis);

  VoziloBuilder setProizvodac(final String proizvodac);

  VoziloBuilder setGodina(final int godina);

  VoziloBuilder setNamjena(final String namjena);

  VoziloBuilder setVrstaPrijevoza(final String vrstaPrijevoza);

  VoziloBuilder setVrstaPogona(final String vrstaPogona);

  VoziloBuilder setMaksimalnaBrzina(final int maksimalnaBrzina);

  VoziloBuilder setMaksimalnaSnaga(final double maksimalnaSnaga);

  VoziloBuilder setBrojSjedecihMjesta(final int brojSjedecihMjesta);

  VoziloBuilder setBrojStajacihMjesta(final int brojStajacihMjesta);

  VoziloBuilder setBrojBicikala(final int brojBicikala);

  VoziloBuilder setBrojKreveta(final int brojKreveta);

  VoziloBuilder setBrojAutomobila(final int brojAutomobila);

  VoziloBuilder setNosivost(final double nosivost);

  VoziloBuilder setPovrsina(final double povrsina);

  VoziloBuilder setZapremnina(final int zapremnina);

  VoziloBuilder setStatusVozila(final String statusVozila);

}
