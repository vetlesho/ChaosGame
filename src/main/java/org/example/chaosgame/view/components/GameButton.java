package org.example.chaosgame.view.components;

import javafx.scene.control.Button;

/**
 * Button for the game related functions.
 */
public class GameButton extends Button {
  public GameButton(String text){
    super(text);
    this.setMaxWidth(180);
    this.setMinWidth(180);
  }
}
