package org.example.chaosgame.view.components;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

/**
 * Class for the CreateJuliaPane, extends HBox.
 */
public class CreateJuliaPane extends HBox {
  private final TextField realPart;
  private final TextField imaginaryPart;

  /**
   * Constructor for the CreateJuliaPane.
   *
   * <p>Creates a pane for creating Julia values.
   */
  public CreateJuliaPane() {
    this.realPart = new TextField();
    this.realPart.setPromptText("Real part");
    this.realPart.setPrefWidth(75);

    this.imaginaryPart = new TextField();
    this.imaginaryPart.setPromptText("Imaginary part");
    this.imaginaryPart.setPrefWidth(75);

    this.getChildren().addAll(realPart, imaginaryPart);
  }

  /**
   * Get the result of the Julia value creation.
   *
   * @return the real and imaginary part of the Julia value
   */
  public Pair<String, String> getResult() {
    return new Pair<>(realPart.getText(), imaginaryPart.getText());
  }
}