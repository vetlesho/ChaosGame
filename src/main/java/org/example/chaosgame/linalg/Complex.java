package org.example.chaosgame.linalg;

/**
 * Class for complex numbers, extends Vector2D.
 * Complex numbers are represented by a real part and an imaginary part.
 * The real part is represented by the x-coordinate,
 * and the imaginary part is represented by the y-coordinate.
 */
public class Complex extends Vector2D {

  /**
   * Constructor for Complex class.
   * super(x, y) is used to call the constructor of the superclass, Vector2D.
   *
   * @param x x-coordinate.
   * @param y y-coordinate.
   */
  public Complex(double x, double y) {
    super(x, y);
  }

  /**
   * Method to calculate the square root of a complex number.
   *
   * @param realPart the real part of the complex number.
   * @param imaginaryPart the imaginary part of the complex number.
   * @return a new complex number that is the square root of the input cRe and cIm.
   */
  public Complex sqrt(double realPart, double imaginaryPart) {
    double a = Math.pow(realPart, 2) + Math.pow(imaginaryPart, 2);

    double r = Math.sqrt(0.5 * (Math.sqrt(a) + realPart));
    double i = Math.signum(imaginaryPart) * Math.sqrt(0.5 * (Math.sqrt(a) - realPart));

    return new Complex(r, i);
  }
}
