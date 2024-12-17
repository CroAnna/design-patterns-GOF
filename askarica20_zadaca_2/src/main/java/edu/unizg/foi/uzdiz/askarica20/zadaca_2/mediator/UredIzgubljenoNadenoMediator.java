package edu.unizg.foi.uzdiz.askarica20.zadaca_2.mediator;

import java.util.ArrayList;
import java.util.List;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.IzgubljeniPredmet;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Korisnik;

public class UredIzgubljenoNadenoMediator implements PosrednikMediator {
  // concrete mediator koji implementira logiku
  private List<IzgubljeniPredmet> nadeniPredmeti = new ArrayList<>();
  private List<IzgubljeniPredmet> izgubljeniPredmeti = new ArrayList<>();

  @Override
  public void prijaviNadenPredmet(Korisnik korisnik, String predmet, String opis) {
    IzgubljeniPredmet noviPredmet = new IzgubljeniPredmet(korisnik, predmet, opis);
    nadeniPredmeti.add(noviPredmet);
    System.out.println("DEBUG: Added to nadeniPredmeti. Current size: " + nadeniPredmeti.size());
    System.out.println("Predmet uspješno prijavljen kao nađen.");
    provjeriPodudaranje(noviPredmet, "pronaden");
  }

  @Override
  public void prijaviIzgubljenPredmet(Korisnik korisnik, String predmet, String opis) {
    IzgubljeniPredmet noviPredmet = new IzgubljeniPredmet(korisnik, predmet, opis);
    izgubljeniPredmeti.add(noviPredmet);
    System.out
        .println("DEBUG: Added to izgubljeniPredmeti. Current size: " + izgubljeniPredmeti.size());
    System.out.println("Predmet uspješno prijavljen kao izgubljen.");
    provjeriPodudaranje(noviPredmet, "izgubljen");
  }

  @Override
  public void pregledPredmeta(Korisnik korisnik) {
    System.out.println("Pronađeni predmeti:");
    if (nadeniPredmeti.isEmpty()) {
      System.out.println("Nema pronađenih predmeta.");
      return;
    }
    for (IzgubljeniPredmet predmet : nadeniPredmeti) {
      System.out.println(predmet);
    }
  }

  private void provjeriPodudaranje(IzgubljeniPredmet noviPredmet, String vrsta) {
    if (noviPredmet == null)
      return;
    System.out.println("DEBUG: Checking - novi predmet: " + noviPredmet.getPredmet() + " "
        + noviPredmet.getOpis());

    if (vrsta.equals("pronaden")) {
      System.out.println("DEBUG: Checking found item against lost items. Lost items size: "
          + izgubljeniPredmeti.size());
      for (IzgubljeniPredmet izgubljeniPredmet : izgubljeniPredmeti) {
        System.out.println("DEBUG: Comparing with lost item: " + izgubljeniPredmet.getPredmet()
            + " " + izgubljeniPredmet.getOpis());

        if (izgubljeniPredmet.getPredmet().equalsIgnoreCase(noviPredmet.getPredmet())
            && izgubljeniPredmet.getOpis().equalsIgnoreCase(noviPredmet.getOpis())) {
          System.out.println(
              "Pronađeno podudaranje! Korisnik " + izgubljeniPredmet.getKorisnik().getIme() + " "
                  + izgubljeniPredmet.getKorisnik().getPrezime() + " je izgubio sličan predmet.");
          return;
        }
      }
      System.out.println("Još nitko nije javio da je izgubio taj predmet.");


    } else if (vrsta.equals("izgubljen")) {
      System.out.println("DEBUG: Checking lost item against found items. Found items size: "
          + nadeniPredmeti.size());
      for (IzgubljeniPredmet nadeniPredmet : nadeniPredmeti) {
        System.out.println("DEBUG: Comparing with found item: " + nadeniPredmet.getPredmet() + " "
            + nadeniPredmet.getOpis());
        if (nadeniPredmet.getPredmet().equalsIgnoreCase(noviPredmet.getPredmet())
            && nadeniPredmet.getOpis().equalsIgnoreCase(noviPredmet.getOpis())) {
          System.out
              .println("Pronađeno podudaranje! Korisnik " + nadeniPredmet.getKorisnik().getIme()
                  + " " + nadeniPredmet.getKorisnik().getPrezime() + " je našao sličan predmet.");
          return;
        }
      }
      System.out.println("Još nitko nije javio da je pronašao taj predmet.");

    } else {
      System.out.println("Nema pronađenih podudaranja.");
    }
  }
}
