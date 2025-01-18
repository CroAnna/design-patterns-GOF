package edu.unizg.foi.uzdiz.askarica20.zadaca_3.stanjaprugestate;

public class StanjeZatvorena implements PrugaState {
	@Override
	public boolean handle(PrugaContext context) {
		// Iz ispravnog ili testiranja može u zatvoreno
		String trenutnaOznaka = context.getTrenutnoStanje().getOznaka();
		System.out.println("trenutnaOznaka " + trenutnaOznaka + " mijenja se u Z");

		if (trenutnaOznaka.equals("I") || trenutnaOznaka.equals("T")) {
			context.setStanje(this);
			return true;
		}
		return false;
	}

	@Override
	public String getOznaka() {
		return "Z";
	}
}