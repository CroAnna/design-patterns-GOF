package edu.unizg.foi.uzdiz.askarica20.zadaca_1.citaccsvdatotekafactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.ZeljeznickiSustav;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.dto.Vozilo;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.vozilobuilder.VoziloBuilder;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.vozilobuilder.VoziloConcreteBuilder;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.vozilobuilder.VoziloDirector;

public class CsvCitacVozilaProduct extends CsvCitacProduct {
  private static final String[] NAZIVI_STUPACA = {"Oznaka", "Opis", "Proizvođač", "Godina",
      "Namjena", "Vrsta prijevoza", "Vrsta pogona", "Maks brzina", "Maks snaga",
      "Broj sjedećih mjesta", "Broj stajaćih mjesta", "Broj bicikala", "Broj kreveta",
      "Broj automobila", "Nosivost", "Površina", "Zapremina", "Status"};

  private static final Pattern[] UZORCI_STUPACA = {Pattern.compile("[^;]+"),
      Pattern.compile("[^;]+"), Pattern.compile("[^;\\-]+|\\-"), Pattern.compile("\\d{4}"),
      Pattern.compile("(PSVPVK|PSVP|PSBP);?+"), Pattern.compile("(N|P|TA|TK|TRS|TTS);?+"),
      Pattern.compile("[DEN];?+"), Pattern.compile("\\d+"), Pattern.compile("-?\\d+[.,]?\\d*"),
      Pattern.compile("\\d*"), Pattern.compile("\\d*"), Pattern.compile("\\d*"),
      Pattern.compile("\\d*"), Pattern.compile("\\d*"), Pattern.compile("\\d+[.,]?\\d*|0"),
      Pattern.compile("\\d+[.,]?\\d*|0"), Pattern.compile("\\d*|0"), Pattern.compile("[IK];?+")};

  @Override
  public void ucitaj(String datoteka) {
    Pattern predlozakPrazanRedak = Pattern.compile("^;*$");
    try (BufferedReader citac = new BufferedReader(new FileReader(datoteka))) {
      obradiSadrzajDatoteke(citac, predlozakPrazanRedak);
    } catch (Exception e) {
      System.out.println("Greška pri čitanju datoteke: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private void obradiSadrzajDatoteke(BufferedReader citac, Pattern predlozakPrazanRedak)
      throws Exception {
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
          brojRetka++;
          continue;
        }

        List<String> greske = validirajRedak(redak);

        if (greske.isEmpty()) {
          String[] dijeloviRetka = redak.split(";");
          try {
            VoziloBuilder builder = new VoziloConcreteBuilder();
            VoziloDirector voziloDirector = new VoziloDirector(builder);
            Vozilo vozilo = konstruirajVozilo(brojRetka, dijeloviRetka, voziloDirector);

            if (vozilo != null) {
              ZeljeznickiSustav.dohvatiInstancu().dodajVozilo(vozilo);
            }
          } catch (NumberFormatException e) {
            ukupanBrojGresakaUDatoteci++;
            ZeljeznickiSustav.dohvatiInstancu().dodajGreskuUSustav();
            System.out.println("Greška pri konverziji brojčanih vrijednosti u retku " + brojRetka
                + ": " + e.getMessage());
          } catch (Exception e) {
            ukupanBrojGresakaUDatoteci++;
            ZeljeznickiSustav.dohvatiInstancu().dodajGreskuUSustav();
            System.out.println(
                "Greška pri konstruiranju vozila u retku " + brojRetka + ": " + e.getMessage());
          }
        } else {
          ukupanBrojGresakaUDatoteci++;
          prikaziGreske(greske, brojRetka, ukupanBrojGresakaUDatoteci);
        }
      }
      brojRetka++;
    }
  }

  private void prikaziGreske(List<String> greske, int brojRetka, int ukupanBrojGresakaUDatoteci) {
    ZeljeznickiSustav.dohvatiInstancu().dodajGreskuUSustav();

    System.out.println("Vozila - Greške u retku " + brojRetka + ":");
    for (String greska : greske) {
      System.out.println("- " + greska);
    }
    System.out.println("Ukupno grešaka u datoteci vozila: " + ukupanBrojGresakaUDatoteci);
    System.out.println(
        "Ukupno grešaka u sustavu: " + ZeljeznickiSustav.dohvatiInstancu().dohvatiGreskeUSustavu());
  }

