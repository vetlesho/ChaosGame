package org.example.chaosgame.controller.observer;

import javafx.scene.Node;

public interface PageSubject {
  void registerObserver(PageObserver observer);
  void removeObserver(PageObserver observer);
  void notifyObservers(Node page);
}
