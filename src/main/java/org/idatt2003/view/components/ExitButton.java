package org.idatt2003.view.components;

import javafx.scene.control.Button;

/**
 * Class for the exit button, extends Button.
 */
public class ExitButton extends Button  {
  public ExitButton() {
    super("Exit");
    this.getStyleClass().add("exit-button");
  }
}
