package org.example.chaosgame.view.components;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Class for the CreateFractalDialog, extends Dialog.
 * Lets the user create their own fractal.
 */
public class CreateFractalDialog extends Dialog<Object> {
  /**
   * Constructor for the CreateFractalDialog.
   *
   * <p>Creates a dialog for the user to create their own fractal.
   */
  public CreateFractalDialog() {
    this.setTitle("Create Fractal");
    this.setHeaderText("Select the type of fractal to create.");

    ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
    this.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

    ToggleGroup group = new ToggleGroup();
    RadioButton affineButton = new RadioButton("Affine");

    affineButton.setToggleGroup(group);
    affineButton.setSelected(true);

    RadioButton juliaButton = new RadioButton("Julia");
    juliaButton.setToggleGroup(group);

    HBox buttonBox = new HBox();
    buttonBox.setSpacing(30);
    buttonBox.getChildren().addAll(affineButton, juliaButton);

    StackPane content = new StackPane();
    content.setPadding(new Insets(10));
    content.setMinHeight(200);
    content.setMinWidth(400);

    CreateAffinePane affinePane = new CreateAffinePane();
    CreateJuliaPane juliaPane = new CreateJuliaPane();
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