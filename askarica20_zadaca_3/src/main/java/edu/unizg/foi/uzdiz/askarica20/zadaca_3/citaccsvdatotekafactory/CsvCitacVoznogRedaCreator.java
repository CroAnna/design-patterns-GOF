package edu.unizg.foi.uzdiz.askarica20.zadaca_3.citaccsvdatotekafactory;

public class CsvCitacVoznogRedaCreator extends CsvCitacCreator {

  @Override
  public CsvCitacProduct kreirajCitac() {
    return new CsvCitacVoznogRedaProduct();
  }

}
