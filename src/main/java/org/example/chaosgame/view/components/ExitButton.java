package org.example.chaosgame.view.components;

import javafx.scene.control.Button;

/**
 * Button for exiting the application.
 */
public class ExitButton extends Button  {
  public ExitButton() {
    super("Exit");
    this.getStyleClass().add("exit-button");
  }
}
