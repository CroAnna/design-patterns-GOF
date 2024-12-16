package edu.unizg.foi.uzdiz.askarica20.zadaca_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Kompozicija;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Korisnik;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.OznakaDana;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Pruga;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Stanica;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Vozilo;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.obavjestavacobserver.KorisnikConcreteObserver;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.IspisEtapaVisitor;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.IspisSimulacijeVisitor;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.IspisVlakovaPoDanimaVisitor;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.IspisVlakovaVisitor;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor.IspisVoznogRedaVisitor;

public class ZeljeznickiSustav {
  private static ZeljeznickiSustav instanca;
  private final List<Vozilo> listaVozila = new ArrayList<Vozilo>();
  private final List<Stanica> listaStanica = new ArrayList<Stanica>();
  private final List<Kompozicija> listaKompozicija = new ArrayList<Kompozicija>();
  private final List<OznakaDana> listaOznakaDana = new ArrayList<OznakaDana>();
  private final List<Pruga> listaPruga = new ArrayList<Pruga>();
  private final List<Korisnik> listaKorisnika = new ArrayList<Korisnik>();
  private final VozniRedComposite vozniRed = new VozniRedComposite();

  private int ukupanBrojGresakaUSustavu = 0, brojacKorisnika = 0, brojacGresakaVlakova = 0;
  private IspisnikPodataka ispisnik = new IspisnikPodataka();

  private ZeljeznickiSustav() {}

  public static ZeljeznickiSustav dohvatiInstancu() {
    if (instanca == null) {
      instanca = new ZeljeznickiSustav();
    }
    return instanca;
  }

  public VozniRedComposite dohvatiVozniRed() {
    return this.vozniRed;
  }

  public void ukloniVlak(VlakComposite vlak) {
    vozniRed.ukloni(vlak);
  }

  public void dodajVlak(VlakComposite vlak) {
    vozniRed.dodaj(vlak);
  }

  public void dohvatiVlak(String oznakaVlaka) {
    vozniRed.dohvatiDijete(oznakaVlaka);
  }

  public void dodajKorisnika(Korisnik korisnik) {
    listaKorisnika.add(korisnik);
  }

  public List<Korisnik> dohvatiListuKorisnika(Korisnik korisnik) {
    return this.listaKorisnika;
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

  public void dodajOznakuDana(OznakaDana oznakaDana) {
    listaOznakaDana.add(oznakaDana);
  }

  public void dodajGreskuUSustav() {
    this.ukupanBrojGresakaUSustavu++;
  }

  public int dohvatiGreskeUSustavu() {
    return this.ukupanBrojGresakaUSustavu;
  }

  public void dodajGreskuVlaka() {
    brojacGresakaVlakova++;
  }

  public int dohvatiBrojGresakaVlakova() {
    return brojacGresakaVlakova;
  }

  public IspisnikPodataka dohvatiIspisnik() {
    return this.ispisnik;
  }

  public List<Pruga> dohvatiListuPruga() {
    return listaPruga; // TODO di se ovo koristi - makni to...
  }

  public List<OznakaDana> dohvatiListuOznakaDana() {
    return listaOznakaDana;
  }

  public void zapocniRadSustava() {
    String unos = "";
    Scanner skener = new Scanner(System.in);
    ispisnik.nacrtajVlak();
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
    } else if (glavniDioKomande.equals("OD")) {
      ispisiOznakeDana(); // moja komanda koja nije trazena
    } else if (glavniDioKomande.equals("IV")) {
      provjeriIV(dijeloviKomande, unos);
    } else if (glavniDioKomande.equals("IEV")) {
      provjeriIEV(dijeloviKomande, unos);
    } else if (glavniDioKomande.equals("IEVD")) {
      provjeriIEVD(dijeloviKomande, unos);
    } else if (glavniDioKomande.equals("IVRV")) {
      provjeriIVRV(dijeloviKomande, unos);
    } else if (glavniDioKomande.equals("IVI2S")) {
      provjeriIVI2S(dijeloviKomande, unos);
    } else if (glavniDioKomande.equals("DK")) {
      provjeriDK(dijeloviKomande, unos);
    } else if (glavniDioKomande.equals("PK")) {
      provjeriPK(dijeloviKomande, unos);
    } else if (glavniDioKomande.equals("DPK")) {
      provjeriDPK(dijeloviKomande, unos);
    } else if (glavniDioKomande.equals("SVV")) {
      provjeriSVV(dijeloviKomande, unos);
    } else {
      if (!unos.equalsIgnoreCase("Q")) {
        System.out.println("Neispravna komanda.");
      }
    }
  }

