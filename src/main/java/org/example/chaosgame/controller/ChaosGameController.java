package org.example.chaosgame.controller;

import org.example.chaosgame.model.chaos.ChaosGame;
import org.example.chaosgame.view.ChaosPage;

public class ChaosGameController implements Observer{
  private final ChaosGame chaosGame;
//  private final ChaosPage chaosPage;




  public ChaosGameController(ChaosGame chaosGame) {
    this.chaosGame = chaosGame;
  }

  @Override
  public void update() {
//    chaosPage.updateCanvas();
    System.out.println("ChaosGameController.update");
  }
}
