package edu.unizg.foi.uzdiz.askarica20.zadaca_2.citaccsvdatotekafactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.ZeljeznickiSustav;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;

public class CsvCitacVoznogRedaProduct extends CsvCitacProduct {
  private static final String[] NAZIVI_STUPACA =
      {"Oznaka pruge", "Smjer", "Polazna stanica", "Odredišna stanica", "Oznaka vlaka",
          "Vrsta vlaka", "Vrijeme polaska", "Trajanje vožnje", "Oznaka dana"};

  private static final Pattern[] UZORCI_STUPACA = {Pattern.compile("[^;]+"),
      Pattern.compile("[NO]"), Pattern.compile(".*"), Pattern.compile(".*"),
      Pattern.compile("[^;]+"), Pattern.compile("(B|U)?"), Pattern.compile("\\d{1,2}:\\d{2}"),
      Pattern.compile("\\d{1,2}:\\d{2}"), Pattern.compile("\\d*")};
  // format vremena (1-2 znamenke za sate, : i dvije znamenke za minute)

  private Map<String, VlakComposite> mapaVlakova = new HashMap<>();

  @Override
  public void ucitaj(String datoteka) {
    Pattern predlozakPrazanRedak = Pattern.compile("^;*$");
    try (BufferedReader citac = new BufferedReader(new FileReader(datoteka))) {
      obradiSadrzajDatoteke(citac, predlozakPrazanRedak);

      ispisiUcitanePodatke();
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
            // TODO slozi ispravno spremanje podataka
            String oznakaPruge = dijeloviRetka[0];
            String smjer = dijeloviRetka[1];
            String polaznaStanica = dijeloviRetka.length > 2 ? dijeloviRetka[2] : null;
            String odredisnaStanica = dijeloviRetka.length > 3 ? dijeloviRetka[3] : null;
            String oznakaVlaka = dijeloviRetka[4];
            String vrstaVlaka = dijeloviRetka.length > 5 ? dijeloviRetka[5] : null;
            String vrijemePolaska = dijeloviRetka[6];
            String trajanjeVoznje = dijeloviRetka[7];
            String oznakaDana = dijeloviRetka.length > 8 ? dijeloviRetka[8] : null;

            System.out.printf("%s; %s; %s; %s; %s; %s; %s; %s; %s%n", oznakaPruge, smjer,
                polaznaStanica, odredisnaStanica, oznakaVlaka, vrstaVlaka, vrijemePolaska,
                trajanjeVoznje, oznakaDana);

            spremiPodatke(dijeloviRetka);

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

  private void spremiPodatke(String[] dijeloviRetka) {
    String oznakaPruge = dijeloviRetka[0];
    String smjer = dijeloviRetka[1];
    String polaznaStanica = dijeloviRetka.length > 2 ? dijeloviRetka[2] : null;
    String odredisnaStanica = dijeloviRetka.length > 3 ? dijeloviRetka[3] : null;
    String oznakaVlaka = dijeloviRetka[4];
    String vrstaVlaka = dijeloviRetka.length > 5 ? dijeloviRetka[5] : null;
    String vrijemePolaska = dijeloviRetka[6];
    String trajanjeVoznje = dijeloviRetka[7];
    String oznakaDana = dijeloviRetka.length > 8 ? dijeloviRetka[8] : null;

    // Dohvati postojeći vlak ili kreiraj novi
    VlakComposite vlak =
        mapaVlakova.computeIfAbsent(oznakaVlaka, k -> new VlakComposite(oznakaVlaka));

    // Kreiraj i popuni etapu
    EtapaLeaf etapa =
        new EtapaLeaf(oznakaPruge, oznakaVlaka, vrstaVlaka, polaznaStanica, odredisnaStanica);
    etapa.setSmjer(smjer);
    etapa.setVrijemePolaskaUMinutama(pretvoriVrijemeUMinute(vrijemePolaska));
    etapa.setTrajanjeVoznjeUMinutama(pretvoriVrijemeUMinute(trajanjeVoznje));
    etapa.setVrijemeDolaskaUMinutama(
        etapa.getVrijemePolaskaUMinutama() + etapa.getTrajanjeVoznjeUMinutama());
    etapa.setOznakaDana(oznakaDana);

    vlak.dodaj(etapa);
  }

  private int pretvoriVrijemeUMinute(String vrijeme) {
    String[] dijelovi = vrijeme.split(":");
    return Integer.parseInt(dijelovi[0]) * 60 + Integer.parseInt(dijelovi[1]);
  }

  public Collection<VlakComposite> dohvatiVlakove() {
    return mapaVlakova.values();
  }

  private void prikaziGreske(List<String> greske, int brojRetka, int ukupanBrojGresakaUDatoteci) {
    ZeljeznickiSustav.dohvatiInstancu().dodajGreskuUSustav();

    System.out.println("Vozni red - Greške u retku " + brojRetka + ":");
    for (String greska : greske) {
      System.out.println("- " + greska);
    }
    System.out.println("Ukupno grešaka u datoteci voznog reda: " + ukupanBrojGresakaUDatoteci);
    System.out.println(
        "Ukupno grešaka u sustavu: " + ZeljeznickiSustav.dohvatiInstancu().dohvatiGreskeUSustavu());
  }


  private List<String> validirajRedak(String redak) {
    List<String> greske = new ArrayList<>();
    String[] dijeloviRetka = redak.split(";");

    var minimalniBrojObaveznihStupaca = 6; // TODO ovo nije bas najsretnija implementacija

    if (dijeloviRetka.length <= minimalniBrojObaveznihStupaca) {
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

  public void ispisiUcitanePodatke() {
    System.out.println("=== ISPIS SVIH UČITANIH VLAKOVA ===");
    if (mapaVlakova.isEmpty()) {
      System.out.println("Nema učitanih podataka!");
      return;
    }

    for (VlakComposite vlak : mapaVlakova.values()) {
      System.out.println("\nVLAK " + vlak.getOznakaVlaka());
      System.out.println("----------------------------------------");

      for (int i = 0; i < vlak.etape.size(); i++) {
        EtapaLeaf etapa = (EtapaLeaf) vlak.dohvatiDijete(i);
        System.out.printf("Etapa %d:%n", i + 1);
        System.out.printf("  Pruga: %s%n", etapa.getOznakaPruge());
        System.out.printf("  Smjer: %s%n", etapa.getSmjer());
        System.out.printf("  Od: %s%n", etapa.getPocetnaStanica());
        System.out.printf("  Do: %s%n", etapa.getZavrsnaStanica());
        System.out.printf("  Polazak: %d min%n", etapa.getVrijemePolaskaUMinutama());
        System.out.printf("  Trajanje: %d min%n", etapa.getTrajanjeVoznjeUMinutama());
        System.out.printf("  Dolazak: %d min%n", etapa.getVrijemeDolaskaUMinutama());
        // System.out.printf(" Vrsta vlaka: %s%n", vlak.getVrstaVlaka());
        System.out.printf("  Oznaka dana: %s%n", etapa.getOznakaDana());
        System.out.println();
      }
    }
    System.out.println("=== KRAJ ISPISA ===\n");
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