  private void ispisiOznakeDana() { // moja komanda koja nije trazena
    for (OznakaDana o : listaOznakaDana) {
      System.out.println(o.toString());
    }
  }

  private void provjeriIV(String[] dijeloviKomande, String unos) {
    Pattern predlozakIV = Pattern.compile("^IV$");
    Matcher poklapanjePredlozakIV = predlozakIV.matcher(unos);

    if (!poklapanjePredlozakIV.matches()) {
      System.out.println("Neispravna komanda - format IV");
    } else {
      // double dispatch - odabire se prava metoda za izvrsavanje na temelju konkretnog tipa
      // visitora i konkretnog tipa elementa koji se posjecuje
      vozniRed.prihvati(new IspisVlakovaVisitor());
    }
  }

  private void provjeriIEV(String[] dijeloviKomande, String unos) {
    Pattern predlozakIEV = Pattern.compile("^IEV (?<oznaka>\\d+)$");
    Matcher poklapanjePredlozakIEV = predlozakIEV.matcher(unos);

    if (!poklapanjePredlozakIEV.matches()) {
      System.out.println("Neispravna komanda - format IEV oznaka");
    } else {
      // TODO
      String oznakaVlaka = poklapanjePredlozakIEV.group("oznaka");
      vozniRed.prihvati(new IspisEtapaVisitor(oznakaVlaka));
    }
  }

  private void provjeriIEVD(String[] dijeloviKomande, String unos) {
    Pattern predlozakIEVD = Pattern.compile("^IEVD (Po|U|Sr|Č|Pe|Su|N)+$");
    Matcher poklapanjePredlozakIEVD = predlozakIEVD.matcher(unos);
    if (!poklapanjePredlozakIEVD.matches()) {
      System.out
          .println("Neispravna komanda - format IEVD dani (Po U Sr Č Pe Su N ili kombinacija)");
      return;
    }

    String daniBezIEVD = unos.substring(5);
    String sortiraniDani = sortirajDane(daniBezIEVD);
    if (sortiraniDani.isEmpty()) {
      System.out.println("Neispravan dan u tjednu!");
      return;
    }
    vozniRed.prihvati(new IspisVlakovaPoDanimaVisitor(sortiraniDani));
  }

  private void provjeriIVRV(String[] dijeloviKomande, String unos) {
    Pattern predlozakIVRV = Pattern.compile("^IVRV (?<oznaka>(\\d+|[A-Z]{1,2} \\d+|[A-Z]\\d+))$");
    Matcher poklapanjePredlozakIVRV = predlozakIVRV.matcher(unos);

    if (!poklapanjePredlozakIVRV.matches()) {
      System.out.println("Neispravna komanda - format IVRV oznaka");
    } else {
      // TODO
      String oznakaVlaka = poklapanjePredlozakIVRV.group("oznaka");
      vozniRed.prihvati(new IspisVoznogRedaVisitor(oznakaVlaka));
    }
  }

