package org.example.chaosgame.model.transformations;

import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.linalg.Vector2D;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ExploreJuliaTest {
  private ExploreJulia exploreJulia;
  private Complex complexPoint;

  @BeforeEach
  void setUp() {
    complexPoint = new Complex(0.355, 0.355);
    exploreJulia = new ExploreJulia(complexPoint);
  }

  @AfterEach
  void tearDown() {
    complexPoint = null;
    exploreJulia = null;
  }

  @Test
  void testGetComplex() {
    assertEquals(complexPoint, exploreJulia.getComplex());
  }
  @Nested
  @DisplayName("Test transforms")
  class TestTransforms {
    @Test
    @DisplayName("Test positive transform")
    void testTransform() {
      Vector2D point = new Vector2D(0.5, 0.5);
      Vector2D transformedPoint = exploreJulia.transform(point);
      assertEquals(new Vector2D(0.355, 0.855), transformedPoint);
    }

    @Test
    @DisplayName("Test negative transform")
    void testTransformFail() {
      Vector2D point = new Vector2D(-0.5, -0.5);
      Vector2D transformedPoint = exploreJulia.transform(point);
      assertNotEquals(new Vector2D(0.355, -0.145), transformedPoint);
    }
  }
}
