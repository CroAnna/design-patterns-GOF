package edu.unizg.foi.uzdiz.askarica20.zadaca_3.stanjaprugestate;

public class StanjeIspravna implements PrugaState {
	@Override
	public boolean handle(PrugaContext context) {
		// Iz testiranja može u ispravno
		String trenutnaOznaka = context.getTrenutnoStanje().getOznaka();

		System.out.println("trenutnaOznaka " + trenutnaOznaka + "mijenja se u I");

		if (trenutnaOznaka.equals("T")) {
			context.setStanje(this);
			return true;
		}
		return false;
	}

	@Override
	public String getOznaka() {
		return "I";
	}
}
