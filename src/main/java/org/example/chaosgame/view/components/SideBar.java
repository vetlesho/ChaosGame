package org.example.chaosgame.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.controller.ExploreGameController;

public class SideBar extends VBox {
  public SideBar(ChaosGameController chaosGameController) {
    FractalSelectionBox fractalSelectionBox = new FractalSelectionBox(chaosGameController);
    ColorPickerComponent colorPicker = new ColorPickerComponent(chaosGameController::updateFractalColor);
    TextField numberOfStepsInput = new NumberOfStepsInput();

    Button coordinatesButton = new GameButton("Set coordinates");
    Button createOwnFractal = new GameButton("Create own fractal");
    Button openFileButton = new GameButton("Open file");
    Button saveFractalButton = new GameButton("Save fractal");

    Button runGame = new GameButton("Run ChaosGame");
    Button resetGame = new GameButton("Reset ChaosGame");

    coordinatesButton.setOnAction(event -> chaosGameController.setMaxMinCoords());
    createOwnFractal.setOnAction(event -> chaosGameController.createOwnFractal());
    openFileButton.setOnAction(event -> chaosGameController.openFromFile());
    saveFractalButton.setOnAction(event -> chaosGameController.saveFractal());
    runGame.setOnAction(event -> chaosGameController.runStepsValidation(numberOfStepsInput));
    resetGame.setOnAction(event -> chaosGameController.resetGame());

    this.getChildren().addAll(
            fractalSelectionBox, colorPicker, numberOfStepsInput,
            coordinatesButton, createOwnFractal, saveFractalButton, openFileButton,
            runGame, resetGame);
    this.setSpacing(10);
    this.setPadding(new Insets(10));
    this.setAlignment(Pos.CENTER_RIGHT);

    VBox.setMargin(coordinatesButton, new Insets(50, 0, 0, 0));
    VBox.setMargin(runGame, new Insets(50, 0, 0, 0));
  }

  public SideBar(ExploreGameController exploreGameController) {
    Button zoomInButton = new GameButton("Zoom in");
    Button zoomOutButton = new GameButton("Zoom out");
    Button resetImage = new GameButton("Reset");
    ColorPickerComponent colorPicker = new ColorPickerComponent(exploreGameController::updateFractalColor);

    zoomInButton.setOnAction(event -> exploreGameController.zoomButtonClicked(1 / 1.05));
    zoomOutButton.setOnAction(event -> exploreGameController.zoomButtonClicked(1.05));
    resetImage.setOnAction(event -> exploreGameController.resetImage());

    this.getChildren().addAll(zoomInButton, zoomOutButton, colorPicker, resetImage);
    this.setAlignment(Pos.CENTER_RIGHT);
    this.setSpacing(10);
  }
}
