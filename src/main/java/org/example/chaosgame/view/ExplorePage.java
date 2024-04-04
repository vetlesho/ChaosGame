package org.example.chaosgame.view;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.chaosgame.chaos.*;
import org.example.chaosgame.linalg.Complex;
import org.example.chaosgame.linalg.Vector2D;
import org.example.chaosgame.transformations.ExploreJulia;
import org.example.chaosgame.transformations.JuliaTransform;

import java.util.List;
import java.util.stream.IntStream;

public class ExplorePage {
  private final StackPane exploreContent;
  private final ExploreGame exploreGame;
  private final ChaosCanvas chaosCanvas;
  private Complex c = new Complex(-0.835, 0.2321);
  private final Canvas canvas;
  private final GraphicsContext gc;

  public ExplorePage() {
    exploreContent = new StackPane();

    ChaosGameDescription description = new ChaosGameDescription(
            new Vector2D(-1.6, -1),
            new Vector2D(1.6, 1),
            List.of(
                    new ExploreJulia(c)
            ));
    exploreGame = new ExploreGame(description, 1200, 800);
    chaosCanvas = exploreGame.getCanvas();
    canvas = new Canvas(chaosCanvas.getWidth(), chaosCanvas.getHeight());
    gc = canvas.getGraphicsContext2D();
    exploreGame.exploreFractals();
    updateCanvas();
    exploreContent.getChildren().addAll(canvas);

  }

  public StackPane getExploreContent() {
    return exploreContent;
  }

  public void updateCanvas() {
    int[][] canvasArray = chaosCanvas.getCanvasArray();
    double cellWidth = gc.getCanvas().getWidth() / chaosCanvas.getWidth();
    double cellHeight = gc.getCanvas().getHeight() / chaosCanvas.getHeight();
    long start = System.currentTimeMillis();

    // Create an off-screen image
    WritableImage offScreenImage = new WritableImage(chaosCanvas.getWidth(), chaosCanvas.getHeight());
    PixelWriter pixelWriter = offScreenImage.getPixelWriter();

    for (int i = 0; i < chaosCanvas.getHeight(); i++) {
      for (int j = 0; j < chaosCanvas.getWidth(); j++) {
        int color = Math.min(canvasArray[i][j] * 3, 255);
        if (color != 0) {
          pixelWriter.setColor(j, i, Color.rgb(color, 0, 0));
        }
      }
    }

    // Draw the off-screen image on the canvas
    gc.drawImage(offScreenImage, 0, 0, cellWidth * chaosCanvas.getWidth(), cellHeight * chaosCanvas.getHeight());

    long end = System.currentTimeMillis();
    System.out.println("Time taken to display: " + (end - start) + "ms");
  }
}
