package edu.unizg.foi.uzdiz.askarica20.zadaca_3.kartememento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// upravlja i cuva memento objekte
public class PovijestKarataCaretaker {

	// lista za cuvanje povijesti
	private List<CuvateljKarteMemento> povijestKarata = new ArrayList<>();

	public void dodajKartu(CuvateljKarteMemento memento) {
		povijestKarata.add(memento);
	}

	public CuvateljKarteMemento dohvatiKartu(int index) {
		if (index >= 0 && index < povijestKarata.size()) {
			return povijestKarata.get(index);
		}
		throw new IndexOutOfBoundsException("Karta s tim indeksom ne postoji!");
	}

	public List<CuvateljKarteMemento> dohvatiSveKarte() {
		return Collections.unmodifiableList(povijestKarata);
	}

}
