package org.example.chaosgame.view.components;

import javafx.scene.control.Slider;
import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.controller.observer.GameController;


/**
 * Abstract class for sliders in the GUI to Julia values.
 */
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

  /**
   * Get the complex number type of the slider.
   *
   * @return the part type of the slider
   */
  protected abstract String getPartType();
}
