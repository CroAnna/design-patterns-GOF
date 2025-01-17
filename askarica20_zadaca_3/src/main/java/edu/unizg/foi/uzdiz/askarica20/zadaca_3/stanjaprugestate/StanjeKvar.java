package edu.unizg.foi.uzdiz.askarica20.zadaca_3.stanjaprugestate;

public class StanjeKvar implements PrugaState {
	@Override
	public boolean postaviIspravna(PrugaContext context) {
		return false; // mora prvo proći testiranje
	}

	@Override
	public boolean postaviKvar(PrugaContext context) {
		return false; // već je u kvaru
	}

	@Override
	public boolean postaviTestiranje(PrugaContext context) {
		context.setStanje(new StanjeTestiranje());
		return true;
	}

	@Override
	public boolean postaviZatvorena(PrugaContext context) {
		context.setStanje(new StanjeZatvorena());
		return true;
	}

	@Override
	public String getOznaka() {
		return "K";
	}
}
