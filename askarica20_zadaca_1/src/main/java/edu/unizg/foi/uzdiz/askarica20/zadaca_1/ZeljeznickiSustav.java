package edu.unizg.foi.uzdiz.askarica20.zadaca_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    skener.close();
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
      int oznaka = Integer.valueOf(poklapanjePredlozakIK.group("oznaka"));
      Kompozicija kompozicija = dohvatiPodatkeKompozicije(oznaka);

      if (kompozicija != null) {
        ispisKompozicije(kompozicija);
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
      LinkedHashMap<Stanica, Integer> stanicaUdaljenostMapa =
          dohvatiMedustanice(poklapanjePredlozakISI2S.group("polaznaStanica"),
              poklapanjePredlozakISI2S.group("odredisnaStanica"));

      /*
       * List<Stanica> medustanice =
       * dohvatiMedustanice(poklapanjePredlozakISI2S.group("polaznaStanica"),
       * poklapanjePredlozakISI2S.group("odredisnaStanica"));
       */

      if (stanicaUdaljenostMapa.size() > 0) {
        ispisListeStanica(stanicaUdaljenostMapa);
      } else {
        System.out.println("Ne postoji pruga s tom oznakom.");
      }
    }
  }

  private Kompozicija dohvatiPodatkeKompozicije(int oznaka) {
    Kompozicija kompozicija = null;
    for (Kompozicija k : listaKompozicija) {
      if (k.getOznaka() == oznaka) {
        return k;
      }
    }
    return null;
  }

  private void ispisStanicaPruge(List<Stanica> stanicePruge, String redoslijed) {
    System.out.println(
        "\n\n-------------------------- ISPIS STANICA PRUGE ---------------------------\n");
    System.out.printf("%-30s %-20s %-20s\n", "Naziv stanice", "Vrsta stanice",
        "Udaljenost od početne");
    System.out
        .println("--------------------------------------------------------------------------");

    int udaljenostOdPocetne = 0;

    if ("N".equals(redoslijed)) {
      for (Stanica s : stanicePruge) {
        udaljenostOdPocetne = udaljenostOdPocetne + s.getDuzina();
        System.out.printf("%-30s %-20s %-20d\n", s.getNazivStanice(), s.getVrstaStanice(),
            udaljenostOdPocetne);;
      }
    } else if ("O".equals(redoslijed)) {
      for (int i = stanicePruge.size() - 1; i >= 0; i--) {
        Stanica s = stanicePruge.get(i);
        System.out.printf("%-30s %-20s %-20d\n", s.getNazivStanice(), s.getVrstaStanice(),
            udaljenostOdPocetne);
        udaljenostOdPocetne = udaljenostOdPocetne + s.getDuzina();
      }
    }
    System.out.println(
        "\n\n--------------------------------------------------------------------------\n");
  }

  private LinkedHashMap<Stanica, Integer> dohvatiMedustanice(String polaznaStanica,
      String odredisnaStanica) {
    System.out.print("Traženje rute: " + polaznaStanica + " -> " + odredisnaStanica);

    LinkedHashMap<Stanica, Integer> medustaniceMap = new LinkedHashMap<Stanica, Integer>();
    List<Stanica> listaSvihPresjedalista = dohvatiPresjedalista();

    /*
     * System.out.println("presjedalista------------------");
     * 
     * for (Stanica pres : listaSvihPresjedalista) { System.out.println(pres); }
     * System.out.println("------------------\n\n");
     */


    // Oznaka pruge polazne stanice
    String oznakaPruge = null;

    // Prvo provjerimo da li polazna stanica postoji i dohvatimo oznaku pruge
    for (Stanica stanica : listaStanica) {
      if (stanica.getNazivStanice().equals(polaznaStanica)) {
        oznakaPruge = stanica.getOznakaPruge();
        System.out.println("Polazna stanica: " + polaznaStanica + ", Oznaka pruge: " + oznakaPruge);
        break;
      }
    }

    if (polaznaStanica.equals(odredisnaStanica)) {
      System.out.print("Polazna i zavrsna su jednake.");
      return medustaniceMap;
    }

    // Ako oznaka pruge nije pronađena, vraćamo prazan popis
    if (oznakaPruge == null) {
      System.out.print("Polazna stanica nije pronađena.");
      return medustaniceMap;
    }

    boolean nadenPocetak = false;
    boolean nadenKraj = false;
    int trenutnaUdaljenost = 0;

    for (Stanica stanica : listaStanica) {
      String nazivStanice = stanica.getNazivStanice();
      System.out.println(
          "Provjeravam stanicu: " + nazivStanice + ", Oznaka pruge: " + stanica.getOznakaPruge());

      // Počnimo s prikupljanjem stanica kada pronađemo polaznu stanicu
      if (nazivStanice.equals(polaznaStanica)) {
        nadenPocetak = true;
        System.out.println("pronaden pocetak!");
      }

      // Ako smo započeli prikupljanje, dodaj trenutnu stanicu na popis
      if (nadenPocetak) {

        if (oznakaPruge.equals(stanica.getOznakaPruge())) {
          if (!stanica.getNazivStanice().equals(polaznaStanica)) {
            trenutnaUdaljenost = trenutnaUdaljenost + stanica.getDuzina();
            System.out.println("povecavam udaljenost na " + trenutnaUdaljenost);
          }



          System.out.println(
              "--> stanica se dodaje " + stanica.getNazivStanice() + " " + trenutnaUdaljenost);
          medustaniceMap.put(stanica, trenutnaUdaljenost);
        }
      }

      // Provjerite je li trenutna stanica krajnja stanica
      if (nazivStanice.equals(odredisnaStanica)) {
        nadenKraj = true;
        System.out.println("pronaden kraj!");

        // Ako je krajnja stanica na drugoj pruzi, provjerite za presjedališta
        if (!stanica.getOznakaPruge().equals(oznakaPruge)) {
          // Provjerite postoje li presjedališta između polazne i krajnje stanice
          List<Stanica> listaPresjedalistaNaPruzi =
              pronadiPresjedalistaNaPruzi(listaSvihPresjedalista, oznakaPruge, odredisnaStanica);
          System.out
              .print("Krajnja stanica nije na istoj pruzi kao polazna, ali postoje presjedališta: "
                  + listaPresjedalistaNaPruzi + "\n\n");

          List<Stanica> ostalePrugePresjedalista =
              dohvatiOstalePrugePresjedalista(listaPresjedalistaNaPruzi);

          System.out
              .print("Ta presjedalista vode do drugih pruga: " + ostalePrugePresjedalista + "\n\n");

          LinkedHashMap<Stanica, Integer> noveMedustaniceMap = null;

          if (listaPresjedalistaNaPruzi.size() > 0 && ostalePrugePresjedalista.size() > 0) {
            boolean pronadenoOdredisteNaPruzi = false;
            for (Stanica p : ostalePrugePresjedalista) {
              pronadenoOdredisteNaPruzi =
                  provjeriJeLiOdredisteNaNovojPruzi(p.getOznakaPruge(), odredisnaStanica);
              if (pronadenoOdredisteNaPruzi) {
                System.out.print("!!!!!Pronadeno odrediste " + odredisnaStanica + " na pruzi "
                    + p.getOznakaPruge());
                noveMedustaniceMap = dodajUdaljenost(p.getOznakaPruge(), odredisnaStanica, p,
                    medustaniceMap, trenutnaUdaljenost);
                /*
                 * System.out.print("------------------------------------");
                 * System.out.print(medustaniceMap);
                 * System.out.print("------------------------------------");
                 */

              } else {
                System.out.print("Nije ronadeno odrediste " + odredisnaStanica + " na pruzi "
                    + p.getOznakaPruge());
              }
            }
          }
          System.out.print("\n1-vracam" + noveMedustaniceMap);
          return noveMedustaniceMap;

        } else {
          System.out.print("2-vracam");
          // Ako su na istoj pruzi, završavamo
          break; // Pronađena je krajnja stanica na istoj pruzi
        }
      }
    }

    System.out.print("Pronađena početna: " + nadenPocetak + ", Pronađena završna: " + nadenKraj);
    System.out.print("3-vracam");
    return medustaniceMap;
  }

  private LinkedHashMap<Stanica, Integer> dodajUdaljenost(String oznakaPruge,
      String odredisnaStanica, Stanica presjedaliste,
      LinkedHashMap<Stanica, Integer> medustaniceMap, int posljednjaUdaljenost) {

    System.out.print("\nposljednjaUdaljenost = " + posljednjaUdaljenost + " \n");

    List<Stanica> stanicePruge = dohvatiStanicePruge(oznakaPruge);
    int udaljenost = posljednjaUdaljenost;
    int udaljenostOdPresjedalista = 0;
    boolean nadenoPresjedaliste = false;

    for (Stanica s : stanicePruge) {
      if (s.getNazivStanice().equals(presjedaliste.getNazivStanice())) {
        udaljenostOdPresjedalista = presjedaliste.getDuzina();
        nadenoPresjedaliste = true;
      }
      if (nadenoPresjedaliste) {
        if (!s.getNazivStanice().equals(presjedaliste.getNazivStanice())) {
          // ne dodaj udaljenost od tog istog stajalista jer je to nula i dalje
          udaljenost = udaljenost + s.getDuzina();
        }
        medustaniceMap.put(s, udaljenost);
        System.out.print("Dodano stajaliste " + s.getNazivStanice() + "\n");
      }

      if (s.getNazivStanice().equals(odredisnaStanica)) {
        /*
         * System.out.print("\n-Finalno:"); System.out.print(medustaniceMap);
         * System.out.print("\n-------\n");
         */
        return medustaniceMap;
      }
    }
    return medustaniceMap;
  }

  private List<Stanica> dohvatiOstalePrugePresjedalista(List<Stanica> listaPresjedalistaNaPruzi) {
    List<Stanica> svePresjedalistaUSustavu = dohvatiPresjedalista(); // dohvaćamo sve presjedališta
                                                                     // u sustavu
    List<Stanica> ostalePrugePresjedalista = new ArrayList<>(); // lista za pohranu stanica s drugim
                                                                // prugama

    for (Stanica presjedalisteNaPruzi : listaPresjedalistaNaPruzi) {
      for (Stanica presjedalisteUSustavu : svePresjedalistaUSustavu) {
        // Provjeravamo je li naziv isti, ali pruga različita
        if (presjedalisteNaPruzi.getNazivStanice().equals(presjedalisteUSustavu.getNazivStanice())
            && !presjedalisteNaPruzi.getOznakaPruge()
                .equals(presjedalisteUSustavu.getOznakaPruge())) {

          // Dodajemo stanicu u rezultirajuću listu
          ostalePrugePresjedalista.add(presjedalisteUSustavu);
        }
      }
    }

    return ostalePrugePresjedalista;
  }


  private boolean provjeriJeLiOdredisteNaNovojPruzi(String oznaka, String odredisnaStanica) {
    List<Stanica> stanicePruge = dohvatiStanicePruge(oznaka);
    for (Stanica s : stanicePruge) {
      if (s.getNazivStanice().equals(odredisnaStanica)) {
        return true;
      }
    }
    return false;
  }

  private List<Stanica> pronadiPresjedalistaNaPruzi(List<Stanica> listaPresjedalista,
      String oznakaPruge, String nazivOdredista) {

    List<Stanica> presjedalistaNaTojPruzi = new ArrayList<Stanica>();
    for (Stanica presjedaliste : listaPresjedalista) {

      if (presjedaliste.getOznakaPruge().equals(oznakaPruge)) {
        System.out.println(
            "Pronađeno presjedalište: " + presjedaliste.getNazivStanice() + " " + oznakaPruge);
        presjedalistaNaTojPruzi.add(presjedaliste);
      }
    }
    return presjedalistaNaTojPruzi;
  }


  private LinkedHashMap<Stanica, Integer> pronadiPresjedalistaNaPruzi(
      List<Stanica> listaPresjedalista, String oznakaPruge, String odrediste,
      LinkedHashMap<Stanica, Integer> medustaniceMap) {
    System.out.println("sad si na pruzi " + oznakaPruge);

    for (Stanica presjedaliste : listaPresjedalista) {
      System.out.println(presjedaliste);
      System.out.println(presjedaliste.getNazivStanice() + " " + presjedaliste.getOznakaPruge());
      // Provjerite je li presjedalište u pravu i na istoj pruzi kao krajnja

      // TODO tu zapne mislim na prvoj pruzi koja je presjedaliste, a ne na onoj na kojoj treba
      // tu provjeri jel to presjedaliste i na pocetnoj pruzi i na pruzi koja nam treba do odredista
      if (presjedaliste.getOznakaPruge().equals(oznakaPruge)) {
        // Pronađena presjedališta, vraćamo sve stanice do njih
        System.out.println(
            "Pronađeno presjedalište: " + presjedaliste.getNazivStanice() + " " + oznakaPruge);

        medustaniceMap =
            odiNaTuPruguNaToPresjedaliste(oznakaPruge, presjedaliste, odrediste, medustaniceMap);
        return medustaniceMap;
      }
      // TODO ne znam jel ce kaj strgat ak tu ne breakam van...
    }
    return medustaniceMap;
  }

  private LinkedHashMap<Stanica, Integer> odiNaTuPruguNaToPresjedaliste(String oznakaPruge,
      Stanica presjedaliste, String odrediste, LinkedHashMap<Stanica, Integer> medustaniceMap) {
    List<Stanica> stanicePruge = dohvatiStanicePruge(oznakaPruge);
    boolean pronadenoPresjedaliste = false;
    boolean pronadenoOdrediste = false;
    boolean pronadenoPresjedaliste2 = false;
    boolean pronadenoOdrediste2 = false;

    for (Stanica s : stanicePruge) {
      if (s.getNazivStanice().equals(presjedaliste.getNazivStanice())) {
        pronadenoPresjedaliste = true;
      }

      if (s.getNazivStanice().equals(odrediste)) {
        pronadenoOdrediste = true;
      }
    }

    if (pronadenoOdrediste && pronadenoPresjedaliste) {
      int trenutnaUdaljenost = 0;

      for (Stanica s : stanicePruge) {
        if (s.getNazivStanice().equals(presjedaliste.getNazivStanice())) {
          pronadenoPresjedaliste2 = true;
        }
        if (pronadenoOdrediste2) {
          trenutnaUdaljenost = trenutnaUdaljenost + s.getDuzina() - presjedaliste.getDuzina();
          medustaniceMap.put(s, trenutnaUdaljenost);

        }


      }

      return medustaniceMap; // todo
    } else {
      return medustaniceMap;
    }
  }

  private List<Stanica> dohvatiPresjedalista() {
    List<Stanica> listaPresjedalista = new ArrayList<>();
    Map<String, List<Stanica>> brojacStanica = new HashMap<>();

    Stanica prethodnaStanica = null;

    for (Stanica stanica : listaStanica) {
      String nazivStanice = stanica.getNazivStanice();

      // Avoid counting consecutive duplicates on the same railway line
      if (prethodnaStanica != null && nazivStanice.equals(prethodnaStanica.getNazivStanice())
          && stanica.getOznakaPruge().equals(prethodnaStanica.getOznakaPruge())) {
        // Skip if it’s the same station consecutively on the same line
        continue;
      }

      // Get or initialize the list of occurrences for the current station
      List<Stanica> occurrences = brojacStanica.getOrDefault(nazivStanice, new ArrayList<>());
      occurrences.add(stanica); // Add the current station occurrence
      brojacStanica.put(nazivStanice, occurrences);

      prethodnaStanica = stanica;
    }

    // Add all stations that appear two or more times (each occurrence)
    for (List<Stanica> stanice : brojacStanica.values()) {
      if (stanice.size() >= 2) {
        listaPresjedalista.addAll(stanice);
      }
    }
    return listaPresjedalista;
  }

  public Vozilo dohvatiVoziloPoOznaci(String oznaka) {
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

  public void ispisKompozicije(Kompozicija k) {
    System.out.println(
        "\n\n--------------------------------------------------------- ISPIS KOMPOZICIJE ---------------------------------------------------------\n");
    System.out.printf("%-10s %-5s %-60s %-10s %-15s %-15s %-10s\n", "Oznaka", "Uloga", "Opis",
        "Godina", "Namjena", "Vrsta Pogona", "Max Brzina");
    System.out.println(
        "-------------------------------------------------------------------------------------------------"
            + "------------------------------------");

    List<Vozilo> vozilaUKompoziciji = k.getVozila();
    for (Vozilo v : vozilaUKompoziciji) {
      String uloga = v.getVrstaPogona().equals("N") ? "V" : "P";
      System.out.printf("%-10s %-5s %-60s %-10s %-15s %-15s %-10s\n", v.getOznaka(), uloga,
          v.getOpis(), v.getGodina(), v.getNamjena(), v.getVrstaPogona(), v.getMaksimalnaBrzina());
    }
    System.out.println(
        "\n-------------------------------------------------------------------------------------------------------------------------------------\n");
  }


  private void ispisiPruge() {
    int udaljenost = 0;
    String prethodnaOznakaPruge = null;
    Stanica pocetnaStanica = null, zavrsnaStanica = null, prethodnaStanica = null;

    System.out.println(
        "\n\n------------------------------------- ISPIS PRUGA --------------------------------------\n");
    System.out.printf("%-15s %-30s %-30s %-10s\n", "Oznaka pruge", "Početna stanica",
        "Završna stanica", "Udaljenost");
    System.out.println(
        "----------------------------------------------------------------------------------------");

    for (Stanica s : listaStanica) {
      if (prethodnaOznakaPruge == null) {
        prethodnaOznakaPruge = s.getOznakaPruge();
        pocetnaStanica = s;
        prethodnaStanica = s;
        udaljenost = udaljenost + s.getDuzina();
      } else if (!prethodnaOznakaPruge.equals(s.getOznakaPruge())) {
        zavrsnaStanica = prethodnaStanica;
        System.out.printf("%-15s %-30s %-30s %-10d\n", prethodnaOznakaPruge,
            pocetnaStanica.getNazivStanice(), zavrsnaStanica.getNazivStanice(), udaljenost);

        prethodnaStanica = s;
        udaljenost = 0;
        pocetnaStanica = s;
        prethodnaOznakaPruge = s.getOznakaPruge();

      } else if (listaStanica.getLast().getId() == s.getId()) {
        udaljenost = udaljenost + s.getDuzina();
        System.out.printf("%-15s %-30s %-30s %-10d\n", prethodnaOznakaPruge,
            pocetnaStanica.getNazivStanice(), zavrsnaStanica.getNazivStanice(), udaljenost);

        System.out.println(
            "\n----------------------------------------------------------------------------------------\n");
      } else {
        udaljenost = udaljenost + s.getDuzina();
        prethodnaStanica = s;
      }
    }
  }

  private void ispisListeStanica(LinkedHashMap<Stanica, Integer> stanice) {
    if (stanice.isEmpty()) {
      System.out.println("Lista stanica je prazna.");
      return;
    }

    System.out.println(
        "\n------------------------------------ ISPIS STANICA  ------------------------------------");
    System.out.printf("%-30s %-15s %-10s %s%n", "Naziv stanice", "Vrsta stanice", "Udaljenost",
        "Od početne stanice");
    System.out.println(
        "----------------------------------------------------------------------------------------");

    Stanica prvaStanica = stanice.keySet().iterator().next();
    for (Map.Entry<Stanica, Integer> entry : stanice.entrySet()) {
      Stanica s = entry.getKey();
      int udaljenost = entry.getValue();
      System.out.printf("%-30s %-15s %-10d km od %-10s%n", s.getNazivStanice(), s.getVrstaStanice(),
          udaljenost, prvaStanica.getNazivStanice());
    }
    System.out.println(
        "\n----------------------------------------------------------------------------------------\n");
  }

  private void nacrtajVlak() {
    String train =
        "       ___\n" + "  __[__|__]__[__|__]__[__|__]__\n" + "   O-O---O-O---O-O---O-O---O-O\n";
    System.out.println(train);
  }
}
