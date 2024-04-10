package org.example.chaosgame.linalg;

/**
 * Class for 2D vectors.
 * Vectors are represented by an x-coordinate and a y-coordinate.
 * The class contains methods for adding and subtracting vectors.
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

  public Vector2D scale(double scalar) {
    return new Vector2D(
            x * scalar,
            y * scalar);
  }

  public Vector2D multiply(Vector2D other) {
    return new Vector2D(
            x * other.x,
            y * other.y);
  }

  public Vector2D divide(Vector2D other) {
    return new Vector2D(
            x / other.x,
            y / other.y);
  }

  public double lengthSQ() {
    return x * x + y * y;
  }
}
