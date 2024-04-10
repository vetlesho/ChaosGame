package org.example.chaosgame.model.transformations;

import org.example.chaosgame.model.linalg.Matrix2x2;
import org.example.chaosgame.model.linalg.Vector2D;

/**
 * Represents an affine transformation in 2D space.
 * The transformation is represented by a 2x2 matrix and a 2D vector.
 * The transformation is applied to a 2D point by first multiplying the point with the matrix
 * and then adding the vector.
 */
public class AffineTransform2D implements Transform2D {
  private final Matrix2x2 matrix;
  private final Vector2D vector;

  /**
   * Constructor for AffineTransform2D.
   * An affine transformation is represented by a 2x2 matrix and a 2D vector.
   * The transformation is applied to a 2D point by first multiplying the point with the matrix
   * and then adding the vector.
   *
   * @param matrix the 2x2 matrix
   * @param vector the 2D vector
   */
  public AffineTransform2D(Matrix2x2 matrix, Vector2D vector) {
    this.matrix = matrix;
    this.vector = vector;
  }

  /**
   * Transforms a 2D point using this affine transformation.
   * Overridden from the Transform2D interface.
   *
   * @param point the point to transform
   *
   * @return the transformed point
   */
  @Override
  public Vector2D transform(Vector2D point) {
    return matrix.multiply(point).add(vector);
  }

  public Matrix2x2 getMatrix() {
    return matrix;
  }

  public Vector2D getVector() {
    return vector;
  }
}
