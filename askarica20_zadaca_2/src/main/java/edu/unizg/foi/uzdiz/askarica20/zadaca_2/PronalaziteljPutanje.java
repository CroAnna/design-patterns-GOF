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

  public PronalaziteljPutanje(List<Stanica> listaStanica) {
    this.listaStanica = listaStanica;
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

    if (indexPolazne < indexOdredisne) {
      for (int i = indexPolazne; i <= indexOdredisne; i++) {
        Stanica stanica = staniceNaPruzi.get(i);
        if (putanja.isEmpty() || !putanja.get(putanja.size() - 1).getNazivStanice()
            .equals(stanica.getNazivStanice())) {
          putanja.add(stanica);
        }
      }
    } else {
      for (int i = indexPolazne; i >= indexOdredisne; i--) {
        Stanica stanica = staniceNaPruzi.get(i);
        if (putanja.isEmpty() || !putanja.get(putanja.size() - 1).getNazivStanice()
            .equals(stanica.getNazivStanice())) {
          putanja.add(stanica);
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

        List<Stanica> prviDio =
            dohvatiPutanjuNaIstojPruzi(polaznaStanica, zajednickaStanica, prugaDoPrve);
        putanja.addAll(prviDio);

        List<Stanica> staniceNaDrugojPruzi =
            listaStanica.stream().filter(s -> s.getOznakaPruge().equals(prugaDoOdredisne)
                && s.getNazivStanice().equals(zajednickaStanica)).collect(Collectors.toList());

        if (!staniceNaDrugojPruzi.isEmpty()) {
          putanja.add(staniceNaDrugojPruzi.get(0));
        }

        List<Stanica> drugiDio =
            dohvatiPutanjuNaIstojPruzi(zajednickaStanica, odredisnaStanica, prugaDoOdredisne);
        if (!drugiDio.isEmpty()) {
          putanja.addAll(drugiDio.subList(1, drugiDio.size()));
        }

        return putanja;
      }

      Set<String> novePosjeceneStanice = new HashSet<>(posjeceneStanice);
      List<Stanica> prviDioPuta =
          dohvatiPutanjuNaIstojPruzi(polaznaStanica, zajednickaStanica, prugaDoPrve);
      if (!prviDioPuta.isEmpty()) {
        putanja.addAll(prviDioPuta);
        putanja.addAll(dohvatiPutanjuPrekoPresjedanja(zajednickaStanica, odredisnaStanica,
            novePosjeceneStanice));
        return putanja;
      }
    }

    return putanja;
  }

}
