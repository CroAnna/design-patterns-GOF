package edu.unizg.foi.uzdiz.askarica20.zadaca_1.citaccsvdatotekafactory;

// ConcreteCreator
public class CsvCitacStanicaCreator extends CsvCitacCreator {

  @Override
  public CsvCitacProduct kreirajCitac() {
    return new CsvCitacStanicaProduct();
  }

}
