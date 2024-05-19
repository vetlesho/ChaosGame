package org.example.chaosgame.view;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import org.example.chaosgame.controller.ExploreGameController;
import org.example.chaosgame.view.components.BottomBar;
import org.example.chaosgame.view.components.SideBar;

public class ExplorePage extends GamePage {

  private final SideBar sidebar;
  private final BottomBar bottomBar;
  private final Button homeButton;
  public ExplorePage(ExploreGameController exploreGameController) {
    super();
    this.setStyle("-fx-background-color: black;");
    this.sidebar = new SideBar(exploreGameController);
    this.homeButton =  createHomeButton(event -> exploreGameController.homeButtonClicked());
    exploreGameController.setCanvas(this.gc.getCanvas());
    this.bottomBar = new BottomBar(exploreGameController);

    this.setLeft(this.gc.getCanvas());
    this.setRight(sidebar);
    this.setBottom(bottomBar);
    this.setTop(homeButton);
//    this.sidebar.toFront();
//    this.bottomBar.toFront();

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
}
