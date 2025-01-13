package edu.unizg.foi.uzdiz.askarica20.zadaca_3.obavjestavacobserver;

public interface ZeljeznickiObserverSubject {
  void prikvaciObservera(ZeljeznickiObserver zeljeznickiObserver);

  void otkvaciObservera(ZeljeznickiObserver zeljeznickiObserver);

  void obavijestiObservere(String poruka);
}
