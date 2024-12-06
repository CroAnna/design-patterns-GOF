package edu.unizg.foi.uzdiz.askarica20.zadaca_2.citaccsvdatotekafactory;

public class CsvCitacVozilaCreator extends CsvCitacCreator {

  @Override
  public CsvCitacProduct kreirajCitac() {
    return new CsvCitacVozilaProduct();
  }

}
