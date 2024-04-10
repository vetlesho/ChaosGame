package org.example.chaosgame.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class MenuView {
  private final HBox menuBar = new HBox();;
  private final Button chaosButton = new Button("Chaos Game");
  private final Button exploreButton = new Button("Explore Game");


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

}
