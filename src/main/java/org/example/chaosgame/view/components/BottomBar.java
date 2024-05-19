package org.example.chaosgame.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import org.example.chaosgame.controller.observer.GameController;
import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.transformations.ExploreJulia;
import org.example.chaosgame.model.transformations.JuliaTransform;
import org.example.chaosgame.model.transformations.Transform2D;


/**
 * Bottom bar of the GUI.
 *
 * <p>Contains sliders and labels for the real and imaginary part of the Julia value.
 */
public class BottomBar extends HBox {
  private Label realPartLabel;
  private Label imaginaryPartLabel;
  private SliderRealPart sliderRealPart;
  private SliderImaginaryPart sliderImaginaryPart;

  /**
   * Constructor for the bottom bar.
   *
   * <p>Initializes the labels and sliders for the real and imaginary part of the Julia value.
   *
   * @param gameController the controller for the chaos game
   */
  public BottomBar(GameController gameController) {
    this.setSpacing(10);
    this.realPartLabel = new Label();
    this.realPartLabel.getStyleClass().add("top-bottom-padding");
    this.imaginaryPartLabel = new Label();
    this.imaginaryPartLabel.getStyleClass().add("top-bottom-padding");
    this.sliderRealPart = new SliderRealPart(gameController);
    this.sliderImaginaryPart = new SliderImaginaryPart(gameController);

    realPartLabel.setMinSize(200, 20);
    imaginaryPartLabel.setMinSize(200, 20);
    this.setAlignment(Pos.CENTER);
    this.getChildren().addAll(realPartLabel, sliderRealPart,
            sliderImaginaryPart, imaginaryPartLabel);

    this.getStyleClass().add("top-bottom-bar");
  }

  public void updateBottomBar(Transform2D transformation) {
    if (transformation instanceof JuliaTransform juliaTransform) {
      juliaInformation(juliaTransform.getComplex());
    } else if (transformation instanceof ExploreJulia exploreJulia) {
      juliaInformation(exploreJulia.getComplex());
    } else {
      sliderInfoVisibility(false);
    }
  }

  private void juliaInformation(Complex complex) {
    sliderInfoVisibility(true);
    sliderRealPart.setValue(complex.getX());
    sliderImaginaryPart.setValue(complex.getY());
    realPartLabel.setText("Real Part: " + (double) Math.round(complex.getX() * 100) / 100);
    imaginaryPartLabel.setText("Imaginary Part: " + (double) Math.round(complex.getY() * 100) / 100);
  }

  private void sliderInfoVisibility(boolean isVisible) {
    realPartLabel.setVisible(isVisible);
    imaginaryPartLabel.setVisible(isVisible);
    sliderRealPart.setVisible(isVisible);
    sliderImaginaryPart.setVisible(isVisible);
  }

  public void setBottomBarStyle(String text) {
    realPartLabel.getStyleClass().add(text);
    imaginaryPartLabel.getStyleClass().add(text);
  }
}
