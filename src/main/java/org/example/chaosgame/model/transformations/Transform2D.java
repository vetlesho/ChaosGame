package org.example.chaosgame.model.transformations;

import org.example.chaosgame.model.linalg.Vector2D;

/**
 * Interface for 2D transformations.
 */
public interface Transform2D {
  public Vector2D transform(Vector2D point);

}
