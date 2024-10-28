package edu.unizg.foi.uzdiz.askarica20.zadaca_1;

public class GlavniProgram {

  public static void main(String[] args) {
    try {
      if (!provjeriSastavArgumenata(args)) {
        System.out.println("Neispravni argumenti.");
      } else {
        System.out.println("-----------ok argumenti----------------");
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
      String zastavica = args[i];
      String datoteka = args[i + 1];

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
          return false; // Nepoznata zastavica
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
}
