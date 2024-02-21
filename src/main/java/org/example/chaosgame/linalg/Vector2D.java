package org.example.chaosgame.linalg;

public class Vector2D {
  private final double x;
  private final double y;

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

  public Vector2D add(Vector2D other) {
    return new Vector2D(x + other.x, y + other.y);
  }

  public Vector2D subtract(Vector2D other) {
    return new Vector2D(x - other.x, y - other.y);
  }


}
