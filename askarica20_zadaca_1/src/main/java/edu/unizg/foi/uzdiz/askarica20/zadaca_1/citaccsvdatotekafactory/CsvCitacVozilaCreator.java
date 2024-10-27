package edu.unizg.foi.uzdiz.askarica20.zadaca_1.citaccsvdatotekafactory;

// ConcreteCreator
public class CsvCitacVozilaCreator extends CsvCitacCreator {

  @Override
  public CsvCitacProduct kreirajCitac() {
    return new CsvCitacVozilaProduct();
  }

}
