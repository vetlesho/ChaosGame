package org.example.chaosgame.linalg;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2DTest {
  private final double x = 1.0;
  private final double y = 2.0;
  Vector2D vector = new Vector2D(x, y);


  @Test
  void getX() {
    assertEquals(1.0, vector.getX());
  }

  @Test
  void getY() {
    assertEquals(2.0, vector.getY());
  }

  @Test
  void add() {
    Vector2D w = vector.add(new Vector2D(1.0, 1.0));
    assertEquals(2.0, w.getX());
    assertEquals(3.0, w.getY());
  }

  @Test
  void subtract() {
    Vector2D w = vector.subtract(new Vector2D(1.0, 1.0));
    assertEquals(0.0, w.getX());
    assertEquals(1.0, w.getY());
  }
}