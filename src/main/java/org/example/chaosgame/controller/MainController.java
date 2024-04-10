package org.example.chaosgame.controller;

import javafx.scene.layout.StackPane;
import org.example.chaosgame.model.chaos.ChaosGame;
import org.example.chaosgame.view.ChaosPage;

public class MainController implements Observer{
  private final ChaosGame chaosGame;
  private final ChaosPage chaosPage;


  public MainController(ChaosGame chaosGame) {
    this.chaosGame = chaosGame;
    this.chaosPage = new ChaosPage();
    chaosGame.registerObserver(this);
  }

  @Override
  public void update() {
    chaosPage.updateCanvas();
  }
}
