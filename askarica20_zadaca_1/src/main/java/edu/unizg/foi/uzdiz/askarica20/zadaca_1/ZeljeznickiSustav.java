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
  // ovo je singleton

  private static ZeljeznickiSustav instanca;

  private final List<Vozilo> listaVozila = new ArrayList();
  private final List<Stanica> listaStanica = new ArrayList();
  private final List<Kompozicija> listaKompozicija = new ArrayList();

  private int ukupanBrojGresakaUSustavu = 0;

  private ZeljeznickiSustav() {}; // singleton ima privatni defaultni konstruktor

  // staticka metoda getInstance koja se ponasa kao konstruktor
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

    System.out.println("-- Dobrodosli u FOI Zeljeznice! --\n");

    do {
      System.out.println("Unesite komandu ili odaberite Q za izlaz.");
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
    } else if (glavniDioKomande.equals("SVAVOZILA")) {
      ispisSvihVozilaUSustavu();
    } else if (glavniDioKomande.equals("SVESTANICE")) {
      ispisSvihStanicaUSustavu();
    } else {
      if (!unos.equalsIgnoreCase("Q")) {
        System.out.println("Neispravna komanda.");
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
      // TODO
    }
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
}
