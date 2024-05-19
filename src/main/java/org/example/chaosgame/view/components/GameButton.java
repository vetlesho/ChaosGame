package org.example.chaosgame.view.components;

import javafx.scene.control.Button;

/**
 * Class for the game button, extends Button.
 * The button is used for game related actions.
 */
public class GameButton extends Button {
  /**
   * Constructor for the GameButton.
   *
   * @param text the text to be displayed on the button
   */
  public GameButton(String text) {
    super(text);
    this.setMaxWidth(180);
    this.setMinWidth(180);
  }
}
