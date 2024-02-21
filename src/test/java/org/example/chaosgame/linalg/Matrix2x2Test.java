package org.example.chaosgame.linalg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Matrix2x2Test {
  private final double a = 1.0;
  private final double b = 2.0;
  private final double c = 3.0;
  private final double d = 4.0;
  Matrix2x2 matrix = new Matrix2x2(a, b, c, d);
  Vector2D vector = new Vector2D(1.0, 2.0);

  @Test
  void getA() {
  }

  @Test
  void getB() {
  }

  @Test
  void getC() {
  }

  @Test
  void getD() {
  }

  @Test
  void multiply() {
    Vector2D w = matrix.multiply(vector);
    assertEquals(5.0, w.getX());
    assertEquals(11.0, w.getY());
  }
}