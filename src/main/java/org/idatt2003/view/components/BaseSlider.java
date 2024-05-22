package org.idatt2003.view.components;

import javafx.scene.control.Slider;
import org.idatt2003.controller.interfaces.GameController;


/**
 * Abstract class for sliders in the GUI to Julia values.
 * Extends the Slider class.
 */
public abstract class BaseSlider extends Slider {
  /**
   * Constructor for the BaseSlider.
   *
   * @param gameController the game controller
   */
  public BaseSlider(GameController gameController) {
    super();
    this.setMin(-1);
    this.setMax(1);
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
