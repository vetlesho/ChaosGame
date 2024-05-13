package org.example.chaosgame.controller;

import org.example.chaosgame.model.chaos.ChaosGame;
import org.example.chaosgame.model.chaos.ChaosGameDescriptionFactory;
import org.example.chaosgame.model.chaos.ChaosGameType;
import org.example.chaosgame.view.ChaosPage;

public class ChaosGameController implements Observer{
  private ChaosGame chaosGame;
  private final ChaosPage chaosPage;




  public ChaosGameController(ChaosPage chaosPage) {
    this.chaosGame = new ChaosGame(ChaosGameDescriptionFactory.get(ChaosGameType.JULIA), 1200, 800);
    this.chaosPage = chaosPage;
  }

  @Override
  public void update() {
    chaosPage.updateCanvas();
    System.out.println("ChaosGameController.update");
  }

  public ChaosPage getChaosPage() {
    return chaosPage;
  }
}
