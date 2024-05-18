package org.example.chaosgame.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.controller.ExploreGameController;

public class BottomBar extends HBox {
  public BottomBar(ChaosGameController chaosGameController) {
    Slider realPartSlider = new DoubleSlider(chaosGameController);
    Slider imaginaryPartSlider = new DoubleSlider(chaosGameController);

    realPartSlider.valueProperty().addListener((observable, oldValue, newValue) ->
      chaosGameController.updateJuliaValue(newValue.doubleValue(), imaginaryPartSlider.getValue()));
    imaginaryPartSlider.valueProperty().addListener((observable, oldValue, newValue) ->
      chaosGameController.updateJuliaValue(realPartSlider.getValue(), newValue.doubleValue()));

    this.getChildren().addAll(realPartSlider, imaginaryPartSlider);
    this.setPadding(new javafx.geometry.Insets(10));

    this.setSpacing(50);
    this.setAlignment(Pos.BOTTOM_CENTER);
    setSliderVisibility(false);
  }

  public BottomBar(ExploreGameController exploreGameController) {
    Slider realPartSlider = new DoubleSlider(exploreGameController);
    Slider imaginaryPartSlider = new DoubleSlider(exploreGameController);

    realPartSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (Math.abs(newValue.doubleValue() - oldValue.doubleValue()) > 0.01) {
        exploreGameController.updateJuliaValue(newValue.doubleValue(), imaginaryPartSlider.getValue());
      }
    });
    imaginaryPartSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (Math.abs(newValue.doubleValue() - oldValue.doubleValue()) > 0.01) {
        exploreGameController.updateJuliaValue(realPartSlider.getValue(), newValue.doubleValue());
      }
    });

    this.getChildren().addAll(realPartSlider, imaginaryPartSlider);
    this.setPadding(new javafx.geometry.Insets(10));

    this.setSpacing(50);
    this.setAlignment(Pos.BOTTOM_CENTER);
    setSliderVisibility(true);
  }

  public void setSliderVisibility(boolean isVisible) {
    this.getChildren().forEach(node -> {
      if (node instanceof Slider) {
        node.setVisible(isVisible);
      }
    });
  }
}
