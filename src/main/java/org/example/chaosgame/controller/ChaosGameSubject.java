package org.example.chaosgame.controller;

public interface ChaosGameSubject {
  void registerChaosGameObserver(ChaosGameObserver observer);
  void removeChaosGameObserver(ChaosGameObserver observer);
  void notifyChaosGameObservers();
}
