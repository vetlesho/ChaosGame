package org.example.chaosgame.view.components;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

public class CreateJuliaPane extends HBox {
  private final TextField realPart;
  private final TextField imaginaryPart;

  public CreateJuliaPane() {
    this.realPart = new TextField();
    this.realPart.setPromptText("Real part");
    this.realPart.setPrefWidth(75);

    this.imaginaryPart = new TextField();
    this.imaginaryPart.setPromptText("Imaginary part");
    this.imaginaryPart.setPrefWidth(75);

    this.getChildren().addAll(realPart, imaginaryPart);
  }

  public Pair<String, String> getResult() {
    return new Pair<>(realPart.getText(), imaginaryPart.getText());
  }
}