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
      citac.readLine();

      String redak;
      int brojRetka = 1, ukupanBrojGresakaUDatoteci = 0;

      while ((redak = citac.readLine()) != null) {
        Matcher poklapanjeStanica = predlozakStanica.matcher(redak);
        Matcher poklapanjePraznogRetka = predlozakPrazanRedak.matcher(redak);

        boolean redakDobrogFormata = poklapanjeStanica.matches();

        String[] dijeloviRetka = redak.split(";");

        // TODO dodaj provjeru jel prvi redak informativni
        // provjeriPrviRedak(dijeloviRetka,citac);

        if (poklapanjePraznogRetka.matches()) {
          continue; // prazan red se ne racuna kao greska, nego se samo treba preskociti
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
              + ". retku! Ukupno gresaka u datoteci: " + ukupanBrojGresakaUDatoteci
              + "! Ukupno gresaka u sustavu: "
              + ZeljeznickiSustav.dohvatiInstancu().dohvatiGreskeUSustavu() + "\n");
        }
        brojRetka++;
      }
      System.out.println("Stanice uspjesno ucitane.");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }


}

