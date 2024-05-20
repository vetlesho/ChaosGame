package org.example.chaosgame.model.chaos;

import java.util.List;
import java.util.Objects;

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

  /**
   * Equals method for ChaosGameDescription.
   * Overrides the default equals method.
   * Compares the minimum and maximum coordinates, the list of transformations,
   * and the list of probabilities.
   *
   * @param o Object to compare
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ChaosGameDescription that = (ChaosGameDescription) o;
    return Objects.equals(minCoords, that.minCoords)
            && Objects.equals(maxCoords, that.maxCoords)
            && Objects.equals(transforms, that.transforms)
            && Objects.equals(probabilities, that.probabilities);
  }

  /**
   * Hashcode method for ChaosGameDescription.
   * Overrides the default hashcode method.
   * Use the minimum and maximum coordinates, the list of transformations,
   * and the list of probabilities to generate the hashcode.
   *
   * @return the hashcode of the ChaosGameDescription object
   */
  @Override
  public int hashCode() {
    return Objects.hash(minCoords, maxCoords, transforms, probabilities);
  }
}
