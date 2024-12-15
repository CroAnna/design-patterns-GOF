package edu.unizg.foi.uzdiz.askarica20.zadaca_2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import edu.unizg.foi.uzdiz.askarica20.zadaca_2.dto.Stanica;

public class PronalaziteljPutanje {
  private final List<Stanica> listaStanica;
  private boolean zaglavjeIspisano = false;
  private int ukupnaUdaljenost = 0;

  public PronalaziteljPutanje(List<Stanica> listaStanica) {
    this.listaStanica = listaStanica;
  }

  public void ispisiPutIzmeduStanica(String polaznaStanica, String odredisnaStanica) {
    zaglavjeIspisano = false;
    ukupnaUdaljenost = 0;
    System.out.println("\nPut između stanica " + polaznaStanica + " i " + odredisnaStanica + ":");

    List<Stanica> polazneStanice = listaStanica.stream()
        .filter(s -> s.getNazivStanice().equals(polaznaStanica)).collect(Collectors.toList());

    List<Stanica> odredisneStanice = listaStanica.stream()
        .filter(s -> s.getNazivStanice().equals(odredisnaStanica)).collect(Collectors.toList());

    if (polazneStanice.isEmpty() || odredisneStanice.isEmpty()) {
      System.out.println("Jedna ili obje stanice ne postoje.");
      return;
    }

    String zajednickaPruga = null;
    for (Stanica polazna : polazneStanice) {
      for (Stanica odredisna : odredisneStanice) {
        if (polazna.getOznakaPruge().equals(odredisna.getOznakaPruge())) {
          zajednickaPruga = polazna.getOznakaPruge();
          break;
        }
      }
    }

    if (zajednickaPruga != null) {
      if (!zaglavjeIspisano) {
        System.out.printf("%-25s %-10s %-10s%n", "Stanica", "Pruga", "Km");
        System.out.println("------------------------------------------");
        zaglavjeIspisano = true;
      }
      ispisiPutNaIstojPruzi(polaznaStanica, odredisnaStanica, zajednickaPruga);
    } else {
      Set<String> posjeceneStanice = new HashSet<>();
      ispisiPutPrekoPresjedanja(polaznaStanica, odredisnaStanica, posjeceneStanice);
    }
  }

  private void ispisiPutNaIstojPruzi(String polaznaStanica, String odredisnaStanica,
      String oznakaPruge) {
    List<Stanica> staniceNaPruzi = listaStanica.stream()
        .filter(s -> s.getOznakaPruge().equals(oznakaPruge)).collect(Collectors.toList());

    int indexPolazne = -1;
    int indexOdredisne = -1;

    for (int i = 0; i < staniceNaPruzi.size(); i++) {
      if (staniceNaPruzi.get(i).getNazivStanice().equals(polaznaStanica)) {
        indexPolazne = i;
      }
      if (staniceNaPruzi.get(i).getNazivStanice().equals(odredisnaStanica)) {
        indexOdredisne = i;
      }
    }

    String prethodnaStanica = "";

    if (indexPolazne < indexOdredisne) {
      // Prvo ispiši polaznu stanicu
      Stanica prvaStanica = staniceNaPruzi.get(indexPolazne);
      System.out.printf("%-25s %-10s %-10d%n", prvaStanica.getNazivStanice(),
          prvaStanica.getOznakaPruge(), ukupnaUdaljenost);
      prethodnaStanica = prvaStanica.getNazivStanice();
      ukupnaUdaljenost += prvaStanica.getDuzina();

      // Onda za ostale prvo zbroji pa ispiši
      for (int i = indexPolazne + 1; i <= indexOdredisne; i++) {
        Stanica stanica = staniceNaPruzi.get(i);
        if (!stanica.getNazivStanice().equals(prethodnaStanica)) {
          System.out.printf("%-25s %-10s %-10d%n", stanica.getNazivStanice(),
              stanica.getOznakaPruge(), ukupnaUdaljenost);
          prethodnaStanica = stanica.getNazivStanice();
          if (i < indexOdredisne) {
            ukupnaUdaljenost += stanica.getDuzina();
          }
        }
      }
    } else {
      for (int i = indexPolazne; i >= indexOdredisne; i--) {
        Stanica stanica = staniceNaPruzi.get(i);
        if (!stanica.getNazivStanice().equals(prethodnaStanica)) {
          System.out.printf("%-25s %-10s %-10d%n", stanica.getNazivStanice(),
              stanica.getOznakaPruge(), ukupnaUdaljenost);
          prethodnaStanica = stanica.getNazivStanice();
          if (i > indexOdredisne) {
            ukupnaUdaljenost += staniceNaPruzi.get(i).getDuzina();
          }
        }
      }
    }
  }

