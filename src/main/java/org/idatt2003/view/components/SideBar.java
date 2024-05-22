package org.idatt2003.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.idatt2003.controller.ChaosGameController;
import org.idatt2003.controller.ExploreGameController;

/**
 * Class for the sidebar, extends VBox.
 * The sidebar is used for displaying buttons and input fields for the game.
 * The class uses two constructors, one for the ChaosGame and one for the ExploreGame.
 */
public class SideBar extends VBox {
  /**
   * Constructor for the SideBar.
   * Creates the sidebar for the ChaosGame.
   * Event handlers are added to the buttons,
   * and call the corresponding methods in the ChaosGameController.
   *
   * @param chaosGameController the ChaosGameController
   */
  public SideBar(ChaosGameController chaosGameController) {
    Button coordinatesButton = new GameButton("Set coordinates");
    coordinatesButton.setOnAction(event -> chaosGameController.setMaxMinCoords());

    Button createOwnFractal = new GameButton("Create fractal");
    createOwnFractal.setOnAction(event -> chaosGameController.createOwnFractal());

    Button openFileButton = new GameButton("Open file");
    openFileButton.setOnAction(event -> chaosGameController.openFromFile());

    Button saveFractalButton = new GameButton("Save fractal");
    saveFractalButton.setOnAction(event -> chaosGameController.saveFractal());

    TextField numberOfStepsInput = new NumberOfStepsInput();
    Button runGame = new GameButton("Run steps");
    runGame.setOnAction(event -> chaosGameController.runStepsValidation(numberOfStepsInput));

    Button resetGame = new GameButton("Clear canvas");
    resetGame.setOnAction(event -> chaosGameController.resetGame());

    FractalSelectionBox fractalSelectionBox = new FractalSelectionBox(chaosGameController);
    ColorPickerComponent colorPicker = new ColorPickerComponent(
            chaosGameController::updateFractalColor);

    this.getChildren().addAll(
            fractalSelectionBox, colorPicker, createOwnFractal,
            coordinatesButton, saveFractalButton, openFileButton,
            numberOfStepsInput, runGame, resetGame);
    this.setAlignment(Pos.CENTER_RIGHT);

    VBox.setMargin(createOwnFractal, new Insets(30, 0, 0, 0));
    VBox.setMargin(numberOfStepsInput, new Insets(30, 0, 0, 0));

    this.getStyleClass().add("side-bar");
  }

  /**
   * Constructor for the SideBar.
   * Creates the sidebar for the ExploreGame.
   *
   * @param exploreGameController the ExploreGameController
   */
  public SideBar(ExploreGameController exploreGameController) {
    Button zoomInButton = new GameButton("Zoom in");
    zoomInButton.setOnAction(event -> exploreGameController.zoomButtonClicked(1 / 1.05));

    Button zoomOutButton = new GameButton("Zoom out");
    zoomOutButton.setOnAction(event -> exploreGameController.zoomButtonClicked(1.05));

    Button resetImage = new GameButton("Reset");
    resetImage.setOnAction(event -> exploreGameController.resetImage());

    ColorPickerComponent colorPicker = new ColorPickerComponent(
            exploreGameController::updateFractalColor);

    this.getChildren().addAll(zoomInButton, zoomOutButton, colorPicker, resetImage);
    this.setAlignment(Pos.CENTER_RIGHT);
    this.getStyleClass().add("side-bar");
  }
}
