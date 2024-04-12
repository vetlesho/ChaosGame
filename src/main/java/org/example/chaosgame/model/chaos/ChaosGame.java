package org.example.chaosgame.model.chaos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.example.chaosgame.controller.Subject;
import org.example.chaosgame.controller.Observer;
import org.example.chaosgame.model.linalg.Vector2D;

/**
 * Class for running a chaos game.
 * The chaos game is a method for generating fractals.
 * The game is played by starting with a point and then randomly
 * selecting a transformation from a set of transformations.
 * The selected transformation is then applied to the current point.
 * The new point is then drawn on the canvas.
 * This process is repeated a selected amount of steps.
 */
public class ChaosGame implements Subject {
  private final ChaosCanvas canvas;

  private final ChaosGameDescription description;

  private Vector2D currentPoint = new Vector2D(0.0, 0.0);

  private final Random random = new Random();
  private List<Observer> observers;

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
    this.observers = new ArrayList<>();
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
    if (description.getProbabilities() != null) {
      runStepsWithProbabilities(steps, description.getProbabilities());
    } else {
      runStepsUniform(steps);
    }
    notifyObservers();
  }
  private void runStepsUniform(int steps) {
    for (int i = 0; i < steps; i++) {
      int transformIndex = random.nextInt(description.getTransforms().size());
      currentPoint = description.getTransforms().get(transformIndex).transform(currentPoint);
      canvas.putPixel(currentPoint);
    }
  }

  /**
   * Method for running the chaos game with probabilities.
   * Randomly selects a transformation from the description based on the probabilities
   * and applies it to the current point.
   *
   * @param steps Number of steps to run
   * @param probabilities List of probabilities for the transformations
   */
  private void runStepsWithProbabilities(int steps, List<Integer> probabilities) {
    for (int i = 0; i < steps; i++) {
      int test = random.nextInt(100);
      int transformIndex = -1;
      for (int j = 0; j < probabilities.size(); j++) {
        test -= probabilities.get(j);
        if (test < 0) {
          transformIndex = j;
          break;
        }
      }
      if (transformIndex == -1) {
        transformIndex = probabilities.size() - 1; // default to the last transformation
      }
      currentPoint = description.getTransforms().get(transformIndex).transform(currentPoint);
      canvas.putPixel(currentPoint);
    }
  }

  @Override
  public void registerObserver(Observer observer) {
    observers.add(observer);
    System.out.println("Observer added");
  }

  @Override
  public void removeObserver(Observer observer) {
    observers.remove(observer);
    System.out.println("Observer removed");
  }

  @Override
  public void notifyObservers() {
    for (Observer observer : observers) {
      observer.update();
      System.out.println("Observer notified");
    }
  }
}

