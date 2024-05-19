package org.example.chaosgame.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.model.chaos.ChaosCanvas;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.JuliaTransform;
import org.example.chaosgame.model.transformations.Transform2D;
import org.example.chaosgame.view.components.*;

import java.util.List;
import java.util.Vector;

public class ChaosPage extends GamePage {
  private final TopBar topBar;
  private final BottomBar bottomBar;

  public ChaosPage(ChaosGameController chaosGameController) {
    super();
    this.topBar = new TopBar(chaosGameController);
    this.topBar.getStyleClass().add("chaos-text");
    this.bottomBar = new BottomBar(chaosGameController);
    this.bottomBar.getStyleClass().add("chaos-text");
    SideBar sideBar = new SideBar(chaosGameController);

    this.setCenter(gc.getCanvas());
    this.setTop(topBar);
    this.setRight(sideBar);
    this.setBottom(bottomBar);
  }

  public void updateInformation(Transform2D transformation, int steps, Vector2D min, Vector2D max) {
    topBar.updateTotalTopBar(transformation, steps, min, max);
    bottomBar.updateBottomBar(transformation);
  }

  public GraphicsContext getGraphicsContext() {
    return gc;
  }
}