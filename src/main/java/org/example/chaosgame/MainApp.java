package org.example.chaosgame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.controller.ExploreGameController;
import org.example.chaosgame.controller.MainController;
import org.example.chaosgame.view.ChaosPage;
import org.example.chaosgame.view.ExplorePage;
import org.example.chaosgame.view.HomePage;

import java.util.Objects;


public class MainApp extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    StackPane mainPane = new StackPane();

    MainController mainController = new MainController(mainPane);
    ExplorePage explorePage = mainController.getExplorePage();
    ChaosPage chaosPage = mainController.getChaosPage();

    ExploreGameController exploreGameController = new ExploreGameController(explorePage);
    ChaosGameController chaosGameController = new ChaosGameController(chaosPage);

    HomePage homePage = mainController.getHomePage();

    mainPane.getChildren().add(homePage);

    Scene scene = new Scene(mainPane, 1200, 800);
    scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/global.css")).toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.setTitle("Chaos Game Canvas");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
