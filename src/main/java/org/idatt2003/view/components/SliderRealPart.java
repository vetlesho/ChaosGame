package org.idatt2003.view.components;

import org.idatt2003.controller.interfaces.GameController;

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
    this.getStyleClass().add("top-bottom-padding");
  }

  @Override
  protected String getPartType() {
    return "real";
  }
}
