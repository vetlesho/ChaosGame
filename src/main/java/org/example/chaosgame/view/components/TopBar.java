package org.example.chaosgame.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.Transform2D;

public class TopBar extends HBox {
  private final Label gameInfo;
  private final Label stepsLabel;
  private final Label coordinatesLabel;

  public TopBar(ChaosGameController chaosGameController) {
    super();
    this.gameInfo = new Label();
    this.stepsLabel = new Label();
    this.coordinatesLabel = new Label();

    Button homeButton = new HomeButton();
    homeButton.setOnAction(event -> chaosGameController.homeButtonClicked());

    this.getChildren().addAll(homeButton, gameInfo, coordinatesLabel, stepsLabel);
    this.setPadding(new javafx.geometry.Insets(10));

    this.setSpacing(50);
    this.setAlignment(Pos.CENTER_LEFT);
  }

  public void updateInformation(Transform2D first, int totalSteps, Vector2D min, Vector2D max) {
    gameInfo.setText("Transformation: " + first.getClass().getSimpleName());
    coordinatesLabel.setText("Min-Coordinate: " + min.getX() + ", " + min.getY() +
            ". Max-Coordinate: " + max.getX() + ", " + max.getY());
    stepsLabel.setText("Total steps: " + totalSteps);
  }
}
