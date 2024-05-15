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

  public PageController(StackPane mainPane, ChaosGameController chaosGameController, ExploreGameController exploreGameController) {
    this.mainPane = mainPane;
    this.chaosGameController = chaosGameController;
    this.exploreGameController = exploreGameController;
    initPages(chaosGameController, exploreGameController);
    chaosGameController.registerObserver(this);
    exploreGameController.registerObserver(this);
  }

  private void initPages(ChaosGameController chaosGameController, ExploreGameController exploreGameController) {
    pages.put("home", new HomePage(this));
    pages.put("chaos", chaosGameController.getChaosPage());
    pages.put("explore", exploreGameController.getExplorePage());
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