package edu.unizg.foi.uzdiz.askarica20.zadaca_1.citaccsvdatotekafactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.ZeljeznickiSustav;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.dto.Stanica;

public class CsvCitacStanicaProduct extends CsvCitacProduct {
  private static final String[] NAZIVI_STUPACA = {"Stanica", "Oznaka pruge", "Vrsta stanice",
      "Status stanice", "Putnici ul/iz", "Roba ut/ist", "Kategorija pruge", "Broj perona",
      "Vrsta pruge", "Broj kolosjeka", "DO po osovini", "DO po duznom m", "Status pruge", "Dužina"};

  private static final Pattern[] UZORCI_STUPACA = {Pattern.compile("[^;]+"), // Naziv stanice
      Pattern.compile("[^;]+"), // Oznaka pruge
      Pattern.compile("(kol\\.|staj\\.)"), // Vrsta stanice
      Pattern.compile("[^;]+"), // Status stanice
      Pattern.compile("DA|NE"), // Putnici ul/iz
      Pattern.compile("DA|NE"), // Roba ut/ist
      Pattern.compile("[LRM];?+"), // Kategorija pruge
      Pattern.compile("\\d+"), // Broj perona
      Pattern.compile("[KE];?+"), // Vrsta pruge
      Pattern.compile("\\d+"), // Broj kolosjeka
      Pattern.compile("\\d+[.,]\\d+"), // DO po osovini
      Pattern.compile("\\d+[.,]\\d+"), // DO po duznom m
      Pattern.compile("[IKZ];?+"), // Status pruge
      Pattern.compile("\\d+") // Duzina
  };

  @Override
  public void ucitaj(String datoteka) {
    Pattern predlozakPrazanRedak = Pattern.compile("^;*$");

    try (BufferedReader citac = new BufferedReader(new FileReader(datoteka))) {
      String redak;
      int brojRetka = 1, ukupanBrojGresakaUDatoteci = 0;

      while ((redak = citac.readLine()) != null) {
        boolean preskociPrvog = false;
        if (brojRetka == 1) {
          preskociPrvog = prviRedakJeInformativan(redak.split(";"), citac);
        }

        if (!preskociPrvog) {
          Matcher poklapanjePraznogRetka = predlozakPrazanRedak.matcher(redak);

          if (poklapanjePraznogRetka.matches() || redak.startsWith("#")) {
            // System.out.println("Preskočen redak: " + redak);
            brojRetka++;
            continue;
          }

          List<String> greske = validirajRedak(redak);

          if (greske.isEmpty()) {
            String[] dijeloviRetka = redak.split(";");
            try {
              Stanica stanica =
                  new Stanica(brojRetka, dijeloviRetka[0], dijeloviRetka[1], dijeloviRetka[2],
                      dijeloviRetka[3], Boolean.valueOf(dijeloviRetka[4].equals("DA")),
                      Boolean.valueOf(dijeloviRetka[5].equals("DA")), dijeloviRetka[6],
                      Integer.valueOf(dijeloviRetka[7]), dijeloviRetka[8],
                      Integer.valueOf(dijeloviRetka[9]),
                      Double.parseDouble(dijeloviRetka[10].replace(',', '.')),
                      Double.parseDouble(dijeloviRetka[11].replace(',', '.')), dijeloviRetka[12],
                      Integer.valueOf(dijeloviRetka[13]));
              ZeljeznickiSustav.dohvatiInstancu().dodajStanicu(stanica);
            } catch (NumberFormatException e) {
              System.out
                  .println("Greška pri konverziji brojčanih vrijednosti u retku " + brojRetka);
              ukupanBrojGresakaUDatoteci++;
              ZeljeznickiSustav.dohvatiInstancu().dodajGreskuUSustav();
            }
          } else {
            ukupanBrojGresakaUDatoteci++;
            ZeljeznickiSustav.dohvatiInstancu().dodajGreskuUSustav();

            System.out.println("Stanice - Greške u retku " + brojRetka + ":");
            for (String greska : greske) {
              System.out.println("- " + greska);
            }
            System.out.println("Ukupno grešaka u datoteci stanica: " + ukupanBrojGresakaUDatoteci);
            System.out.println("Ukupno grešaka u sustavu: "
                + ZeljeznickiSustav.dohvatiInstancu().dohvatiGreskeUSustavu());
          }
        }
        brojRetka++;
      }
      System.out.println("Stanice uspješno učitane.");
    } catch (Exception e) {
      System.out.println("Greška pri čitanju datoteke: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private List<String> validirajRedak(String redak) {
    List<String> greske = new ArrayList<>();
    String[] dijeloviRetka = redak.split(";");

    if (dijeloviRetka.length != NAZIVI_STUPACA.length) {
      greske.add("Neispravan broj stupaca. Očekivano: " + NAZIVI_STUPACA.length + ", dobiveno: "
          + dijeloviRetka.length);
      return greske;
    }

    for (int i = 0; i < NAZIVI_STUPACA.length; i++) {
      String vrijednost = dijeloviRetka[i].trim();
      if (!UZORCI_STUPACA[i].matcher(vrijednost).matches()) {
        greske.add("Neispravan format u stupcu '" + NAZIVI_STUPACA[i] + "': vrijednost '"
            + vrijednost + "' ne odgovara očekivanom formatu");
      }
    }

    return greske;
  }

  private boolean prviRedakJeInformativan(String[] dijeloviRetka, BufferedReader citac) {
    String[] dijeloviRetkaBezBOM = ukloniBOM(dijeloviRetka);

    for (int i = 0; i < NAZIVI_STUPACA.length; i++) {
      if (i >= dijeloviRetkaBezBOM.length || !NAZIVI_STUPACA[i].equals(dijeloviRetkaBezBOM[i])) {
        System.out
            .println("Prvi redak stanica nije informativan - ne odgovara očekivanoj strukturi.");
        return false;
      }
    }

    System.out.println("Prvi redak stanica je informativan.");
    return true;
  }

  private String[] ukloniBOM(String[] dijeloviRetka) {
    for (int i = 0; i < dijeloviRetka.length; i++) {
      dijeloviRetka[i] = dijeloviRetka[i].trim().replaceAll("[\\uFEFF]", "");
    }
    return dijeloviRetka;
  }
}
