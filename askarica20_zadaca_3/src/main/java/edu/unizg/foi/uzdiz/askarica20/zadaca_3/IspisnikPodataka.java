package edu.unizg.foi.uzdiz.askarica20.zadaca_3;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.unizg.foi.uzdiz.askarica20.zadaca_3.dto.Kompozicija;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.dto.Korisnik;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.dto.Pruga;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.dto.Stanica;
import edu.unizg.foi.uzdiz.askarica20.zadaca_3.dto.Vozilo;

public class IspisnikPodataka {

	public void ispisiPruge(List<Pruga> pruge) {
		if (pruge.isEmpty()) {
			System.out.println("Nema unesenih pruga u sustavu.");
			return;
		}

		System.out.println(
				"\n\n------------------------------------- ISPIS PRUGA --------------------------------------\n");
		System.out.printf("%-15s %-30s %-30s %-10s\n", "Oznaka pruge", "Početna stanica", "Završna stanica",
				"Udaljenost");
		System.out.println("----------------------------------------------------------------------------------------");

		for (Pruga pruga : pruge) {
			Stanica pocetnaStanica = pruga.dohvatiPocetnuStanicu();
			Stanica zavrsnaStanica = pruga.dohvatiZavrsnuStanicu();

			String pocetna = (pocetnaStanica != null) ? pocetnaStanica.getNazivStanice() : "N/A";
			String zavrsna = (zavrsnaStanica != null) ? zavrsnaStanica.getNazivStanice() : "N/A";

			System.out.printf("%-15s %-30s %-30s %-10d\n", pruga.getOznaka(), pocetna, zavrsna,
					pruga.dohvatiUkupnuUdaljenost());
		}

		System.out.println(
				"\n----------------------------------------------------------------------------------------\n");
	}

	public void ispisiKorisnike(List<Korisnik> korisnici) {
		if (korisnici.isEmpty()) {
			System.out.println("Nema unesenih korisnika u sustavu.");
			return;
		}

		System.out.println(
				"\n\n----------------------------------- ISPIS KORISNIKA ------------------------------------\n");
		System.out.printf("%-3s %-12s %-20s\n", "ID", "Ime", "Prezime");
		System.out.println("----------------------------------------------------------------------------------------");
		for (Korisnik korisnik : korisnici) {
			System.out.printf("%-3s %-12s %-20s \n", korisnik.getId(), korisnik.getIme(), korisnik.getPrezime());
		}
		System.out.println(
				"\n----------------------------------------------------------------------------------------\n");
	}

	public void ispisListeStanica(LinkedHashMap<Stanica, Integer> stanice) {
		if (stanice.isEmpty()) {
			System.out.println("Lista stanica je prazna.");
			return;
		}

		System.out
				.println("\n------------------------------------ ISPIS STANICA  ------------------------------------");
		System.out.printf("%-30s %-15s %-10s %s%n", "Naziv stanice", "Vrsta stanice", "Udaljenost",
				"od početne stanice");
		System.out.println("----------------------------------------------------------------------------------------");

		Stanica prvaStanica = stanice.keySet().iterator().next();
		for (Map.Entry<Stanica, Integer> entry : stanice.entrySet()) {
			Stanica s = entry.getKey();
			int udaljenost = entry.getValue();
			System.out.printf("%-30s %-15s %-10d km od %-10s%n", s.getNazivStanice(), s.getVrstaStanice(), udaljenost,
					prvaStanica.getNazivStanice());
		}
		System.out.println(
				"\n----------------------------------------------------------------------------------------\n");
	}

	public void ispisStanicaPruge(List<Stanica> stanicePruge, String redoslijed) {
		System.out.println("\n\n-------------------------- ISPIS STANICA PRUGE ---------------------------\n");
		System.out.printf("%-30s %-20s %-20s\n", "Naziv stanice", "Vrsta stanice", "Udaljenost od početne");
		System.out.println("--------------------------------------------------------------------------");

		int udaljenostOdPocetne = 0;

		if ("N".equals(redoslijed)) {
			for (Stanica s : stanicePruge) {
				udaljenostOdPocetne = udaljenostOdPocetne + s.getDuzina();
				System.out.printf("%-30s %-20s %-20d\n", s.getNazivStanice(), s.getVrstaStanice(), udaljenostOdPocetne);
				;
			}
		} else if ("O".equals(redoslijed)) {
			for (int i = stanicePruge.size() - 1; i >= 0; i--) {
				Stanica s = stanicePruge.get(i);
				System.out.printf("%-30s %-20s %-20d\n", s.getNazivStanice(), s.getVrstaStanice(), udaljenostOdPocetne);
				udaljenostOdPocetne = udaljenostOdPocetne + s.getDuzina();
			}
		}
		System.out.println("\n\n--------------------------------------------------------------------------\n");
	}

