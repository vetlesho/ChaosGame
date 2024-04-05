package org.example.chaosgame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.chaosgame.controller.ChaosGameController;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    ChaosGameController controller = new ChaosGameController();

    // Create a JavaFX window
    VBox root = new VBox();
    root.getChildren().addAll(controller.getMenuView().getMenuBox(), controller.getView().getCanvas());
    Scene scene = new Scene(root, 1200, 800);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Chaos Game Canvas");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}