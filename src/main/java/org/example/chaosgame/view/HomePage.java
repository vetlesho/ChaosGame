package org.example.chaosgame.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.chaosgame.controller.PageController;
import org.example.chaosgame.view.components.ChooseGameButton;
import org.example.chaosgame.view.components.ChooseGameButton;
import org.example.chaosgame.view.components.GameButton;
import org.example.chaosgame.view.components.GameHeader;

public class HomePage extends VBox {
  public HomePage(PageController pageController) {
    Text header = new GameHeader("Welcome to ChaosGame");
    Button chaosGameButton = new ChooseGameButton("Chaos Game");
    Button exploreGameButton = new ChooseGameButton("Explore Game");
    Button exitButton = new ChooseGameButton("Exit");

    setAlignment(Pos.CENTER);
    setSpacing(20);

    chaosGameButton.setOnAction(e -> pageController.goToPage("chaos"));
    exploreGameButton.setOnAction(e -> pageController.goToPage("explore"));
    exitButton.setOnAction(e -> pageController.exitGame());

    getChildren().addAll(header, chaosGameButton, exploreGameButton, exitButton);
  }
}
