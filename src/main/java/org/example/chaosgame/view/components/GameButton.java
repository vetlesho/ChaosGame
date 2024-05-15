package org.example.chaosgame.view.components;

import javafx.scene.control.Button;

public class GameButton extends Button {
  public GameButton(String text){
    super(text);
    this.setMaxWidth(150);
    this.setMinWidth(150);
  }
}
