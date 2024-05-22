package org.idatt2003.view.components;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Class for the CreateAffinePane, extends GridPane.
 */
public class CreateAffinePane extends GridPane {
  private final List<List<TextField>> lines;
  private final List<HBox> matrixBoxes;
  private final List<HBox> vectorBoxes;

  /**
   * Constructor for the CreateAffinePane.
   *
   * <p>Creates a pane for creating affine transformations.
   */
  public CreateAffinePane() {
    this.lines = new ArrayList<>();
    this.matrixBoxes = new ArrayList<>();
    this.vectorBoxes = new ArrayList<>();
    this.setHeight(600);
    addLine();

    Button addButton = new Button("Add line");
    addButton.setOnAction(event -> {
      if (lines.size() < 4) {
        addLine();
      }
    });

    Button removeButton = new Button("Remove line");
    removeButton.setOnAction(event -> {
      if (!lines.isEmpty()) {
        removeLine();
      }
    });

    HBox buttonBox = new HBox();
    buttonBox.getChildren().addAll(addButton, removeButton);
    this.add(buttonBox, 0, 4);
  }

  /**
   * Adds a line to the pane.
   */
  private void addLine() {
    char[] labels = {'a', 'b', 'c', 'd', 'x', 'y'};

    List<TextField> fields = new ArrayList<>();
    HBox matrixBox = new HBox();
    HBox vectorBox = new HBox();

    for (int j = 0; j < 4; j++) {
      TextField field = new TextField();
      field.setPromptText(String.valueOf(labels[j]));
      field.setPrefWidth(75);
      fields.add(field);
      matrixBox.getChildren().add(field);
    }

    for (int j = 4; j < 6; j++) {
      TextField field = new TextField();
      field.setPromptText(String.valueOf(labels[j]));
      field.setPrefWidth(75);
      fields.add(field);
      vectorBox.getChildren().add(field);
    }

    lines.add(fields);
    matrixBox.setPadding(new Insets(0, 30, 0, 0));
    matrixBoxes.add(matrixBox);
    vectorBoxes.add(vectorBox);
    this.add(matrixBox, 0, lines.size() - 1);
    this.add(vectorBox, 1, lines.size() - 1);
  }

  /**
   * Removes the last line from the pane.
   */
  private void removeLine() {
    List<TextField> lastLine = lines.removeLast();
    for (TextField field : lastLine) {
      this.getChildren().remove(field);
    }

    HBox lastMatrixBox = matrixBoxes.removeLast();
    this.getChildren().remove(lastMatrixBox);

    HBox lastVectorBox = vectorBoxes.removeLast();
    this.getChildren().remove(lastVectorBox);
  }

  /**
   * Get the result of the affine transformation creation.
   *
   * @return the matrix and vector of the affine transformation
   */
  public List<List<String>> getResult() {
    List<List<String>> result = new ArrayList<>();
    for (List<TextField> line : lines) {
      List<String> lineResult = new ArrayList<>();
      for (TextField field : line) {
        lineResult.add(field.getText());
      }
      result.add(lineResult);
    }
    return result;
  }
}