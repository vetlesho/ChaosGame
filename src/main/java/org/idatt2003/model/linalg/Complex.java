package org.idatt2003.model.linalg;

import java.util.Random;

/**
 * Class for complex numbers, extends Vector2D.
 * Complex numbers are represented by a real part and an imaginary part.
 * The real part is represented by the x-coordinate,
 * and the imaginary part is represented by the y-coordinate.
 */
public class Complex extends Vector2D {

  /**
   * Constructor for Complex class.
   * Method super(x, y) is used to call the constructor of the superclass, Vector2D.
   *
   * @param real x-coordinate.
   * @param imaginary y-coordinate.
   */
  public Complex(double real, double imaginary) {
    super(real, imaginary);
  }

  /**
   * Method to calculate the square root of a complex number.
   * This formula describes the transformation:
   * <br>
   * <span style="font-family: Courier">
   *   z → &#177;&radic;&#x305;(&#x305;a&#x305; &#x305;+&#x305;
   *   &#x305;b&#x305;i&#x305;) </span>
   * where:
   * <ul>
   *   <li>a is the real part of the complex number</li>
   *   <li>b is the imaginary part of the complex number</li>
   *   <li>i is the imaginary unit</li>
   * </ul>
   *
   * @param realPart the real part of the complex number.
   * @param imaginaryPart the imaginary part of the complex number.
   * @return a new complex number that is the square root of the input realPart and imaginaryPart.
   */
  public Complex sqrt(double realPart, double imaginaryPart) {
    double a = Math.pow(realPart, 2) + Math.pow(imaginaryPart, 2);
    int random = new Random().nextInt(2) == 0 ? 1 : -1;
    double r = random * Math.sqrt(0.5 * (Math.sqrt(a) + realPart));
    double i = random * Math.signum(imaginaryPart) * Math.sqrt(0.5 * (Math.sqrt(a) - realPart));

    return new Complex(r, i);
  }
}
