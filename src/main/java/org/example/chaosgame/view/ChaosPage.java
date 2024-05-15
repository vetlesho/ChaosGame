package org.example.chaosgame.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.model.chaos.*;
import org.example.chaosgame.view.components.*;

public class ChaosPage extends GamePage {
  public ChaosPage(ChaosGameController chaosGameController) {
    super();
    VBox sidebar = new SideBar(chaosGameController);
    Button homeButton = createHomeButton(event -> chaosGameController.homeButtonClicked());
    this.getChildren().addAll(gc.getCanvas(), sidebar, homeButton);
  }
}