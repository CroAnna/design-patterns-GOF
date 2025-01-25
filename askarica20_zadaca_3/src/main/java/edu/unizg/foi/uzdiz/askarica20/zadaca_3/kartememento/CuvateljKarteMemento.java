package edu.unizg.foi.uzdiz.askarica20.zadaca_3.kartememento;

import java.time.LocalDateTime;

public class CuvateljKarteMemento {
	private String oznakaVlaka;
	private String polaznaStanica;
	private String odredisnaStanica;
	private LocalDateTime vrijemePolaska;
	private LocalDateTime vrijemeDolaska;
	private double osnovnaCijena;
	private double konacnaCijena;
	private String nacinKupnje;
	private LocalDateTime datumKupovine;

	public CuvateljKarteMemento(String oznakaVlaka, String polaznaStanica, String odredisnaStanica,
			LocalDateTime vrijemePolaska, LocalDateTime vrijemeDolaska, double osnovnaCijena, double konacnaCijena,
			String nacinKupovine, LocalDateTime datumKupovine) {
		this.oznakaVlaka = oznakaVlaka;
		this.polaznaStanica = polaznaStanica;
		this.odredisnaStanica = odredisnaStanica;
		this.vrijemePolaska = vrijemePolaska;
		this.vrijemeDolaska = vrijemeDolaska;
		this.osnovnaCijena = osnovnaCijena;
		this.konacnaCijena = konacnaCijena;
		this.nacinKupnje = nacinKupovine;
		this.datumKupovine = datumKupovine;
	}

	public String getOznakaVlaka() {
		return this.oznakaVlaka;
	}

	public String getPolaznaStanica() {
		return this.polaznaStanica;
	}

	public String getOdredisnaStanica() {
		return this.odredisnaStanica;
	}

	public LocalDateTime getVrijemePolaska() {
		return this.vrijemePolaska;
	}

	public LocalDateTime getVrijemeDolaska() {
		return this.vrijemeDolaska;
	}

	public double getOsnovnaCijena() {
		return this.osnovnaCijena;
	}

	public double getKonacnaCijena() {
		return this.konacnaCijena;
	}

	public String getNacinKupnje() {
		return this.nacinKupnje;
	}

	public LocalDateTime getDatumKupovine() {
		return this.datumKupovine;
	}

}
