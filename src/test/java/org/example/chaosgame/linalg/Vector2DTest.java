package org.example.chaosgame.linalg;

import org.example.chaosgame.model.linalg.Vector2D;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class Vector2DTest {
  private double x;
  private double y;
  private Vector2D vector;
  private Vector2D otherVector;

  @BeforeEach
  void setUp() {
    x = 1.0;
    y = 2.0;
    vector = new Vector2D(x, y);
    otherVector = new Vector2D(1.0, 1.0);
  }

  @AfterEach
  void tearDown() {
    vector = null;
    otherVector = null;
  }

  @Nested
  @DisplayName("Test getX and getY")
  class TestGetters {
    @Test
    @DisplayName("Test getX")
    void getX() {
      assertEquals(x, vector.getX());
    }

    @Test
    @DisplayName("Test getY")
    void getY() {
      assertEquals(y, vector.getY());
    }
  }

  @Nested
  @DisplayName("Test add and subtract")
  class TestAddAndSubtract {
    @Test
    @DisplayName("Test add should work")
    void add() {
      Vector2D w = vector.add(otherVector);
      assertEquals(2.0, w.getX());
      assertEquals(3.0, w.getY());
    }

    @Test
    @DisplayName("Test add should not work")
    void addFail() {
      Vector2D w = vector.add(otherVector);
      assertNotEquals(1.0, w.getX());
      assertNotEquals(2.0, w.getY());
    }

    @Test
    @DisplayName("Test subtract should work")
    void subtract() {
      Vector2D w = vector.subtract(otherVector);
      assertEquals(0.0, w.getX());
      assertEquals(1.0, w.getY());
    }

    @Test
    @DisplayName("Test subtract should not work")
    void subtractFail() {
      Vector2D w = vector.subtract(otherVector);
      assertNotEquals(1.0, w.getX());
      assertNotEquals(2.0, w.getY());
    }
  }
}