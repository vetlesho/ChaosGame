package org.idatt2003.view.components;

import java.util.List;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Class for the MinMaxDialog, extends Dialog.
 * The dialog has fields for the min and max x and y coordinates.
 */
public class MinMaxDialog extends Dialog<List<String>> {
  private final TextField xMinField;
  private final TextField yMinField;
  private final TextField xMaxField;
  private final TextField yMaxField;

  /**
   * Constructor for the MinMaxDialog.
   * Creates a dialog for setting the min and max coordinates.
   */
  public MinMaxDialog() {
    setTitle("Set Min/Max Coordinates");
    setHeaderText("Please enter the min and max coordinates:");

    ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
    this.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);

    xMinField = new TextField();
    xMinField.setPromptText("Min X");
    yMinField = new TextField();
    yMinField.setPromptText("Min Y");

    xMaxField = new TextField();
    xMaxField.setPromptText("Max X");
    yMaxField = new TextField();
    yMaxField.setPromptText("Max Y");

    grid.add(xMinField, 0, 0);
    grid.add(yMinField, 1, 0);
    grid.add(xMaxField, 0, 1);
    grid.add(yMaxField, 1, 1);

    getDialogPane().setContent(grid);

    setResultConverter(dialogButton -> {
      if (dialogButton == saveButtonType) {
        String minX = xMinField.getText();
        String minY = yMinField.getText();
        String maxX = xMaxField.getText();
        String maxY = yMaxField.getText();
        return List.of(minX, minY, maxX, maxY);
      }
      return null;
    });
  }
}