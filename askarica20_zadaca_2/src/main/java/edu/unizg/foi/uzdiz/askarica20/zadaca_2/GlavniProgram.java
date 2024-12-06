package edu.unizg.foi.uzdiz.askarica20.zadaca_2;

import edu.unizg.foi.uzdiz.askarica20.zadaca_2.citaccsvdatotekafactory.CsvCitacCreator;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.citaccsvdatotekafactory.CsvCitacKompozicijaCreator;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.citaccsvdatotekafactory.CsvCitacProduct;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.citaccsvdatotekafactory.CsvCitacStanicaCreator;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.citaccsvdatotekafactory.CsvCitacVozilaCreator;

public class GlavniProgram {

  public static void main(String[] args) {
    try {
      if (!provjeriSastavArgumenata(args)) {
        System.out.println("Neispravni argumenti.");
      } else {
        ucitajDatoteke(args);
        ZeljeznickiSustav.dohvatiInstancu().zapocniRadSustava();
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Greška: " + e.getMessage());
    }
  }

  public static boolean provjeriSastavArgumenata(String[] args) {
    int zsZastavica = 0, zpsZastavica = 0, zkZastavica = 0;

    if (args.length != 6) {
      throw new IllegalArgumentException("Broj argumenata nije dobar.");
    }

    for (int i = 0; i < args.length; i += 2) {
      String zastavica = args[i], datoteka = args[i + 1];

      switch (zastavica) {
        case "--zs":
          zsZastavica++;
          break;
        case "--zps":
          zpsZastavica++;
          break;
        case "--zk":
          zkZastavica++;
          break;
        default:
          return false;
      }

      if (datoteka.isEmpty()) {
        throw new IllegalArgumentException("Datoteka u argumentima je prazna.");
      }
    }

    if (zsZastavica == 1 && zpsZastavica == 1 && zkZastavica == 1) {
      return true;
    } else {
      throw new IllegalArgumentException("Nema po jedan dokument za svaku zastavicu.");
    }
  }

  public static void ucitajDatoteke(String[] args) {
    String stanicaDatoteka = null, vozilaDatoteka = null, kompozicijaDatoteka = null;

    for (int i = 0; i < args.length; i += 2) {
      String zastavica = args[i];
      String datoteka = args[i + 1];

      switch (zastavica) {
        case "--zs":
          stanicaDatoteka = datoteka;
          break;
        case "--zps":
          vozilaDatoteka = datoteka;
          break;
        case "--zk":
          kompozicijaDatoteka = datoteka;
          break;
      }
    }

    if (stanicaDatoteka != null) {
      procesirajDatoteku(stanicaDatoteka, new CsvCitacStanicaCreator());
    }
    if (vozilaDatoteka != null) {
      procesirajDatoteku(vozilaDatoteka, new CsvCitacVozilaCreator());
    }
    if (kompozicijaDatoteka != null) {
      procesirajDatoteku(kompozicijaDatoteka, new CsvCitacKompozicijaCreator());
    }
  }

  private static void procesirajDatoteku(String datoteka, CsvCitacCreator kreator) {
    CsvCitacProduct konkretanCitac = kreator.kreirajCitac();
    konkretanCitac.ucitaj(datoteka);
  }

}
