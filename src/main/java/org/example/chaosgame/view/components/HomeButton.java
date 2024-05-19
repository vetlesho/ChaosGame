package org.example.chaosgame.view.components;

import java.util.Objects;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class for the home button, extends Button.
 * The button is used for navigating to the home screen.
 */
public class HomeButton extends Button {
  /**
   * Constructor for the HomeButton.
   * Sets the icon for the home button.
   */
  public HomeButton() {
    super();
    String filePath = Objects.requireNonNull(getClass()
            .getResource("/White-home-icon.png")).toString();
    ImageView homeIcon = new ImageView(new Image(filePath));
    homeIcon.setFitHeight(24);
    homeIcon.setFitWidth(24);
    this.setGraphic(homeIcon);
  }
}
