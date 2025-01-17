package edu.unizg.foi.uzdiz.askarica20.zadaca_3.stanjaprugestate;

public interface PrugaState {
	boolean postaviIspravna(PrugaContext context);

	boolean postaviKvar(PrugaContext context);

	boolean postaviTestiranje(PrugaContext context);

	boolean postaviZatvorena(PrugaContext context);

	String getOznaka();
}
