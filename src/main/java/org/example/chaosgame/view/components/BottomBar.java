package org.example.chaosgame.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.model.transformations.JuliaTransform;
import org.example.chaosgame.model.transformations.Transform2D;

public class BottomBar extends HBox {
  private final Label realPartLabel;
  private final Label imaginaryPartLabel;
  private final SliderRealPart sliderRealPart;
  private final SliderImaginaryPart sliderImaginaryPart;

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
}
