package edu.unizg.foi.uzdiz.askarica20.zadaca_1;

import java.util.ArrayList;
import java.util.List;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.dto.Kompozicija;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.dto.Stanica;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.dto.Vozilo;

public class ZeljeznickiSustav {
  // ovo je singleton

  private static ZeljeznickiSustav instanca;

  private final List<Vozilo> listaVozila = new ArrayList();
  private final List<Stanica> listaStanica = new ArrayList();
  private final List<Kompozicija> listaKompozicija = new ArrayList();

  private ZeljeznickiSustav() {}; // singleton ima privatni defaultni konstruktor

  // staticka metoda getInstance koja se ponasa kao konstruktor
  public static ZeljeznickiSustav dohvatiInstancu() {
    if (instanca == null) {
      instanca = new ZeljeznickiSustav();
    }
    return instanca;
  }
}
