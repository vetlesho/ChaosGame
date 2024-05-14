package org.example.chaosgame.view;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.chaosgame.controller.PageController;

public class HomePage extends VBox {
  public HomePage(PageController pageController) {
    Text header = new Text("Welcome to Chaos Game");
    Button chaosGameButton = new Button("Chaos Game");
    Button exploreGameButton = new Button("Explore Game");

    chaosGameButton.setOnAction(e -> pageController.goToPage("chaos"));
    exploreGameButton.setOnAction(e -> pageController.goToPage("explore"));

    getChildren().addAll(header, chaosGameButton, exploreGameButton);
  }
}
