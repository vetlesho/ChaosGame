package org.example.chaosgame.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import org.example.chaosgame.model.chaos.ChaosCanvas;

/**
 * Abstract class for the GamePage, extends BorderPane.
 * The GamePage is used for displaying the game.
 */
public abstract class GamePage extends BorderPane {
  protected final GraphicsContext gc;
  private static final int COLOR_FACTOR = 6;
  private static final int CANVAS_WIDTH = 1250;
  private static final int CANVAS_HEIGHT = 805;
  private static final int MAX_COLOR_VALUE = 255;
  protected Color fractalColor;

  /**
   * Constructor for the GamePage.
   * Initializes the canvas and fractal color.
   */
  public GamePage() {
    this.gc = createCanvas();
    this.fractalColor = Color.WHITE;
  }

  /**
   * Method for setting color of the fractal.
   *
   * @param newFractalColor the new fractal color
   */
  public void setFractalColor(Color newFractalColor) {
    this.fractalColor = newFractalColor;
  }

  /**
   * Method for creating the canvas.
   *
   * @return the GraphicsContext
   */
  private GraphicsContext createCanvas() {
    Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    return canvas.getGraphicsContext2D();
  }

  /**
   * Method for updating the canvas.
   * Clears the canvas and draws the canvas.
   *
   * @param chaosCanvas the chaos canvas
   */
  public void updateCanvas(ChaosCanvas chaosCanvas) {
    clearCanvas();
    drawCanvas(chaosCanvas);
  }

  /**
   * Method for clearing the canvas.
   */
  public void clearCanvas() {
    gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
  }

  /**
   * Method for drawing the canvas.
   *
   * @param chaosCanvas the chaos canvas
   */
  private void drawCanvas(ChaosCanvas chaosCanvas) {
    double[][] canvasArray = chaosCanvas.getCanvasArray();
    double cellWidth = gc.getCanvas().getWidth() / chaosCanvas.getWidth();
    double cellHeight = gc.getCanvas().getHeight() / chaosCanvas.getHeight();

    WritableImage offScreenImage = createOffScreenImage(chaosCanvas, canvasArray);
    gc.drawImage(offScreenImage, 0, 0, cellWidth * chaosCanvas.getWidth(),
            cellHeight * chaosCanvas.getHeight());
  }

  /**
   * Method for creating the offscreen image.
   * Creates a WritableImage and sets the pixel color.
   *
   * @param chaosCanvas the chaos canvas
   * @param canvasArray the canvas array
   * @return the WritableImage
   */
  private WritableImage createOffScreenImage(ChaosCanvas chaosCanvas, double[][] canvasArray) {
    WritableImage offScreenImage = new WritableImage(
            chaosCanvas.getWidth(), chaosCanvas.getHeight());
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
}