  private void provjeriIVI2S(String[] dijeloviKomande, String unos) {
    Pattern predlozakIVI2S = Pattern.compile(
        "^IVI2S (?<polaznaStanica>[\\p{L}- ]+) - (?<odredisnaStanica>[\\p{L}- ]+) - (?<dan>[A-Za-z]+) - (?<odVr>\\d{1,2}:\\d{2}) - (?<doVr>\\d{1,2}:\\d{2}) - (?<prikaz>[A-Z]+)$");
    Matcher poklapanjePredlozakIVI2S = predlozakIVI2S.matcher(unos);

    if (!poklapanjePredlozakIVI2S.matches()) {
      System.out.println(
          "Neispravna komanda - format IVI2S polaznaStanica - odredišnaStanica - dan - odVr - doVr - prikaz ");
    } else {
      // TODO
    }
  }

  private void provjeriDK(String[] dijeloviKomande, String unos) {
    Pattern predlozakDK =
        Pattern.compile("^DK (?<ime>[A-Za-zČčĆćĐđŠšŽž]+) (?<prezime>[A-Za-zČčĆćĐđŠšŽž]+)$");
    Matcher poklapanjePredlozakDK = predlozakDK.matcher(unos);

    if (!poklapanjePredlozakDK.matches()) {
      System.out.println("Neispravna komanda - format DK ime prezime");
    } else {
      brojacKorisnika++;
      String ime = poklapanjePredlozakDK.group("ime");
      String prezime = poklapanjePredlozakDK.group("prezime");
      Korisnik korisnik = new Korisnik(brojacKorisnika, ime, prezime);
      dodajKorisnika(korisnik);
      System.out.println("Korisnik uspjesno dodan.");
    }
  }

  private void provjeriPK(String[] dijeloviKomande, String unos) {
    Pattern predlozakPK = Pattern.compile("^PK$");
    Matcher poklapanjePredlozakPK = predlozakPK.matcher(unos);

    if (!poklapanjePredlozakPK.matches()) {
      System.out.println("Neispravna komanda - format PK");
    } else {
      ispisnik.ispisiKorisnike(listaKorisnika);
    }
  }

  private void provjeriDPK(String[] dijeloviKomande, String unos) {
    Pattern predlozakDPK = Pattern.compile(
        "^DPK (?<ime>[A-Za-zČčĆćĐđŠšŽž]+) (?<prezime>[A-Za-zČčĆćĐđŠšŽž]+) - (?<oznakaVlaka>\\d+)(?:\\s*-\\s*(?<stanica>[\\p{L}- ]+))?$");
    Matcher poklapanjePredlozakDPK = predlozakDPK.matcher(unos);

    if (!poklapanjePredlozakDPK.matches()) {
      System.out.println("Neispravna komanda - format DPK ime prezime - oznakaVlaka [- stanica]");
    } else {
      // TODO
      String ime = poklapanjePredlozakDPK.group("ime");
      String prezime = poklapanjePredlozakDPK.group("prezime");
      String oznakaVlaka = poklapanjePredlozakDPK.group("oznakaVlaka");
      String nazivStanice = poklapanjePredlozakDPK.group("stanica");

      Korisnik korisnik = dohvatiKorisnikaPoImenu(ime, prezime);
      if (korisnik != null) {
        zakaciKorisnikaZaPracenje(korisnik, oznakaVlaka, nazivStanice, ime, prezime);
      }
    }
  }

  private void zakaciKorisnikaZaPracenje(Korisnik korisnik, String oznakaVlaka, String nazivStanice,
      String ime, String prezime) {
    System.out.println("Tražim vlak s oznakom: " + oznakaVlaka);
    KorisnikConcreteObserver observer =
        new KorisnikConcreteObserver(korisnik, oznakaVlaka, nazivStanice);

    VlakComposite vlak = (VlakComposite) vozniRed.dohvatiDijete(oznakaVlaka);
    if (vlak != null) {
      System.out.println("Pronađen vlak: " + vlak.getClass().getSimpleName());
      vlak.prikvaciObservera(observer);

      if (nazivStanice != null) {
        System.out
            .println("Dodavanje korisnika " + ime + " " + prezime + " za praćenje vlaka s oznakom "
                + oznakaVlaka + " za željezničku stanicu " + nazivStanice);
      } else {
        System.out.println("Dodavanje korisnika " + ime + " " + prezime
            + " za praćenje vlaka s oznakom " + oznakaVlaka);
      }
    } else {
      System.out.println("Vlak s oznakom " + oznakaVlaka + " ne postoji.");
    }
  }

