package org.idatt2003.controller;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import org.idatt2003.controller.interfaces.Observer;
import org.idatt2003.view.HomePage;

/**
 * Controller for the different pages in the application.
 *
 * <p>Implements the Observer interface to listen for changes in the game controllers.
 */
public class PageController implements Observer {
  private final StackPane mainPane;
  private final Map<String, Node> pages = new HashMap<>();
  private final ChaosGameController chaosGameController;
  private final ExploreGameController exploreGameController;
  private final HomePage homePage;

  /**
   * Constructor for the PageController.
   * Initializes the different pages and sets the main pane.
   *
   * @param mainPane              the main pane
   * @param chaosGameController   the chaos game controller
   * @param exploreGameController the explore game controller
   */
  public PageController(StackPane mainPane, ChaosGameController chaosGameController,
                        ExploreGameController exploreGameController) {
    this.mainPane = mainPane;
    this.chaosGameController = chaosGameController;
    this.exploreGameController = exploreGameController;
    HomeController homeController = new HomeController(this);

    this.homePage = homeController.getHomePage();
    this.homePage.setBind(mainPane);
    this.exploreGameController.setBind(mainPane);
    this.chaosGameController.setBind(mainPane);

    initPages(chaosGameController, exploreGameController);
    chaosGameController.registerObserver(this);
    exploreGameController.registerObserver(this);
  }

  /**
   * Initializes the different pages in the application.
   *
   * @param chaosGameController   the chaos game controller
   * @param exploreGameController the explore game controller
   */
  private void initPages(ChaosGameController chaosGameController,
                         ExploreGameController exploreGameController) {
    pages.put("home", homePage);
    pages.put("chaos", chaosGameController.getGamePage());
    pages.put("explore", exploreGameController.getGamePage());
    goToPage("home");
  }

  /**
   * Navigates to the specified page.
   * Removes all children from the main pane and adds the specified page.
   *
   * @param page the page to navigate to
   */
  public void navigateToPage(Node page) {
    mainPane.getChildren().clear();
    mainPane.getChildren().add(page);
  }

  /**
   * Navigates to the specified page.
   *
   * @param page the page to navigate to
   */
  public void goToPage(String page) {
    navigateToPage(pages.get(page));
  }

  /**
   * Exits the application.
   */
  public void exitGame() {
    chaosGameController.removeObserver(this);
    exploreGameController.removeObserver(this);
    System.exit(0);
  }

  /**
   * Updates the page controller.
   * Navigates to the home page.
   */
  @Override
  public void update() {
    navigateToPage(pages.get("home"));
  }
}