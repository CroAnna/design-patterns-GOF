package edu.unizg.foi.uzdiz.askarica20.zadaca_2.visitor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.PronalaziteljPutanje;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.ZeljeznickiSustav;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.EtapaLeaf;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VlakComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedBaseComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComponent;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.composite.VozniRedComposite;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.OznakaDana;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Stanica;

public class IspisVozRedaDoStaniceVisitor implements VozniRedVisitor {
  private final String polaznaStanica;
  private final String odredisnaStanica;
  private final String dan;
  private final String odVr;
  private final String doVr;
  private final String prikaz;
  private final List<Stanica> putanja;
  private VozniRedComposite vozniRed;

  public IspisVozRedaDoStaniceVisitor(String polaznaStanica, String odredisnaStanica, String dan,
      String odVr, String doVr, String prikaz) {
    this.polaznaStanica = polaznaStanica;
    this.odredisnaStanica = odredisnaStanica;
    this.dan = dan;
    this.odVr = odVr;
    this.doVr = doVr;
    this.prikaz = prikaz;

    PronalaziteljPutanje pronalazitelj =
        new PronalaziteljPutanje(ZeljeznickiSustav.dohvatiInstancu().dohvatiListuStanica());
    this.putanja = pronalazitelj.dohvatiPutanjuIzmeduStanica(polaznaStanica, odredisnaStanica);

    for (Stanica s : putanja) {
      System.out.println("Stanica: " + s.getNazivStanice() + ", udaljenost: " + s.getDuzina());
    }
  }

  @Override
  public void posjetiElement(VozniRedBaseComposite vozniRedBaseComposite) {
    if (vozniRedBaseComposite instanceof VozniRedComposite) {
      this.vozniRed = (VozniRedComposite) vozniRedBaseComposite;
      ispisiRezultate();
    }
  }

  private String dohvatiSmjerEtape(VlakComposite vlak) {
    for (VozniRedComponent komponenta : vlak.dohvatiDjecu()) {
      if (komponenta instanceof EtapaLeaf) {
        EtapaLeaf etapa = (EtapaLeaf) komponenta;
        List<Stanica> staniceEtape = etapa.getListaStanicaEtape();

        // Check if both our stations are in this etapa
        boolean containsPolazna = false;
        boolean containsOdredisna = false;
        int indexPolazna = -1;
        int indexOdredisna = -1;

        for (int i = 0; i < staniceEtape.size(); i++) {
          String nazivStanice = staniceEtape.get(i).getNazivStanice();
          if (nazivStanice.equals(polaznaStanica)) {
            containsPolazna = true;
            indexPolazna = i;
          }
          if (nazivStanice.equals(odredisnaStanica)) {
            containsOdredisna = true;
            indexOdredisna = i;
          }
        }

        if (containsPolazna && containsOdredisna) {
          return indexPolazna < indexOdredisna ? "N" : "O";
        }
      }
    }
    return "N"; // default to N if not found
  }

  private void ispisiRezultate() {
    if (putanja.isEmpty()) {
      System.out.println("Nema pronađenog puta između zadanih stanica.");
      return;
    }

    // Find all trains that pass through this route
    List<VlakComposite> vlakovi = new ArrayList<>();
    for (VozniRedComponent komponenta : vozniRed.dohvatiDjecu()) {
      if (komponenta instanceof VlakComposite) {
        VlakComposite vlak = (VlakComposite) komponenta;
        if (prolaziliVlakKrozStanice(vlak)) {
          vlakovi.add(vlak);
        }
      }
    }
    vlakovi.sort(Comparator.comparingInt(VlakComposite::getVrijemePolaska));

    if (!vlakovi.isEmpty()) {
      // Get direction from the first train that matches our route
      String smjer = dohvatiSmjerEtape(vlakovi.get(0));

      // Print header dynamically based on prikaz
      StringBuilder zaglavlje = new StringBuilder();
      for (char format : prikaz.toCharArray()) {
        switch (format) {
          case 'K':
            zaglavlje.append(String.format("%-6s", "km"));
            break;
          case 'P':
            zaglavlje.append(String.format("%-10s", "Pruga"));
            break;
          case 'S':
            zaglavlje.append(String.format("%-20s", "Stanica"));
            break;
          case 'V':
            for (VlakComposite vlak : vlakovi) {
              zaglavlje.append(String.format("%-10s", "vlak " + vlak.getOznakaVlaka()));
            }
            break;
        }
      }
      System.out.println(zaglavlje.toString());

      // Print rows dynamically based on prikaz
      int ukupnoKm = 0;
      for (Stanica stanica : putanja) {
        StringBuilder red = new StringBuilder();

        // Add distance before printing for direction N
        if (smjer.equals("N")) {
          ukupnoKm += stanica.getDuzina();
        }

        for (char format : prikaz.toCharArray()) {
          switch (format) {
            case 'K':
              red.append(String.format("%-6d", ukupnoKm));
              break;
            case 'P':
              red.append(String.format("%-10s", stanica.getOznakaPruge()));
              break;
            case 'S':
              String nazivStanice = stanica.getNazivStanice();
              if (nazivStanice.length() > 19) {
                nazivStanice = nazivStanice.substring(0, 19);
              }
              red.append(String.format("%-20s", nazivStanice));
              break;
            case 'V':
              for (VlakComposite vlak : vlakovi) {
                String vrijemePolaska = dohvatiVrijemePolaska(vlak, stanica);
                red.append(String.format("%-10s", vrijemePolaska));
              }
              break;
          }
        }
        System.out.println(red.toString());

        // Add distance after printing for direction O
        if (smjer.equals("O")) {
          ukupnoKm += stanica.getDuzina();
        }
      }
    }
  }

