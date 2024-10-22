package org.idatt2003;

import java.util.Objects;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.idatt2003.controller.ChaosGameController;
import org.idatt2003.controller.ExploreGameController;
import org.idatt2003.controller.PageController;

/**
 * This is the main class for the JavaFX application.
 * It sets up the primary stage and the main pane.
 * It also sets up the controllers.
 */
public class MainApp extends Application {
  @Override
  public void start(Stage primaryStage) {
    StackPane mainPane = new StackPane();

    ExploreGameController exploreGameController = new ExploreGameController();
    ChaosGameController chaosGameController = new ChaosGameController();
    new PageController(mainPane, chaosGameController, exploreGameController);

    Scene scene = new Scene(mainPane, 1200, 800);
    scene.getStylesheets().add(
            Objects.requireNonNull(getClass()
                    .getResource("/stylesheets/global.css"))
                    .toExternalForm());

    primaryStage.setMinWidth(1000);
    primaryStage.setMinHeight(650);

    primaryStage.setScene(scene);
    primaryStage.setTitle("Chaos Game Canvas");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
