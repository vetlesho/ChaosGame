package org.idatt2003.controller.interfaces;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.idatt2003.view.GamePage;

/**
 * Interface for the GameController.
 * Includes abstract methods for setting canvas bind,
 * updating julia values, home button clicked and updating fractal colors
 */
public interface GameController {

  GamePage getGamePage();

  /**
   * Method for binding the canvas to the main pane.
   *
   * @param mainPane Main pane of the application
   */
  void setBind(StackPane mainPane);

  /**
   * Method for updating the Julia value with slider components.
   *
   * @param partType Type of the part to update (real or imaginary)
   * @param value    New value for the part
   */
  void updateJuliaValue(String partType, double value);

  /**
   * Goes to HomePage.
   */
  void homeButtonClicked();

  /**
   * Method to update color of the fractal.
   *
   * @param color the new color.
   */
  void updateFractalColor(Color color);

}
