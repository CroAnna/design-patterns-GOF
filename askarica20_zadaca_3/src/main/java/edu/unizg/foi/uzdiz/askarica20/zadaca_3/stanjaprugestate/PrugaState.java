package edu.unizg.foi.uzdiz.askarica20.zadaca_3.stanjaprugestate;

public interface PrugaState {

	boolean handle(PrugaContext context);

	String getOznaka();
}
