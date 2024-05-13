package org.example.chaosgame.model.transformations;

import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.linalg.Vector2D;

/**
 * Class for the Julia transformation.
 * The transformation is given by the formula:
 * <br>
 * <span style="font-family: Courier">
 *  z → &#177;&radic;&#x305;z&#x305; &#x305;-&#x305; &#x305;c
 *</span>
 *
 */
public class JuliaTransform implements Transform2D {
  private final Complex point;
  private final int sign;

  public Complex getComplex() {
    return point;
  }

  /**
   * Constructor for JuliaTransform.
   *
   * @param point the complex number c
   * @param sign the sign of the transformation
   */
  public JuliaTransform(Complex point, int sign) {
    this.point = point;
    this.sign = sign;
  }

  /**
   * Method to transform a 2D vector using the Julia transformation.
   * The transformation is given by the formula:
   * <br>
   * <span style="font-family: Courier">
   *   z → &#177;&radic;&#x305;z&#x305; &#x305;-&#x305; &#x305;c
   *
   * @param point the 2D vector to transform
   * @return a new 2D vector
   */
  @Override
  public Vector2D transform(Vector2D point) {
    Vector2D result = this.point.sqrt(
            point.getX() - this.point.getX(),
            point.getY() - this.point.getY());
    double a = sign * result.getX();
    double b = sign * result.getY();
    return new Vector2D(a, b);
  }
}
