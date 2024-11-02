package edu.unizg.foi.uzdiz.askarica20.zadaca_1.citaccsvdatotekafactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.ZeljeznickiSustav;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.dto.Vozilo;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.vozilobuilder.VoziloBuilder;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.vozilobuilder.VoziloConcreteBuilder;
import edu.unizg.foi.uzdiz.askarica20.zadaca_1.vozilobuilder.VoziloDirector;

// ConcreteProduct
public class CsvCitacVozilaProduct extends CsvCitacProduct {

  @Override
  public void ucitaj(String datoteka) {
    Pattern predlozakPrazanRedak = Pattern.compile("^;*$");
    Pattern predlozakVozilo = Pattern.compile("(?<oznaka>[^;]+);" + "(?<opis>[^;]+);"
        + "(?<proizvodac>[^;\\-]+|\\-);" + "(?<godina>\\d{4});" + "(?<namjena>[^;]+);"
        + "(?<vrstaPrijevoza>[^;]+);" + "(?<vrstaPogona>[^;]+);" + "(?<maksimalnaBrzina>\\d+);"
        + "(?<maksimalnaSnaga>-?\\d+[.,]?\\d*);" + "(?<brojSjedecihMjesta>\\d*);"
        + "(?<brojStajacihMjesta>\\d*);" + "(?<brojBicikala>\\d*);" + "(?<brojKreveta>\\d*);"
        + "(?<brojAutomobila>\\d*);" + "(?<nosivost>\\d+[.,]?\\d*|0);"
        + "(?<povrsina>\\d+[.,]?\\d*|0);" + "(?<zapremnina>\\d*|0);" + "(?<statusVozila>[^;]+)$");


    try (BufferedReader citac = new BufferedReader(new FileReader(datoteka))) {
      String redak;
      int brojRetka = 1, ukupanBrojGresakaUDatoteci = 0;

      while ((redak = citac.readLine()) != null) {
        boolean preskociPrvog = false;
        if (brojRetka == 1) {
          preskociPrvog = prviRedakJeInformativan(redak.split(";"), citac);
        }

        if (!preskociPrvog) {
          Matcher poklapanjeVozila = predlozakVozilo.matcher(redak);
          Matcher poklapanjePraznogRetka = predlozakPrazanRedak.matcher(redak);

          boolean redakDobrogFormata = poklapanjeVozila.matches();

          String[] dijeloviRetka = redak.split(";");

          // TODO dodaj provjeru jel prvi redak informativni
          // provjeriPrviRedak(dijeloviRetka,citac);

          if (poklapanjePraznogRetka.matches() || redak.startsWith("#")) {
            System.out.print("preskocen" + redak + "\n");
            continue; // prazan red ni ak počinje s # se ne racuna kao greska, nego se samo treba
                      // preskociti
          }

          if (redakDobrogFormata && dijeloviRetka.length == 18) {
            VoziloBuilder builder = new VoziloConcreteBuilder();
            VoziloDirector voziloDirector = new VoziloDirector(builder);
            Vozilo vozilo = null;

            switch (dijeloviRetka[5]) {
              case "N":
                vozilo = voziloDirector.konstruirajLokomotivu(brojRetka, dijeloviRetka[0],
                    dijeloviRetka[1], dijeloviRetka[2], Integer.valueOf(dijeloviRetka[3]),
                    dijeloviRetka[4], dijeloviRetka[5], dijeloviRetka[6],
                    Integer.valueOf(dijeloviRetka[7]),
                    Double.parseDouble(dijeloviRetka[8].replace(',', '.')), dijeloviRetka[17]);
                break;
              case "P":
                vozilo = voziloDirector.konstruirajPutnickiVagon(brojRetka, dijeloviRetka[0],
                    dijeloviRetka[1], dijeloviRetka[2], Integer.valueOf(dijeloviRetka[3]),
                    dijeloviRetka[4], dijeloviRetka[5], dijeloviRetka[6],
                    Integer.valueOf(dijeloviRetka[7]),
                    Double.parseDouble(dijeloviRetka[8].replace(',', '.')),
                    Integer.valueOf(dijeloviRetka[9]), Integer.valueOf(dijeloviRetka[10]),
                    Integer.valueOf(dijeloviRetka[11]), Integer.valueOf(dijeloviRetka[12]),
                    dijeloviRetka[17]);
                break;
              case "TA":
                vozilo = voziloDirector.konstruirajTeretniAutomobilskiVagon(brojRetka,
                    dijeloviRetka[0], dijeloviRetka[1], dijeloviRetka[2],
                    Integer.valueOf(dijeloviRetka[3]), dijeloviRetka[4], dijeloviRetka[5],
                    dijeloviRetka[6], Integer.valueOf(dijeloviRetka[7]),
                    Double.parseDouble(dijeloviRetka[8].replace(',', '.')),
                    Integer.valueOf(dijeloviRetka[13]),
                    Double.valueOf(dijeloviRetka[14].replace(',', '.')),
                    Double.valueOf(dijeloviRetka[15].replace(',', '.')), dijeloviRetka[17]);
                break;
              case "TK":
                vozilo = voziloDirector.konstruirajTeretniKontejnerskiVagon(brojRetka,
                    dijeloviRetka[0], dijeloviRetka[1], dijeloviRetka[2],
                    Integer.valueOf(dijeloviRetka[3]), dijeloviRetka[4], dijeloviRetka[5],
                    dijeloviRetka[6], Integer.valueOf(dijeloviRetka[7]),
                    Double.parseDouble(dijeloviRetka[8].replace(',', '.')),
                    Double.valueOf(dijeloviRetka[14].replace(',', '.')),
                    Double.valueOf(dijeloviRetka[15].replace(',', '.')), dijeloviRetka[17]);
                break;
              case "TRS":
                vozilo = voziloDirector.konstruirajTeretniRobniRasutoVagon(brojRetka,
                    dijeloviRetka[0], dijeloviRetka[1], dijeloviRetka[2],
                    Integer.valueOf(dijeloviRetka[3]), dijeloviRetka[4], dijeloviRetka[5],
                    dijeloviRetka[6], Integer.valueOf(dijeloviRetka[7]),
                    Double.parseDouble(dijeloviRetka[8].replace(',', '.')),
                    Double.valueOf(dijeloviRetka[14].replace(',', '.')),
                    Double.valueOf(dijeloviRetka[15].replace(',', '.')),
                    Integer.valueOf(dijeloviRetka[16]), dijeloviRetka[17]);
                break;
              case "TTS":
                vozilo = voziloDirector.konstruirajTeretniRobniTekuceVagon(brojRetka,
                    dijeloviRetka[0], dijeloviRetka[1], dijeloviRetka[2],
                    Integer.valueOf(dijeloviRetka[3]), dijeloviRetka[4], dijeloviRetka[5],
                    dijeloviRetka[6], Integer.valueOf(dijeloviRetka[7]),
                    Double.parseDouble(dijeloviRetka[8].replace(',', '.')),
                    Double.valueOf(dijeloviRetka[14].replace(',', '.')),
                    Integer.valueOf(dijeloviRetka[16]), dijeloviRetka[17]);
                break;
              default:
                throw new Error("Neispravna vrsta vozila.");
            }

            ZeljeznickiSustav.dohvatiInstancu().dodajVozilo(vozilo);

          } else {
            ukupanBrojGresakaUDatoteci++;
            ZeljeznickiSustav.dohvatiInstancu().dodajGreskuUSustav();

            System.out.println(
                "Svi stupci datoteke vozila nisu ispravno popunjeni! Ukupno gresaka u datoteci vozila: "
                    + ukupanBrojGresakaUDatoteci + "! Ukupno gresaka u sustavu: "
                    + ZeljeznickiSustav.dohvatiInstancu().dohvatiGreskeUSustavu() + "\n");
          }
        }
        brojRetka++;
      }
      System.out.println("Vozila uspjesno ucitana.");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  private boolean prviRedakJeInformativan(String[] dijeloviRetka, BufferedReader citac) {
    String[] dijeloviRetkaBezBOM = ukloniBOM(dijeloviRetka);

    if ("Oznaka".equals(dijeloviRetkaBezBOM[0]) && "Opis".equals(dijeloviRetkaBezBOM[1])
        && "Proizvođač".equals(dijeloviRetkaBezBOM[2]) && "Godina".equals(dijeloviRetkaBezBOM[3])
        && "Namjena".equals(dijeloviRetkaBezBOM[4])
        && "Vrsta prijevoza".equals(dijeloviRetkaBezBOM[5])
        && "Vrsta pogona".equals(dijeloviRetkaBezBOM[6])
        && "Maks brzina".equals(dijeloviRetkaBezBOM[7])
        && "Maks snaga".equals(dijeloviRetkaBezBOM[8])
        && "Broj sjedećih mjesta".equals(dijeloviRetkaBezBOM[9])
        && "Broj stajaćih mjesta".equals(dijeloviRetkaBezBOM[10])
        && "Broj bicikala".equals(dijeloviRetkaBezBOM[11])
        && "Broj kreveta".equals(dijeloviRetkaBezBOM[12])
        && "Broj automobila".equals(dijeloviRetkaBezBOM[13])
        && "Nosivost".equals(dijeloviRetkaBezBOM[14]) && "Površina".equals(dijeloviRetkaBezBOM[15])
        && "Zapremina".equals(dijeloviRetkaBezBOM[16])
        && "Status".equals(dijeloviRetkaBezBOM[17])) {
      System.out.println("Prvi redak vozila je informativan.");
      return true;
    } else {
      System.out.println("Prvi redak vozila nije informativan.");
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
