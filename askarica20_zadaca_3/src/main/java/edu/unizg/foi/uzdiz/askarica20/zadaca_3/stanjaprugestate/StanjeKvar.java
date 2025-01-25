package edu.unizg.foi.uzdiz.askarica20.zadaca_3.stanjaprugestate;

public class StanjeKvar implements PrugaState {
	@Override
	public boolean handle(PrugaContext context) {
		context.setStanje(this);
		return true;
	}

	@Override
	public String getOznaka() {
		return "K";
	}
}
