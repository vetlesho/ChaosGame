package org.example.chaosgame.view.components;

import javafx.scene.text.Text;

public class GameHeader extends Text {
  public GameHeader(String text){
    super(text);
    this.getStyleClass().add("game-header");
  }
}