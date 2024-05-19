package org.example.chaosgame.view.components;

import javafx.scene.control.ComboBox;
import org.example.chaosgame.controller.ChaosGameController;

public class FractalSelectionBox extends ComboBox<String> {
  public FractalSelectionBox(ChaosGameController chaosGameController){
    this.setPrefWidth(200);
    this.setPromptText("Sierpinski ");
    this.getItems().addAll("Sierpinski", "Barnsley", "Julia");

    this.setOnAction(event -> {
      String selectedGame = this.getValue().trim().toUpperCase();
      chaosGameController.gameSelection(selectedGame);
    });
  }
}
