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
    this.bottomBar = new BottomBar(chaosGameController);
    SideBar sideBar = new SideBar(chaosGameController);

    this.setTop(topBar);
    this.setRight(sideBar);
    this.setBottom(bottomBar);
    this.setCenter(gc.getCanvas());
  }

  public void updateInformation(Transform2D transformation, int steps, Vector2D min, Vector2D max) {
    topBar.updateInformation(transformation, steps, min, max);
    bottomBar.updateInformation(transformation);
  }

  public GraphicsContext getGraphicsContex() {
    return gc;
  }
}