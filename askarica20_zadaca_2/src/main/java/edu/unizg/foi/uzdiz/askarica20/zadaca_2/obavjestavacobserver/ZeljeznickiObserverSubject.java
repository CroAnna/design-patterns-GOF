package edu.unizg.foi.uzdiz.askarica20.zadaca_2.obavjestavacobserver;

public interface ZeljeznickiObserverSubject {
  void prikvaciObservera(ZeljeznickiObserver zeljeznickiObserver);

  void otkvaciObservera(ZeljeznickiObserver zeljeznickiObserver);

  void obavijestiObservere(String poruka);
}
