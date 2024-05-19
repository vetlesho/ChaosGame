package org.example.chaosgame.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.model.chaos.ChaosCanvas;
import org.example.chaosgame.view.components.*;

public class ChaosPage extends GamePage {
  private final BottomBar bottomBar;

  public ChaosPage(ChaosGameController chaosGameController) {
    super();
    SideBar sidebar = new SideBar(chaosGameController);
    Button homeButton = createHomeButton(event -> chaosGameController.homeButtonClicked());
    this.bottomBar = new BottomBar(chaosGameController);

    this.setRight(sidebar);
    this.setBottom(bottomBar);
    this.setTop(homeButton);
    this.setLeft(gc.getCanvas());
  }

  public void setSliderVisibility(boolean isVisible) {
    bottomBar.setSliderVisibility(isVisible);
  }

  public GraphicsContext getGraphicsContex() {
    return gc;
  }
}