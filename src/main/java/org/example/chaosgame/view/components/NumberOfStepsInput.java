package org.example.chaosgame.view.components;

import javafx.scene.control.TextField;

public class NumberOfStepsInput extends TextField {
  public NumberOfStepsInput(){
    this.setPromptText("Number of steps");
    this.setMaxWidth(200);
    this.setMinWidth(200);
    this.setMinHeight(30);
    this.setMaxHeight(30);
  }
}
