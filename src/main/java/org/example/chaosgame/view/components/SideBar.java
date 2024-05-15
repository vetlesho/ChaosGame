package org.example.chaosgame.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.chaosgame.controller.ChaosGameController;

public class SideBar extends VBox {
  public SideBar(ChaosGameController chaosGameController){
    GameSelectionBox gameSelectionBox = new GameSelectionBox(chaosGameController);
    TextField numberOfStepsInput = new NumberOfStepsInput();
    Button runStepsButton = new GameButton("Run steps");
    Button openFileButton = new GameButton("Open file");
    ColorPickerComponent colorPicker = new ColorPickerComponent(chaosGameController::updateFractalColor);

    runStepsButton.setOnAction(event -> chaosGameController.runStepsValidation(numberOfStepsInput));
    openFileButton.setOnAction(event -> chaosGameController.openFromFile());

    this.getChildren().addAll(gameSelectionBox, colorPicker, numberOfStepsInput, runStepsButton, openFileButton);
    this.setSpacing(10);
    this.setPadding(new Insets(10));
    this.setAlignment(Pos.CENTER_RIGHT);
  }
}
