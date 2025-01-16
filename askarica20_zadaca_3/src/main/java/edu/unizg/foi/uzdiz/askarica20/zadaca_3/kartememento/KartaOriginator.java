package edu.unizg.foi.uzdiz.askarica20.zadaca_3.kartememento;

import java.time.LocalDateTime;

// klasa cije stanje zelimo sacuvati
public class KartaOriginator {
	// atributi s getterima i setterima
	private String oznakaVlaka;
	private String polaznaStanica;
	private String odredisnaStanica;
	private LocalDateTime vrijemePolaska;
	private LocalDateTime vrijemeDolaska;
	private double osnovnaCijena;
	private double konacnaCijena;
	private String nacinKupnje;
	private LocalDateTime datumKupovine;

	public KartaOriginator(String oznakaVlaka, String polaznaStanica, String odredisnaStanica,
			LocalDateTime vrijemePolaska, LocalDateTime vrijemeDolaska, double osnovnaCijena, double konacnaCijena,
			String nacinKupnje, LocalDateTime datumKupovine) {
		this.oznakaVlaka = oznakaVlaka;
		this.polaznaStanica = polaznaStanica;
		this.odredisnaStanica = odredisnaStanica;
		this.vrijemePolaska = vrijemePolaska;
		this.vrijemeDolaska = vrijemeDolaska;
		this.osnovnaCijena = osnovnaCijena;
		this.konacnaCijena = konacnaCijena;
		this.nacinKupnje = nacinKupnje;
		this.datumKupovine = datumKupovine;
	}

	public CuvateljKarteMemento spremiStanjeUMemento() {
		// kreiranje mementa
		return new CuvateljKarteMemento(oznakaVlaka, polaznaStanica, odredisnaStanica, vrijemePolaska, vrijemeDolaska,
				osnovnaCijena, konacnaCijena, nacinKupnje, datumKupovine);
	}

	public void vratiStanjeIzMementa(CuvateljKarteMemento memento) {
		// vracanje stanja
		this.oznakaVlaka = memento.getOznakaVlaka();
		this.polaznaStanica = memento.getPolaznaStanica();
		this.odredisnaStanica = memento.getOdredisnaStanica();
		this.vrijemePolaska = memento.getVrijemePolaska();
		this.vrijemeDolaska = memento.getVrijemeDolaska();
		this.osnovnaCijena = memento.getOsnovnaCijena();
		this.konacnaCijena = memento.getKonacnaCijena();
		this.nacinKupnje = memento.getNacinKupnje();
		this.datumKupovine = memento.getDatumKupovine();
	}

	public String getOznakaVlaka() {
		return oznakaVlaka;
	}

	public void setOznakaVlaka(String oznakaVlaka) {
		this.oznakaVlaka = oznakaVlaka;
	}

	public String getPolaznaStanica() {
		return polaznaStanica;
	}

	public void setPolaznaStanica(String polaznaStanica) {
		this.polaznaStanica = polaznaStanica;
	}

	public String getOdredisnaStanica() {
		return odredisnaStanica;
	}

	public void setOdredisnaStanica(String odredisnaStanica) {
		this.odredisnaStanica = odredisnaStanica;
	}

	public LocalDateTime getVrijemePolaska() {
		return vrijemePolaska;
	}

	public void setVrijemePolaska(LocalDateTime vrijemePolaska) {
		this.vrijemePolaska = vrijemePolaska;
	}

	public LocalDateTime getVrijemeDolaska() {
		return vrijemeDolaska;
	}

	public void setVrijemeDolaska(LocalDateTime vrijemeDolaska) {
		this.vrijemeDolaska = vrijemeDolaska;
	}

	public double getOsnovnaCijena() {
		return osnovnaCijena;
	}

	public void setOsnovnaCijena(double osnovnaCijena) {
		this.osnovnaCijena = osnovnaCijena;
	}

	public double getKonacnaCijena() {
		return konacnaCijena;
	}

	public void setKonacnaCijena(double konacnaCijena) {
		this.konacnaCijena = konacnaCijena;
	}

	public String getNacinKupnje() {
		return nacinKupnje;
	}

	public void setNacinKupnje(String nacinKupnje) {
		this.nacinKupnje = nacinKupnje;
	}

	public LocalDateTime getDatumKupovine() {
		return datumKupovine;
	}

	public void setDatumKupovine(LocalDateTime datumKupovine) {
		this.datumKupovine = datumKupovine;
	}

}
