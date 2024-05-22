package org.idatt2003.model.transformations;

import org.idatt2003.model.linalg.Vector2D;

/**
 * Interface for 2D transformations.
 * Implementing classes should transform a 2D point.
 */
public interface Transform2D {
  Vector2D transform(Vector2D point);
}