  private void ispisiPutPrekoPresjedanja(String polaznaStanica, String odredisnaStanica,
      Set<String> posjeceneStanice) {
    posjeceneStanice.add(polaznaStanica);
    final Set<String> finalPosjeceneStanice = posjeceneStanice;

    Set<String> zajednickeStanice = listaStanica.stream()
        .collect(Collectors.groupingBy(Stanica::getNazivStanice)).entrySet().stream()
        .filter(e -> e.getValue().size() > 1).map(Map.Entry::getKey)
        .filter(stanica -> !finalPosjeceneStanice.contains(stanica)).collect(Collectors.toSet());

    for (String zajednickaStanica : zajednickeStanice) {
      String prugaDoPrve = pronadiPrugu(polaznaStanica, zajednickaStanica);
      if (prugaDoPrve == null)
        continue;

      String prugaDoOdredisne = pronadiPrugu(zajednickaStanica, odredisnaStanica);
      if (prugaDoOdredisne != null) {
        if (!zaglavjeIspisano) {
          System.out.printf("%-25s %-10s %-10s%n", "Stanica", "Pruga", "Km");
          System.out.println("------------------------------------------");
          zaglavjeIspisano = true;
        }
        ispisiPutNaIstojPruzi(polaznaStanica, zajednickaStanica, prugaDoPrve);
        ispisiPutNaIstojPruzi(zajednickaStanica, odredisnaStanica, prugaDoOdredisne);
        return;
      }

      Set<String> novePosjeceneStanice = new HashSet<>(posjeceneStanice);
      if (!zaglavjeIspisano) {
        System.out.printf("%-25s %-10s %-10s%n", "Stanica", "Pruga", "Km");
        System.out.println("------------------------------------------");
        zaglavjeIspisano = true;
      }
      ispisiPutNaIstojPruzi(polaznaStanica, zajednickaStanica, prugaDoPrve);
      ispisiPutPrekoPresjedanja(zajednickaStanica, odredisnaStanica, novePosjeceneStanice);
      return;
    }

    if (posjeceneStanice.size() == 1) {
      System.out.println("Ne postoji povezani put između zadanih stanica.");
    }
  }

  private String pronadiPrugu(String stanica1, String stanica2) {
    for (String pruga : listaStanica.stream().map(Stanica::getOznakaPruge).distinct()
        .collect(Collectors.toList())) {
      List<String> staniceNaPruzi =
          listaStanica.stream().filter(s -> s.getOznakaPruge().equals(pruga))
              .map(Stanica::getNazivStanice).collect(Collectors.toList());

      if (staniceNaPruzi.contains(stanica1) && staniceNaPruzi.contains(stanica2)) {
        return pruga;
      }
    }
    return null;
  }

  public List<Stanica> dohvatiPutanjuIzmeduStanica(String polaznaStanica, String odredisnaStanica) {
    List<Stanica> putanja = new ArrayList<>();

    List<Stanica> polazneStanice = listaStanica.stream()
        .filter(s -> s.getNazivStanice().equals(polaznaStanica)).collect(Collectors.toList());

    List<Stanica> odredisneStanice = listaStanica.stream()
        .filter(s -> s.getNazivStanice().equals(odredisnaStanica)).collect(Collectors.toList());

    if (polazneStanice.isEmpty() || odredisneStanice.isEmpty()) {
      return putanja;
    }

    String zajednickaPruga = null;
    for (Stanica polazna : polazneStanice) {
      for (Stanica odredisna : odredisneStanice) {
        if (polazna.getOznakaPruge().equals(odredisna.getOznakaPruge())) {
          zajednickaPruga = polazna.getOznakaPruge();
          break;
        }
      }
    }

    if (zajednickaPruga != null) {
      return dohvatiPutanjuNaIstojPruzi(polaznaStanica, odredisnaStanica, zajednickaPruga);
    } else {
      Set<String> posjeceneStanice = new HashSet<>();
      return dohvatiPutanjuPrekoPresjedanja(polaznaStanica, odredisnaStanica, posjeceneStanice);
    }
  }

