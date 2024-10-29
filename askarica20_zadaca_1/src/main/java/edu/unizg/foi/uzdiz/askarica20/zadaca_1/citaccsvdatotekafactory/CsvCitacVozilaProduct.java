package edu.unizg.foi.uzdiz.askarica20.zadaca_1.citaccsvdatotekafactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.ZeljeznickiSustav;

// ConcreteProduct
public class CsvCitacVozilaProduct extends CsvCitacProduct {

  @Override
  public void ucitaj(String datoteka) {
    System.out.println("ucitaj datoteku vozila");

    Pattern predlozakPrazanRedak = Pattern.compile("^;*$");
    Pattern predlozakVozilo = Pattern.compile("(?<oznaka>[^;]+);" + "(?<opis>[^;]+);"
        + "(?<proizvodac>[^;\\-]+|\\-);" + "(?<godina>\\d{4});" + "(?<namjena>[^;]+);"
        + "(?<vrstaPrijevoza>[^;]+);" + "(?<vrstaPogona>[^;]+);" + "(?<maksimalnaBrzina>\\d+);"
        + "(?<maksimalnaSnaga>-?\\d+[.,]?\\d*);" + "(?<brojSjedecihMjesta>\\d*);"
        + "(?<brojStajacihMjesta>\\d*);" + "(?<brojBicikala>\\d*);" + "(?<brojKreveta>\\d*);"
        + "(?<brojAutomobila>\\d*);" + "(?<nosivost>\\d+[.,]?\\d*|0);"
        + "(?<povrsina>\\d+[.,]?\\d*|0);" + "(?<zapremnina>\\d*|0);" + "(?<statusVozila>[^;]+)$");


    try (BufferedReader citac = new BufferedReader(new FileReader(datoteka))) {
      citac.readLine();

      String redak;
      int brojRetka = 1, ukupanBrojGresakaUDatoteci = 0;

      while ((redak = citac.readLine()) != null) {
        System.out.println(redak);

        Matcher poklapanjeVozila = predlozakVozilo.matcher(redak);
        Matcher poklapanjePraznogRetka = predlozakPrazanRedak.matcher(redak);

        boolean redakDobrogFormata = poklapanjeVozila.matches();

        String[] dijeloviRetka = redak.split(";");
        System.out.println(dijeloviRetka.length);

        // TODO dodaj provjeru jel prvi redak informativni
        // provjeriPrviRedak(dijeloviRetka,citac);

        if (poklapanjePraznogRetka.matches()) {
          continue; // prazan red se ne racuna kao greska, nego se samo treba preskociti
        }

        if (redakDobrogFormata && dijeloviRetka.length == 18) {
          // TODO dodaj builder kreiranje objekata
          /*
           * Vozilo vozilo = new Vozilo(brojRetka, dijeloviRetka[0], dijeloviRetka[1],
           * dijeloviRetka[2], Integer.valueOf(dijeloviRetka[3]), dijeloviRetka[4],
           * dijeloviRetka[5], dijeloviRetka[6], Integer.valueOf(dijeloviRetka[7]),
           * Double.parseDouble(dijeloviRetka[8].replace(',', '.')),
           * Integer.valueOf(dijeloviRetka[9]), Integer.valueOf(dijeloviRetka[10]),
           * Integer.valueOf(dijeloviRetka[11]), Integer.valueOf(dijeloviRetka[12]),
           * Integer.valueOf(dijeloviRetka[13]), Double.valueOf(dijeloviRetka[14].replace(',',
           * '.')), Double.valueOf(dijeloviRetka[15].replace(',', '.')),
           * Integer.valueOf(dijeloviRetka[16]), dijeloviRetka[17]);
           */

          // ZeljeznickiSustav.dohvatiInstancu().dodajVozilo(vozilo);
        } else {
          ukupanBrojGresakaUDatoteci++;
          ZeljeznickiSustav.dohvatiInstancu().dodajGreskuUSustav();

          System.out.println(
              "Svi stupci datoteke vozila nisu ispravno popunjeni! Ukupno gresaka u datoteci: "
                  + ukupanBrojGresakaUDatoteci + "! Ukupno gresaka u sustavu: "
                  + ZeljeznickiSustav.dohvatiInstancu().dohvatiGreskeUSustavu() + "\n");
        }
        brojRetka++;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

}
