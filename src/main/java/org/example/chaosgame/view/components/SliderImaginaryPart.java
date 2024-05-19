package org.example.chaosgame.view.components;

import org.example.chaosgame.controller.ChaosGameController;

public class SliderImaginaryPart extends BaseSlider {
  public SliderImaginaryPart(ChaosGameController chaosGameController) {
    super(chaosGameController);
    this.setMin(-1);
    this.setMax(1);
  }

  @Override
  protected String getPartType() {
    return "imaginary";
  }
}
