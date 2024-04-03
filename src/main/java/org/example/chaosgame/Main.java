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

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    // Create ChaosGameDescription and ChaosGame
    AffineTransform2D transform = new AffineTransform2D(
            new Matrix2x2(0.5, 0, 0, 0.5),
            new Vector2D(0, 0));
    AffineTransform2D transform2 = new AffineTransform2D(
            new Matrix2x2(0.5, 0, 0, 0.5),
            new Vector2D(0.5, 0));
    AffineTransform2D transform3 = new AffineTransform2D(
            new Matrix2x2(0.5, 0, 0, 0.5),
            new Vector2D(0.25, 0.5));
    List<Transform2D> transforms = List.of(transform, transform2, transform3);

   JuliaTransform juliaTransform = new JuliaTransform(
           new Complex(-0.70176, -0.3842), 1);
   JuliaTransform juliaTransform2 = new JuliaTransform(
           new Complex(0.3, 0.2), -1);
   JuliaTransform juliaTransform3 = new JuliaTransform(
           new Complex(0.5, 0.2), 1);
   Complex point = new Complex(-0.70176, -0.3842);
    ExploreJulia exploreTransform = new ExploreJulia(point);

    List<Transform2D> juliaTransforms = List.of(exploreTransform);

    ChaosGameDescription description = new ChaosGameDescription(
            new Vector2D(-1.6, -1),
            new Vector2D(1.6, 1),
            juliaTransforms
    );
    ExploreGame exploreGame = new ExploreGame(description, 1200, 800);
    ChaosGame game = new ChaosGame(description, 1200, 800);
    exploreGame.exploreFractals();
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

          int scaledValue = Math.min(255, canvasArray[i][j] * 3); // Scale up, but don't exceed 255
          gc.setFill(Color.rgb(scaledValue, 0, 0, 1));
//          gc.setFill(Color.rgb(canvasArray[i][j], 0, 0, 1));

        gc.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
      }
    }
    System.out.println(canvasArray[700][1100]);

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
