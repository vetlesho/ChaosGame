package org.example.chaosgame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.chaosgame.controller.MainController;
import org.example.chaosgame.model.chaos.ChaosGame;
import org.example.chaosgame.model.chaos.ChaosGameDescription;
import org.example.chaosgame.model.chaos.ChaosGameDescriptionFactory;
import org.example.chaosgame.model.chaos.ChaosGameFileHandler;
import org.example.chaosgame.view.ChaosPage;
import org.example.chaosgame.view.ExplorePage;
import org.example.chaosgame.view.MenuView;

import java.util.Objects;


public class MainApp extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
    ChaosGameDescription description = null;

    BorderPane borderPane = new BorderPane();
    ChaosPage chaosPage = new ChaosPage();
    ExplorePage explorePage = new ExplorePage();
    MenuView menuView = new MenuView();


    borderPane.setCenter(chaosPage.getChaosContent());
    Button chaosButton = menuView.getChaosButton();
    chaosButton.setOnAction(e -> {
      borderPane.setCenter(chaosPage.getChaosContent());
    });
    Button exploreButton = menuView.getExploreButton();
    exploreButton.setOnAction(e-> {
      borderPane.setCenter(explorePage.getExploreContent());
    });
    HBox menuBar = menuView.getMenuBar();

    borderPane.setTop(menuBar);


    Scene scene = new Scene(borderPane, 1200, 800);
    scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/global.css")).toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.setTitle("Chaos Game Canvas");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
