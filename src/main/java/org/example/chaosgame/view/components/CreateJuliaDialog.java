package org.example.chaosgame.view.components;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

public class CreateJuliaDialog extends Dialog<Pair<String, String>> {
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
        return new Pair<>(realPart.getText(), imaginaryPart.getText());
      }
      return null;
    });
  }
}
