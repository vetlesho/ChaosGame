package org.example.chaosgame.view.components;

import org.example.chaosgame.controller.ChaosGameController;

/**
 * Slider for the imaginary part of a complex number.
 */
public class SliderImaginaryPart extends BaseSlider {
  public SliderImaginaryPart(ChaosGameController chaosGameController) {
    super(chaosGameController);
    this.setMin(-1);
    this.setMax(1);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  protected String getPartType() {
    return "imaginary";
  }
}
