package org.example.chaosgame.view.components;

import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.controller.observer.GameController;

/**
 * Slider for the imaginary part of a complex number.
 */
public class SliderImaginaryPart extends BaseSlider {
  public SliderImaginaryPart(GameController gameController) {
    super(gameController);
    this.setMin(-1);
    this.setMax(1);
    this.getStyleClass().add("top-bottom-padding");
  }
  /**
   * {@inheritDoc}
   */
  @Override
  protected String getPartType() {
    return "imaginary";
  }
}
