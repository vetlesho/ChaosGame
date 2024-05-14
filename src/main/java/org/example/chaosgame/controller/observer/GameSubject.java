package org.example.chaosgame.controller.observer;

public interface GameSubject {
  void registerObserver(GameObserver gameObserver);
  void removeObserver(GameObserver gameObserver);
  void notifyObservers();
}