  private void provjeriSVV(String[] dijeloviKomande, String unos) {
    Pattern predlozakSVV = Pattern
        .compile("^SVV (?<oznaka>\\d+) - (?<dan>(Po|U|Sr|Č|Pe|Su|N)) - (?<koeficijent>\\d+)$");
    Matcher poklapanjePredlozakSVV = predlozakSVV.matcher(unos);

    if (!poklapanjePredlozakSVV.matches()) {
      System.out.println(
          "Neispravna komanda - format SVV oznaka - dan - koeficijent. Dan = (Po U Sr Č Pe Su N)");
    } else {
      try {
        String oznakaVlaka = poklapanjePredlozakSVV.group("oznaka");
        String dan = poklapanjePredlozakSVV.group("dan");
        int koeficijent = Integer.parseInt(poklapanjePredlozakSVV.group("koeficijent"));

        VlakComposite vlak = (VlakComposite) vozniRed.dohvatiDijete(oznakaVlaka);
        if (vlak != null) {
          vozniRed.prihvati(new IspisSimulacijeVisitor(oznakaVlaka, dan, koeficijent));
        } else {
          System.out.println("Vlak s oznakom " + oznakaVlaka + " ne postoji.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Koeficijent mora biti cijeli broj.");
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
        ispisnik.ispisKompozicije(kompozicija);
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
        ispisnik.ispisStanicaPruge(stanicePruge, poklapanjePredlozakISP.group("redoslijed"));
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
      ispisnik.ispisiPruge(listaPruga);
    }
  }

  private void provjeriISI2S(String[] dijeloviKomande, String unos) {
    Pattern predlozakISI2S = Pattern
        .compile("^ISI2S (?<polaznaStanica>[\\p{L}- ]+) - (?<odredisnaStanica>[\\p{L}- ]+)$");
    Matcher poklapanjePredlozakISI2S = predlozakISI2S.matcher(unos);

    if (!poklapanjePredlozakISI2S.matches()) {
      System.out.println("Neispravna komanda - format ISI2S polaziste - odrediste");
    } else {
      LinkedHashMap<Stanica, Integer> stanicaUdaljenostMapa =
          dohvatiMedustanice(poklapanjePredlozakISI2S.group("polaznaStanica"),
              poklapanjePredlozakISI2S.group("odredisnaStanica"));

      if (stanicaUdaljenostMapa != null && stanicaUdaljenostMapa.size() > 0) {
        ispisnik.ispisListeStanica(stanicaUdaljenostMapa);
      } else {
        System.out.println("Pokusajte s nekim drugim stanicama.");
      }
    }
  }

  private Kompozicija dohvatiPodatkeKompozicije(int oznaka) {
    for (Kompozicija k : listaKompozicija) {
      if (k.getOznaka() == oznaka) {
        return k;
      }
    }
    return null;
  }

  private Pruga dohvatiPruguPoOznaci(String oznakaPruge) {
    for (Pruga p : listaPruga) {
      if (p.getOznaka().equals(oznakaPruge)) {
        return p;
      }
    }
    return null;
  }

  public List<Stanica> dohvatiListuMedustanica(String polaznaStanica, String odredisnaStanica,
      String oznakaPruge, String smjer) {
    Pruga pruga = dohvatiPruguPoOznaci(oznakaPruge);

    if (pruga != null)
      return pruga.dohvatiMedustanice(polaznaStanica, odredisnaStanica);

    return new ArrayList<Stanica>();
  }


  private List<String> pronadiOznakePolaznePruge(String nazivPolazneStanice) {
    List<String> oznakePruge = new ArrayList<String>();
    for (Stanica stanica : listaStanica) {
      if (stanica.getNazivStanice().equals(nazivPolazneStanice)) {
        oznakePruge.add(stanica.getOznakaPruge());
      }
    }
    return oznakePruge;
  }

  private boolean provjeriIspravnostProcesa(String polaznaStanica, String odredisnaStanica,
      List<String> oznakePruge) {
    if (polaznaStanica.equals(odredisnaStanica)) {
      System.out.print("Greska: polazna i zavrsna stanica su jednake.");
      return false;
    } else if (oznakePruge.size() == 0) {
      System.out.println("Greska: ni jedna pruga nije pronađena.");
      return false;
    } else
      return true;
  }

  private LinkedHashMap<Stanica, Integer> dohvatiMedustanice(String polaznaStanica,
      String odredisnaStanica) {
    LinkedHashMap<Stanica, Integer> medustaniceMap = new LinkedHashMap<Stanica, Integer>();
    List<Stanica> listaSvihPresjedalista = dohvatiPresjedalista();
    List<String> oznakePruge = pronadiOznakePolaznePruge(polaznaStanica);

    if (!provjeriIspravnostProcesa(polaznaStanica, odredisnaStanica, oznakePruge)) {
      return medustaniceMap;
    }

    int trenutnaUdaljenost = 0;
    String oznakaIstePruge = "";

    for (Stanica stanica : listaStanica) {
      String nazivStanice = stanica.getNazivStanice();
      if (nazivStanice.equals(polaznaStanica)) {
        oznakaIstePruge = provjeriJeLiOdredisteNaNekojOdTihPruga(oznakePruge, odredisnaStanica);
        if (!oznakaIstePruge.equals("")) {
          medustaniceMap = izracunajUdaljenostStanicaNaIstojPruzi(oznakaIstePruge, polaznaStanica,
              odredisnaStanica);
        }
      }
    }

    if (oznakaIstePruge.equals("")) {
      boolean nadenPocetak = false;

      for (Stanica stanica : listaStanica) {
        String nazivStanice = stanica.getNazivStanice();
        String oznakaPruge = oznakePruge.get(0); // radi samo za prvu dohvacenu prugu

        if (nazivStanice.equals(polaznaStanica)) {
          nadenPocetak = true;
        }

        if (nadenPocetak) {
          if (oznakaPruge.equals(stanica.getOznakaPruge())) {
            if (!stanica.getNazivStanice().equals(polaznaStanica)) {
              trenutnaUdaljenost = trenutnaUdaljenost + stanica.getDuzina();
            }

            medustaniceMap.put(stanica, trenutnaUdaljenost);
          }
        }

        if (nadenPocetak && nazivStanice.equals(odredisnaStanica)) {
          if (!stanica.getOznakaPruge().equals(oznakaPruge)) {
            List<Stanica> listaPresjedalistaNaPruzi =
                pronadiPresjedalistaNaPruzi(listaSvihPresjedalista, oznakaPruge, odredisnaStanica);
            List<Stanica> ostalePrugePresjedalista =
                dohvatiOstalePrugePresjedalista(listaPresjedalistaNaPruzi);

            LinkedHashMap<Stanica, Integer> noveMedustaniceMap = null;
            if (listaPresjedalistaNaPruzi.size() > 0 && ostalePrugePresjedalista.size() > 0) {
              boolean pronadenoOdredisteNaNovojPruzi = false;
              for (Stanica p : ostalePrugePresjedalista) {
                pronadenoOdredisteNaNovojPruzi =
                    provjeriJeLiOdredisteNaTojPruzi(p.getOznakaPruge(), odredisnaStanica);
                if (pronadenoOdredisteNaNovojPruzi) {
                  noveMedustaniceMap = dodajUdaljenost(p.getOznakaPruge(), odredisnaStanica, p,
                      medustaniceMap, trenutnaUdaljenost);
                }
              }
            }
            return noveMedustaniceMap;
          } else {
            break;
          }
        }
      }
    }
    return medustaniceMap;
  }

  public LinkedHashMap<Stanica, Integer> izracunajUdaljenostStanicaNaIstojPruzi(String oznakaPruge,
      String polaznaStanica, String odredisnaStanica) {
    List<Stanica> listaStanicaNaTojPruzi = dohvatiStanicePruge(oznakaPruge);
    boolean silazniSmjer = false, polaznaNadenaZaSmjer = false, trajeUnosStanica = false;

    for (Stanica s : listaStanicaNaTojPruzi) {
      if (polaznaStanica.equals(s.getNazivStanice())) {
        polaznaNadenaZaSmjer = true;
      }
      if (polaznaNadenaZaSmjer && odredisnaStanica.equals(s.getNazivStanice())) {
        silazniSmjer = true;
      }
    }
    LinkedHashMap<Stanica, Integer> medustaniceMap = unesiMedustaniceIstePrugePremaSmjeru(
        silazniSmjer, listaStanicaNaTojPruzi, trajeUnosStanica, polaznaStanica, odredisnaStanica);
    return medustaniceMap;
  }

  private LinkedHashMap<Stanica, Integer> unesiMedustaniceIstePrugePremaSmjeru(boolean silazniSmjer,
      List<Stanica> listaStanicaNaTojPruzi, boolean trajeUnosStanica, String polaznaStanica,
      String odredisnaStanica) {
    int udaljenost = 0;
    LinkedHashMap<Stanica, Integer> medustaniceMap = new LinkedHashMap<Stanica, Integer>();

    if (silazniSmjer) {
      for (Stanica s : listaStanicaNaTojPruzi) {
        if (!trajeUnosStanica && polaznaStanica.equals(s.getNazivStanice())) {
          medustaniceMap.put(s, 0);
          trajeUnosStanica = true;
        } else if (trajeUnosStanica && !odredisnaStanica.equals(s.getNazivStanice())) {
          udaljenost = udaljenost + s.getDuzina();
          medustaniceMap.put(s, udaljenost);
        } else if (trajeUnosStanica && odredisnaStanica.equals(s.getNazivStanice())) {
          trajeUnosStanica = false;
          udaljenost = udaljenost + s.getDuzina();
          medustaniceMap.put(s, udaljenost);
        }
      }
    } else {
      boolean startAdding = false;
      udaljenost = 0;
      Stanica previousStation = null;
      for (int i = listaStanicaNaTojPruzi.size() - 1; i >= 0; i--) {
        Stanica s = listaStanicaNaTojPruzi.get(i);
        if (!startAdding && polaznaStanica.equals(s.getNazivStanice())) {
          medustaniceMap.put(s, 0);
          startAdding = true;
          previousStation = s;
        } else if (startAdding && !odredisnaStanica.equals(s.getNazivStanice())) {
          udaljenost = udaljenost + previousStation.getDuzina();
          medustaniceMap.put(s, udaljenost);
          previousStation = s;
        } else if (startAdding && odredisnaStanica.equals(s.getNazivStanice())) {
          udaljenost = udaljenost + previousStation.getDuzina();
          medustaniceMap.put(s, udaljenost);
          break;
        }
      }
    }
    return medustaniceMap;
  }

  private LinkedHashMap<Stanica, Integer> dodajUdaljenost(String oznakaPruge,
      String odredisnaStanica, Stanica presjedaliste,
      LinkedHashMap<Stanica, Integer> medustaniceMap, int posljednjaUdaljenost) {
    List<Stanica> stanicePruge = dohvatiStanicePruge(oznakaPruge);
    int udaljenost = posljednjaUdaljenost;
    boolean nadenoPresjedaliste = false;

    for (Stanica s : stanicePruge) {
      if (s.getNazivStanice().equals(presjedaliste.getNazivStanice())) {
        nadenoPresjedaliste = true;
      }
      if (nadenoPresjedaliste) {
        if (!s.getNazivStanice().equals(presjedaliste.getNazivStanice())) {
          udaljenost = udaljenost + s.getDuzina();
        }
        medustaniceMap.put(s, udaljenost);
      }
      if (s.getNazivStanice().equals(odredisnaStanica)) {
        return medustaniceMap;
      }
    }
    return medustaniceMap;
  }

  private List<Stanica> dohvatiOstalePrugePresjedalista(List<Stanica> listaPresjedalistaNaPruzi) {
    List<Stanica> svePresjedalistaUSustavu = dohvatiPresjedalista();
    List<Stanica> ostalePrugePresjedalista = new ArrayList<>();

    for (Stanica presjedalisteNaPruzi : listaPresjedalistaNaPruzi) {
      for (Stanica presjedalisteUSustavu : svePresjedalistaUSustavu) {
        if (presjedalisteNaPruzi.getNazivStanice().equals(presjedalisteUSustavu.getNazivStanice())
            && !presjedalisteNaPruzi.getOznakaPruge()
                .equals(presjedalisteUSustavu.getOznakaPruge())) {
          ostalePrugePresjedalista.add(presjedalisteUSustavu);
        }
      }
    }
    return ostalePrugePresjedalista;
  }


  private boolean provjeriJeLiOdredisteNaTojPruzi(String oznakaPruge, String odredisnaStanica) {
    List<Stanica> listaStanicaNaTojPruzi = dohvatiStanicePruge(oznakaPruge);
    for (Stanica s : listaStanicaNaTojPruzi) {
      if (odredisnaStanica.equals(s.getNazivStanice())) {
        return true;
      }
    }
    return false;
  }

  private String provjeriJeLiOdredisteNaNekojOdTihPruga(List<String> oznakePruga,
      String odredisnaStanica) {
    for (String oznakaPruge : oznakePruga) {
      List<Stanica> listaStanicaNaTojPruzi = dohvatiStanicePruge(oznakaPruge);
      for (Stanica s : listaStanicaNaTojPruzi) {
        if (odredisnaStanica.equals(s.getNazivStanice())) {
          return s.getOznakaPruge();
        }
      }
    }
    return "";
  }

  private List<Stanica> pronadiPresjedalistaNaPruzi(List<Stanica> listaPresjedalista,
      String oznakaPruge, String nazivOdredista) {
    List<Stanica> presjedalistaNaTojPruzi = new ArrayList<Stanica>();
    for (Stanica presjedaliste : listaPresjedalista) {
      if (presjedaliste.getOznakaPruge().equals(oznakaPruge)) {
        presjedalistaNaTojPruzi.add(presjedaliste);
      }
    }
    return presjedalistaNaTojPruzi;
  }

  private List<Stanica> dohvatiPresjedalista() {
    List<Stanica> listaPresjedalista = new ArrayList<>();
    Map<String, List<Stanica>> brojacStanica = new HashMap<>();
    Stanica prethodnaStanica = null;

    for (Stanica stanica : listaStanica) {
      String nazivStanice = stanica.getNazivStanice();
      if (prethodnaStanica != null && nazivStanice.equals(prethodnaStanica.getNazivStanice())
          && stanica.getOznakaPruge().equals(prethodnaStanica.getOznakaPruge())) {
        continue;
      }
      List<Stanica> brojPojavljivanja = brojacStanica.getOrDefault(nazivStanice, new ArrayList<>());
      brojPojavljivanja.add(stanica);
      brojacStanica.put(nazivStanice, brojPojavljivanja);
      prethodnaStanica = stanica;
    }

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

  public List<Stanica> dohvatiStanicePruge(String oznakaPruge) {
    List<Stanica> stanicePruge = new ArrayList<>();
    for (Stanica s : listaStanica) {
      if (s.getOznakaPruge().equals(oznakaPruge)) {
        stanicePruge.add(s);
      }
    }
    return stanicePruge;
  }

  private Pruga pronadiPruguPremaOznaci(String oznakaPruge) {
    Pruga trazena = null;
    for (Pruga p : listaPruga) {
      if (p.getOznaka().equals(oznakaPruge)) {
        trazena = p;
        break;
      }
    }
    return trazena;
  }

  public String dohvatiPrvuStanicuPrugeSmjer(String oznakaPruge, String smjer) {
    Pruga trazena = pronadiPruguPremaOznaci(oznakaPruge);
    if (trazena == null) {
      return null;
    }

    Stanica prva = trazena.dohvatiPrvuStanicuSmjer(smjer);
    return prva != null ? prva.getNazivStanice() : null;
  }

  public String dohvatiZadnjuStanicuPrugeSmjer(String oznakaPruge, String smjer) {
    Pruga trazena = pronadiPruguPremaOznaci(oznakaPruge);
    if (trazena == null) {
      return null;
    }
    Stanica zadnja = trazena.dohvatiZadnjuStanicuSmjer(smjer);
    return zadnja != null ? zadnja.getNazivStanice() : null;
  }

  public String dohvatiNazivOznakeDanaPoOznaci(Integer oznakaDana) {
    for (OznakaDana od : listaOznakaDana) {
      if (od.getOznakaDana() == oznakaDana) {
        return od.getDaniVoznje();
      }
    }
    return "PoUSrČPeSuN";
  }

  private boolean parsirajValidirajDane(String dani, Set<String> daniSet) {
    int pozicija = 0;
    while (pozicija < dani.length()) {
      if (pozicija + 2 <= dani.length()) {
        String dvoslovniDan = dani.substring(pozicija, pozicija + 2);
        if (dvoslovniDan.equals("Po") || dvoslovniDan.equals("Su") || dvoslovniDan.equals("Pe")
            || dvoslovniDan.equals("Sr")) {
          if (!daniSet.add(dvoslovniDan)) {
            return false;
          }
          pozicija += 2;
          continue;
        }
      }
      if (pozicija + 1 <= dani.length()) {
        String jednoslovniDan = dani.substring(pozicija, pozicija + 1);
        if (jednoslovniDan.equals("U") || jednoslovniDan.equals("Č")
            || jednoslovniDan.equals("N")) {
          if (!daniSet.add(jednoslovniDan)) {
            return false;
          }
          pozicija += 1;
          continue;
        }
      }
      return false;
    }
    return true;
  }

  private String sortirajDane(String dani) {
    Set<String> daniSet = new LinkedHashSet<>();
    if (!parsirajValidirajDane(dani, daniSet)) {
      return "";
    }
    List<String> redoslijedDana = Arrays.asList("Po", "U", "Sr", "Č", "Pe", "Su", "N");
    StringBuilder sortirano = new StringBuilder();
    for (String dan : redoslijedDana) {
      if (daniSet.contains(dan)) {
        sortirano.append(dan);
      }
    }
    return sortirano.toString();
  }

  private Korisnik dohvatiKorisnikaPoIdu(int id) {
    for (Korisnik k : listaKorisnika) {
      if (k.getId() == id) {
        return k;
      }
    }
    System.out.println("Korisnik s ID " + id + " ne postoji.");
    return null;
  }

  private Korisnik dohvatiKorisnikaPoImenu(String ime, String prezime) {
    for (Korisnik k : listaKorisnika) {
      if (k.getIme().equals(ime) && k.getPrezime().equals(prezime)) {
        return k;
      }
    }
    System.out.println("Korisnik s imenom " + ime + " " + prezime + " ne postoji.");
    return null;
  }
}
