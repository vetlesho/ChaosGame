package org.example.chaosgame.chaos;

import org.example.chaosgame.linalg.Matrix2x2;
import org.example.chaosgame.linalg.Vector2D;
import org.example.chaosgame.transformations.AffineTransform2D;

/**
 * A class representing a canvas for the chaos game.
 * The canvas is a 2D grid of pixels, where each pixel is represented by an integer value.
 * The canvas has a coordinate system, where the origin is in the lower left corner of the canvas,
 * and the x-axis and y-axis are horizontal and vertical, respectively.
 * The canvas has a minimum and maximum coordinate, which defines the extent of the canvas.
 * The canvas also has an Affine transformation that maps coordinates, {@link Vector2D},
 * to indices in the canvas array. This is used to map points to pixels in the canvas.
 */

public class ChaosCanvas {
  private final int width;
  private final int height;
  private final int[][] canvas;
  private final Vector2D minCoords;
  private final Vector2D maxCoords;
  private final AffineTransform2D transformCoordsToIndices;

  /**
   * Creates a new ChaosCanvas with the given width, height, minimum and maximum coordinates.
   * The canvas is initialized with all pixel values set to 0.
   * The Affine transformation is calculated based on the width, height,
   * minimum and maximum coordinates.
   *
   * @param width The width of the canvas
   *
   * @param height The height of the canvas
   *
   * @param minCoords The minimum coordinates of the canvas
   *
   * @param maxCoords The maximum coordinates of the canvas
   *
   */
  public ChaosCanvas(int width,
                     int height, Vector2D minCoords,
                     Vector2D maxCoords) {
    this.width = width;
    this.height = height;
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;
    this.transformCoordsToIndices = new AffineTransform2D(
        new Matrix2x2(0.0, ((height - 1)
                / (minCoords.getY() - maxCoords.getY())),
                ((width - 1) / (maxCoords.getX() - minCoords.getX())), 0.0),

            new Vector2D((((height - 1.0) * maxCoords.getY())
                    / (maxCoords.getY() - minCoords.getY())),
                    ((width - 1.0) * minCoords.getX()) / (minCoords.getX() - maxCoords.getX())
  ));
    this.canvas = new int[height][width];
  }

  public int[][] getCanvasArray() {
    return canvas;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Vector2D getMinCoords() {
    return minCoords;
  }

  public Vector2D getMaxCoords() {
    return maxCoords;
  }

  public int getPixel(Vector2D point) {
    Vector2D indices = transformCoordsToIndices.transform(point);
    int x = (int) indices.getX();
    int y = (int) indices.getY();
    return canvas[x][y];
  }

  /**
   * Increments the pixel value at the given point by 1.
   * If the point is outside the canvas, the method does nothing.
   *
   * @param point The point to put a pixel at
   */
  public void putPixel(Vector2D point) {
    Vector2D indices = transformCoordsToIndices.transform(point);
    int x = (int) indices.getX();
    int y = (int) indices.getY();
    if (x >= 0 && x < height && y >= 0 && y < width) {
      canvas[x][y] += 1;
    }
  }

  public void clearCanvas() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        canvas[i][j] = 0;
      }
    }
  }
}
