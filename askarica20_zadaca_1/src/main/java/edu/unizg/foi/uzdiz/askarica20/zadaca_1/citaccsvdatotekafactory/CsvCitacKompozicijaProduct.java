package edu.unizg.foi.uzdiz.askarica20.zadaca_1.citaccsvdatotekafactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.ZeljeznickiSustav;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.dto.Kompozicija;

// ConcreteProduct
public class CsvCitacKompozicijaProduct extends CsvCitacProduct {

  @Override
  public void ucitaj(String datoteka) {
    Pattern predlozakPrazanRedak = Pattern.compile("^;*$");
    Pattern predlozakKompozicija = Pattern
        .compile("^(?<oznaka>\\d+);" + "(?<oznakaPrijevoznogSredstva>[^;]+);" + "(?<uloga>[^;]+)$");

    try (BufferedReader citac = new BufferedReader(new FileReader(datoteka))) {
      String redak;
      int brojRetka = 1, ukupanBrojGresakaUDatoteci = 0;

      while ((redak = citac.readLine()) != null) {
        boolean preskociPrvog = false;
        if (brojRetka == 1) {
          preskociPrvog = prviRedakJeInformativan(redak.split(";"), citac);
        }

        if (!preskociPrvog) {
          Matcher poklapanjeKompozicija = predlozakKompozicija.matcher(redak);
          Matcher poklapanjePraznogRetka = predlozakPrazanRedak.matcher(redak);
          boolean redakDobrogFormata = poklapanjeKompozicija.matches();
          String[] dijeloviRetka = redak.split(";");

          if (poklapanjePraznogRetka.matches()) {
            continue; // prazan red se ne racuna kao greska, nego se samo treba preskociti
          }

          if (redakDobrogFormata && dijeloviRetka.length == 3) {
            Kompozicija kompozicija = new Kompozicija(brojRetka, Integer.valueOf(dijeloviRetka[0]),
                dijeloviRetka[1], dijeloviRetka[2]);
            ZeljeznickiSustav.dohvatiInstancu().dodajKompoziciju(kompozicija);
          } else {
            ukupanBrojGresakaUDatoteci++;
            ZeljeznickiSustav.dohvatiInstancu().dodajGreskuUSustav();

            System.out.println("Svi stupci nisu ispravno popunjeni u " + brojRetka
                + ". retku! Ukupno gresaka u datoteci kompozicija: " + ukupanBrojGresakaUDatoteci
                + "! Ukupno gresaka u sustavu: "
                + ZeljeznickiSustav.dohvatiInstancu().dohvatiGreskeUSustavu() + "\n");
          }
        }
        brojRetka++;
      }
      System.out.println("Kompozicije uspjesno ucitane.");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  private boolean prviRedakJeInformativan(String[] dijeloviRetka, BufferedReader citac) {
    String[] dijeloviRetkaBezBOM = ukloniBOM(dijeloviRetka);

    if ("Oznaka".equals(dijeloviRetkaBezBOM[0])
        && "Oznaka prijevoznog sredstva".equals(dijeloviRetkaBezBOM[1])
        && "Uloga".equals(dijeloviRetkaBezBOM[2])) {
      System.out.println("Prvi redak kompozicija je informativan.");
      return true;
    } else {
      System.out.println("Prvi redak kompozicija nije informativan.");
      return false;
    }
  }

  private String[] ukloniBOM(String[] dijeloviRetka) {
    for (int i = 0; i < dijeloviRetka.length; i++) {
      dijeloviRetka[i] = dijeloviRetka[i].trim().replaceAll("[\\uFEFF]", "");
    }
    return dijeloviRetka;
  }

}
