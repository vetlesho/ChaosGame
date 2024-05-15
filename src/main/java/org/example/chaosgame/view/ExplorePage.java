package org.example.chaosgame.view;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import org.example.chaosgame.controller.ExploreGameController;
import org.example.chaosgame.view.components.JuliaSlider;
import org.example.chaosgame.view.components.SideBar;

public class ExplorePage extends GamePage {

  private final SideBar sidebar;
  public ExplorePage(ExploreGameController exploreGameController) {
    super();
    this.sidebar = new SideBar(exploreGameController);
    Button homeButton = createHomeButton(event -> exploreGameController.homeButtonClicked());
    exploreGameController.setCanvas(gc.getCanvas());
    Slider juliaSliderReal = new JuliaSlider();
    juliaSliderReal.valueProperty().addListener((observable, oldValue, newValue) -> {
      exploreGameController.updateJuliaValue(newValue.doubleValue(), true);
    });
    VBox realBox = new VBox(juliaSliderReal);
    realBox.setSpacing(10);
    realBox.setAlignment(Pos.BOTTOM_CENTER);

    Slider juliaSliderImaginary = new JuliaSlider();
    juliaSliderImaginary.valueProperty().addListener((observable, oldValue, newValue) -> {
      exploreGameController.updateJuliaValue(newValue.doubleValue(), false);
    });
    juliaSliderImaginary.setOrientation(Orientation.VERTICAL);
    VBox imgBox = new VBox(juliaSliderImaginary);
    imgBox.setSpacing(10);
    imgBox.setAlignment(Pos.CENTER_LEFT);
    this.getChildren().addAll(gc.getCanvas(), sidebar, homeButton, realBox, imgBox);
    this.setStyle("-fx-background-color: black;");
    this.setOnScroll(event -> {
      try{
      exploreGameController.onScroll(event);
    } catch (Exception e) {
      exploreGameController.resetImage();
    }
    });
    this.setOnMousePressed(exploreGameController::mousePressed);
    this.setOnMouseDragged(exploreGameController::mouseDragged);
    this.setOnMouseReleased(exploreGameController::mouseReleased);
  }
}
