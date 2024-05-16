package org.example.chaosgame.view.components;

import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import org.example.chaosgame.controller.ChaosGameController;

import java.util.List;

public class DoubleSlider extends Slider {
  public DoubleSlider(ChaosGameController chaosGameController) {
    //min value -1 and max is 1
    super();
    this.setMin(-1);
    this.setMax(1);
    this.setValue(0);
    this.setShowTickLabels(true);
    this.setShowTickMarks(true);
    this.setMaxWidth(200);

  }
}
