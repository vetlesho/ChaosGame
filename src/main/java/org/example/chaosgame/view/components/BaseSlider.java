package org.example.chaosgame.view.components;

import javafx.scene.control.Slider;
import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.controller.observer.GameController;

public abstract class BaseSlider extends Slider {
  public BaseSlider(GameController gameController) {
    super();
    this.setValue(0);
    this.setShowTickLabels(true);
    this.setMaxWidth(200);

    this.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (Math.abs(newValue.doubleValue() - oldValue.doubleValue()) > 0.01) {
        gameController.updateJuliaValue(getPartType(), newValue.doubleValue());
      }
    });
  }

  protected abstract String getPartType();
}
