package edu.unizg.foi.uzdiz.askarica20.zadaca_1.citaccsvdatotekafactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.ZeljeznickiSustav;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.dto.Stanica;

// ConcreteProduct
public class CsvCitacStanicaProduct extends CsvCitacProduct {

  @Override
  public void ucitaj(String datoteka) {
    Pattern predlozakPrazanRedak = Pattern.compile("^;*$");
    Pattern predlozakStanica = Pattern.compile("^(?<nazivStanice>[^;]+);" + "(?<oznakaPruge>[^;]+);"
        + "(?<vrstaStanice>[^;]+);" + "(?<statusStanice>[^;]+);" + "(?<putniciUlIz>DA|NE);"
        + "(?<robaUtIst>DA|NE);" + "(?<kategorijaPruge>[^;]+);" + "(?<brojPerona>\\d+);"
        + "(?<vrstaPruge>[^;]+);" + "(?<brojKolosjeka>\\d+);" + "(?<doPoOsovini>\\d+[.,]\\d+);"
        + "(?<doPoDuznomM>\\d+[.,]\\d+);" + "(?<statusPruge>[^;]+);" + "(?<duzina>\\d+)$");

    try (BufferedReader citac = new BufferedReader(new FileReader(datoteka))) {
      String redak;
      int brojRetka = 1, ukupanBrojGresakaUDatoteci = 0;

      while ((redak = citac.readLine()) != null) {
        boolean preskociPrvog = false;
        if (brojRetka == 1) {
          preskociPrvog = prviRedakJeInformativan(redak.split(";"), citac);
        }

        if (!preskociPrvog) {
          Matcher poklapanjeStanica = predlozakStanica.matcher(redak);
          Matcher poklapanjePraznogRetka = predlozakPrazanRedak.matcher(redak);
          boolean redakDobrogFormata = poklapanjeStanica.matches();
          String[] dijeloviRetka = redak.split(";");

          if (poklapanjePraznogRetka.matches() || redak.startsWith("#")) {
            System.out.print("preskocen" + redak + "\n");
            continue; // prazan red ni ak počinje s # se ne racuna kao greska, nego se samo treba
                      // preskociti
          }

          if (redakDobrogFormata && dijeloviRetka.length == 14) {
            Stanica stanica = new Stanica(brojRetka, dijeloviRetka[0], dijeloviRetka[1],
                dijeloviRetka[2], dijeloviRetka[3], Boolean.valueOf(dijeloviRetka[4]),
                Boolean.valueOf(dijeloviRetka[5]), dijeloviRetka[6],
                Integer.valueOf(dijeloviRetka[7]), dijeloviRetka[8],
                Integer.valueOf(dijeloviRetka[9]),
                Double.parseDouble(dijeloviRetka[10].replace(',', '.')),
                Double.valueOf(dijeloviRetka[11].replace(',', '.')), dijeloviRetka[12],
                Integer.valueOf(dijeloviRetka[13]));

            ZeljeznickiSustav.dohvatiInstancu().dodajStanicu(stanica);
          } else {
            ukupanBrojGresakaUDatoteci++;
            ZeljeznickiSustav.dohvatiInstancu().dodajGreskuUSustav();

            System.out.println("Svi stupci nisu ispravno popunjeni u " + brojRetka
                + ". retku! Ukupno gresaka u datoteci stanica: " + ukupanBrojGresakaUDatoteci
                + "! Ukupno gresaka u sustavu: "
                + ZeljeznickiSustav.dohvatiInstancu().dohvatiGreskeUSustavu() + "\n");
          }
        }
        brojRetka++;
      }
      System.out.println("Stanice uspjesno ucitane.");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  private boolean prviRedakJeInformativan(String[] dijeloviRetka, BufferedReader citac) {
    String[] dijeloviRetkaBezBOM = ukloniBOM(dijeloviRetka);

    // TODO ovo bi se dalo da svi Producti koriste istu metodu - odvojit ovo nekud i da se jos
    // proslijedi lista s nazivima stupaca i da se po toj listi prolazi ILI napravi regex s kojim bu
    // se ovo sve provjeravalo

    if ("Stanica".equals(dijeloviRetkaBezBOM[0]) && "Oznaka pruge".equals(dijeloviRetkaBezBOM[1])
        && "Vrsta stanice".equals(dijeloviRetkaBezBOM[2])
        && "Status stanice".equals(dijeloviRetkaBezBOM[3])
        && "Putnici ul/iz".equals(dijeloviRetkaBezBOM[4])
        && "Roba ut/ist".equals(dijeloviRetkaBezBOM[5])
        && "Kategorija pruge".equals(dijeloviRetkaBezBOM[6])
        && "Broj perona".equals(dijeloviRetkaBezBOM[7])
        && "Vrsta pruge".equals(dijeloviRetkaBezBOM[8])
        && "Broj kolosjeka".equals(dijeloviRetkaBezBOM[9])
        && "DO po osovini".equals(dijeloviRetkaBezBOM[10])
        && "DO po duznom m".equals(dijeloviRetkaBezBOM[11])
        && "Status pruge".equals(dijeloviRetkaBezBOM[12])
        && "Dužina".equals(dijeloviRetkaBezBOM[13])) {
      System.out.println("Prvi redak stanica je informativan.");
      return true;
    } else {
      System.out.println("Prvi redak stanica nije informativan.");
      return false;
    }
  }

  private String[] ukloniBOM(String[] dijeloviRetka) { // TODO ovo svi isto koriste
    for (int i = 0; i < dijeloviRetka.length; i++) {
      dijeloviRetka[i] = dijeloviRetka[i].trim().replaceAll("[\\uFEFF]", "");
    }
    return dijeloviRetka;
  }

}
