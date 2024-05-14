package org.example.chaosgame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.controller.ExploreGameController;
import org.example.chaosgame.controller.PageController;
import org.example.chaosgame.observer.PageObserver;
import org.example.chaosgame.view.ChaosPage;
import org.example.chaosgame.view.ExplorePage;
import org.example.chaosgame.view.HomePage;

import java.util.Objects;


public class MainApp extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    StackPane mainPane = new StackPane();

    ExploreGameController exploreGameController = new ExploreGameController();
    ChaosGameController chaosGameController = new ChaosGameController();
    new PageController(mainPane, chaosGameController, exploreGameController);

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
