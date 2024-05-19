package org.example.chaosgame.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import org.example.chaosgame.controller.ChaosGameController;

import org.example.chaosgame.controller.observer.GameController;
import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.transformations.ExploreJulia;
import org.example.chaosgame.model.transformations.JuliaTransform;
import org.example.chaosgame.model.transformations.Transform2D;

import org.example.chaosgame.controller.ExploreGameController;


public class BottomBar extends HBox {
  private Label realPartLabel;
  private Label imaginaryPartLabel;
  private SliderRealPart sliderRealPart;
  private SliderImaginaryPart sliderImaginaryPart;

  public BottomBar(GameController gameController) {
    this.setSpacing(10);
    this.realPartLabel = new Label();
    this.imaginaryPartLabel = new Label();
    this.sliderRealPart = new SliderRealPart(gameController);
    this.sliderImaginaryPart = new SliderImaginaryPart(gameController);

    realPartLabel.setMinSize(200, 20);
    imaginaryPartLabel.setMinSize(200, 20);
    realPartLabel.setAlignment(Pos.CENTER);
    imaginaryPartLabel.setAlignment(Pos.CENTER);

    this.setAlignment(javafx.geometry.Pos.CENTER);
    this.getChildren().addAll(realPartLabel, sliderRealPart, sliderImaginaryPart, imaginaryPartLabel);
    this.setSpacing(50);
    this.setAlignment(Pos.BOTTOM_CENTER);

    this.getStyleClass().add("top-bottom-bar");
    this.setStyle("-fx-background-color: #f0f0f0;");

  }
  public void updateBottomBar(Transform2D transformation) {
    if (transformation instanceof JuliaTransform juliaTransform) {
      juliaInformation(juliaTransform.getComplex());
    } else if (transformation instanceof ExploreJulia exploreJulia) {
      juliaInformation(exploreJulia.getComplex());
    } else {
      sliderInfoVisability(false);
    }
  }

  private void juliaInformation(Complex complex) {
    sliderInfoVisability(true);
    sliderRealPart.setValue(complex.getX());
    sliderImaginaryPart.setValue(complex.getY());
    realPartLabel.setText("Real Part: " + (double) Math.round(complex.getX() * 100) / 100);
    imaginaryPartLabel.setText("Imaginary Part: " + (double) Math.round(complex.getY() * 100) / 100);
  }

//  public BottomBar(ExploreGameController exploreGameController) {
//    this.realPartLabel = new Label();
//    this.imaginaryPartLabel = new Label();
//    this.sliderRealPart = new SliderRealPart(exploreGameController);
//    this.sliderImaginaryPart = new SliderImaginaryPart(exploreGameController);
//
//    this.getChildren().addAll(realPartLabel, sliderRealPart, sliderImaginaryPart, imaginaryPartLabel);
//    this.setPadding(new javafx.geometry.Insets(10));
//
//    this.setSpacing(50);
//    this.setAlignment(Pos.BOTTOM_CENTER);
//    this.getStyleClass().add("top-bottom-bar");
//  }

//  public void setSliderVisibility(boolean isVisible) {
//    this.getChildren().forEach(node -> {
//      if (node instanceof Slider) {
//        node.setVisible(isVisible);
//      }
//    });
//
//  }

  private void sliderInfoVisability(boolean isVisible) {
    realPartLabel.setVisible(isVisible);
    imaginaryPartLabel.setVisible(isVisible);
    sliderRealPart.setVisible(isVisible);
    sliderImaginaryPart.setVisible(isVisible);
  }
}
