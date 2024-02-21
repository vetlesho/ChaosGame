package org.example.chaosgame.transformations;

import org.example.chaosgame.linalg.Complex;
import org.example.chaosgame.linalg.Vector2D;

public class JuliaTransform implements Transform2D{
  private final Complex point;
  private final int sign;

  public JuliaTransform(Complex point, int sign) {
    this.point = point;
    this.sign = sign;
  }

  @Override
  public Vector2D transform(Vector2D point) {
    Vector2D result = this.point.sqrt(point.getX(), point.getY());
    double a = sign * result.getX();
    double b = sign * result.getY();
    return new Vector2D(a, b);
  }
}
