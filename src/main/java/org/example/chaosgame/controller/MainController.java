package org.example.chaosgame.controller;

import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;
import org.example.chaosgame.view.ChaosPage;
import org.example.chaosgame.view.ExplorePage;
import org.example.chaosgame.view.HomePage;

public class MainController implements Observer{

  private HomePage homePage;
  private final StackPane mainPane;

  private final ChaosPage chaosPage;
  private final ExplorePage explorePage;

  public MainController(StackPane mainPane) {
    this.mainPane = mainPane;
    this.homePage = new HomePage(this);
    this.chaosPage = new ChaosPage(this);
    this.explorePage = new ExplorePage(this);

//    homeButtonClicked();

  }

  public void chaosGameButtonClicked() {
      mainPane.getChildren().clear();
      mainPane.getChildren().add(chaosPage.getChaosContent());
  }

  public void exploreGameButtonClicked() {
    mainPane.getChildren().clear();
    mainPane.getChildren().add(explorePage.getExploreContent());
  }

  public void homeButtonClicked() {
      mainPane.getChildren().clear();
      mainPane.getChildren().add(homePage);
  }



  public HomePage getHomePage() {
    return homePage;
  }

  public ChaosPage getChaosPage() {
    return chaosPage;
  }

  public ExplorePage getExplorePage() {
    return explorePage;
  }

  @Override
  public void update() {

  }
}
