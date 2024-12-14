package edu.unizg.foi.uzdiz.askarica20.zadaca_2.obavjestavacobserver;

public interface ZeljeznickiObserverSubject {
  // zna svoje observere?? kaj to treba znacit
  // sucelje za dodavanje i brisanje observera objekata
  void prikvaciObservera(ZeljeznickiObserver zeljeznickiObserver);

  void otkvaciObservera(ZeljeznickiObserver zeljeznickiObserver);

  void obavijestiObservere(String poruka);
}
