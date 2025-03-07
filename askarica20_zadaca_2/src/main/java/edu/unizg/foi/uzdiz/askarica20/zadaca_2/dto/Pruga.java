package edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto;

import java.util.ArrayList;
import java.util.List;

public class Pruga {
  private String oznaka;
  private List<Stanica> stanice;

  public Pruga() {
    super();
  }

  public Pruga(String oznaka) {
    super();
    this.oznaka = oznaka;
    this.stanice = new ArrayList<>();
  }

  public String getOznaka() {
    return oznaka;
  }

  public List<Stanica> getStanice() {
    return new ArrayList<Stanica>(stanice);
  }

  public List<Stanica> dohvatiMedustanice(String pocetnaStanica, String zavrsnaStanica) {
    int indexPocetne = -1, indexZavrsne = -1;
    for (int i = 0; i < stanice.size(); i++) {
      var st = stanice.get(i).getNazivStanice();
      if (zavrsnaStanica.equals(st))
        indexZavrsne = i;
      if (pocetnaStanica.equals(st))
        indexPocetne = i;
    }

    if (indexPocetne == -1 || indexZavrsne == -1)
      return new ArrayList<Stanica>();

    if (indexPocetne > indexZavrsne) {
      int pom = indexPocetne;
      indexPocetne = indexZavrsne;
      indexZavrsne = pom;
    }
    return new ArrayList<>(stanice.subList(indexPocetne, 1 + indexZavrsne));
  }

  public Stanica dohvatiPocetnuStanicu() {
    if (stanice.isEmpty())
      return null;

    for (int i = 0; i < stanice.size(); i++) {
      if (i == 0) {
        return stanice.get(i);
      }
    }
    return null;
  }

  public Stanica dohvatiZavrsnuStanicu() {
    if (stanice.isEmpty())
      return null;

    for (int i = stanice.size() - 1; i >= 0; i--) {
      if (i == stanice.size() - 1) {
        return stanice.get(i);
      }
    }
    return null;
  }

  public Stanica dohvatiPrvuStanicuSmjer(String smjer) {
    if (stanice.isEmpty())
      return null;

    if (smjer.equals("N")) {
      return stanice.get(0);
    } else if (smjer.equals("O")) {
      return stanice.get(stanice.size() - 1);
    }
    return null;
  }

  public Stanica dohvatiZadnjuStanicuSmjer(String smjer) {
    if (stanice.isEmpty())
      return null;

    if (smjer.equals("N")) {
      return stanice.get(stanice.size() - 1);
    } else if (smjer.equals("O")) {
      return stanice.get(0);
    }
    return null;
  }

  public void dodajStanicu(Stanica stanica) {
    stanice.add(stanica);
  }

  public int dohvatiUkupnuUdaljenost() {
    int uk = 0;
    for (Stanica stanica : stanice) {
      uk += stanica.getDuzina();
    }
    return uk;
  }

}
