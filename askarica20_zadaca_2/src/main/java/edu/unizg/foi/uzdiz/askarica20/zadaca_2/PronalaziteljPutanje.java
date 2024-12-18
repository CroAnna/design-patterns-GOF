package edu.unizg.foi.uzdiz.askarica20.zadaca_2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    // Try to find path through connecting station (Čakovec)
    String connectingStation = "Čakovec";

    String pruga1 = pronadiPrugu(polaznaStanica, connectingStation);
    String pruga2 = pronadiPrugu(connectingStation, odredisnaStanica);

    if (pruga1 != null && pruga2 != null) {
      List<Stanica> path = new ArrayList<>();
      List<Stanica> firstPart =
          dohvatiPutanjuNaIstojPruzi(polaznaStanica, connectingStation, pruga1);
      List<Stanica> secondPart =
          dohvatiPutanjuNaIstojPruzi(connectingStation, odredisnaStanica, pruga2);

      path.addAll(firstPart);
      // Add only the last station from second part to avoid duplicate Čakovec
      if (!secondPart.isEmpty()) {
        path.addAll(secondPart.subList(1, secondPart.size()));
      }
      return path;
    }

    // If no connecting path found, try direct path
    String directPruga = pronadiPrugu(polaznaStanica, odredisnaStanica);
    if (directPruga != null) {
      return dohvatiPutanjuNaIstojPruzi(polaznaStanica, odredisnaStanica, directPruga);
    }

    return new ArrayList<>();
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

    if (indexPolazne != -1 && indexOdredisne != -1) {
      int start = Math.min(indexPolazne, indexOdredisne);
      int end = Math.max(indexPolazne, indexOdredisne);

      for (int i = start; i <= end; i++) {
        Stanica stanica = staniceNaPruzi.get(i);
        if (putanja.isEmpty() || !putanja.get(putanja.size() - 1).getNazivStanice()
            .equals(stanica.getNazivStanice())) {
          putanja.add(stanica);
        }
      }

      if (indexPolazne > indexOdredisne) {
        Collections.reverse(putanja);
      }
    }

    return putanja;
  }
}
