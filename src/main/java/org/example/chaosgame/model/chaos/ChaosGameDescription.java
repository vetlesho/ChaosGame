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
    validateCoordinates(minCoords, maxCoords);
    validateTransforms(transforms);
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
                              List<Transform2D> transforms, List<Integer> probabilities)
          throws IllegalArgumentException {
    validateCoordinates(minCoords, maxCoords);
    validateTransforms(transforms);
    if (probabilities != null && probabilities.size() != transforms.size()) {
      throw new IllegalArgumentException("Probabilities must match the number of transformations");
    }
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;
    this.transforms = transforms;
    this.probabilities = probabilities;
  }

  /**
   * Method for validating the coordinates.
   *
   * @param minCoords Minimum coordinates of the game area
   *
   * @param maxCoords Maximum coordinates of the game area
   */
  private void validateCoordinates(Vector2D minCoords, Vector2D maxCoords) {
    if (minCoords.getX() < -50 || minCoords.getY() < -50
            || minCoords.getX() > 50 || minCoords.getY() > 50
            || maxCoords.getX() > 50 || maxCoords.getY() > 50
            || maxCoords.getX() < -50 || maxCoords.getY() < -50) {
      throw new IllegalArgumentException("Coordinates must be between -50 and 50");
    } else if (minCoords.getX() > maxCoords.getX() || minCoords.getY() > maxCoords.getY()) {
      throw new IllegalArgumentException(
              "Minimum coordinates must be less than maximum coordinates");
    }
    if (minCoords.equals(maxCoords)) {
      throw new IllegalArgumentException("Minimum and maximum coordinates cannot be the same");
    }
  }

  /**
   * Method for validating the transformations.
   *
   * @param transforms List of transformations to apply to the points
   */
  private void validateTransforms(List<Transform2D> transforms) {
    if (transforms == null) {
      throw new IllegalArgumentException("Transformations cannot be null");
    }
    if (transforms.size() > 4 || transforms.isEmpty()) {
      throw new IllegalArgumentException("Number of transformations must be between 1 and 4");
    }
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
    validateTransforms(transforms);
    this.transforms = transforms;
  }

  public void setMinCoords(Vector2D minCoords) {
    this.minCoords = minCoords;
  }

  public void setMaxCoords(Vector2D maxCoords) {
    this.maxCoords = maxCoords;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChaosGameDescription that = (ChaosGameDescription) o;
    return Objects.equals(minCoords, that.minCoords)
            && Objects.equals(maxCoords, that.maxCoords)
            && Objects.equals(transforms, that.transforms)
            && Objects.equals(probabilities, that.probabilities);
  }

  @Override
  public int hashCode() {
    return Objects.hash(minCoords, maxCoords, transforms, probabilities);
  }
}