  private List<Stanica> dohvatiPutanjuNaIstojPruzi(String polaznaStanica, String odredisnaStanica,
      String oznakaPruge) {
    List<Stanica> putanja = new ArrayList<>();
    List<Stanica> staniceNaPruzi = listaStanica.stream()
        .filter(s -> s.getOznakaPruge().equals(oznakaPruge)).collect(Collectors.toList());

    int indexPolazne = -1;
    int indexOdredisne = -1;

    for (int i = 0; i < staniceNaPruzi.size(); i++) {
      if (staniceNaPruzi.get(i).getNazivStanice().equals(polaznaStanica)) {
        indexPolazne = i;
      }
      if (staniceNaPruzi.get(i).getNazivStanice().equals(odredisnaStanica)) {
        indexOdredisne = i;
      }
    }

    String prethodnaStanica = "";

    if (indexPolazne < indexOdredisne) {
      for (int i = indexPolazne; i <= indexOdredisne; i++) {
        Stanica stanica = staniceNaPruzi.get(i);
        if (!stanica.getNazivStanice().equals(prethodnaStanica)) {
          putanja.add(stanica);
          prethodnaStanica = stanica.getNazivStanice();
        }
      }
    } else {
      for (int i = indexPolazne; i >= indexOdredisne; i--) {
        Stanica stanica = staniceNaPruzi.get(i);
        if (!stanica.getNazivStanice().equals(prethodnaStanica)) {
          putanja.add(stanica);
          prethodnaStanica = stanica.getNazivStanice();
        }
      }
    }

    return putanja;
  }

  private List<Stanica> dohvatiPutanjuPrekoPresjedanja(String polaznaStanica,
      String odredisnaStanica, Set<String> posjeceneStanice) {
    List<Stanica> putanja = new ArrayList<>();
    posjeceneStanice.add(polaznaStanica);
    final Set<String> finalPosjeceneStanice = posjeceneStanice;

    Set<String> zajednickeStanice = listaStanica.stream()
        .collect(Collectors.groupingBy(Stanica::getNazivStanice)).entrySet().stream()
        .filter(e -> e.getValue().size() > 1).map(Map.Entry::getKey)
        .filter(stanica -> !finalPosjeceneStanice.contains(stanica)).collect(Collectors.toSet());

    for (String zajednickaStanica : zajednickeStanice) {
      String prugaDoPrve = pronadiPrugu(polaznaStanica, zajednickaStanica);
      if (prugaDoPrve == null)
        continue;

      String prugaDoOdredisne = pronadiPrugu(zajednickaStanica, odredisnaStanica);
      if (prugaDoOdredisne != null) {
        putanja.addAll(dohvatiPutanjuNaIstojPruzi(polaznaStanica, zajednickaStanica, prugaDoPrve));
        // Remove the last station to avoid duplication of the transfer station
        if (!putanja.isEmpty()) {
          putanja.remove(putanja.size() - 1);
        }
        putanja.addAll(
            dohvatiPutanjuNaIstojPruzi(zajednickaStanica, odredisnaStanica, prugaDoOdredisne));
        return putanja;
      }

      Set<String> novePosjeceneStanice = new HashSet<>(posjeceneStanice);
      List<Stanica> prviDioPuta =
          dohvatiPutanjuNaIstojPruzi(polaznaStanica, zajednickaStanica, prugaDoPrve);
      if (!prviDioPuta.isEmpty()) {
        putanja.addAll(prviDioPuta);
        // Remove the last station to avoid duplication of the transfer station
        putanja.remove(putanja.size() - 1);
        putanja.addAll(dohvatiPutanjuPrekoPresjedanja(zajednickaStanica, odredisnaStanica,
            novePosjeceneStanice));
        return putanja;
      }
    }

    return putanja;
  }
}
