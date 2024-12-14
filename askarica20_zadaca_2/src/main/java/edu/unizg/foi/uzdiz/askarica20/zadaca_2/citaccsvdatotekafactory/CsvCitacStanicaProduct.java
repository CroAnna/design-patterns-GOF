package edu.unizg.foi.uzdiz.askarica20.zadaca_2.citaccsvdatotekafactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.ZeljeznickiSustav;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Pruga;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Stanica;

public class CsvCitacStanicaProduct extends CsvCitacProduct {
  private static final String[] NAZIVI_STUPACA = {"Stanica", "Oznaka pruge", "Vrsta stanice",
      "Status stanice", "Putnici ul/iz", "Roba ut/ist", "Kategorija pruge", "Broj perona",
      "Vrsta pruge", "Broj kolosjeka", "DO po osovini", "DO po duznom m", "Status pruge", "Dužina",
      "Vrijeme normalni vlak", "Vrijeme ubrzani vlak", "Vrijeme brzi vlak"};

  // TODO jel osim kol i staj sad moze bit i rasp i otpr ili je to greska?
  private static final Pattern[] UZORCI_STUPACA =
      {Pattern.compile("[^;]+"), Pattern.compile("[^;]+"), Pattern.compile("(kol\\.|staj\\.)"),
          Pattern.compile("[^;]+"), Pattern.compile("DA|NE"), Pattern.compile("DA|NE"),
          Pattern.compile("[LRM];?+"), Pattern.compile("\\d+"), Pattern.compile("[KE];?+"),
          Pattern.compile("\\d+"), Pattern.compile("\\d+([.,]\\d+)?"),
          Pattern.compile("\\d+([.,]\\d+)?"), Pattern.compile("[IKZ];?+"), Pattern.compile("\\d+"),
          Pattern.compile("\\d*"), Pattern.compile("\\d*"), Pattern.compile("\\d*")};

  private Map<String, Pruga> prugeMap = new HashMap<>();

  @Override
  public void ucitaj(String datoteka) {
    Pattern predlozakPrazanRedak = Pattern.compile("^;*$");
    try (BufferedReader citac = new BufferedReader(new FileReader(datoteka))) {
      obradiSadrzajDatoteke(citac, predlozakPrazanRedak);
      dodajStaniceUListuPruga();
    } catch (Exception e) {
      System.out.println("Greška pri čitanju datoteke: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private void dodajStaniceUListuPruga() {
    for (Pruga pruga : prugeMap.values()) {
      ZeljeznickiSustav.dohvatiInstancu().dohvatiListuPruga().add(pruga);
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
            Integer vrijednost14 = null;
            Integer vrijednost15 = null;
            Integer vrijednost16 = null;

            if (dijeloviRetka.length > 14 && !dijeloviRetka[14].isEmpty()) {
              vrijednost14 = Integer.valueOf(dijeloviRetka[14]);
            }
            if (dijeloviRetka.length > 15 && !dijeloviRetka[15].isEmpty()) {
              vrijednost15 = Integer.valueOf(dijeloviRetka[15]);
            }
            if (dijeloviRetka.length > 16 && !dijeloviRetka[16].isEmpty()) {
              vrijednost16 = Integer.valueOf(dijeloviRetka[16]);
            }

            Stanica stanica = new Stanica(brojRetka, dijeloviRetka[0], dijeloviRetka[1],
                dijeloviRetka[2], dijeloviRetka[3], dijeloviRetka[4].equals("DA"),
                dijeloviRetka[5].equals("DA"), dijeloviRetka[6], Integer.valueOf(dijeloviRetka[7]),
                dijeloviRetka[8], Integer.valueOf(dijeloviRetka[9]),
                Double.parseDouble(dijeloviRetka[10].replace(',', '.')),
                Double.parseDouble(dijeloviRetka[11].replace(',', '.')), dijeloviRetka[12],
                Integer.valueOf(dijeloviRetka[13]), vrijednost14, vrijednost15, vrijednost16);

            ZeljeznickiSustav.dohvatiInstancu().dodajStanicu(stanica);
            stvoriIliDodajStanicePruga(dijeloviRetka[1], stanica);

          } catch (NumberFormatException e) {
            System.out.println("Greška pri konverziji brojčanih vrijednosti u retku " + brojRetka);
            ukupanBrojGresakaUDatoteci++;
            ZeljeznickiSustav.dohvatiInstancu().dodajGreskuUSustav();
          }
        } else {
          ukupanBrojGresakaUDatoteci++;
          prikaziGreske(greske, brojRetka, ukupanBrojGresakaUDatoteci);
        }
      }
      brojRetka++;
    }
  }


  private void stvoriIliDodajStanicePruga(String oznakaPruge, Stanica stanica) {
    Pruga pruga = prugeMap.computeIfAbsent(oznakaPruge, k -> new Pruga(k));
    pruga.dodajStanicu(stanica);
  }

  private void prikaziGreske(List<String> greske, int brojRetka, int ukupanBrojGresakaUDatoteci) {
    ZeljeznickiSustav.dohvatiInstancu().dodajGreskuUSustav();

    System.out.println("Stanice - Greške u retku " + brojRetka + ":");
    for (String greska : greske) {
      System.out.println("- " + greska);
    }
    System.out.println("Ukupno grešaka u datoteci stanica: " + ukupanBrojGresakaUDatoteci);
    System.out.println(
        "Ukupno grešaka u sustavu: " + ZeljeznickiSustav.dohvatiInstancu().dohvatiGreskeUSustavu());
  }

  private List<String> validirajRedak(String redak) {
    List<String> greske = new ArrayList<>();
    String[] dijeloviRetka = redak.split(";");

    if (dijeloviRetka.length <= NAZIVI_STUPACA.length - 3) { // TODO ovo popravit da bolje radi
      greske.add("Neispravan broj stupaca. Očekivano: " + NAZIVI_STUPACA.length + ", dobiveno: "
          + dijeloviRetka.length);
      return greske;
    }

    int brojStupacaZaValidaciju = Math.min(dijeloviRetka.length, UZORCI_STUPACA.length);

    for (int i = 0; i < brojStupacaZaValidaciju; i++) {
      String vrijednost = dijeloviRetka[i].trim();
      if (!UZORCI_STUPACA[i].matcher(vrijednost).matches()) {
        greske.add("Neispravan format u stupcu '" + NAZIVI_STUPACA[i] + "': vrijednost '"
            + vrijednost + "' ne odgovara očekivanom formatu: " + UZORCI_STUPACA[i]);
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
