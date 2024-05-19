package org.example.chaosgame.model.chaos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.example.chaosgame.controller.observer.Observer;
import org.example.chaosgame.controller.observer.Subject;
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

  public void setSteps(int steps) {
    this.steps = steps;
  }


  /**
   * Method for setting the total number of steps.
   *
   * @param totalSteps Number of steps
   */
  public void setTotalSteps(int totalSteps) {
    this.totalSteps = totalSteps;
  }


  /**
   * Method for adding steps to the total number of steps.
   *
   * @param newSteps Number of steps to add
   */
  public void addTotalSteps(int newSteps) {
    this.totalSteps += newSteps;
  }

  /**
   * Method for resetting the total number of steps.
   */
  public void resetTotalSteps() {
    this.totalSteps = 0;
  }


  /**
   * Method for running the chaos game. Randomly selects a transformation
   * from the description and applies it to the current point.
   */
  public void runSteps() {
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

  public void setChaosGameDescription(ChaosGameDescription newDescription) {
    if (!this.description.equals(newDescription)) {
      this.resetTotalSteps();
    }
    this.description = newDescription;
    this.totalSteps = 0;
    setChaosCanvas(description.getMinCoords(), description.getMaxCoords());
    notifyObservers();
  }

  public void setChaosCanvas(Vector2D minCoords, Vector2D maxCoords) {
    this.canvas.clearCanvas();
    this.canvas.setMinCoords(minCoords);
    this.canvas.setMaxCoords(maxCoords);
    this.canvas.setTransformCoordsToIndices();
  }

  @Override
  public void registerObserver(Observer gameObserver) {
    gameObservers.add(gameObserver);
    System.out.println("Observer added");
  }

  @Override
  public void removeObserver(Observer gameObserver) {
    gameObservers.remove(gameObserver);
    System.out.println("Observer removed");
  }

  @Override
  public void notifyObservers() {
    for (Observer gameObserver : gameObservers) {
      gameObserver.update();
      System.out.println("Observer notified drawed");
    }
  }
}

