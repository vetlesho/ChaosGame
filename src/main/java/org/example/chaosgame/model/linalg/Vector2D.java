package org.example.chaosgame.model.linalg;

import java.util.Objects;

/**
 * Class for 2D vectors.
 * Vectors are represented by an x-coordinate and a y-coordinate.
 * The class contains methods for adding and subtracting vectors.
 *
 * <p>Chosen to use x and y instead of the traditional row, column notation for simplicity.
 */
public class Vector2D {
  private final double x;
  private final double y;

  /**
   * Constructor for Vector2D.
   *
   * @param x x-coordinate
   * @param y y-coordinate
   */
  public Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  /**
   * Add two vectors together.
   *
   * @param other the other vector
   * @return the sum of the two vectors
   */
  public Vector2D add(Vector2D other) {
    return new Vector2D(
            x + other.x,
            y + other.y);
  }

  /**
   * Subtract one vector from another.
   *
   * @param other the other vector
   * @return the difference of the two vectors
   */
  public Vector2D subtract(Vector2D other) {
    return new Vector2D(
            x - other.x,
            y - other.y);
  }

  /**
   * Scale a vector by a scalar.
   *
   * @param scalar the scalar to multiply the vector by
   * @return the scaled vector
   */
  public Vector2D scale(double scalar) {
    return new Vector2D(
            x * scalar,
            y * scalar);
  }

  /**
   * Multiply two vectors together.
   *
   * @param other the other vector
   * @return the product of the two vectors
   */
  public Vector2D multiply(Vector2D other) {
    return new Vector2D(
            x * other.x,
            y * other.y);
  }

  /**
   * Divide one vector by another.
   *
   * @param other the other vector
   * @return the quotient of the two vectors
   */
  public Vector2D divide(Vector2D other) {
    return new Vector2D(
            x / other.x,
            y / other.y);
  }

  /**
   * Calculate the length of a vector.
   *
   * @return the length of the vector
   */
  public double lengthSq() {
    return x * x + y * y;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Vector2D vector2D = (Vector2D) o;
    return Double.compare(x, vector2D.x) == 0 && Double.compare(y, vector2D.y) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
