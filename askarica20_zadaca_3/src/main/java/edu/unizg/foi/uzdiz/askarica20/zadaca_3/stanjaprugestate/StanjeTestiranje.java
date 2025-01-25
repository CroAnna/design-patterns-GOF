package edu.unizg.foi.uzdiz.askarica20.zadaca_3.stanjaprugestate;

public class StanjeTestiranje implements PrugaState {
	@Override
	public boolean handle(PrugaContext context) {
		String trenutnaOznaka = context.getTrenutnoStanje().getOznaka();
		if (trenutnaOznaka.equals("K") || trenutnaOznaka.equals("Z")) {
			context.setStanje(this);
			return true;
		}
		return false;
	}

	@Override
	public String getOznaka() {
		return "T";
	}
}