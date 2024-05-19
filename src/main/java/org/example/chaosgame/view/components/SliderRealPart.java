package org.example.chaosgame.view.components;

import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.controller.observer.GameController;

public class SliderRealPart extends BaseSlider {
  public SliderRealPart(GameController gameController) {
    super(gameController);
    this.setMin(-1);
    this.setMax(1);
  }

  @Override
  protected String getPartType() {
    return "real";
  }
}