  private Vozilo konstruirajVozilo(int brojRetka, String[] dijeloviRetka,
      VoziloDirector voziloDirector) {
    switch (dijeloviRetka[5]) {
      case "N":
        return voziloDirector.konstruirajLokomotivu(brojRetka, dijeloviRetka[0], dijeloviRetka[1],
            dijeloviRetka[2], Integer.valueOf(dijeloviRetka[3]), dijeloviRetka[4], dijeloviRetka[5],
            dijeloviRetka[6], Integer.valueOf(dijeloviRetka[7]),
            Double.parseDouble(dijeloviRetka[8].replace(',', '.')), dijeloviRetka[17]);
      case "P":
        return voziloDirector.konstruirajPutnickiVagon(brojRetka, dijeloviRetka[0],
            dijeloviRetka[1], dijeloviRetka[2], Integer.valueOf(dijeloviRetka[3]), dijeloviRetka[4],
            dijeloviRetka[5], dijeloviRetka[6], Integer.valueOf(dijeloviRetka[7]),
            Double.parseDouble(dijeloviRetka[8].replace(',', '.')),
            Integer.valueOf(dijeloviRetka[9]), Integer.valueOf(dijeloviRetka[10]),
            Integer.valueOf(dijeloviRetka[11]), Integer.valueOf(dijeloviRetka[12]),
            dijeloviRetka[17]);
      case "TA":
        return voziloDirector.konstruirajTeretniAutomobilskiVagon(brojRetka, dijeloviRetka[0],
            dijeloviRetka[1], dijeloviRetka[2], Integer.valueOf(dijeloviRetka[3]), dijeloviRetka[4],
            dijeloviRetka[5], dijeloviRetka[6], Integer.valueOf(dijeloviRetka[7]),
            Double.parseDouble(dijeloviRetka[8].replace(',', '.')),
            Integer.valueOf(dijeloviRetka[13]), Double.valueOf(dijeloviRetka[14].replace(',', '.')),
            Double.valueOf(dijeloviRetka[15].replace(',', '.')), dijeloviRetka[17]);
      case "TK":
        return voziloDirector.konstruirajTeretniKontejnerskiVagon(brojRetka, dijeloviRetka[0],
            dijeloviRetka[1], dijeloviRetka[2], Integer.valueOf(dijeloviRetka[3]), dijeloviRetka[4],
            dijeloviRetka[5], dijeloviRetka[6], Integer.valueOf(dijeloviRetka[7]),
            Double.parseDouble(dijeloviRetka[8].replace(',', '.')),
            Double.valueOf(dijeloviRetka[14].replace(',', '.')),
            Double.valueOf(dijeloviRetka[15].replace(',', '.')), dijeloviRetka[17]);
      case "TRS":
        return voziloDirector.konstruirajTeretniRobniRasutoVagon(brojRetka, dijeloviRetka[0],
            dijeloviRetka[1], dijeloviRetka[2], Integer.valueOf(dijeloviRetka[3]), dijeloviRetka[4],
            dijeloviRetka[5], dijeloviRetka[6], Integer.valueOf(dijeloviRetka[7]),
            Double.parseDouble(dijeloviRetka[8].replace(',', '.')),
            Double.valueOf(dijeloviRetka[14].replace(',', '.')),
            Double.valueOf(dijeloviRetka[15].replace(',', '.')), Integer.valueOf(dijeloviRetka[16]),
            dijeloviRetka[17]);
      case "TTS":
        return voziloDirector.konstruirajTeretniRobniTekuceVagon(brojRetka, dijeloviRetka[0],
            dijeloviRetka[1], dijeloviRetka[2], Integer.valueOf(dijeloviRetka[3]), dijeloviRetka[4],
            dijeloviRetka[5], dijeloviRetka[6], Integer.valueOf(dijeloviRetka[7]),
            Double.parseDouble(dijeloviRetka[8].replace(',', '.')),
            Double.valueOf(dijeloviRetka[14].replace(',', '.')), Integer.valueOf(dijeloviRetka[16]),
            dijeloviRetka[17]);
      default:
        throw new IllegalArgumentException("Neispravna vrsta vozila: " + dijeloviRetka[5]);
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
        return false;
      }
    }
    return true;
  }

  private String[] ukloniBOM(String[] dijeloviRetka) {
    for (int i = 0; i < dijeloviRetka.length; i++) {
      dijeloviRetka[i] = dijeloviRetka[i].trim().replaceAll("[\\uFEFF]", "");
    }
    return dijeloviRetka;
  }
}
