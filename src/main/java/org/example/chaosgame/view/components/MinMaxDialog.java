package org.example.chaosgame.view.components;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class MinMaxDialog extends Dialog<Pair<String, String>> {
  private final TextField minXField;
  private final TextField minYField;
  private final TextField maxXField;
  private final TextField maxYField;

  public MinMaxDialog() {
    setTitle("Set Min/Max Coordinates");
    setHeaderText("Please enter the min and max coordinates:");

    ButtonType okButtonType = new ButtonType("OK");
    getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, okButtonType);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);

    minXField = new TextField();
    minXField.setPromptText("Min X");
    minYField = new TextField();
    minYField.setPromptText("Min Y");

    maxXField = new TextField();
    maxXField.setPromptText("Max X");
    maxYField = new TextField();
    maxYField.setPromptText("Max Y");

    grid.add(minXField, 0, 0);
    grid.add(minYField, 1, 0);
    grid.add(maxXField, 0, 1);
    grid.add(maxYField, 1, 1);

    getDialogPane().setContent(grid);

    setResultConverter(dialogButton -> {
      if (dialogButton == okButtonType) {
        String minX = minXField.getText();
        String minY = minYField.getText();
        String maxX = maxXField.getText();
        String maxY = maxYField.getText();
        return new Pair<>(minX + "," + minY, maxX + "," + maxY);
      }
      return null;
    });
  }
}