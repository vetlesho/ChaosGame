package org.example.chaosgame.controller;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import org.example.chaosgame.controller.observer.PageObserver;
import org.example.chaosgame.view.HomePage;

import java.util.HashMap;
import java.util.Map;

public class PageController implements PageObserver {
  private final StackPane mainPane;
  private final Map<String, Node> pages = new HashMap<>();
  private final ChaosGameController chaosGameController;
  private final ExploreGameController exploreGameController;
  private final HomePage homePage;
  private final HomeController homeController;

  public PageController(StackPane mainPane,  ChaosGameController chaosGameController, ExploreGameController exploreGameController) {
    this.mainPane = mainPane;
    this.chaosGameController = chaosGameController;
    this.exploreGameController = exploreGameController;
    this.homeController = new HomeController(this);
    this.homePage = homeController.getHomePage();
    this.homePage.setBind(mainPane);
    this.exploreGameController.setBind(mainPane);
    this.chaosGameController.setBind(mainPane);
    initPages(chaosGameController, exploreGameController);
    chaosGameController.registerObserver(this);
    exploreGameController.registerObserver(this);
  }

  private void initPages(ChaosGameController chaosGameController, ExploreGameController exploreGameController) {
    pages.put("home", homePage);
    pages.put("chaos", chaosGameController.getGamePage());
    pages.put("explore", exploreGameController.getGamePage());
    goToPage("home");
  }

  public void goToPage(String pageName) {
    Node page = pages.get(pageName);
    if (page != null) {
      navigateToPage(page);
    } else {
      navigateToPage(pages.get("home"));
    }
  }

  private void navigateToPage(Node page) {
    mainPane.getChildren().clear();
    mainPane.getChildren().add(page);
  }

  public void exitGame() {
    chaosGameController.removeObserver(this);
    exploreGameController.removeObserver(this);
    System.exit(0);
  }

  @Override
  public void update(Node page) {
    if (page != pages.get("home")) {
      navigateToPage(pages.get("home"));
      return;
    }
    navigateToPage(page);
  }
}