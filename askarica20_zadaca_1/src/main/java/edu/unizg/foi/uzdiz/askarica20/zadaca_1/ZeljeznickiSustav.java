package edu.unizg.foi.uzdiz.askarica20.zadaca_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.dto.Kompozicija;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.dto.Stanica;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.dto.Vozilo;

public class ZeljeznickiSustav {
  private static ZeljeznickiSustav instanca;
  private final List<Vozilo> listaVozila = new ArrayList();
  private final List<Stanica> listaStanica = new ArrayList();
  private final List<Kompozicija> listaKompozicija = new ArrayList();
  private int ukupanBrojGresakaUSustavu = 0;

  private ZeljeznickiSustav() {};

  public static ZeljeznickiSustav dohvatiInstancu() {
    if (instanca == null) {
      instanca = new ZeljeznickiSustav();
    }
    return instanca;
  }

  public void dodajVozilo(Vozilo vozilo) {
    listaVozila.add(vozilo);
  }

  public void dodajStanicu(Stanica stanica) {
    listaStanica.add(stanica);
  }

  public void dodajKompoziciju(Kompozicija kompozicija) {
    listaKompozicija.add(kompozicija);
  }

  public void dodajGreskuUSustav() {
    this.ukupanBrojGresakaUSustavu++;
  }

  public int dohvatiGreskeUSustavu() {
    return this.ukupanBrojGresakaUSustavu;
  }

  public void zapocniRadSustava() {
    String unos = "";
    Scanner skener = new Scanner(System.in);
    nacrtajVlak();
    System.out.println("-- Dobrodosli u FOI Zeljeznice! --");

    do {
      System.out.println("\nUnesite komandu ili odaberite Q za izlaz.");
      unos = skener.nextLine();
      provjeraVrsteUnosa(unos);
    } while (!unos.equalsIgnoreCase("Q"));

    System.out.println("Gasenje sustava FOI Zeljeznice...\n");
  }

  private void provjeraVrsteUnosa(String unos) {
    String[] dijeloviKomande = unos.split(" ");
    String glavniDioKomande = dijeloviKomande[0];

    if (glavniDioKomande.equals("IP")) {
      provjeriIP(dijeloviKomande, unos);
    } else if (glavniDioKomande.equals("ISP")) {
      provjeriISP(dijeloviKomande, unos);
    } else if (glavniDioKomande.equals("ISI2S")) {
      provjeriISI2S(dijeloviKomande, unos);
    } else if (glavniDioKomande.equals("IK")) {
      provjeriIK(dijeloviKomande, unos);
    } else if (glavniDioKomande.equals("SVAVOZILA")) {
      ispisSvihVozilaUSustavu();
    } else if (glavniDioKomande.equals("SVESTANICE")) {
      ispisSvihStanicaUSustavu();
    } else if (glavniDioKomande.equals("SVEKOMP")) {
      ispisSvihKompozicijaUSustavu();
    } else {
      if (!unos.equalsIgnoreCase("Q")) {
        System.out.println("Neispravna komanda.");
      }
    }
  }

  private void provjeriIK(String[] dijeloviKomande, String unos) {
    Pattern predlozakIK = Pattern.compile("^IK (?<oznaka>\\d+)$");
    Matcher poklapanjePredlozakIK = predlozakIK.matcher(unos);

    if (!poklapanjePredlozakIK.matches()) {
      System.out.println("Neispravna komanda - format IK oznaka");
    } else {
      List<Vozilo> vozilaKompozicije =
          dohvatiVozilaKompozicije(Integer.valueOf(poklapanjePredlozakIK.group("oznaka")));
      if (vozilaKompozicije.size() > 0) {
        ispisDijelovaKompozicije(vozilaKompozicije);
      } else {
        System.out.println("Ne postoji kompozicija s tom oznakom.");
      }
    }
  }

