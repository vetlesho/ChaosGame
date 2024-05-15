package org.example.chaosgame.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.controller.ExploreGameController;

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
  public SideBar(ExploreGameController exploreGameController){
    Button zoomInButton = new GameButton("Zoom in");
    Button zoomOutButton = new GameButton("Zoom out");
    Button resetImage = new GameButton("Reset");

    zoomInButton.setOnAction(event -> exploreGameController.zoomButtonClicked(1 / 1.05));
    zoomOutButton.setOnAction(event -> exploreGameController.zoomButtonClicked(1.05));
    resetImage.setOnAction(event -> exploreGameController.resetImage());

    this.getChildren().addAll(zoomInButton, zoomOutButton, resetImage);
    this.setAlignment(Pos.CENTER_RIGHT);
    this.setSpacing(10);
  }
}
