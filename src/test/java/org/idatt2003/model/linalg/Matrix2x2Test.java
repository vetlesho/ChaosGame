package org.idatt2003.model.linalg;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class Matrix2x2Test {
  private  double a;
  private  double b;
  private  double c;
  private  double d;
  private Matrix2x2 matrix;
  private Vector2D vector;

  @BeforeEach
  void setUp() {
    a = 1.0;
    b = 2.0;
    c = 3.0;
    d = 4.0;
    matrix = new Matrix2x2(a, b, c, d);
    vector = new Vector2D(1.0, 2.0);
  }

  @AfterEach
  void tearDown() {
    matrix = null;
    vector = null;
  }

  @Nested
  @DisplayName("Test for getters")
  class TestGetters {
    @Test
    @DisplayName("Test getA")
    void getA() {
      assertEquals(a, matrix.a());
    }

    @Test
    @DisplayName("Test getB")
    void getB() {
      assertEquals(b, matrix.b());
    }

    @Test
    @DisplayName("Test getC")
    void getC() {
      assertEquals(c, matrix.c());
    }

    @Test
    @DisplayName("Test getD")
    void getD() {
      assertEquals(d, matrix.d());
    }
  }

  @Nested
  @DisplayName("Test multiply method")
  class TestMultiply {
    @Test
    @DisplayName("Test multiply should work")
    void multiply() {
      Vector2D w = matrix.multiply(vector);
      assertEquals(5.0, w.getX());
      assertEquals(11.0, w.getY());
    }

    @Test
    @DisplayName("Test multiply should not work")
    void multiplyFail() {
      Vector2D w = matrix.multiply(vector);
      assertNotEquals(1.0, w.getX());
      assertNotEquals(2.0, w.getY());
    }
  }
}