  private void provjeriISP(String[] dijeloviKomande, String unos) {
    Pattern predlozakISP =
        Pattern.compile("^ISP (?<oznakaPruge>[\\p{L}\\d]+) (?<redoslijed>[NO])\\s*$");
    Matcher poklapanjePredlozakISP = predlozakISP.matcher(unos);

    if (!poklapanjePredlozakISP.matches()) {
      System.out.println("Neispravna komanda - format ISP oznakaPruge redoslijed");
    } else {
      List<Stanica> stanicePruge = dohvatiStanicePruge(poklapanjePredlozakISP.group("oznakaPruge"));
      if (stanicePruge.size() > 0) {
        ispisStanicaPruge(stanicePruge, poklapanjePredlozakISP.group("redoslijed"));
      } else {
        System.out.println("Ne postoji pruga s tom oznakom.");
      }
    }
  }

  private void ispisDijelovaKompozicije(List<Vozilo> vozilaKompozicije) {
    for (Vozilo v : vozilaKompozicije) {
      String uloga = v.getVrstaPogona().equals("N") ? "V" : "P";
      System.out.println(v.getOznaka() + " " + uloga + " " + v.getOpis() + " " + v.getGodina() + " "
          + v.getNamjena() + " " + v.getVrstaPogona() + " " + v.getMaksimalnaBrzina()

      );
    }
  }

  private void ispisStanicaPruge(List<Stanica> stanicePruge, String redoslijed) {
    System.out.println("\n\n------------ ISPIS STANICA PRUGE ------------\n");
    int udaljenostOdPocetne = 0;

    if ("N".equals(redoslijed)) {
      for (Stanica s : stanicePruge) {
        udaljenostOdPocetne = udaljenostOdPocetne + s.getDuzina();
        System.out.println(s.getNazivStanice() + " " + s.getVrstaPruge() + " "
            + String.valueOf(udaljenostOdPocetne));
      }
    } else if ("O".equals(redoslijed)) {
      for (Stanica s : stanicePruge) {
        udaljenostOdPocetne = udaljenostOdPocetne + s.getDuzina();
      }
      for (int i = stanicePruge.size() - 1; i >= 0; i--) {
        Stanica s = stanicePruge.get(i);
        System.out
            .println(s.getNazivStanice() + " " + s.getVrstaPruge() + " " + udaljenostOdPocetne);
        udaljenostOdPocetne = udaljenostOdPocetne - s.getDuzina();
      }
    }
    System.out.println("\n\n---------------------------------------------\n");
  }

  private void provjeriIP(String[] dijeloviKomande, String unos) {
    Pattern predlozakIP = Pattern.compile("^IP$");
    Matcher poklapanjePredlozakIP = predlozakIP.matcher(unos);

    if (!poklapanjePredlozakIP.matches()) {
      System.out.println("Neispravna komanda - format IP");
    } else {
      ispisiPruge();
    }
  }

  private void provjeriISI2S(String[] dijeloviKomande, String unos) {
    Pattern predlozakISI2S =
        Pattern.compile("^ISI2S (?<polaznaStanica>[\\p{L} ]+) - (?<odredisnaStanica>[\\p{L} ]+)$");
    Matcher poklapanjePredlozakISI2S = predlozakISI2S.matcher(unos);

    if (!poklapanjePredlozakISI2S.matches()) {
      System.out.println("Neispravna komanda - format ISI2S polaziste - odrediste");
    } else {
      List<Stanica> medustanice =
          dohvatiMedustanice(poklapanjePredlozakISI2S.group("polaznaStanica"),
              poklapanjePredlozakISI2S.group("odredisnaStanica"));

      if (medustanice.size() > 0) {
        ispisListeStanica(medustanice);
      } else {
        System.out.println("Ne postoji pruga s tom oznakom.");
      }
    }
  }

  private List<Stanica> dohvatiMedustanice(String polaznaStanica, String odredisnaStanica) {
    List<Stanica> listaMedustanica = new ArrayList();
    // TODO

    return listaMedustanica;
  }

