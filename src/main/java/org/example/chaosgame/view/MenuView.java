package org.example.chaosgame.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class MenuView {
  private final HBox menuBar = new HBox();;
  private final Button chaosButton = shadedButton("Chaos Game");

  private final Button exploreButton = shadedButton("Explore Game");
  public static final String MENU_BUTTON_STYLE = "-fx-text-fill: white; -fx-font: 22 arial; -fx-background-color: #0d2d3a; -fx-border-radius: 20; -fx-background-insets: 0; -fx-background-radius: 20; -fx-border-color: black; -fx-border-width: 2;";


  public MenuView() {

    chaosButton.setPadding(new Insets(10, 20, 10, 20));
    menuBar.setAlignment(Pos.CENTER_LEFT);

    menuBar.getChildren().addAll(chaosButton, exploreButton);
    menuBar.setPrefHeight(60);
    menuBar.setSpacing(10);
    menuBar.setPadding(new Insets(10));
  }
  public HBox getMenuBar() {
    return menuBar;
  }
  public Button getChaosButton() {
    return chaosButton;
  }
  public Button getExploreButton() {
    return exploreButton;
  }

  public static Button shadedButton(String buttonName) {

    ColorAdjust shade = new ColorAdjust();
    shade.setBrightness(0.4);

    Button button = new Button(buttonName);
    button.setMaxHeight(50);
    button.setStyle(MENU_BUTTON_STYLE);
    button.addEventHandler(MouseEvent.MOUSE_ENTERED,
            e -> button.setEffect(shade));
    button.addEventHandler(MouseEvent.MOUSE_EXITED,
            e -> button.setEffect(null));
    button.setAlignment(Pos.CENTER);
    return button;
  }
}
