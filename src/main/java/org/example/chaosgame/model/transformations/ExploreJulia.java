package org.example.chaosgame.model.transformations;

import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.linalg.Vector2D;

public class ExploreJulia implements Transform2D{
  private final Complex point;

  /**
   * Constructor for JuliaTransform.
   *
   * @param point the complex number c
   */
  public ExploreJulia(Complex point) {
    this.point = point;
  }

  public Complex getComplex() {
    return point;
  }

  /**
   * Method to transform a 2D vector using the Julia transformation.
   * The transformation is given by the formula:
   * <br>
   * <span style="font-family: Courier">
   *   z → z<sup>2</sup> + c
   *
   * @param point the 2D vector to transform
   * @return a new 2D vector
   */
  @Override
  public Vector2D transform(Vector2D point) {
    double temp = point.getX() * point.getX() - point.getY() * point.getY();
    double b = 2 * point.getX() * point.getY() + this.point.getY();
    double a = temp + this.point.getX();
    return new Vector2D(a, b);
  }

}
