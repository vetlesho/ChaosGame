package org.example.chaosgame.view.components;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.example.chaosgame.model.linalg.Matrix2x2;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.AffineTransform2D;

/**
 * Pane for creating affine transformations.
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
   * Returns the list of affine transformations created in the pane.
   *
   * @return The list of affine transformations.
   */
  public List<AffineTransform2D> getResult() {
    List<AffineTransform2D> transformations = new ArrayList<>();
    for (List<TextField> fields : lines) {
      double a = Double.parseDouble(fields.get(0).getText());
      double b = Double.parseDouble(fields.get(1).getText());
      double c = Double.parseDouble(fields.get(2).getText());
      double d = Double.parseDouble(fields.get(3).getText());
      double x = Double.parseDouble(fields.get(4).getText());
      double y = Double.parseDouble(fields.get(5).getText());

      transformations.add(new AffineTransform2D(new Matrix2x2(a, b, c, d), new Vector2D(x, y)));
    }
    return transformations;
  }
}