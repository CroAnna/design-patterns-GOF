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
    System.out.println("Predmet uspješno prijavljen kao nađen.");
    provjeriPodudaranje(noviPredmet, korisnik, "pronaden");
  }

  @Override
  public void prijaviIzgubljenPredmet(Korisnik korisnik, String predmet, String opis) {
    IzgubljeniPredmet noviPredmet = new IzgubljeniPredmet(korisnik, predmet, opis);
    izgubljeniPredmeti.add(noviPredmet);
    System.out.println("Predmet uspješno prijavljen kao izgubljen.");
    provjeriPodudaranje(noviPredmet, korisnik, "izgubljen");
  }

  private void provjeriPodudaranje(IzgubljeniPredmet noviPredmet, Korisnik korisnik, String vrsta) {
    if (noviPredmet == null)
      return;

    if (vrsta.equals("pronaden")) {
      for (IzgubljeniPredmet izgubljeniPredmet : izgubljeniPredmeti) {
        if (izgubljeniPredmet.getPredmet().equalsIgnoreCase(noviPredmet.getPredmet())
            && izgubljeniPredmet.getOpis().equalsIgnoreCase(noviPredmet.getOpis())) {

          korisnik.obavijesti(
              "Pronađeno podudaranje! Korisnik " + izgubljeniPredmet.getKorisnik().getIme() + " "
                  + izgubljeniPredmet.getKorisnik().getPrezime() + " je izgubio sličan predmet.");
          return;
        }
      }
      System.out.println("Još nitko nije javio da je izgubio taj predmet.");


    } else if (vrsta.equals("izgubljen")) {
      for (IzgubljeniPredmet nadeniPredmet : nadeniPredmeti) {
        if (nadeniPredmet.getPredmet().equalsIgnoreCase(noviPredmet.getPredmet())
            && nadeniPredmet.getOpis().equalsIgnoreCase(noviPredmet.getOpis())) {

          korisnik
              .obavijesti("Pronađeno podudaranje! Korisnik " + nadeniPredmet.getKorisnik().getIme()
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
