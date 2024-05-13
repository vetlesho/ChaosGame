package org.example.chaosgame.controller;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.example.chaosgame.view.ChaosPage;
import org.example.chaosgame.view.ExplorePage;
import org.example.chaosgame.view.HomePage;
import org.example.chaosgame.view.components.HomeButton;

public class PageController implements Observer{
  private HomePage homePage;
  private final StackPane mainPane;
  private ChaosPage chaosPage;
  private ExplorePage explorePage;

  public PageController(StackPane mainPane){
    this.mainPane = mainPane;
    this.homePage = new HomePage(mainPane, this);
  }
  public void chaosGameButtonClicked() {
    mainPane.getChildren().clear();
    mainPane.getChildren().add(chaosPage);
  }

  public void exploreGameButtonClicked() {
    mainPane.getChildren().clear();
    mainPane.getChildren().add(explorePage);
  }

  public void homeButtonClicked() {
    mainPane.getChildren().clear();
    mainPane.getChildren().add(homePage);
  }

  public HomePage getHomePage() {
    return homePage;
  }


  public void setChaosPage(ChaosPage chaosPage) {
    this.chaosPage = chaosPage;
  }

  public void setExplorePage(ExplorePage explorePage) {
    this.explorePage = explorePage;
  }

  @Override
  public void update() {
  }
}
