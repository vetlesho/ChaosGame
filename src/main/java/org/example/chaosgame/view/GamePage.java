package org.example.chaosgame.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import org.example.chaosgame.model.chaos.ChaosCanvas;
import org.example.chaosgame.view.components.HomeButton;

import java.util.Objects;

public abstract class GamePage extends BorderPane {
  protected final GraphicsContext gc;
  private static final int COLOR_FACTOR = 3;
  private static final int CANVAS_WIDTH = 1020;
  private static final int CANVAS_HEIGHT = 680;
  private static final int MAX_COLOR_VALUE = 255;

  protected Color fractalColor;

  public GamePage() {
    this.gc = createCanvas();
    this.fractalColor = Color.WHITE;
  }

  private GraphicsContext createCanvas() {
    Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    return canvas.getGraphicsContext2D();
  }

  public void updateCanvas(ChaosCanvas chaosCanvas) {
    clearCanvas();
    drawCanvas(chaosCanvas);
  }

  public void setCanvasSize(double width, double height) {
    gc.getCanvas().setWidth(width);
    gc.getCanvas().setHeight(height);
  }

  public void clearCanvas() {
    gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
  }

  private void drawCanvas(ChaosCanvas chaosCanvas) {
    double[][] canvasArray = chaosCanvas.getCanvasArray();
    double cellWidth = gc.getCanvas().getWidth() / chaosCanvas.getWidth();
    double cellHeight = gc.getCanvas().getHeight() / chaosCanvas.getHeight();

    // Create an off-screen image
    WritableImage offScreenImage = createOffScreenImage(chaosCanvas, canvasArray);
    // Draw the off-screen image on the canvas
    gc.drawImage(offScreenImage, 0, 0, cellWidth * chaosCanvas.getWidth(), cellHeight * chaosCanvas.getHeight());
  }

  // Creates an off-screen image from the given ChaosCanvas and canvas array
  private WritableImage createOffScreenImage(ChaosCanvas chaosCanvas, double[][] canvasArray) {
    WritableImage offScreenImage = new WritableImage(chaosCanvas.getWidth(), chaosCanvas.getHeight());
    PixelWriter pixelWriter = offScreenImage.getPixelWriter();

    Color minColor = Color.BLACK;
    Color maxColor = fractalColor;

    for (int i = 0; i < chaosCanvas.getHeight(); i++) {
      for (int j = 0; j < chaosCanvas.getWidth(); j++) {
        double iterationCount = Math.min(canvasArray[i][j] * COLOR_FACTOR, MAX_COLOR_VALUE);
        if (iterationCount > 0 && iterationCount <= MAX_COLOR_VALUE) {
          double t = iterationCount / MAX_COLOR_VALUE;
          double red = (1 - t) * minColor.getRed() + t * maxColor.getRed();
          double green = (1 - t) * minColor.getGreen() + t * maxColor.getGreen();
          double blue = (1 - t) * minColor.getBlue() + t * maxColor.getBlue();
          pixelWriter.setColor(j, i, Color.color(red, green, blue));
        }
      }
    }

    return offScreenImage;
  }

  public void setFractalColor(Color newFractalColor) {
    this.fractalColor = newFractalColor;
  }

  protected Button createHomeButton(EventHandler<ActionEvent> eventHandler) {
    Button homeButton = new HomeButton();
    homeButton.setOnAction(eventHandler);
    return homeButton;
  }
}
