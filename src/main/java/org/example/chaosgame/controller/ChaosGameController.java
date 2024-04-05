package org.example.chaosgame.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.example.chaosgame.model.GameObserver;
import org.example.chaosgame.model.chaos.ChaosGame;
import org.example.chaosgame.model.chaos.ChaosGameDescriptionFactory;
import org.example.chaosgame.view.ChaosGameView;
import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.view.MenuView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ChaosGameController implements GameObserver {
  private final int width = 1200;
  private final int height = 800;
  private ChaosGame game;
  private final ChaosGameView view;
  private final MenuView menuView;

  public ChaosGameController() {
    this.view = new ChaosGameView(width, height);

    this.menuView = new MenuView();
    this.menuView.getComboBox().valueProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        // Define the action for the selected option
        game = new ChaosGame(ChaosGameDescriptionFactory.get(newValue), width, height);
        game.addObserver(ChaosGameController.this);
        runGame(1000000);
      }
    });
  }

  public void runGame(int steps) {
    game.runSteps(steps);
    view.updateView(game.getCanvas());
  }

  public ChaosGameView getView() {
    return view;
  }

  @Override
  public void update() {
    view.updateView(game.getCanvas());
  }

  public MenuView getMenuView() {
    return this.menuView;
  }
}