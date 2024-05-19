package org.example.chaosgame.view.components;

import org.example.chaosgame.controller.interfaces.GameController;

/**
 * Class for the imaginary part slider, extends BaseSlider.
 * The slider is used for setting the imaginary part of the complex number.
 */
public class SliderImaginaryPart extends BaseSlider {
  /**
   * Constructor for the SliderImaginaryPart.
   *
   * @param gameController the game controller
   */
  public SliderImaginaryPart(GameController gameController) {
    super(gameController);
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
