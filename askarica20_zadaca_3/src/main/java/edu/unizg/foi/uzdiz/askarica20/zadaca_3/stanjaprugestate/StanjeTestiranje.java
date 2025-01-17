package edu.unizg.foi.uzdiz.askarica20.zadaca_3.stanjaprugestate;

public class StanjeTestiranje implements PrugaState {
	@Override
	public boolean postaviIspravna(PrugaContext context) {
		context.setStanje(new StanjeIspravna());
		return true;
	}

	@Override
	public boolean postaviKvar(PrugaContext context) {
		context.setStanje(new StanjeKvar());
		return true;
	}

	@Override
	public boolean postaviTestiranje(PrugaContext context) {
		return false; // već je u testiranju
	}

	@Override
	public boolean postaviZatvorena(PrugaContext context) {
		context.setStanje(new StanjeZatvorena());
		return true;
	}

	@Override
	public String getOznaka() {
		return "T";
	}
}
