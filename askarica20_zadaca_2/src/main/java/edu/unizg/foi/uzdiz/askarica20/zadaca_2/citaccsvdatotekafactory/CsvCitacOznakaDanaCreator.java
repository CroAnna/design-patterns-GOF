package edu.unizg.foi.uzdiz.askarica20.zadaca_2.citaccsvdatotekafactory;

public class CsvCitacOznakaDanaCreator extends CsvCitacCreator {

  @Override
  public CsvCitacProduct kreirajCitac() {
    return new CsvCitacOznakaDanaProduct();
  }
}
