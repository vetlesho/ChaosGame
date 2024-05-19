package org.example.chaosgame.view.components;

import javafx.scene.control.TextField;

/**
 * Class for the NumberOfStepsInput, extends TextField.
 * The input is used for setting the number of steps for the game.
 */
public class NumberOfStepsInput extends TextField {
  /**
   * Constructor for the NumberOfStepsInput.
   * Sets the prompt text for the input.
   */
  public NumberOfStepsInput() {
    this.setPromptText("Number of steps");
    this.setMaxWidth(180);
    this.setMinWidth(180);
    this.setMinHeight(30);
    this.setMaxHeight(30);
  }
}
