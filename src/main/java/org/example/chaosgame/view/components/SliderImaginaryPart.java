package org.example.chaosgame.view.components;

import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.controller.observer.GameController;

public class SliderImaginaryPart extends BaseSlider {
  public SliderImaginaryPart(GameController gameController) {
    super(gameController);
    this.setMin(-1);
    this.setMax(1);
  }

  @Override
  protected String getPartType() {
    return "imaginary";
  }
}
