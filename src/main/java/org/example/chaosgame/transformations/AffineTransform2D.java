package org.example.chaosgame.transformations;

import org.example.chaosgame.linalg.Matrix2x2;
import org.example.chaosgame.linalg.Vector2D;

public class AffineTransform2D implements Transform2D{
  private final Matrix2x2 matrix;
  private final Vector2D vector;

  public AffineTransform2D(Matrix2x2 matrix, Vector2D vector) {
    this.matrix = matrix;
    this.vector = vector;
  }

  @Override
  public Vector2D transform(Vector2D point) {
    return matrix.multiply(point).add(vector);
  }
}
