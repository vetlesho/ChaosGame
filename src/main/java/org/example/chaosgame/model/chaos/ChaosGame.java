package org.example.chaosgame.model.chaos;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Random;
import org.example.chaosgame.controller.interfaces.Observer;
import org.example.chaosgame.controller.interfaces.Subject;
import org.example.chaosgame.model.linalg.Vector2D;

/**
 * Class for running a chaos game.
 * The chaos game is a method for generating fractals.
 * The game is played by starting with a point and then randomly
 * selecting a transformation from a set of transformations.
 * The selected transformation is then applied to the current point.
 * The new point is then drawn on the canvas.
 * This process is repeated a selected number of steps.
 */
public class ChaosGame implements Subject {
  private static ChaosGame instance = null;
  private final ChaosCanvas canvas;
  private ChaosGameDescription description;
  private Vector2D currentPoint;
  private final Random random = new Random();
  private final List<Observer> gameObservers;
  private int steps;
  private int totalSteps;

  /**
   * Private constructor for ChaosGame.
   * Secures that only one instance of the ChaosGame can be created.
   *
   * @param description Description of the chaos game
   *
   * @param width Width of the canvas
   *
   * @param height Height of the canvas
   */
  private ChaosGame(ChaosGameDescription description, int width, int height) {
    this.description = description;
    this.canvas = new ChaosCanvas(width, height,
            description.getMinCoords(), description.getMaxCoords());
    this.currentPoint = new Vector2D(0.0, 0.0);
    this.gameObservers = new ArrayList<>();
    this.steps = 0;
    this.totalSteps = 0;
  }

  /**
   * Method for getting an instance of the ChaosGame.
   * If an instance already exists, the existing instance is returned.
   * If no instance exists, a new instance is created.
   *
   * @param description Description of the chaos game
   * @param width Width of the canvas
   * @param height Height of the canvas
   * @return Instance of the ChaosGame
   */
  public static ChaosGame getInstance(ChaosGameDescription description, int width, int height) {
    if (instance == null) {
      instance = new ChaosGame(description, width, height);
    }
    return instance;
  }

  public ChaosCanvas getCanvas() {
    return canvas;
  }

  public ChaosGameDescription getDescription() {
    return description;
  }

  public int getSteps() {
    return steps;
  }

  public int getTotalSteps() {
    return totalSteps;
  }

  /**
   * Method for setting the number of steps to run.
   *
   * @param steps Number of steps to run
   * @throws IllegalArgumentException If steps is less than 0 or greater than 1000000
   */
  public void setSteps(int steps) throws IllegalArgumentException {
    if (steps < 0) {
      throw new IllegalArgumentException("Steps must be a positive number");
    }
    if (steps > 1000000) {
      throw new IllegalArgumentException("Steps must be less than 1000000");
    }
    this.steps = steps;
  }

  public void setTotalSteps(int totalSteps) {
    this.totalSteps = totalSteps;
  }

  /**
   * Method for setting the chaos game description.
   *
   * @param newDescription New description of the chaos game
   * @throws IllegalArgumentException If newDescription is null
   */
  public void setChaosGameDescription(ChaosGameDescription newDescription)
          throws IllegalArgumentException {
    if (newDescription == null) {
      throw new IllegalArgumentException("Description cannot be null");
    }
    this.description = newDescription;
    resetTotalSteps();
    setChaosCanvas(description.getMinCoords(), description.getMaxCoords());
    notifyObservers();
  }

  /**
   * Method for setting the chaos canvas.
   *
   * @param minCoords Minimum coordinates of the canvas
   * @param maxCoords Maximum coordinates of the canvas
   */
  public void setChaosCanvas(Vector2D minCoords, Vector2D maxCoords) {
    this.canvas.clearCanvas();
    this.canvas.setMinCoords(minCoords);
    this.canvas.setMaxCoords(maxCoords);
    this.canvas.setTransformCoordsToIndices();
  }

  /**
   * Method for adding steps to the total number of steps.
   *
   * @param newSteps Number of steps to add
   * @throws IllegalArgumentException If newSteps is less than 0
   */
  public void addTotalSteps(int newSteps) throws IllegalArgumentException {
    if (newSteps < 0) {
      throw new IllegalArgumentException("Steps must be a positive number");
    }
    this.totalSteps += newSteps;
  }

  public void resetTotalSteps() {
    this.totalSteps = 0;
  }


  /**
   * Method for running the chaos game.
   * Selects which runSteps method to use based if it has a list of probabilities.
   * Notifies observers after running the steps.
   */
  public void runSteps() {
    if (description.getProbabilities() != null) {
      runStepsWithProbabilities(steps, description.getProbabilities());
    } else {
      runStepsUniform(steps);
    }
    notifyObservers();
  }

  /**
   * Method for running the chaos game. Randomly selects a transformation
   * from the description and applies it to the current point.
   *
   * @param steps Number of steps to run
   */
  private void runStepsUniform(int steps) {
    currentPoint = new Vector2D(0.0, 0.0);
    for (int i = 0; i < steps; i++) {
      int transformIndex = random.nextInt(description.getTransforms().size());
      currentPoint = description.getTransforms().get(transformIndex).transform(currentPoint);
      canvas.putPixelChaos(currentPoint);
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
    currentPoint = new Vector2D(0.0, 0.0);
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
      canvas.putPixelChaos(currentPoint);
    }
  }

  /**
   * Method for registering an observer.
   *
   * @param gameObserver Observer to register
   */
  @Override
  public void registerObserver(Observer gameObserver) {
    gameObservers.add(gameObserver);
  }

  /**
   * Method for removing an observer.
   *
   * @param gameObserver Observer to remove
   */
  @Override
  public void removeObserver(Observer gameObserver) {
    gameObservers.remove(gameObserver);
  }

  /**
   * Method for notifying observers.
   */
  @Override
  public void notifyObservers() {
    for (Observer gameObserver : gameObservers) {
      gameObserver.update();
    }
  }
}

