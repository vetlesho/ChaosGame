package org.example.chaosgame.view.components;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.example.chaosgame.model.linalg.Matrix2x2;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.AffineTransform2D;

import java.util.ArrayList;
import java.util.List;


public class CreateAffineDialog extends Dialog<List<AffineTransform2D>> {
  public CreateAffineDialog() {
    this.setTitle("Create Affine Transformations");
    this.setHeaderText("Enter the values for the affine transformation matrices");

    ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
    this.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

    GridPane content = new GridPane();
    List<TextField> fields = new ArrayList<>();

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 6; j++) {
        TextField field = new TextField();
        field.setPromptText("Value " + (j + 1));
        fields.add(field);
        content.add(field, j, i);
      }
    }

    this.getDialogPane().setContent(content);

    this.setResultConverter(dialogButton -> {
      if (dialogButton == createButtonType) {
        try {
          List<AffineTransform2D> transformations = new ArrayList<>();
          for (int i = 0; i < 3; i++) {
            double a = Double.parseDouble(fields.get(i * 6).getText());
            double b = Double.parseDouble(fields.get(i * 6 + 1).getText());
            double c = Double.parseDouble(fields.get(i * 6 + 2).getText());
            double d = Double.parseDouble(fields.get(i * 6 + 3).getText());
            double x = Double.parseDouble(fields.get(i * 6 + 4).getText());
            double y = Double.parseDouble(fields.get(i * 6 + 5).getText());

            transformations.add(new AffineTransform2D(new Matrix2x2(a, b, c, d), new Vector2D(x, y)));
          }
          return transformations;
        } catch (NumberFormatException ex) {
          return null;
        }
      }
      return null;
    });
  }
}
