package org.example.chaosgame.transformations;

import org.example.chaosgame.linalg.Vector2D;

/**
 * Interface for 2D transformations.
 */
public interface Transform2D {
  public Vector2D transform(Vector2D point);

}
