package org.idatt2003.view;

import javafx.scene.canvas.GraphicsContext;
import org.idatt2003.controller.ChaosGameController;
import org.idatt2003.model.linalg.Vector2D;
import org.idatt2003.model.transformations.Transform2D;
import org.idatt2003.view.components.BottomBar;
import org.idatt2003.view.components.SideBar;
import org.idatt2003.view.components.TopBar;

/**
 * Class for the ChaosPage, extends GamePage.
 * The ChaosPage is used for displaying the chaosGame.
 */
public class ChaosPage extends GamePage {
  private final TopBar topBar;
  private final BottomBar bottomBar;

  /**
   * Constructor for the ChaosPage.
   * Initializes the top bar, bottom bar and sidebar components.
   *
   * @param chaosGameController the chaos game controller
   */
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

  /**
   * Calls the updateTopBar and updateBottomBar methods in the ChaosPage.
   *
   * @param transformation the transformation
   * @param steps          the steps
   * @param min            the min coordinates
   * @param max            the max coordinates
   */
  public void updateInformation(Transform2D transformation, int steps, Vector2D min, Vector2D max) {
    topBar.updateTotalTopBar(transformation, steps, min, max);
    bottomBar.updateBottomBar(transformation);
  }

  /**
   * Method for getting the GraphicsContext.
   *
   * @return the GraphicsContext
   */
  public GraphicsContext getGraphicsContext() {
    return gc;
  }
}