package org.example.chaosgame.chaos;

import org.example.chaosgame.linalg.Vector2D;

import java.util.Random;
import java.util.stream.IntStream;
import javafx.concurrent.Task;

/**
 * Class for exploring julia sets.
 */
public class ExploreGame{
  private final int MAX_ITER = 256;

  private final ChaosCanvas canvas;
  private final ChaosGameDescription description;
  private Vector2D currentPoint = new Vector2D(0.0, 0.0);

  /**
   * Constructor for ExploreGame.
   *
   * @param description Description of the chaos game
   *
   * @param width Width of the canvas
   *
   * @param height Height of the canvas
   */
  public ExploreGame(ChaosGameDescription description, int width, int height) {
    this.description = description;
    this.canvas = new ChaosCanvas(width, height, description.getMinCoords(), description.getMaxCoords());
  }

  /**
   * Method for exploring fractals. Iterates over all pixels in the canvas
   * and applies the transformation to the current point.
   */
  public void exploreFractals(){

    long start = System.currentTimeMillis();
    for (int y = 0; y < canvas.getHeight(); y++) {
      for (int x = 0; x < canvas.getWidth(); x++) {
        int iter = 0;
        currentPoint = canvas.transformIndicesToCoords(x, y);
        Vector2D tempPoint = currentPoint;
        while (iter < MAX_ITER && tempPoint.getX() >= description.getMinCoords().getX() && tempPoint.getX() <= description.getMaxCoords().getX() &&
                tempPoint.getY() >= description.getMinCoords().getY() && tempPoint.getY() <= description.getMaxCoords().getY()) {
          tempPoint = description.getTransforms().getFirst().transform(tempPoint);
          iter++;
        }
        canvas.putPixel(x, y, iter);

      }

    }
    long end = System.currentTimeMillis();
    System.out.println("Time taken: " + (end - start) + "ms");
  }

  public ChaosCanvas getCanvas() {
    return canvas;
  }
}
