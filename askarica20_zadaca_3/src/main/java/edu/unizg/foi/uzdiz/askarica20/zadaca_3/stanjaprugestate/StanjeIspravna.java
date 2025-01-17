package edu.unizg.foi.uzdiz.askarica20.zadaca_3.stanjaprugestate;

public class StanjeIspravna implements PrugaState {
	@Override
	public boolean postaviIspravna(PrugaContext context) {
		return false; // već je ispravna
	}

	@Override
	public boolean postaviKvar(PrugaContext context) {
		context.setStanje(new StanjeKvar());
		return true;
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
		return "I";
	}
}
