package org.example.chaosgame.view.components;

import javafx.scene.control.Button;

public class ChooseGameButton extends Button {
  public ChooseGameButton(String text){
    super(text);
    this.getStyleClass().add("choose-game-button");
  }
}
