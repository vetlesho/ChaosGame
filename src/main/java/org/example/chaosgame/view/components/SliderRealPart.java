package org.example.chaosgame.view.components;

import org.example.chaosgame.controller.interfaces.GameController;

/**
 * Class for the real part slider, extends BaseSlider.
 * The slider is used for setting the real part of the complex number.
 */
public class SliderRealPart extends BaseSlider {
  /**
   * Constructor for the SliderRealPart.
   *
   * @param gameController the game controller
   */
  public SliderRealPart(GameController gameController) {
    super(gameController);
  }

  @Override
  protected String getPartType() {
    return "real";
  }
}
