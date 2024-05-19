package org.example.chaosgame.view.components;

import javafx.scene.control.Slider;
import org.example.chaosgame.controller.ChaosGameController;


/**
 * Abstract class for sliders in the GUI to Julia values.
 */
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

  /**
   * Get the complex number type of the slider.
   *
   * @return the part type of the slider
   */
  protected abstract String getPartType();
}
