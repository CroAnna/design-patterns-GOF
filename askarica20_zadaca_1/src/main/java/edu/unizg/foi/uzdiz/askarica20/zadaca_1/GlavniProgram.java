package edu.unizg.foi.uzdiz.askarica20.zadaca_1;

import edu.unizg.foi.uzdiz.askarica20.zadaca_1.citaccsvdatotekafactory.CsvCitacCreator;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.citaccsvdatotekafactory.CsvCitacKompozicijaCreator;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.citaccsvdatotekafactory.CsvCitacProduct;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.citaccsvdatotekafactory.CsvCitacStanicaCreator;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.citaccsvdatotekafactory.CsvCitacVozilaCreator;

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
    int zsFlag = 0, zpsFlag = 0, zkFlag = 0;

    if (args.length != 6) {
      throw new IllegalArgumentException("Broj argumenata nije dobar.");
    }

    for (int i = 0; i < args.length; i += 2) {
      String zastavica = args[i], datoteka = args[i + 1];

      switch (zastavica) {
        case "--zs":
          zsFlag++;
          break;
        case "--zps":
          zpsFlag++;
          break;
        case "--zk":
          zkFlag++;
          break;
        default:
          return false;
      }

      if (datoteka.isEmpty()) {
        throw new IllegalArgumentException("Datoteka u argumentima je prazna.");
      }
    }

    if (zsFlag == 1 && zpsFlag == 1 && zkFlag == 1) {
      return true;
    } else {
      throw new IllegalArgumentException("Nema po jedan dokument za svaku zastavicu.");
    }
  }

  public static void ucitajDatoteke(String[] args) {
    String stanicaFile = null, vozilaFile = null, kompozicijaFile = null;

    for (int i = 0; i < args.length; i += 2) {
      String zastavica = args[i];
      String datoteka = args[i + 1];

      switch (zastavica) {
        case "--zs":
          stanicaFile = datoteka;
          break;
        case "--zps":
          vozilaFile = datoteka;
          break;
        case "--zk":
          kompozicijaFile = datoteka;
          break;
      }
    }

    if (stanicaFile != null) {
      processFile(stanicaFile, new CsvCitacStanicaCreator());
    }
    if (vozilaFile != null) {
      processFile(vozilaFile, new CsvCitacVozilaCreator());
    }
    if (kompozicijaFile != null) {
      processFile(kompozicijaFile, new CsvCitacKompozicijaCreator());
    }
  }

  private static void processFile(String file, CsvCitacCreator creator) {
    CsvCitacProduct concreteProduct = creator.kreirajCitac(); // factory method
    concreteProduct.ucitaj(file);
  }

}
