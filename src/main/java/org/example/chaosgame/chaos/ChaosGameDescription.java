package org.example.chaosgame.chaos;

import java.util.List;
import org.example.chaosgame.linalg.Vector2D;
import org.example.chaosgame.transformations.Transform2D;


/**
 * This class represents a chaos game description.
 * It contains the minimum and maximum coordinates of the game area,
 * and a list of transformations to apply to the points.
 */
public class ChaosGameDescription {
  private final Vector2D minCoords;
  private final Vector2D maxCoords;
  private final List<Transform2D> transforms;

  private final List<Integer> probabilities;

  /**
   * Constructor for ChaosGameDescription.
   *
   * @param minCoords Minimum coordinates of the game area
   *
   * @param maxCoords Maximum coordinates of the game area
   *
   * @param transforms List of transformations to apply to the points
   */
  public ChaosGameDescription(Vector2D minCoords, Vector2D maxCoords,
                              List<Transform2D> transforms) {
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;
    this.transforms = transforms;
    this.probabilities = null;
  }

  public ChaosGameDescription(Vector2D minCoords, Vector2D maxCoords,
                              List<Transform2D> transforms, List<Integer> probabilities) {
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;
    this.transforms = transforms;
    this.probabilities = probabilities;
  }

  public Vector2D getMinCoords() {
    return minCoords;
  }

  public Vector2D getMaxCoords() {
    return maxCoords;
  }

  public List<Transform2D> getTransforms() {
    return transforms;
  }

  public List<Integer> getProbabilities() {
    return probabilities;
  }
}
