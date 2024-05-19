package org.example.chaosgame.controller.observer;

import javafx.scene.layout.StackPane;
import org.example.chaosgame.view.GamePage;

public interface GameController {
  void setBind(StackPane mainPane);

  GamePage getGamePage();

  void updateJuliaValue(String partType, double value);

  void homeButtonClicked();

}
