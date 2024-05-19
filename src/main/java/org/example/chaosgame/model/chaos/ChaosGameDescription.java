package org.example.chaosgame.model.chaos;

import java.util.List;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.Transform2D;


/**
 * This class represents a chaos game description.
 * It contains the minimum and maximum coordinates of the game area,
 * and a list of transformations to apply to the points.
 */
public class ChaosGameDescription {
  private Vector2D minCoords;
  private Vector2D maxCoords;
  private List<Transform2D> transforms;

  private List<Integer> probabilities;

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
  }

  /**
   * Second constructor for ChaosGameDescription.
   * Includes a list of probabilities for the transformations.
   *
   * @param minCoords Minimum coordinates of the game area
   *
   * @param maxCoords Maximum coordinates of the game area
   *
   * @param transforms List of transformations to apply to the points
   *
   * @param probabilities List of probabilities for the transformations
   */
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

  public void setTransforms(List<Transform2D> transforms) {
    this.transforms = transforms;
  }

  public void setMinCoords(Vector2D minCoords) {
    this.minCoords = minCoords;
  }

  public void setMaxCoords(Vector2D maxCoords) {
    this.maxCoords = maxCoords;
  }
}
