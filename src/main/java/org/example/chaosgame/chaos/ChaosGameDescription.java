package org.example.chaosgame.chaos;

import org.example.chaosgame.linalg.Vector2D;
import org.example.chaosgame.transformations.Transform2D;

import java.util.List;

public class ChaosGameDescription {
  private final Vector2D minCoords;
  private final Vector2D maxCoords;
  private final List<Transform2D> transforms;

  public ChaosGameDescription(Vector2D minCoords, Vector2D maxCoords, List<Transform2D> transforms) {
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;
    this.transforms = transforms;
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
}
