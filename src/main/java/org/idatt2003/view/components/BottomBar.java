package org.idatt2003.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.idatt2003.controller.interfaces.GameController;
import org.idatt2003.model.linalg.Complex;
import org.idatt2003.model.transformations.ExploreJulia;
import org.idatt2003.model.transformations.JuliaTransform;
import org.idatt2003.model.transformations.Transform2D;


/**
 * Bottom bar of the GUI, extends HBox.
 * Contains sliders and labels for the real and imaginary part of the Julia value.
 */
public class BottomBar extends HBox {
  private final Label realPartLabel;
  private final Label imaginaryPartLabel;
  private final SliderRealPart sliderRealPart;
  private final SliderImaginaryPart sliderImaginaryPart;

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

  /**
   * Check if the transformation is a Julia transformation or an ExploreJulia transformation.
   * Else, hide the sliders and labels.
   *
   * @param transformation the transformation to update the bottom bar with
   */
  public void updateBottomBar(Transform2D transformation) {
    if (transformation instanceof JuliaTransform juliaTransform) {
      juliaInformation(juliaTransform.getComplex());
    } else if (transformation instanceof ExploreJulia exploreJulia) {
      juliaInformation(exploreJulia.getComplex());
    } else {
      sliderInfoVisibility(false);
    }
  }

  /**
   * Update the bottom bar with the Julia value.
   *
   * @param complex the complex number to update the bottom bar with
   */
  private void juliaInformation(Complex complex) {
    sliderInfoVisibility(true);
    sliderRealPart.setValue(complex.getX());
    sliderImaginaryPart.setValue(complex.getY());
    realPartLabel.setText("Real Part: "
            + (double) Math.round(complex.getX() * 100) / 100);
    imaginaryPartLabel.setText("Imaginary Part: "
            + (double) Math.round(complex.getY() * 100) / 100);
  }

  /**
   * Set the visibility of the sliders and labels.
   *
   * @param isVisible the visibility of the sliders and labels
   */
  private void sliderInfoVisibility(boolean isVisible) {
    realPartLabel.setVisible(isVisible);
    imaginaryPartLabel.setVisible(isVisible);
    sliderRealPart.setVisible(isVisible);
    sliderImaginaryPart.setVisible(isVisible);
  }
}
