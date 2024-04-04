package org.example.chaosgame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.chaosgame.chaos.*;
import org.example.chaosgame.linalg.Complex;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
    ChaosGameDescription description = null;
    /*
    String path = "src/main/resources/julia.txt";
    try {
      description = fileHandler.readFromFile(path);
      fileHandler.writeToFile(description, path);
    } catch (Exception e) {
      System.err.println(e);;
    }
    ChaosGame game = new ChaosGame(description, 1200, 800);
    */

    Complex c = new Complex(-0.835, 0.2321);
    ChaosGame game = new ChaosGame(ChaosGameDescriptionFactory.get("Julia", c), 1200, 800);

    game.runSteps(1000000);
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

        int color = Math.min(canvasArray[i][j] * 3 , 255);
        if (color == 0) {
          gc.setFill(Color.BLACK);
        } else {
          //hue based on the value of the pixel
          gc.setFill(Color.rgb(color, 255, 255));
        }
        gc.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
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
