package org.example.chaosgame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.chaosgame.chaos.ChaosCanvas;
import org.example.chaosgame.chaos.ChaosGame;
import org.example.chaosgame.chaos.ChaosGameDescription;
import org.example.chaosgame.chaos.ChaosGameFileHandler;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
    ChaosGameDescription description = null;
    try {
      // Change this to the path of the file you want to read
      description = fileHandler.readFromFile("src/main/resources/julia.txt");
    } catch (Exception e) {
      System.err.println(e);;
    }

    if (description == null) {
      System.out.println("Failed to read file");
      return;
    }

    ChaosGame game = new ChaosGame(description, 1200, 800);
    game.runSteps(10000000);
    ChaosCanvas chaosCanvas = game.getCanvas();

    // Create a JavaFX canvas
    Canvas canvas = new Canvas(chaosCanvas.getWidth(), chaosCanvas.getHeight());
    GraphicsContext gc = canvas.getGraphicsContext2D();


    // Draw ChaosCanvas on JavaFX canvas
    int[][] canvasArray = chaosCanvas.getCanvasArray();
    double cellWidth = canvas.getWidth() / chaosCanvas.getWidth();
    double cellHeight = canvas.getHeight() / chaosCanvas.getHeight();
    for (int i = 0; i < chaosCanvas.getHeight(); i++) {
      for (int j = 0; j < chaosCanvas.getWidth(); j++) {
        Color color;
        int value = chaosCanvas.getCanvasArray()[i][j];
        if (value >= 255) {
          value = 255;
        }
        if (value == 0) {
          color = Color.LIGHTGREY;
        } else {
          // Adjust the hue component based on the canvas array value
          color = Color.rgb(value / 255, 0,0,1);
        }
        gc.setFill(color);
        gc.fillRect(j, i, cellWidth, cellHeight);
      }
    }

    // Create a JavaFX window
    StackPane root = new StackPane();
    root.getChildren().add(canvas);
    Scene scene = new Scene(root, chaosCanvas.getWidth(), chaosCanvas.getHeight());
    primaryStage.setScene(scene);
    primaryStage.setTitle("Chaos Game Canvas");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
