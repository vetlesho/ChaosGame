package org.example.chaosgame.view.components;

import javafx.scene.control.ComboBox;
import org.example.chaosgame.controller.ChaosGameController;

public class GameSelectionBox extends ComboBox<String> {
  public GameSelectionBox(ChaosGameController chaosGameController){
    this.setPrefWidth(200);
    this.setPromptText("Select fractal");
    this.getItems().addAll("Julia", "Sierpinski", "Barnsley");
    this.setOnAction(event -> {
      String selectedGame = this.getValue().trim().toUpperCase();
      chaosGameController.gameSelection(selectedGame);
    });
  }
}
