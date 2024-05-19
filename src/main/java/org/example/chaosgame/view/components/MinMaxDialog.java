package org.example.chaosgame.view.components;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.example.chaosgame.model.linalg.Vector2D;

public class MinMaxDialog extends Dialog<Pair<Vector2D, Vector2D>> {
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
        Vector2D min = new Vector2D(Double.parseDouble(minXField.getText()), Double.parseDouble(minYField.getText()));
        Vector2D max = new Vector2D(Double.parseDouble(maxXField.getText()), Double.parseDouble(maxYField.getText()));
        return new Pair<>(min, max);
      }
      return null;
    });
  }
}