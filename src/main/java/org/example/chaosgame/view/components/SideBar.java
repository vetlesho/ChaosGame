package org.example.chaosgame.view.components;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.controller.ExploreGameController;
import javafx.beans.value.ChangeListener;

public class SideBar extends VBox {
  private Slider reValueslider;
  private Slider imValueslider;
  private TextField numberOfStepsInput;

  public SideBar(ChaosGameController chaosGameController){
    GameSelectionBox gameSelectionBox = new GameSelectionBox(chaosGameController);
    this.numberOfStepsInput = new NumberOfStepsInput();
    this.reValueslider = new DoubleSlider(chaosGameController);
    this.imValueslider = new DoubleSlider(chaosGameController);

    Button createOwnJuliaFractalButton = new GameButton("Create Julia fractal");
    Button createOwnAffineFractalButton = new GameButton("Create Affine fractal");
    Button saveFractalButton = new GameButton("Save fractal");
    Button openFileButton = new GameButton("Open file");
    ColorPickerComponent colorPicker = new ColorPickerComponent(chaosGameController::updateFractalColor);

    Button runStepsButton = new GameButton("Run ChaosGame");

    openFileButton.setOnAction(event -> chaosGameController.openFromFile());
    createOwnJuliaFractalButton.setOnAction(event -> chaosGameController.createOwnJuliaFractal());
    createOwnAffineFractalButton.setOnAction(event -> chaosGameController.createOwnAffineFractal());
    saveFractalButton.setOnAction(event -> chaosGameController.saveFractal());
    reValueslider.valueProperty().addListener((observable, oldValue, newValue) ->
            chaosGameController.updateJuliaReValue(newValue.doubleValue(), imValueslider.getValue()));
    imValueslider.valueProperty().addListener((observable, oldValue, newValue) ->
            chaosGameController.updateJuliaImValue(reValueslider.getValue(), newValue.doubleValue()));

    runStepsButton.setOnAction(event -> chaosGameController.runStepsValidation(numberOfStepsInput));

    this.getChildren().addAll(gameSelectionBox, colorPicker, numberOfStepsInput,
            reValueslider, imValueslider,
            createOwnJuliaFractalButton, createOwnAffineFractalButton,
            saveFractalButton, openFileButton, runStepsButton);
    this.setSpacing(10);
    this.setPadding(new Insets(10));
    this.setAlignment(Pos.CENTER_RIGHT);

    VBox.setMargin(createOwnJuliaFractalButton, new Insets(50, 0, 0, 0));
    VBox.setMargin(runStepsButton, new Insets(50, 0, 0, 0));



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

  public TextField getStepsTextField() {
    return numberOfStepsInput;
  }
}
