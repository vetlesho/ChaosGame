package org.example.chaosgame.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.example.chaosgame.model.chaos.ChaosCanvas;
import org.example.chaosgame.view.components.HomeButton;

public abstract class GamePage extends StackPane {
  protected final GraphicsContext gc;
  private static final int CANVAS_WIDTH = 1200;
  private static final int CANVAS_HEIGHT = 800;
  private static final int COLOR_FACTOR = 3;
  private static final int MAX_COLOR_VALUE = 255;

  public GamePage() {
    this.gc = createCanvas();
  }

  private GraphicsContext createCanvas() {
    Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    return canvas.getGraphicsContext2D();
  }

  public void updateCanvas(ChaosCanvas chaosCanvas) {
    clearCanvas();
    drawCanvas(chaosCanvas);
  }

  private void clearCanvas() {
    gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
  }

  private void drawCanvas(ChaosCanvas chaosCanvas) {
    double[][] canvasArray = chaosCanvas.getCanvasArray();
    double cellWidth = gc.getCanvas().getWidth() / chaosCanvas.getWidth();
    double cellHeight = gc.getCanvas().getHeight() / chaosCanvas.getHeight();

    // Create an off-screen image
    WritableImage offScreenImage =createOffScreenImage(chaosCanvas, canvasArray);
    // Draw the off-screen image on the canvas
    gc.drawImage(offScreenImage, 0, 0, cellWidth * chaosCanvas.getWidth(), cellHeight * chaosCanvas.getHeight());
  }

  // Creates an off-screen image from the given ChaosCanvas and canvas array
  private WritableImage createOffScreenImage(ChaosCanvas chaosCanvas, double[][] canvasArray) {
    WritableImage offScreenImage = new WritableImage(chaosCanvas.getWidth(), chaosCanvas.getHeight());
    PixelWriter pixelWriter = offScreenImage.getPixelWriter();

    for (int i = 0; i < chaosCanvas.getHeight(); i++) {
      for (int j = 0; j < chaosCanvas.getWidth(); j++) {
        double color = Math.min(canvasArray[i][j] * COLOR_FACTOR, MAX_COLOR_VALUE);
        if (color != 0) {
          pixelWriter.setColor(j, i, Color.rgb((int) (color), 0, 0));
        }
      }
    }

    return offScreenImage;
  }

  protected Button createHomeButton(EventHandler<ActionEvent> eventHandler) {
    Button homeButton = new HomeButton();
    homeButton.setOnAction(eventHandler);
    return homeButton;
  }
}
