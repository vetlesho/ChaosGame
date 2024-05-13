package org.example.chaosgame.view;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.chaosgame.controller.PageController;

public class HomePage extends VBox {
  private final Text header;
  private final Button chaosGameButton;
  private final Button exploreGameButton;


  public HomePage(StackPane mainPane, PageController pageController) {
    header = new Text("Welcome to Chaos Game");
    chaosGameButton = new Button("Chaos Game");
    exploreGameButton = new Button("Explore Game");
    chaosGameButton.setOnAction(e -> pageController.chaosGameButtonClicked());
    exploreGameButton.setOnAction(e -> pageController.exploreGameButtonClicked());
    getChildren().addAll(header, chaosGameButton, exploreGameButton);
  }
}
