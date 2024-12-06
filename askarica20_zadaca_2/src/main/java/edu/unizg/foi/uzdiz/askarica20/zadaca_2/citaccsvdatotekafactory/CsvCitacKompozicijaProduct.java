package edu.unizg.foi.uzdiz.askarica20.zadaca_2.citaccsvdatotekafactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.ZeljeznickiSustav;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Kompozicija;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Vozilo;

public class CsvCitacKompozicijaProduct extends CsvCitacProduct {
  private static final String[] NAZIVI_STUPACA =
      {"Oznaka kompozicije", "Oznaka prijevoznog sredstva", "Uloga"};

  private static final Pattern[] UZORCI_STUPACA =
      {Pattern.compile("\\d+"), Pattern.compile("[^;]+"), Pattern.compile("[PV];?+")};

  private Kompozicija trenutnaKompozicija = null;
  private boolean imaPogon = false;
  private boolean imaVagon = false;
  int brojRetka = 1, ukupanBrojGresakaUDatoteci = 0;

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
      throws IOException {
    String redak;
    while ((redak = citac.readLine()) != null) {
      boolean preskociPrvog = false;
      if (brojRetka == 1) {
        preskociPrvog = prviRedakJeInformativan(redak.split(";"), citac);
      }

      if (!preskociPrvog) {
        Matcher poklapanjePraznogRetka = predlozakPrazanRedak.matcher(redak);

        if (redak.startsWith("#")) {
          brojRetka++;
          continue;
        }

        if (poklapanjePraznogRetka.matches()) {
          provjeriIspravnostKompozicije();
          trenutnaKompozicija = null;
          imaPogon = false;
          imaVagon = false;
          brojRetka++;
          continue;
        }
        obradiRedak(redak);
      }
      brojRetka++;
    }
    provjeriIspravnostKompozicije();
  }

  private void provjeriIspravnostKompozicije() {
    if (trenutnaKompozicija != null && imaPogon && imaVagon) {
      ZeljeznickiSustav.dohvatiInstancu().dodajKompoziciju(trenutnaKompozicija);
    } else if ((!imaPogon || !imaVagon)) {
      ukupanBrojGresakaUDatoteci++;
      ZeljeznickiSustav.dohvatiInstancu().dodajGreskuUSustav();
      System.out.println("Kompozicija - Greška u retku " + brojRetka
          + ": Prijevozno sredstvo mora imati bar 1 pogon i 1 vagon.");
    }
  }

  private void obradiRedak(String redak) {
    List<String> greske = validirajRedak(redak);

    if (greske.isEmpty()) {
      String[] dijeloviRetka = redak.split(";");
      try {
        if (trenutnaKompozicija == null
            || trenutnaKompozicija.getOznaka() != Integer.valueOf(dijeloviRetka[0])) {
          if (trenutnaKompozicija != null && imaPogon && imaVagon) {
            ZeljeznickiSustav.dohvatiInstancu().dodajKompoziciju(trenutnaKompozicija);
          }
          trenutnaKompozicija = new Kompozicija(brojRetka, Integer.valueOf(dijeloviRetka[0]));
          imaPogon = false;
          imaVagon = false;
        }

        Vozilo vozilo = ZeljeznickiSustav.dohvatiInstancu().dohvatiVoziloPoOznaci(dijeloviRetka[1]);

        if ("P".equals(dijeloviRetka[2])) {
          imaPogon = true;
          trenutnaKompozicija.addVozilo(vozilo);
        } else if (imaPogon && "V".equals(dijeloviRetka[2])) {
          imaVagon = true;
          trenutnaKompozicija.addVozilo(vozilo);
        }
      } catch (Exception e) {
        ukupanBrojGresakaUDatoteci++;
        ZeljeznickiSustav.dohvatiInstancu().dodajGreskuUSustav();
        System.out.println("Greška pri obradi retka " + brojRetka + ": " + e.getMessage());
      }
    } else {
      ukupanBrojGresakaUDatoteci++;
      prikaziGreske(greske);
    }
  }

  private void prikaziGreske(List<String> greske) {
    ZeljeznickiSustav.dohvatiInstancu().dodajGreskuUSustav();

    System.out.println("Kompozicije - Greške u retku " + brojRetka + ":");
    for (String greska : greske) {
      System.out.println("- " + greska);
    }
    System.out.println("Ukupno grešaka u datoteci kompozicija: " + ukupanBrojGresakaUDatoteci);
    System.out.println(
        "Ukupno grešaka u sustavu: " + ZeljeznickiSustav.dohvatiInstancu().dohvatiGreskeUSustavu());
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

    if (greske.isEmpty()) {
      String oznakaPrijevoznogSredstva = dijeloviRetka[1];
      Vozilo vozilo =
          ZeljeznickiSustav.dohvatiInstancu().dohvatiVoziloPoOznaci(oznakaPrijevoznogSredstva);

      if (vozilo == null) {
        greske.add("Prijevozno sredstvo s oznakom '" + oznakaPrijevoznogSredstva
            + "' ne postoji u sustavu");
      }
    }

    return greske;
  }

  private boolean prviRedakJeInformativan(String[] dijeloviRetka, BufferedReader citac) {
    String[] dijeloviRetkaBezBOM = ukloniBOM(dijeloviRetka);

    if ("Oznaka".equals(dijeloviRetkaBezBOM[0])
        && "Oznaka prijevoznog sredstva".equals(dijeloviRetkaBezBOM[1])
        && "Uloga".equals(dijeloviRetkaBezBOM[2])) {
      return true;
    } else {
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
