package org.example.chaosgame.linalg;

public class Complex extends Vector2D {
  public Complex(double x, double y) {
    super(x, y);
  }

  public Complex sqrt(double cRe, double cIm) {
    double a = Math.pow(cRe, 2) + Math.pow(cIm, 2);

    double r = Math.sqrt(0.5 * (Math.sqrt(a) + cRe));
    double i = Math.signum(cIm) * Math.sqrt(0.5 * (Math.sqrt(a) - cRe));

    return new Complex(r, i);
  }
}
