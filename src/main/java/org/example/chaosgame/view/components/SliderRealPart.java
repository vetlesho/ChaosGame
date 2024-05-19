package org.example.chaosgame.view.components;

import org.example.chaosgame.controller.ChaosGameController;

public class SliderRealPart extends BaseSlider {
  public SliderRealPart(ChaosGameController chaosGameController) {
    super(chaosGameController);
    this.setMin(-1);
    this.setMax(1);
  }

  @Override
  protected String getPartType() {
    return "real";
  }
}
