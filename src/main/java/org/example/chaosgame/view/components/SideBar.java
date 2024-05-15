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

    Button createOwnJuliaFractalButton = new GameButton("Create Julia fractal");
    Button saveFractalButton = new GameButton("Save fractal");
    Button openFileButton = new GameButton("Open file");
    ColorPickerComponent colorPicker = new ColorPickerComponent(chaosGameController::updateFractalColor);

    Button runStepsButton = new GameButton("Run ChaosGame");

    runStepsButton.setOnAction(event -> chaosGameController.runStepsValidation(numberOfStepsInput));
    openFileButton.setOnAction(event -> chaosGameController.openFromFile());
    createOwnJuliaFractalButton.setOnAction(event -> chaosGameController.createOwnJuliaFractal());
    saveFractalButton.setOnAction(event -> chaosGameController.saveFractal());

    this.getChildren().addAll(gameSelectionBox, colorPicker, numberOfStepsInput,
            createOwnJuliaFractalButton, saveFractalButton, openFileButton, runStepsButton);
    this.setSpacing(10);
    this.setPadding(new Insets(10));
    this.setAlignment(Pos.CENTER_RIGHT);

    VBox.setMargin(createOwnJuliaFractalButton, new Insets(50, 0, 0, 0));
    VBox.setMargin(runStepsButton, new Insets(50, 0, 0, 0));
  }
}