  private String dohvatiVrijemePolaska(VlakComposite vlak, Stanica stanica) {
    int vrijemePolaska = -1;

    // Prolazimo kroz sve etape vlaka
    for (VozniRedComponent komponenta : vlak.dohvatiDjecu()) {
      if (komponenta instanceof EtapaLeaf) {
        EtapaLeaf etapa = (EtapaLeaf) komponenta;

        // Provjeravamo sve stanice u etapi
        List<Stanica> staniceEtape = etapa.getListaStanicaEtape();
        for (int i = 0; i < staniceEtape.size(); i++) {
          if (staniceEtape.get(i).getNazivStanice().equals(stanica.getNazivStanice())) {
            vrijemePolaska = etapa.getVrijemePolaskaUMinutama();
            // Dodajemo vrijeme potrebno da vlak dođe do ove stanice
            for (int j = 0; j < i; j++) {
              vrijemePolaska += staniceEtape.get(j).getVrNorm();
            }
            return pretvoriMinuteUVrijeme(vrijemePolaska);
          }
        }
      }
    }
    return "";
  }

  private boolean prolaziliVlakKrozStanice(VlakComposite vlak) {
    Set<String> staniceVlaka = new HashSet<>();

    if (!voziLiVlakNaDan(vlak.getOznakaVlaka(), dan)) {
      return false;
    }

    // First check if the train operates within the specified time range
    int odVrijeme = pretvoriVrijemeUMinute(odVr);
    int doVrijeme = pretvoriVrijemeUMinute(doVr);

    // Get train's departure time from first station
    int vrijemePolaskaVlaka = vlak.getVrijemePolaska();

    // Filter out trains that don't match the time criteria
    if (vrijemePolaskaVlaka < odVrijeme || vrijemePolaskaVlaka > doVrijeme) {
      return false;
    }

    // Collect all stations the train passes through
    for (VozniRedComponent komponenta : vlak.dohvatiDjecu()) {
      if (komponenta instanceof EtapaLeaf) {
        EtapaLeaf etapa = (EtapaLeaf) komponenta;
        for (Stanica stanica : etapa.getListaStanicaEtape()) {
          staniceVlaka.add(stanica.getNazivStanice());
        }
      }
    }

    // Check if the train passes through both required stations
    return staniceVlaka.contains(polaznaStanica) && staniceVlaka.contains(odredisnaStanica);
  }

  private String pretvoriMinuteUVrijeme(int minute) {
    int sati = minute / 60;
    int min = minute % 60;
    return String.format("%02d:%02d", sati, min);
  }

  private int pretvoriVrijemeUMinute(String vrijeme) {
    String[] dijelovi = vrijeme.split(":");
    return Integer.parseInt(dijelovi[0]) * 60 + Integer.parseInt(dijelovi[1]);
  }

  private boolean voziLiVlakNaDan(String oznakaVlaka, String trazeniDan) {
    for (OznakaDana oznakaDana : ZeljeznickiSustav.dohvatiInstancu().dohvatiListuOznakaDana()) {
      String brojOznake = oznakaVlaka.replaceAll("\\D+", "");
      if (brojOznake.isEmpty())
        continue;

      if (oznakaDana.getOznakaDana() == Integer.parseInt(brojOznake)) {
        String daniVoznje = oznakaDana.getDaniVoznje();
        String prviDani = daniVoznje.split("\\s+")[0];
        return prviDani.contains(trazeniDan);
      }
    }
    return true;
  }

  private boolean jesuLiVremenaUnutarIntervala(VlakComposite vlak) {
    int odVrijeme = pretvoriVrijemeUMinute(odVr);
    int doVrijeme = pretvoriVrijemeUMinute(doVr);
    return vlak.getVrijemePolaska() >= odVrijeme && vlak.getVrijemeDolaska() <= doVrijeme;
  }

  @Override
  public void posjetiElement(EtapaLeaf etapaLeaf) {}
}
