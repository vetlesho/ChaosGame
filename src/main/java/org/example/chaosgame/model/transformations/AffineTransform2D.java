package org.example.chaosgame.model.transformations;

import org.example.chaosgame.model.linalg.Matrix2x2;
import org.example.chaosgame.model.linalg.Vector2D;

/**
 * Record for 2D affine transformations.
 * The transformation is represented by a 2x2 matrix and a 2D vector.
 * The transformation is applied to a 2D point by first multiplying the point with the matrix
 * and then adding the vector.
 */
public record AffineTransform2D(Matrix2x2 matrix, Vector2D vector) implements Transform2D {
  /**
   * Constructor for AffineTransform2D.
   * An affine transformation is represented by a 2x2 matrix and a 2D vector.
   * The transformation is applied to a 2D point by first multiplying the point with the matrix
   * and then adding the vector.
   *
   * @param matrix the 2x2 matrix
   * @param vector the 2D vector
   */
  public AffineTransform2D {
  }

  /**
   * Transforms a 2D point using this affine transformation.
   * Overridden from the Transform2D interface.
   *
   * @param point the point to transform
   * @return the transformed point
   */
  @Override
  public Vector2D transform(Vector2D point) {
    return matrix.multiply(point).add(vector);
  }
}
