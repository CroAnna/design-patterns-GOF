package edu.unizg.foi.uzdiz.askarica20.zadaca_3.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.unizg.foi.uzdiz.askarica20.zadaca_3.stanjaprugestate.PrugaContext;

public class Pruga {
	private String oznaka;
	private List<Stanica> stanice;
	private Map<String, PrugaContext> stanjaRelacija;

	public Pruga() {
		super();
	}

	public Pruga(String oznaka) {
		super();
		this.oznaka = oznaka;
		this.stanice = new ArrayList<>();
		this.stanjaRelacija = new HashMap<>();
	}

	public String getOznaka() {
		return oznaka;
	}

	public List<Stanica> getStanice() {
		return new ArrayList<Stanica>(stanice);
	}

	public boolean promijeniStanjeRelacije(String polaznaStanica, String odredisnaStanica, String novoStanje) {
		String kljucRelacije = polaznaStanica + "-" + odredisnaStanica;

		List<Stanica> medustanice = dohvatiMedustanice(polaznaStanica, odredisnaStanica);
		if (medustanice.isEmpty()) {
			System.out.println("Nema medustanica.");
			return false;
		}
		// Provjera preklapanja relacija za pruge s jednim kolosijekom
		if (medustanice.get(0).getBrojKolosjeka() == 1) {
			for (var entry : stanjaRelacija.entrySet()) {
				// Preskočimo provjeru ako gledamo istu relaciju
				if (entry.getKey().equals(kljucRelacije)) {
					continue;
				}

				String[] stanice = entry.getKey().split("-");
				List<Stanica> postojeceMedustanice = dohvatiMedustanice(stanice[0], stanice[1]);

				// Provjera presjeka relacija
				for (Stanica s : postojeceMedustanice) {
					if (medustanice.contains(s)) {
						System.out.println("Greška: Postoji preklapanje relacija na pruzi s jednim kolosijekom!");
						return false;
					}
				}
			}
		}

		PrugaContext context;
		if (!stanjaRelacija.containsKey(kljucRelacije)) {
			context = new PrugaContext(polaznaStanica, odredisnaStanica, medustanice.get(0).getBrojKolosjeka());
			stanjaRelacija.put(kljucRelacije, context);
		} else {
			context = stanjaRelacija.get(kljucRelacije);
		}

		String rezultat = context.promijeniStanje(novoStanje);
		if (!rezultat.equals("OK")) {
			System.out.println("Greška: " + rezultat);
			return false;
		}
		return true;
	}

	public List<String> dohvatiRelacijeUStanju(String status) {
		List<String> relacije = new ArrayList<>();
		for (Map.Entry<String, PrugaContext> entry : stanjaRelacija.entrySet()) {
			if (entry.getValue().getTrenutnoStanje().getOznaka().equals(status)) {
				relacije.add(entry.getKey());
			}
		}
		return relacije;
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
