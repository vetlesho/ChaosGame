package org.example.chaosgame.view;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import org.example.chaosgame.controller.ExploreGameController;
import org.example.chaosgame.view.components.SideBar;

public class ExplorePage extends GamePage {

  private final SideBar sidebar;
  public ExplorePage(ExploreGameController exploreGameController) {
    super();
    this.sidebar = new SideBar(exploreGameController);
    Button homeButton = createHomeButton(event -> exploreGameController.homeButtonClicked());
    exploreGameController.setCanvas(gc.getCanvas());

    this.getChildren().addAll(gc.getCanvas(), sidebar, homeButton);
    this.setOnScroll(event -> {
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
