package org.example.chaosgame.chaos;

import org.example.chaosgame.linalg.Matrix2x2;
import org.example.chaosgame.linalg.Vector2D;
import org.example.chaosgame.transformations.AffineTransform2D;

public class ChaosCanvas {
  private final int width;
  private final int height;
  private final int[][] canvas;
  private final Vector2D minCoords;
  private final Vector2D maxCoords;
  private final AffineTransform2D transformCoordsToIndices;

  public ChaosCanvas(int width,
                     int height, Vector2D minCoords,
                     Vector2D maxCoords) {
    this.width = width;
    this.height = height;
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;
    this.transformCoordsToIndices = new AffineTransform2D(
        new Matrix2x2(0.0, ((height - 1) / (minCoords.getY() - maxCoords.getY())),
                ((width - 1) / (maxCoords.getX() - minCoords.getX())), 0.0),

            new Vector2D((((height - 1.0) * maxCoords.getY()) / (maxCoords.getY() - minCoords.getY())),
                    ((width - 1.0) * minCoords.getX()) / (minCoords.getX() - maxCoords.getX())
  ));
    //Vector2D a  = transformCoordsToIndices.transform(new Vector2D(width, height));
    this.canvas = new int[height][width];
  }

  public int[][] getCanvasArray(){
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

  public int getPixel(Vector2D point){
    Vector2D indices = transformCoordsToIndices.transform(point);
    int x = (int) indices.getX();
    int y = (int) indices.getY();
    return canvas[x][y];
  }

  public void putPixel (Vector2D point){
    Vector2D indices = transformCoordsToIndices.transform(point);
    int x = (int) indices.getX();
    int y = (int) indices.getY();
    if (x >= 0 && x < height && y >= 0 && y < width) {
      canvas[x][y] = 1;
    }
  }
  public void clearCanvas(){
    for (int i = 0; i < height; i++){
      for (int j = 0; j < width; j++){
        canvas[i][j] = 0;
      }
    }
  }
}
