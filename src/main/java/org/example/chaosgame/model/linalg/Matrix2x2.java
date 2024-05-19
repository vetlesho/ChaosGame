package org.example.chaosgame.model.linalg;


/**
 * Record for 2x2 matrices.
 * The matrices are represented by four double values: a, b, c, and d.
 *
 * <br>
 * [ a b ]
 * <br>
 * [ c d ]
 *
 * <p>Chosen to use a, b, c, d instead of the traditional row, column notation for simplicity.
 */
public record Matrix2x2(double a, double b, double c, double d) {
  /**
   * Constructor for Matrix2x2.
   *
   * @param a first row, first column in the matrix.
   * @param b first row, second column in the matrix.
   * @param c second row, first column in the matrix.
   * @param d second row, second column in the matrix.
   */
  public Matrix2x2 {
  }


  /**
   * Method to multiply a 2x2 matrix with a 2D vector.
   *
   * @param vector the vector to multiply with.
   * @return a new 2D vector.
   */
  public Vector2D multiply(Vector2D vector) {
    return new Vector2D(
            this.a * vector.getX() + this.b * vector.getY(),
            this.c * vector.getX() + this.d * vector.getY()
    );
  }
}
