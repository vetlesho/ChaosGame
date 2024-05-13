package org.example.chaosgame.controller;

import javafx.scene.layout.StackPane;
import org.example.chaosgame.model.chaos.ChaosGame;
import org.example.chaosgame.model.chaos.ChaosGameDescriptionFactory;
import org.example.chaosgame.model.chaos.ChaosGameType;
import org.example.chaosgame.view.ChaosPage;

public class MainController {
  private final ChaosGame chaosGame;
  private final ChaosPage chaosPage;

  public MainController() {
    this.chaosGame = new ChaosGame(ChaosGameDescriptionFactory.get(ChaosGameType.JULIA), 1200, 800);
    this.chaosPage = new ChaosPage(chaosGame);
  }

  public ChaosGame getChaosGame() {
    return chaosGame;
  }

  public ChaosPage getChaosContent() {
    return chaosPage;
  }

  public void updateChaosGame(ChaosGameType selectedGame) {
    chaosGame.setChaosGameDescription(ChaosGameDescriptionFactory.get(selectedGame));
  }


}
