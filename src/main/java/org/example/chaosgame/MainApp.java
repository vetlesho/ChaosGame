package org.example.chaosgame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.controller.ExploreGameController;
import org.example.chaosgame.controller.PageController;
import org.example.chaosgame.view.ChaosPage;
import org.example.chaosgame.view.ExplorePage;
import org.example.chaosgame.view.HomePage;

import java.util.Objects;


public class MainApp extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    StackPane mainPane = new StackPane();
    PageController pageController = new PageController(mainPane);
    ExploreGameController exploreGameController = new ExploreGameController(pageController);
    ChaosGameController chaosGameController = new ChaosGameController(pageController);
    ExplorePage explorePage = exploreGameController.getExplorePage();
    ChaosPage chaosPage = chaosGameController.getChaosPage();
    pageController.setChaosPage(chaosPage);
    pageController.setExplorePage(explorePage);
    HomePage homePage = pageController.getHomePage();
    mainPane.getChildren().add(homePage);

    Scene scene = new Scene(mainPane, 1200, 800);
    mainPane.prefWidthProperty().bind(scene.widthProperty());
    mainPane.prefHeightProperty().bind(scene.heightProperty());
    scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/global.css")).toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.setTitle("Chaos Game Canvas");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
