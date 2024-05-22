package org.idatt2003.view.components;

import java.util.function.Consumer;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

/**
 * Class for the color picker component, extends ColorPicker.
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
    this.setMaxWidth(180);
    this.setMinWidth(180);
    this.setMinHeight(40);
    this.setMaxHeight(40);
    this.setOnAction(event -> colorChangeHandler.accept(this.getValue()));
  }
}
