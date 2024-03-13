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
import org.example.chaosgame.linalg.Complex;
import org.example.chaosgame.linalg.Vector2D;
import org.example.chaosgame.transformations.AffineTransform2D;
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

    AffineTransform2D barnsleyFernTransform = new AffineTransform2D(
            new Matrix2x2(0, 0, 0, 0.16),
            new Vector2D(0, 0));
    AffineTransform2D barnsleyFernTransform2 = new AffineTransform2D(
            new Matrix2x2(0.85, 0.04, -0.04, 0.85),
            new Vector2D(0, 1.6));
    AffineTransform2D barnsleyFernTransform3 = new AffineTransform2D(
            new Matrix2x2(0.2, -0.26, 0.23, 0.22),
            new Vector2D(0, 1.6));
    AffineTransform2D barnsleyFernTransform4 = new AffineTransform2D(
            new Matrix2x2(-0.15, 0.28, 0.26, 0.24),
            new Vector2D(0, 0.44));
    List<Transform2D> barnsleyFernTransforms = List.of(barnsleyFernTransform, barnsleyFernTransform2,
            barnsleyFernTransform3, barnsleyFernTransform4);


    JuliaTransform juliaTransform = new JuliaTransform(
           new Complex(-0.8, 0.1889), 1);

    List<Transform2D> juliaTransforms = List.of(juliaTransform);

    ChaosGameDescription description = new ChaosGameDescription(
            //new Vector2D(-2.65, 0),
            //new Vector2D(2.65, 10),
            //new Vector2D(0,0),
            //new Vector2D(1,1),
            new Vector2D(-1.6, -1),
            new Vector2D(1.6, 1),
            juliaTransforms
    );

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
        if (canvasArray[i][j] == 1) {
          gc.setFill(Color.BLACK);
        } else {
          gc.setFill(Color.WHITE);
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