	public void ispisKompozicije(Kompozicija k) {
		System.out.println(
				"\n\n--------------------------------------------------------- ISPIS KOMPOZICIJE ---------------------------------------------------------\n");
		System.out.printf("%-10s %-5s %-60s %-10s %-15s %-15s %-10s\n", "Oznaka", "Uloga", "Opis", "Godina", "Namjena",
				"Vrsta pogona", "Max brzina");
		System.out.println(
				"-------------------------------------------------------------------------------------------------"
						+ "------------------------------------");

		List<Vozilo> vozilaUKompoziciji = k.getVozila();
		for (Vozilo v : vozilaUKompoziciji) {
			String uloga = v.getVrstaPogona().equals("N") ? "V" : "P";
			System.out.printf("%-10s %-5s %-60s %-10s %-15s %-15s %-10s\n", v.getOznaka(), uloga, v.getOpis(),
					v.getGodina(), v.getNamjena(), v.getVrstaPogona(), v.getMaksimalnaBrzina());
		}
		System.out.println(
				"\n-------------------------------------------------------------------------------------------------------------------------------------\n");
	}

	public void ispisRelacijaSaStatusom(String status, String oznakaPruge, List<Pruga> pruge) {
		if (oznakaPruge != null) {
			// Ispis za specifičnu prugu
			Pruga pruga = null;
			for (Pruga p : pruge) {
				if (p.getOznaka().equals(oznakaPruge)) {
					pruga = p;
					break;
				}
			}

			if (pruga == null) {
				System.out.println("Pruga s oznakom " + oznakaPruge + " ne postoji!");
				return;
			}

			List<String> relacije = pruga.dohvatiRelacijeUStanju(status);
			if (relacije.isEmpty()) {
				System.out.println("Nema relacija sa statusom " + status + " na pruzi " + oznakaPruge);
				return;
			}

			System.out.println("\n\n---------------------------- ISPIS RELACIJA SA STATUSOM " + status + " - PRUGA "
					+ oznakaPruge + " ----------------------------\n");
			System.out.printf("%-10s %-30s %-30s %-10s\n", "Pruga", "Polazna stanica", "Odredišna stanica", "Status");
			System.out.println(
					"--------------------------------------------------------------------------------------------------");

			for (String relacija : relacije) {
				String[] dijelovi = relacija.split("-");
				System.out.printf("%-10s %-30s %-30s %-10s\n", oznakaPruge, dijelovi[0], dijelovi[1], status);
			}
			System.out.println(
					"\n--------------------------------------------------------------------------------------------------\n");
		} else {
			// Ispis za sve pruge
			boolean pronadeno = false;
			System.out.println("\n\n------------------------------- ISPIS SVIH RELACIJA SA STATUSOM " + status
					+ " --------------------------------\n");
			System.out.printf("%-10s %-30s %-30s %-10s\n", "Pruga", "Polazna stanica", "Odredišna stanica", "Status");
			System.out.println(
					"--------------------------------------------------------------------------------------------------");

			for (Pruga pruga : pruge) {
				List<String> relacije = pruga.dohvatiRelacijeUStanju(status);
				for (String relacija : relacije) {
					pronadeno = true;
					String[] dijelovi = relacija.split("-");
					System.out.printf("%-10s %-30s %-30s %-10s\n", pruga.getOznaka(), dijelovi[0], dijelovi[1], status);
				}
			}
			if (!pronadeno) {
				System.out.println("Nema relacija sa statusom " + status);
			}
			System.out.println(
					"\n--------------------------------------------------------------------------------------------------\n");
		}
	}

	public void ispisPromjeneStanjaPruge(String oznaka, String polaznaStanica, String odredisnaStanica, String status,
			boolean uspjesno) {
		System.out.println(
				"\n\n----------------------------------- PROMJENA STANJA PRUGE -----------------------------------\n");
		System.out.printf("%-10s %-30s %-30s %-10s %-10s\n", "Pruga", "Polazna stanica", "Odredišna stanica", "Status",
				"Uspješno");
		System.out.println(
				"---------------------------------------------------------------------------------------------");
		System.out.printf("%-10s %-30s %-30s %-10s %-10s\n", oznaka, polaznaStanica, odredisnaStanica, status,
				uspjesno ? "DA" : "NE");
		System.out.println(
				"\n---------------------------------------------------------------------------------------------\n");
	}

	public void nacrtajVlak() {
		String train = "       ___\n" + "  __[__|__]__[__|__]__[__|__]__\n" + "   O-O---O-O---O-O---O-O---O-O\n";
		System.out.println(train);
	}
}
