package org.example.chaosgame.view.components;

import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.util.function.Consumer;

public class ColorPickerComponent extends ColorPicker {
  public ColorPickerComponent(Consumer<Color> colorChangeHandler) {
    super();
    this.getStyleClass().add("color-picker");
    this.setMaxWidth(200);
    this.setMinWidth(200);
    this.setOnAction(event -> colorChangeHandler.accept(this.getValue() ));
  }
}
