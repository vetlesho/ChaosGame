package org.example.chaosgame.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.model.chaos.*;
import org.example.chaosgame.view.components.*;

public class ChaosPage extends GamePage {
  private final VBox sidebar;
  private final Button homeButton;

  public ChaosPage(ChaosGameController chaosGameController) {
    super();
    this.sidebar = new SideBar(chaosGameController);
    this.homeButton = createHomeButton(event -> chaosGameController.homeButtonClicked());
    this.getChildren().addAll(gc.getCanvas(), sidebar, homeButton);
  }
}