  private List<Vozilo> dohvatiVozilaKompozicije(int oznaka) {
    List<Vozilo> vozilaKompozicije = new ArrayList();
    for (Kompozicija k : listaKompozicija) {
      if (k.getOznaka() == oznaka) {
        Vozilo vozilo = dohvatiVoziloPoOznaci(k.getOznakaPrijevoznogSredstva());
        if (vozilo != null) {
          vozilaKompozicije.add(vozilo);
        }
      }
    }
    return vozilaKompozicije;
  }

  private Vozilo dohvatiVoziloPoOznaci(String oznaka) {
    for (Vozilo v : listaVozila) {
      if (v.getOznaka().equals(oznaka)) {
        return v;
      }
    }
    return null;
  }

  private List<Stanica> dohvatiStanicePruge(String oznakaPruge) {
    List<Stanica> stanicePruge = new ArrayList();
    for (Stanica s : listaStanica) {
      if (s.getOznakaPruge().equals(oznakaPruge)) {
        stanicePruge.add(s);
      }
    }
    return stanicePruge;
  }

  private void ispisiPruge() {
    int udaljenost = 0;
    String prethodnaOznakaPruge = null;
    Stanica pocetnaStanica = null, zavrsnaStanica = null, prethodnaStanica = null;

    System.out.println("\n\n---------------- ISPIS PRUGA ----------------   \n");

    for (Stanica s : listaStanica) {
      if (prethodnaOznakaPruge == null) {
        prethodnaOznakaPruge = s.getOznakaPruge();
        pocetnaStanica = s;
        prethodnaStanica = s;
        udaljenost = udaljenost + s.getDuzina();
      } else if (!prethodnaOznakaPruge.equals(s.getOznakaPruge())) {
        zavrsnaStanica = prethodnaStanica;
        System.out.println(prethodnaOznakaPruge + " " + pocetnaStanica.getNazivStanice() + " - "
            + zavrsnaStanica.getNazivStanice() + " " + String.valueOf(udaljenost));

        prethodnaStanica = s;
        udaljenost = 0;
        pocetnaStanica = s;
        prethodnaOznakaPruge = s.getOznakaPruge();

      } else if (listaStanica.getLast().getId() == s.getId()) {
        udaljenost = udaljenost + s.getDuzina();
        System.out.println(prethodnaOznakaPruge + " " + pocetnaStanica.getNazivStanice() + " - "
            + s.getNazivStanice() + " " + String.valueOf(udaljenost)
            + "\n\n---------------------------------------------\n");
      } else {
        udaljenost = udaljenost + s.getDuzina();
        prethodnaStanica = s;
      }
    }
  }

  private void ispisListeStanica(List<Stanica> stanice) {
    System.out.println("\n--- Ispis stanica  ---");
    for (Stanica s : stanice) {
      System.out.println(s);
    }
    if (listaStanica.isEmpty()) {
      System.out.println("Lista stanica je prazna.");
    }
  }


  private void ispisSvihVozilaUSustavu() {
    System.out.println("\n--- Ispis svih vozila u sustavu ---");
    for (Vozilo v : listaVozila) {
      System.out.println(v);
    }
    if (listaVozila.isEmpty()) {
      System.out.println("Lista vozila je prazna.");
    }
  }

  private void ispisSvihStanicaUSustavu() {
    System.out.println("\n--- Ispis svih stanica u sustavu ---");
    for (Stanica s : listaStanica) {
      System.out.println(s);
    }
    if (listaStanica.isEmpty()) {
      System.out.println("Lista stanica je prazna.");
    }
  }

  private void ispisSvihKompozicijaUSustavu() {
    System.out.println("\n--- Ispis svih kompozicija u sustavu ---");
    for (Kompozicija k : listaKompozicija) {
      System.out.println(k);
    }
    if (listaKompozicija.isEmpty()) {
      System.out.println("Lista kompozicija je prazna.");
    }
  }

  private void nacrtajVlak() {
    String train =
        "       ___\n" + "  __[__|__]__[__|__]__[__|__]__\n" + "   O-O---O-O---O-O---O-O---O-O\n";
    System.out.println(train);
  }
}
