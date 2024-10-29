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
    System.out.println("ucitaj datoteku stanica");

    Pattern predlozakPrazanRedak = Pattern.compile("^;*$");
    Pattern predlozakStanica = Pattern.compile("^(?<nazivStanice>[^;]+);" + // Station name
        "(?<oznakaPruge>[^;]+);" + // Line designation
        "(?<vrstaStanice>[^;]+);" + // Station type
        "(?<statusStanice>[^;]+);" + // Station status
        "(?<putniciUlIz>DA|NE);" + // Passengers entering/exiting (boolean)
        "(?<robaUtIst>DA|NE);" + // Goods entering/exiting (boolean)
        "(?<kategorijaPruge>[^;]+);" + // Track category
        "(?<brojPerona>\\d+);" + // Number of platforms
        "(?<vrstaPruge>[^;]+);" + // Track type
        "(?<brojKolosjeka>\\d+);" + // Number of tracks
        "(?<doPoOsovini>\\d+[.,]\\d+);" + // Load per axle (double)
        "(?<doPoDuznomM>\\d+[.,]\\d+);" + // Length (double)
        "(?<statusPruge>[^;]+);" + // Track status
        "(?<duzina>\\d+)$" // Length (integer)
    );

    try (BufferedReader citac = new BufferedReader(new FileReader(datoteka))) {
      citac.readLine();

      String redak;
      int brojRetka = 1, ukupanBrojGresakaUDatoteci = 0;

      while ((redak = citac.readLine()) != null) {
        System.out.println(redak);

        Matcher poklapanjeStanica = predlozakStanica.matcher(redak);
        Matcher poklapanjePraznogRetka = predlozakPrazanRedak.matcher(redak);

        boolean redakDobrogFormata = poklapanjeStanica.matches();

        String[] dijeloviRetka = redak.split(";");
        System.out.println(dijeloviRetka.length);

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
              + ZeljeznickiSustav.dohvatiInstancu().dohvatiGreskeUSustavu());
        }
        brojRetka++;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }


}

