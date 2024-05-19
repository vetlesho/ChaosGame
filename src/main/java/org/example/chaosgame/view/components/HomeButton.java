package org.example.chaosgame.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.example.chaosgame.controller.ChaosGameController;

import java.util.Objects;

public class HomeButton extends Button {
  public HomeButton(){
    super();
    String filePath = Objects.requireNonNull(getClass().getResource("/White-home-icon.png")).toString();
    ImageView homeIcon = new ImageView(new Image(filePath));
    homeIcon.setFitHeight(24);
    homeIcon.setFitWidth(24);
    this.setGraphic(homeIcon);
    StackPane.setAlignment(this, Pos.TOP_LEFT);
    StackPane.setMargin(this, new Insets(5));
  }
}
