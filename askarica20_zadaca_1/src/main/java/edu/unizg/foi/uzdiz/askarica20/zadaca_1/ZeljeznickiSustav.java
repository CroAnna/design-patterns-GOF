package edu.unizg.foi.uzdiz.askarica20.zadaca_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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

    System.out.println("Dobrodosli u FOI Zeljeznice!\n");
    // radi i slusaj komande dok korisnik ne odabere Q
    do {
      System.out.println("Unesite komandu ili odaberite Q za izlaz.");
      unos = skener.nextLine();

      System.out.println("Odabrano: " + unos);
      String glavniDioKomande = unos.split(" ")[0];

      if (glavniDioKomande.equalsIgnoreCase("IP")) {
        System.out.println("IP --> " + unos);
      } else if (glavniDioKomande.equalsIgnoreCase("ISP")) {
        System.out.println("ISP --> " + unos);
      } else if (glavniDioKomande.equalsIgnoreCase("ISI2S")) {
        System.out.println("ISI2S --> " + unos);
      } else if (glavniDioKomande.equalsIgnoreCase("IK")) {
        System.out.println("IK --> " + unos);
      } else {
        if (!unos.equalsIgnoreCase("Q")) {
          System.out.println("Neispravna komanda.");
        }
      }

    } while (!unos.equalsIgnoreCase("Q"));
    System.out.println("Gasenje sustava FOI Zeljeznice...\n");
  }
}
