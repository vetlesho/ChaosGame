package org.example.chaosgame.view;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.chaosgame.controller.ExploreGameController;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.Transform2D;
import org.example.chaosgame.view.components.BottomBar;
import org.example.chaosgame.view.components.SideBar;
import org.example.chaosgame.view.components.TopBar;

public class ExplorePage extends GamePage {

  private final SideBar sidebar;
  private final BottomBar bottomBar;
  private final TopBar topBar;
  public ExplorePage(ExploreGameController exploreGameController) {
    super();
    this.setStyle("-fx-background-color: black;");
    this.sidebar = new SideBar(exploreGameController);
    this.sidebar.setStyle("-fx-background-color: black;");
    this.topBar = new TopBar(exploreGameController);
    this.topBar.setStyle("-fx-background-color: black;");
    this.bottomBar = new BottomBar(exploreGameController);
    this.bottomBar.setStyle("-fx-background-color: black;");
    exploreGameController.setCanvas(this.gc.getCanvas());
    this.setCenter(this.gc.getCanvas());
    this.setRight(sidebar);
    this.setBottom(bottomBar);
    this.setTop(topBar);

    gc.getCanvas().setOnScroll(event -> {
      try{
      exploreGameController.onScroll(event);
    } catch (Exception e) {
      exploreGameController.resetImage();
    }
    });
    this.setOnMousePressed(exploreGameController::mousePressed);
    this.setOnMouseDragged(exploreGameController::mouseDragged);
    this.setOnMouseReleased(exploreGameController::mouseReleased);
  }

  public void updateInformation(Transform2D transformation, Vector2D min, Vector2D max) {
    topBar.updateTopBar(min, max);
    bottomBar.updateBottomBar(transformation);
  }
}
