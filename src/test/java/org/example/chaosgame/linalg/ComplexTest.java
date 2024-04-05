package org.example.chaosgame.linalg;

import org.example.chaosgame.model.linalg.Complex;
import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;

class ComplexTest {
  private double x;
  private double y;
  private Complex complex;

  @BeforeEach
  void setUp() {
    x = 0.1;
    y = -0.4;
    complex = new Complex(x, y);
  }

  @AfterEach
  void tearDown() {
    complex = null;
  }

  @Nested
  @DisplayName("Test sqrt")
  class TestSqrt {
    @Test
    @DisplayName("Test sqrt should work")
    void sqrt() {
      Complex newComplex = complex.sqrt(x, y);
      assertEquals(0.5061178531536732, newComplex.getX()); //r-part is based on the example in the task description.
      assertEquals(-0.3951648786024424, newComplex.getY()); //i-part is based on the example in the task description.
    }

    @Test
    @DisplayName("Test sqrt should not work")
    void sqrtFail() {
      Complex newComplex = complex.sqrt(x, y);
      assertNotEquals(0.1, newComplex.getX());
      assertNotEquals(-0.4, newComplex.getY());
    }
  }
}