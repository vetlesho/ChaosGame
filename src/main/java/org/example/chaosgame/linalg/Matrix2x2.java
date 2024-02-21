package org.example.chaosgame.linalg;

public class Matrix2x2 {
  private final double a, b, c, d;

  public Matrix2x2(double a, double b, double c, double d) {
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
  }

  public double getA() {
    return a;
  }

  public double getB() {
    return b;
  }

  public double getC() {
    return c;
  }

  public double getD() {
    return d;
  }


  public Vector2D multiply(Vector2D vector) {
    return new Vector2D(
      a * vector.getX() + b * vector.getY(),
      c * vector.getX() + d * vector.getY()
    );
  }
}
