package org.example.chaosgame.view.components;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

public class CreateJuliaDialog extends Dialog<Pair<Double, Double>> {
  public CreateJuliaDialog() {
    this.setTitle("Create Julia fractal");
    this.setHeaderText("Enter the real and imaginary part of the constant c.");

    ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
    this.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

    HBox content = new HBox();
    TextField realPart = new TextField();
    realPart.setPromptText("Real part");
    TextField imaginaryPart = new TextField();
    imaginaryPart.setPromptText("Imaginary part");

    content.getChildren().addAll(realPart, imaginaryPart);
    this.getDialogPane().setContent(content);

    this.setResultConverter(dialogButton -> {
      if (dialogButton == createButtonType) {
        try {
          double real = Double.parseDouble(realPart.getText());
          double imaginary = Double.parseDouble(imaginaryPart.getText());

          if (real < -1 || real > 1 || imaginary < -1 || imaginary > 1) {
            throw new NumberFormatException();
          }

          return new Pair<>(real, imaginary);
        } catch (NumberFormatException ex) {
          return null;
        }
      }
      return null;
    });
  }

  public void showInvalidInputDialog() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error Dialog");
    alert.setHeaderText("Invalid Input");
    alert.setContentText("Please enter a double between -1 and 1. No letters are allowed.");
    alert.showAndWait();
  }
}
