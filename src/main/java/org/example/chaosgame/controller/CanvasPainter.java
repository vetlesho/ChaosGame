package org.example.chaosgame.controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.example.chaosgame.model.chaos.ChaosCanvas;


/**
 * Class for the CanvasPainter.
 * The CanvasPainter is used for painting a fractal 
 * on the canvas.
 * All methods are public and static.
 */
public abstract class CanvasPainter {

  protected Color fractalColor = Color.WHITE;
  private static final int COLOR_FACTOR = 3;
  private static final int MAX_COLOR_VALUE = 255;

  /**
   * Method for painting the canvas.
   * Draws the canvas on the graphics context.
   * Uses the method createOffScreenImage to create the offscreen image.
   *
   * @param chaosCanvas the chaos canvas
   * @param gc the graphics context
   */
  private void drawCanvas(ChaosCanvas chaosCanvas, GraphicsContext gc) {
    double[][] canvasArray = chaosCanvas.getCanvasArray();
    double cellWidth = gc.getCanvas().getWidth() / chaosCanvas.getWidth();
    double cellHeight = gc.getCanvas().getHeight() / chaosCanvas.getHeight();

    WritableImage offScreenImage = createOffScreenImage(chaosCanvas, canvasArray);
    gc.drawImage(offScreenImage, 0, 0, cellWidth * chaosCanvas.getWidth(),
            cellHeight * chaosCanvas.getHeight());
  }

  /**
   * Method for updating the canvas.
   * Clears the canvas and draws the canvas.
   *
   * @param chaosCanvas the chaos canvas
   */
  protected void updateCanvas(ChaosCanvas chaosCanvas, GraphicsContext gc) {
    this.clearCanvas(gc);
    drawCanvas(chaosCanvas, gc);
  }

  /**
   * Method for clearing the canvas.
   *
   * @param gc the graphics context
   */
  protected void clearCanvas(GraphicsContext gc) {
    gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
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

  /**
   * Method for setting color of the fractal.
   *
   * @param newFractalColor the new fractal color
   */
  protected void setFractalColor(Color newFractalColor) {
    fractalColor = newFractalColor;
  }
}
