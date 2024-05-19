package org.example.chaosgame.view.components;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CreateFractalDialog extends Dialog<Object> {
  public CreateFractalDialog() {
    this.setTitle("Create Fractal");
    this.setHeaderText("Select the type of fractal to create.");

    ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
    this.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

    ToggleGroup group = new ToggleGroup();

    HBox buttonBox = new HBox();
    RadioButton affineButton = new RadioButton("Affine");
    affineButton.setToggleGroup(group);
    affineButton.setSelected(true);

    RadioButton juliaButton = new RadioButton("Julia");
    juliaButton.setToggleGroup(group);

    buttonBox.setSpacing(30);
    buttonBox.getChildren().addAll(affineButton, juliaButton);

    CreateAffinePane affinePane = new CreateAffinePane();
    CreateJuliaPane juliaPane = new CreateJuliaPane();

    StackPane content = new StackPane();
    content.setPadding(new Insets(10));
    content.setMinHeight(200);
    content.setMinWidth(400);
    content.getChildren().addAll(affinePane, juliaPane);

    affinePane.visibleProperty().bind(affineButton.selectedProperty());
    juliaPane.visibleProperty().bind(juliaButton.selectedProperty());

    VBox layout = new VBox();
    layout.getChildren().addAll(buttonBox, content);

    this.getDialogPane().setContent(layout);

    this.setResultConverter(dialogButton -> {
      if (dialogButton == createButtonType) {
        if (affineButton.isSelected()) {
          return affinePane.getResult();
        } else if (juliaButton.isSelected()) {
          return juliaPane.getResult();
        }
      }
      return null;
    });
  }
}