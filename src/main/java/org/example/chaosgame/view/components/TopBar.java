package org.example.chaosgame.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.controller.ExploreGameController;
import org.example.chaosgame.controller.observer.GameController;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.Transform2D;

public class TopBar extends HBox {
  private final Label gameInfo;
  private final Label stepsLabel;
  private final Label coordinatesLabel;

  public TopBar(GameController gameController) {
    super();
    this.gameInfo = new Label();
    this.stepsLabel = new Label();
    this.coordinatesLabel = new Label();

    Button homeButton = new HomeButton();
    homeButton.setOnAction(event -> gameController.homeButtonClicked());

    this.getChildren().addAll(homeButton, gameInfo, coordinatesLabel, stepsLabel);
    this.setPadding(new javafx.geometry.Insets(10));

    this.setSpacing(50);
    this.setAlignment(Pos.CENTER_LEFT);
    this.getStyleClass().add("top-bottom-bar");
  }

  public void updateTopBar(Transform2D first, int totalSteps, Vector2D min, Vector2D max) {
    gameInfo.setText("Transformation: " + first.getClass().getSimpleName());
    coordinatesLabel.setText("Min-Coordinate: " + min.getX() + ", " + min.getY() +
            ". Max-Coordinate: " + max.getX() + ", " + max.getY());
    stepsLabel.setText("Total steps: " + totalSteps);
  }

  public void updateTopBar(Vector2D min, Vector2D max) {
    coordinatesLabel.setText("Min-Coordinate: " + (double) Math.round(min.getX() * 100) / 100 + ", " + (double) Math.round(min.getY() * 100) / 100 +
            ". Max-Coordinate: " + (double) Math.round(max.getX() * 100) / 100 + ", " + (double) Math.round(max.getY() * 100) / 100);
  }

  public void setTopBarStyle(String text) {
    coordinatesLabel.getStyleClass().add(text);
  }
}
