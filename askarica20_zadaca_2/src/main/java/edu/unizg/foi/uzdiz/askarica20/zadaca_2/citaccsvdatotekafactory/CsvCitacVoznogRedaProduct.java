package edu.unizg.foi.uzdiz.askarica20.zadaca_2.citaccsvdatotekafactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.ZeljeznickiSustav;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComponent;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Stanica;

public class CsvCitacVoznogRedaProduct extends CsvCitacProduct {
  private Set<String> neispravneOznakeVlakova = new HashSet<>();

  private static final String[] NAZIVI_STUPACA =
      {"Oznaka pruge", "Smjer", "Polazna stanica", "Odredišna stanica", "Oznaka vlaka",
          "Vrsta vlaka", "Vrijeme polaska", "Trajanje vožnje", "Oznaka dana"};

  private static final Pattern[] UZORCI_STUPACA = {Pattern.compile("[^;]+"),
      Pattern.compile("[NO]"), Pattern.compile(".*"), Pattern.compile(".*"),
      Pattern.compile("[^;]+"), Pattern.compile("(B|U)?"), Pattern.compile("\\d{1,2}:\\d{2}"),
      Pattern.compile("\\d{1,2}:\\d{2}"), Pattern.compile("\\d*")};

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
    EtapaLeaf etapa = pripremiEtapu(dijeloviRetka);

    if (neispravneOznakeVlakova.contains(etapa.getOznakaVlaka())) {
      return;
    }

    VozniRedComponent vlak =
        ZeljeznickiSustav.dohvatiInstancu().dohvatiVozniRed().dohvatiDijete(etapa.getOznakaVlaka());

    if (vlak == null) {
      VlakComposite noviVlak = new VlakComposite(etapa.getOznakaVlaka(), etapa.getVrstaVlaka());
      boolean uspjeh = noviVlak.dodaj(etapa);
      if (uspjeh) {
        ZeljeznickiSustav.dohvatiInstancu().dodajVlak(noviVlak);
      } else {
        ZeljeznickiSustav.dohvatiInstancu().dodajGreskuVlaka();
        neispravneOznakeVlakova.add(etapa.getOznakaVlaka());
        List<String> greske = new ArrayList<>();
        greske.add("Neispravne etape vlaka " + etapa.getOznakaVlaka()
            + " - neusklađena vremena ili stanice između etapa ili brzine na svim etapama nisu iste");
        prikaziGreske(greske, -1, ZeljeznickiSustav.dohvatiInstancu().dohvatiBrojGresakaVlakova());
      }
    } else {
      boolean uspjeh = vlak.dodaj(etapa);
      if (!uspjeh) {
        ZeljeznickiSustav.dohvatiInstancu().dodajGreskuVlaka();
        neispravneOznakeVlakova.add(etapa.getOznakaVlaka());
        List<String> greske = new ArrayList<>();
        greske.add("Neispravne etape vlaka " + etapa.getOznakaVlaka()
            + " - neusklađena vremena ili stanice između etapa ili brzine na svim etapama nisu iste");
        prikaziGreske(greske, -1, ZeljeznickiSustav.dohvatiInstancu().dohvatiBrojGresakaVlakova());
      } else {
        ((VlakComposite) vlak).izracunajUkupnePodatke();
      }
    }
  }

  private EtapaLeaf pripremiEtapu(String[] dijeloviRetka) {
    String oznakaPruge = dijeloviRetka[0];
    String smjer = dijeloviRetka[1];
    String polaznaStanica =
        (dijeloviRetka.length > 2 && !dijeloviRetka[2].trim().isEmpty()) ? dijeloviRetka[2]
            : ZeljeznickiSustav.dohvatiInstancu().dohvatiPrvuStanicuPrugeSmjer(oznakaPruge, smjer);
    String odredisnaStanica = (dijeloviRetka.length > 3 && !dijeloviRetka[3].trim().isEmpty())
        ? dijeloviRetka[3]
        : ZeljeznickiSustav.dohvatiInstancu().dohvatiZadnjuStanicuPrugeSmjer(oznakaPruge, smjer);
    String oznakaVlaka = dijeloviRetka[4];
    String vrstaVlaka =
        dijeloviRetka.length > 5 && !dijeloviRetka[5].trim().isEmpty() ? dijeloviRetka[5] : "N";
    String vrijemePolaska = dijeloviRetka[6];
    String trajanjeVoznje = dijeloviRetka[7];
    String oznakaDana = dijeloviRetka.length > 8 && !dijeloviRetka[8].trim().isEmpty()
        ? ZeljeznickiSustav.dohvatiInstancu()
            .dohvatiNazivOznakeDanaPoOznaci(Integer.valueOf(dijeloviRetka[8]))
        : ZeljeznickiSustav.dohvatiInstancu().dohvatiNazivOznakeDanaPoOznaci(-1);

    int vrijemePolaskaUMin = pretvoriVrijemeUMinute(vrijemePolaska);
    int trajanjeVoznjeUMin = pretvoriVrijemeUMinute(trajanjeVoznje);
    int vrijemeDolaskaUMin = vrijemePolaskaUMin + trajanjeVoznjeUMin;

    LinkedHashMap<Stanica, Integer> udaljenostiMapa = ZeljeznickiSustav.dohvatiInstancu()
        .izracunajUdaljenostStanicaNaIstojPruzi(oznakaPruge, polaznaStanica, odredisnaStanica);
    int udaljenost = 0;

    for (Integer vrijed : udaljenostiMapa.values()) {
      udaljenost = vrijed;
    }

    EtapaLeaf etapa = new EtapaLeaf(oznakaPruge, oznakaVlaka, vrstaVlaka, polaznaStanica,
        odredisnaStanica, trajanjeVoznjeUMin, vrijemePolaskaUMin, vrijemeDolaskaUMin, udaljenost,
        smjer, oznakaDana);

    List<Stanica> staniceEtape = ZeljeznickiSustav.dohvatiInstancu()
        .dohvatiListuMedustanica(polaznaStanica, odredisnaStanica, oznakaPruge, smjer);

    if (staniceEtape.size() > 0) {
      etapa.setListaStanicaEtape(staniceEtape);
    }

    return etapa;
  }

  private int pretvoriVrijemeUMinute(String vrijeme) {
    String[] dijelovi = vrijeme.split(":");
    return Integer.parseInt(dijelovi[0]) * 60 + Integer.parseInt(dijelovi[1]);
  }

  private void prikaziGreske(List<String> greske, int brojRetka, int ukupanBrojGresakaUDatoteci) {
    if (brojRetka == -1) {
      System.out.println("Vozni red (vlak) - Greška u vlaku");
    } else {
      System.out.println("Vozni red - Greške u retku " + brojRetka + ":");
    }

    for (String greska : greske) {
      System.out.println("- " + greska);
    }

    if (brojRetka != -1) {
      System.out.println("Ukupno grešaka u datoteci voznog reda: " + ukupanBrojGresakaUDatoteci);
    } else {
      System.out.println("Ukupno grešaka u vlakovima: "
          + ZeljeznickiSustav.dohvatiInstancu().dohvatiBrojGresakaVlakova());
    }

    System.out.println(
        "Ukupno grešaka u sustavu: " + ZeljeznickiSustav.dohvatiInstancu().dohvatiGreskeUSustavu());
  }

  private List<String> validirajRedak(String redak) {
    List<String> greske = new ArrayList<>();
    String[] dijeloviRetka = redak.split(";");

    var minimalniBrojObaveznihStupaca = 6;

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
