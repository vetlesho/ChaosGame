package org.example.chaosgame.observer;

import javafx.scene.Node;
import org.example.chaosgame.view.ChaosPage;

public interface PageSubject {
  void registerObserver(PageObserver observer);
  void removeObserver(PageObserver observer);
  void notifyObservers(Node page);
}
