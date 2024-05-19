package org.example.chaosgame.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import org.example.chaosgame.controller.ChaosGameController;

import org.example.chaosgame.model.transformations.JuliaTransform;
import org.example.chaosgame.model.transformations.Transform2D;

import org.example.chaosgame.controller.ExploreGameController;


public class BottomBar extends HBox {
  private Label realPartLabel;
  private Label imaginaryPartLabel;
  private SliderRealPart sliderRealPart;
  private SliderImaginaryPart sliderImaginaryPart;

  public BottomBar(ChaosGameController chaosGameController) {
    this.setSpacing(10);
    this.realPartLabel = new Label();
    this.imaginaryPartLabel = new Label();
    this.sliderRealPart = new SliderRealPart(chaosGameController);
    this.sliderImaginaryPart = new SliderImaginaryPart(chaosGameController);

    realPartLabel.setMinSize(200, 20);
    imaginaryPartLabel.setMinSize(200, 20);
    realPartLabel.setAlignment(Pos.CENTER);
    imaginaryPartLabel.setAlignment(Pos.CENTER);

    this.setSpacing(40);
    this.setVisible(false);
    this.setAlignment(javafx.geometry.Pos.CENTER);
    this.getChildren().addAll(realPartLabel, sliderRealPart, sliderImaginaryPart, imaginaryPartLabel);
  }


  public void updateInformation(Transform2D transformation) {
    if (transformation instanceof JuliaTransform juliaTransform) {
      this.setVisible(true);
      sliderRealPart.setValue(juliaTransform.getComplex().getX());
      sliderImaginaryPart.setValue(juliaTransform.getComplex().getY());
      realPartLabel.setText("Real Part: " + (double) Math.round(juliaTransform.getComplex().getX() * 100) / 100);
      imaginaryPartLabel.setText("Imaginary Part: " + (double) Math.round(juliaTransform.getComplex().getY() * 100) / 100);
    } else {
      this.setVisible(false);
    }
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
