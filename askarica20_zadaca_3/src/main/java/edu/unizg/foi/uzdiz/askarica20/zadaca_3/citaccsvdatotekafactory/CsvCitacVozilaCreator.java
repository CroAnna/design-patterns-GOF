package edu.unizg.foi.uzdiz.askarica20.zadaca_3.citaccsvdatotekafactory;

public class CsvCitacVozilaCreator extends CsvCitacCreator {

  @Override
  public CsvCitacProduct kreirajCitac() {
    return new CsvCitacVozilaProduct();
  }

}
