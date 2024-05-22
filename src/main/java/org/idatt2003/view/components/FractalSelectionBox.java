package org.idatt2003.view.components;

import javafx.scene.control.ComboBox;
import org.idatt2003.controller.ChaosGameController;

/**
 * Class for the FractalSelectionBox, extends ComboBox.
 * Creates a ComboBox for selecting already implemented fractals.
 */
public class FractalSelectionBox extends ComboBox<String> {

  /**
   * Constructor for the FractalSelectionBox.
   *
   * <p>Creates a ComboBox for selecting already implemented fractals.
   *
   * @param chaosGameController the controller for the chaos game
   */
  public FractalSelectionBox(ChaosGameController chaosGameController) {
    this.setPrefWidth(180);
    this.setPromptText("Julia ");
    this.getItems().addAll("Sierpinski", "Barnsley", "Julia");

    this.setOnAction(event -> {
      String selectedGame = this.getValue().trim().toUpperCase();
      chaosGameController.gameSelection(selectedGame);
    });
  }
}
