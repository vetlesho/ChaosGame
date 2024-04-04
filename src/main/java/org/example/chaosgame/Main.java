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
import org.example.chaosgame.chaos.ExploreGame;
import org.example.chaosgame.linalg.Complex;
import org.example.chaosgame.linalg.Vector2D;
import org.example.chaosgame.transformations.AffineTransform2D;
import org.example.chaosgame.transformations.ExploreJulia;
import org.example.chaosgame.transformations.JuliaTransform;
import org.example.chaosgame.transformations.Transform2D;
import org.example.chaosgame.linalg.Matrix2x2;

import java.util.List;

import org.example.chaosgame.chaos.ChaosGameFileHandler;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
//    try {
//      // Change this to the path of the file you want to read
//      description = fileHandler.readFromFile("src/main/resources/barnsley.txt");
//    } catch (Exception e) {
//      System.err.println(e);;
//    }

   JuliaTransform juliaTransform = new JuliaTransform(
           new Complex(-0.70176, -0.3842), 1);
   JuliaTransform juliaTransform2 = new JuliaTransform(
           new Complex(0.3, 0.2), -1);
   JuliaTransform juliaTransform3 = new JuliaTransform(
           new Complex(0.5, 0.2), 1);
   Complex point = new Complex(-0.70176, -0.3842);
    ExploreJulia exploreTransform = new ExploreJulia(point);

    List<Transform2D> juliaTransforms = List.of(exploreTransform);
    ChaosGameDescription description = new ChaosGameDescription(new Vector2D(-1.6, -1),
            new Vector2D(1.6, 1), juliaTransforms);


    ExploreGame exploreGame = new ExploreGame(description, 1200, 800);
    exploreGame.exploreFractals();

    if (description == null) {
      System.out.println("Failed to read file");
      return;
    }

//    ChaosGame game = new ChaosGame(description, 1200, 800);
//    game.runStepsBarnsley(1000000);
    ChaosCanvas chaosCanvas = exploreGame.getCanvas();

    // Create a JavaFX canvas
    Canvas canvas = new Canvas(chaosCanvas.getWidth(), chaosCanvas.getHeight());
    GraphicsContext gc = canvas.getGraphicsContext2D();


    // Draw ChaosCanvas on JavaFX canvas
    int[][] canvasArray = chaosCanvas.getCanvasArray();
    double cellWidth = canvas.getWidth() / chaosCanvas.getWidth();
    double cellHeight = canvas.getHeight() / chaosCanvas.getHeight();
    for (int i = 0; i < chaosCanvas.getHeight(); i++) {
      for (int j = 0; j < chaosCanvas.getWidth(); j++) {

        int color = Math.min(canvasArray[i][j] * 3, 255);
        if (color == 0) {
          gc.setFill(Color.WHITE);
        } else {
          //hue based on the value of the pixel
          gc.setFill(Color.rgb(color, 0, 0));
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
