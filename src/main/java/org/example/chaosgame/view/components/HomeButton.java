package org.example.chaosgame.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.example.chaosgame.controller.ChaosGameController;

public class HomeButton extends Button {
  public HomeButton(){
    super("Home");
    StackPane.setAlignment(this, Pos.TOP_LEFT);
    StackPane.setMargin(this, new Insets(5));
  }
}
