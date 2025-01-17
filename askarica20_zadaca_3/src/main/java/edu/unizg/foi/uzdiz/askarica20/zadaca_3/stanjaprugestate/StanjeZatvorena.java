package edu.unizg.foi.uzdiz.askarica20.zadaca_3.stanjaprugestate;

public class StanjeZatvorena implements PrugaState {
	@Override
	public boolean postaviIspravna(PrugaContext context) {
		return false; // mora prvo proći testiranje
	}

	@Override
	public boolean postaviKvar(PrugaContext context) {
		return false; // pruga je zatvorena
	}

	@Override
	public boolean postaviTestiranje(PrugaContext context) {
		context.setStanje(new StanjeTestiranje());
		return true;
	}

	@Override
	public boolean postaviZatvorena(PrugaContext context) {
		return false; // već je zatvorena
	}

	@Override
	public String getOznaka() {
		return "Z";
	}
}
