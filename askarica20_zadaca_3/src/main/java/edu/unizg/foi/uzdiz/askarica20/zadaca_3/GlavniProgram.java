package edu.unizg.foi.uzdiz.askarica20.zadaca_3;

import java.io.File;

import edu.unizg.foi.uzdiz.askarica20.zadaca_3.citaccsvdatotekafactory.CsvCitacCreator;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.citaccsvdatotekafactory.CsvCitacKompozicijaCreator;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.citaccsvdatotekafactory.CsvCitacOznakaDanaCreator;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.citaccsvdatotekafactory.CsvCitacProduct;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.citaccsvdatotekafactory.CsvCitacStanicaCreator;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.citaccsvdatotekafactory.CsvCitacVozilaCreator;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.citaccsvdatotekafactory.CsvCitacVoznogRedaCreator;

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
    int zsZastavica = 0, zpsZastavica = 0, zkZastavica = 0, zvrZastavica = 0, zodZastavica = 0;

    if (args.length != 10) {
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
        case "--zvr":
          zvrZastavica++;
          break;
        case "--zod":
          zodZastavica++;
          break;
        default:
          return false;
      }

      File file = new File(datoteka);
      if (!file.exists()) {
        throw new IllegalArgumentException("Datoteka ne postoji: " + datoteka);
      }

      if (datoteka.isEmpty()) {
        throw new IllegalArgumentException("Datoteka u argumentima je prazna.");
      }

    }

    if (zsZastavica == 1 && zpsZastavica == 1 && zkZastavica == 1 && zvrZastavica == 1
        && zodZastavica == 1) {
      return true;
    } else {
      throw new IllegalArgumentException("Nema po jedan dokument za svaku zastavicu.");
    }
  }

  public static void ucitajDatoteke(String[] args) {
    String stanicaDatoteka = null, vozilaDatoteka = null, kompozicijaDatoteka = null,
        vozniRedDatoteka = null, oznakaDanaDatoteka = null;

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
        case "--zvr":
          vozniRedDatoteka = datoteka;
          break;
        case "--zod":
          oznakaDanaDatoteka = datoteka;
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
    if (oznakaDanaDatoteka != null) {
      procesirajDatoteku(oznakaDanaDatoteka, new CsvCitacOznakaDanaCreator());
    }
    if (vozniRedDatoteka != null) {
      procesirajDatoteku(vozniRedDatoteka, new CsvCitacVoznogRedaCreator());
    }

  }

  private static void procesirajDatoteku(String datoteka, CsvCitacCreator kreator) {
    CsvCitacProduct konkretanCitac = kreator.kreirajCitac();
    konkretanCitac.ucitaj(datoteka);
  }

}
