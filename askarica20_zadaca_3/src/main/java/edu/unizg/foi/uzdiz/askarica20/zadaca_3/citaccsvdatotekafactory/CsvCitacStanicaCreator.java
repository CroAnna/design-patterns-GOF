package edu.unizg.foi.uzdiz.askarica20.zadaca_3.citaccsvdatotekafactory;

public class CsvCitacStanicaCreator extends CsvCitacCreator {

  @Override
  public CsvCitacProduct kreirajCitac() {
    return new CsvCitacStanicaProduct();
  }

}
