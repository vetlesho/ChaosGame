package org.example.chaosgame.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.chaosgame.Main;
import org.example.chaosgame.controller.MainController;

public class HomePage extends VBox {
  private final Text header;
  private final Button chaosGameButton;
  private final Button exploreGameButton;


  public HomePage(MainController mainController) {
    header = new Text("Welcome to Chaos Game");
    chaosGameButton = new Button("Chaos Game");
    exploreGameButton = new Button("Explore Game");
    chaosGameButton.setOnAction(e -> mainController.chaosGameButtonClicked());
    exploreGameButton.setOnAction(e -> mainController.exploreGameButtonClicked());
    getChildren().addAll(header, chaosGameButton, exploreGameButton);
  }

  public Button getChaosGameButton() {
    return chaosGameButton;
  }

  public Button getExploreGameButton() {
    return exploreGameButton;
  }

}
