package org.example.chaosgame.view;

import org.example.chaosgame.controller.ExploreGameController;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.Transform2D;
import org.example.chaosgame.view.components.BottomBar;
import org.example.chaosgame.view.components.SideBar;
import org.example.chaosgame.view.components.TopBar;

/**
 * Class for the ExplorePage, extends GamePage.
 * The ExplorePage is used for displaying the exploreGame.
 */
public class ExplorePage extends GamePage {
  private final BottomBar bottomBar;
  private final TopBar topBar;

  /**
   * Constructor for the ExplorePage.
   * Initializes the top bar, bottom bar and sidebar components.
   *
   * @param exploreGameController the explore game controller
   */
  public ExplorePage(ExploreGameController exploreGameController) {
    super();
    this.setStyle("-fx-background-color: black;");
    SideBar sidebar = new SideBar(exploreGameController);
    sidebar.setStyle("-fx-background-color: black;");
    this.topBar = new TopBar(exploreGameController);
    this.topBar.setStyle("-fx-background-color: black;");
    this.bottomBar = new BottomBar(exploreGameController);
    this.bottomBar.setStyle("-fx-background-color: black;");
    exploreGameController.setCanvas(this.gc.getCanvas());
    this.setCenter(this.gc.getCanvas());
    this.setRight(sidebar);
    this.setBottom(bottomBar);
    this.setTop(topBar);

    gc.getCanvas().setOnScroll(exploreGameController::onScroll);
    this.setOnMousePressed(exploreGameController::mousePressed);
    this.setOnMouseDragged(exploreGameController::mouseDragged);
    this.setOnMouseReleased(exploreGameController::mouseReleased);
  }

  /**
   * Calls the updateTopBar and updateBottomBar methods in the ExplorePage.
   *
   * @param transformation the transformation
   * @param min            the min coordinates
   * @param max            the max coordinates
   */
  public void updateInformation(Transform2D transformation, Vector2D min, Vector2D max) {
    topBar.updateTopBar(min, max);
    bottomBar.updateBottomBar(transformation);
  }
}
