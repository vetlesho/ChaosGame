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

    IntStream yStream = IntStream.range(0, canvas.getHeight());
    yStream.parallel().forEach(y -> {
      for (int x = 0; x < canvas.getWidth(); x++) {
        int iter = 0;
        currentPoint = canvas.transformIndicesToCoords(x, y);
        Vector2D tempPoint = currentPoint;
        while (iter < MAX_ITER && tempPoint.lengthSQ() < 4){
          tempPoint = description.getTransforms().getFirst().transform(tempPoint);
          iter++;
        }
        double abs = Math.sqrt(tempPoint.lengthSQ());
        double smooth = iter - Math.log(Math.log(abs)) / Math.log(2);

        canvas.putPixel(x, y, smooth);

      }
    });
//    for (int y = 0; y < canvas.getHeight(); y++) {
//      for (int x = 0; x < canvas.getWidth(); x++) {
//        int iter = 0;
//        currentPoint = canvas.transformIndicesToCoords(x, y);
//        Vector2D tempPoint = currentPoint;
//        while (iter < MAX_ITER && tempPoint.lengthSQ() < 4){
//          tempPoint = description.getTransforms().getFirst().transform(tempPoint);
//          iter++;
//        }
//        double abs = Math.sqrt(tempPoint.lengthSQ());
//        double smooth = iter - Math.log(Math.log(abs)) / Math.log(2);
//
//        canvas.putPixel(x, y, smooth);
//
//      }
//
//    }
    long end = System.currentTimeMillis();
    System.out.println("Time taken: " + (end - start) + "ms");
  }

  public ChaosCanvas getCanvas() {
    return canvas;
  }
}
