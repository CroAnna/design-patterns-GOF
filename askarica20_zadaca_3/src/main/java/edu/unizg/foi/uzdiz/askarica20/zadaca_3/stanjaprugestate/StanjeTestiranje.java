package edu.unizg.foi.uzdiz.askarica20.zadaca_3.stanjaprugestate;

public class StanjeTestiranje implements PrugaState {
	@Override
	public boolean handle(PrugaContext context) {
		// Iz kvara ili zatvorenog može u testiranje
		String trenutnaOznaka = context.getTrenutnoStanje().getOznaka();
		System.out.println("trenutnaOznaka " + trenutnaOznaka + "mijenja se u T");
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