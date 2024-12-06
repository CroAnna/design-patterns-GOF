package edu.unizg.foi.uzdiz.askarica20.zadaca_2.citaccsvdatotekafactory;

public class CsvCitacKompozicijaCreator extends CsvCitacCreator {

  @Override
  public CsvCitacProduct kreirajCitac() {
    return new CsvCitacKompozicijaProduct();
  }

}
