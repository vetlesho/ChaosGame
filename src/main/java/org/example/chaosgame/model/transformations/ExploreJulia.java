package org.example.chaosgame.model.transformations;

import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.linalg.Vector2D;

public class ExploreJulia implements Transform2D{
  private final Complex point;

  public ExploreJulia(Complex point) {
    this.point = point;
  }

  @Override
  public Vector2D transform(Vector2D point) {
    double temp = point.getX() * point.getX() - point.getY() * point.getY();
    double b = 2 * point.getX() * point.getY() + this.point.getY();
    double a = temp + this.point.getX();
    return new Vector2D(a, b);
  }
}
