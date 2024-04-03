package org.example.chaosgame.chaos;

import java.util.Random;
import org.example.chaosgame.linalg.Vector2D;

/**
 * Class for running a chaos game.
 * The chaos game is a method for generating fractals.
 * The game is played by starting with a point and then randomly
 * selecting a transformation from a set of transformations.
 * The selected transformation is then applied to the current point.
 * The new point is then drawn on the canvas.
 * This process is repeated a selected amount of steps.
 */
public class ChaosGame {
  private final ChaosCanvas canvas;

  private final ChaosGameDescription description;

  private Vector2D currentPoint = new Vector2D(0.0, 0.0);

  public final Random random = new Random();

  /**
   * Constructor for ChaosGame.
   *
   * @param description Description of the chaos game
   *
   * @param width Width of the canvas
   *
   * @param height Height of the canvas
   */

  public ChaosGame(ChaosGameDescription description, int width, int height) {
    this.description = description;
    this.canvas = new ChaosCanvas(width, height,
            description.getMinCoords(), description.getMaxCoords());
  }


  public ChaosCanvas getCanvas() {
    return canvas;
  }

  /**
   * Method for running the chaos game. Randomly selects a transformation
   * from the description and applies it to the current point.
   *
   * @param steps Number of steps to run
   */
  public void runSteps(int steps) {
    for (int i = 0; i < steps; i++) {
      int transformIndex = random.nextInt(description.getTransforms().size());
      currentPoint = description.getTransforms().get(transformIndex).transform(currentPoint);
      canvas.putPixel(currentPoint);
    }
  }

  /**
   * Method for running the Barnsley chaos game. Randomly selects a transformation
   * from the description and applies it to the current point.
   * The Barnsley chaos game has a different probability distribution
   * for selecting transformations.
   *
   * @param steps Number of steps to run
   */
  public void runStepsBarnsley(int steps) {
    for (int i = 0; i < steps; i++) {
      int test = random.nextInt(100);
      if (test < 1) {
        currentPoint = description.getTransforms().getFirst().transform(currentPoint);
      } else if (test < 86) {
        currentPoint = description.getTransforms().get(1).transform(currentPoint);
      } else if (test < 93) {
        currentPoint = description.getTransforms().get(2).transform(currentPoint);
      } else {
        currentPoint = description.getTransforms().get(3).transform(currentPoint);
      }
      canvas.putPixel(currentPoint);
    }
  }
}
