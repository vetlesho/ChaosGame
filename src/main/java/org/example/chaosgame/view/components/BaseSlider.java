package org.example.chaosgame.view.components;

import javafx.scene.control.Slider;
import org.example.chaosgame.controller.ChaosGameController;

public abstract class BaseSlider extends Slider {
  public BaseSlider(ChaosGameController chaosGameController) {
    super();
    this.setValue(0);
    this.setShowTickLabels(true);
    this.setMaxWidth(200);

    this.valueProperty().addListener((observable, oldValue, newValue) -> {
      chaosGameController.updateJuliaValue(getPartType(), newValue.doubleValue());
    });
  }

  protected abstract String getPartType();
}
