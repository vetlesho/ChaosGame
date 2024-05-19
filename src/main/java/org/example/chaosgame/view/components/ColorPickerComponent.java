package org.example.chaosgame.view.components;

import java.util.function.Consumer;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

/**
 * Color picker component using ColorPicker from JavaFX.
 */
public class ColorPickerComponent extends ColorPicker {
  /**
   * Constructor for the color picker component.
   *
   * @param colorChangeHandler the handler for the color change event
   */
  public ColorPickerComponent(Consumer<Color> colorChangeHandler) {
    super();
    this.getStyleClass().add("color-picker");
    this.setMaxWidth(200);
    this.setMinWidth(200);
    this.setOnAction(event -> colorChangeHandler.accept(this.getValue()));
  }
}
