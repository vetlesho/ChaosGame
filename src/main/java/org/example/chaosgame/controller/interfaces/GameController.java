package org.example.chaosgame.controller.interfaces;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.example.chaosgame.view.GamePage;

/**
 * Interface for the GameController.
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
