package org.example.chaosgame.controller;

public interface ChaosGameSubject {
  void registerObserver(Observer observer);
  void removeObserver(Observer observer);
  void notifyObservers();
}
