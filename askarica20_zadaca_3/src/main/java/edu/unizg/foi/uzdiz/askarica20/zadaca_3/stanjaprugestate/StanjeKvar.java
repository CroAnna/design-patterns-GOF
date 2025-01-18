package edu.unizg.foi.uzdiz.askarica20.zadaca_3.stanjaprugestate;

public class StanjeKvar implements PrugaState {
	@Override
	public boolean handle(PrugaContext context) {
		// Bitno je trenutno stanje, NE stanje u koje idemo!
		System.out.println("trenutnaOznaka nebitna. mijenja se u K");

		context.setStanje(this);
		return true; // Uvijek dopuštamo postavljanje kvara
	}

	@Override
	public String getOznaka() {
		return "K";
	}
}
