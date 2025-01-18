package edu.unizg.foi.uzdiz.askarica20.zadaca_3.stanjaprugestate;

public class PrugaContext {
	private PrugaState trenutnoStanje; // privatno stanje
	private final String polaznaStanica;
	private final String odredisnaStanica;
	private final int brojKolosijeka;

	public PrugaContext(String polaznaStanica, String odredisnaStanica, int brojKolosijeka) {
		this.polaznaStanica = polaznaStanica;
		this.odredisnaStanica = odredisnaStanica;
		this.brojKolosijeka = brojKolosijeka;
		this.trenutnoStanje = new StanjeIspravna();
	}

	public PrugaState getTrenutnoStanje() { // get stanje
		return this.trenutnoStanje;
	}

	public void setStanje(PrugaState novoStanje) { // set stanje
		this.trenutnoStanje = novoStanje;
	}

	public String getPolaznaStanica() {
		return polaznaStanica;
	}

	public String getOdredisnaStanica() {
		return odredisnaStanica;
	}

	public int getBrojKolosijeka() {
		return brojKolosijeka;
	}

	public String promijeniStanje(String novoStanje) {
		if (trenutnoStanje.getOznaka().equals(novoStanje)) {
			System.out.println("Pruga je već u tom stanju.");
			return "OK";
		}

		PrugaState novoStanjeObj = switch (novoStanje) {
		case "I" -> new StanjeIspravna();
		case "K" -> new StanjeKvar();
		case "T" -> new StanjeTestiranje();
		case "Z" -> new StanjeZatvorena();
		default -> null;
		};

		if (novoStanjeObj == null) {
			return "Nepoznato stanje!";
		}

		if (novoStanjeObj.handle(this)) {
			trenutnoStanje = novoStanjeObj; // Postavljamo novo stanje tek nakon što znamo da je prijelaz dozvoljen
			return "OK";
		} else {
			return "Nedozvoljen prijelaz iz stanja " + trenutnoStanje.getOznaka() + " u stanje " + novoStanje;
		}
	}

}
