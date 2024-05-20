package org.example.chaosgame.model.chaos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.example.chaosgame.controller.ChaosGameDescriptionFactory;
import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.linalg.Matrix2x2;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.AffineTransform2D;
import org.example.chaosgame.model.transformations.JuliaTransform;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ChaosGameDescriptionFactoryTest {
  private static ChaosGameDescription expectedJulia;
  private static ChaosGameDescription expectedSierpinski;
  private static ChaosGameDescription expectedBarnsley;

  @BeforeAll
  static void setUp() {
    expectedJulia = new ChaosGameDescription(
            new Vector2D(-1.6, -1.0),
            new Vector2D(1.6, 1.0),
            List.of(
                    new JuliaTransform(new Complex(-0.70176, -0.3842), 1)
            )
    );

    expectedSierpinski = new ChaosGameDescription(
            new Vector2D(0.0, 0.0),
            new Vector2D(1.0, 1.0),
            List.of(
                    new AffineTransform2D(new Matrix2x2(0.5, 0.0, 0.0, 0.5),
                            new Vector2D(0.0, 0.0)),
                    new AffineTransform2D(new Matrix2x2(0.5, 0.0, 0.0, 0.5),
                            new Vector2D(0.25, 0.50)),
                    new AffineTransform2D(new Matrix2x2(0.5, 0.0, 0.0, 0.5),
                            new Vector2D(0.5, 0.0))
            )
    );

    expectedBarnsley = new ChaosGameDescription(
            new Vector2D(-2.65, 0.0),
            new Vector2D(2.65, 10.0),
            List.of(
                    new AffineTransform2D(new Matrix2x2(0.0, 0.0, 0.0, 0.16),
                            new Vector2D(0.0, 0.0)),
                    new AffineTransform2D(new Matrix2x2(0.85, 0.04, -0.04, 0.85),
                            new Vector2D(0.0, 1.60)),
                    new AffineTransform2D(new Matrix2x2(0.20, -0.26, 0.23, 0.22),
                            new Vector2D(0.0, 1.60)),
                    new AffineTransform2D(new Matrix2x2(-0.15, 0.28, 0.26, 0.24),
                            new Vector2D(0.0, 0.44))
            ), List.of(2, 84, 7, 7)
    );
  }


  @Nested
  class JuliaTests {
    @Test
    void testJuliaNotNull() {
      ChaosGameDescription juliaResult = ChaosGameDescriptionFactory.get(ChaosGameType.JULIA);
      assertNotNull(juliaResult);
    }

    @Test
    void testJuliaInstanceOf() {
      ChaosGameDescription juliaResult = ChaosGameDescriptionFactory.get(ChaosGameType.JULIA);
      assertInstanceOf(ChaosGameDescription.class, juliaResult);
    }

    @Test
    void testJuliaEquals() {
      ChaosGameDescription juliaResult = ChaosGameDescriptionFactory.get(ChaosGameType.JULIA);
      assertEquals(expectedJulia, juliaResult);
    }
  }

  @Nested
  class SierpinskiTests {
    @Test
    void testSierpinskiNotNull() {
      ChaosGameDescription sierpinskiResult =
              ChaosGameDescriptionFactory.get(ChaosGameType.SIERPINSKI);
      assertNotNull(sierpinskiResult);
    }

    @Test
    void testSierpinskiInstanceOf() {
      ChaosGameDescription sierpinskiResult =
              ChaosGameDescriptionFactory.get(ChaosGameType.SIERPINSKI);
      assertInstanceOf(ChaosGameDescription.class, sierpinskiResult);
    }

    @Test
    void testSierpinskiEquals() {
      ChaosGameDescription sierpinskiResult =
              ChaosGameDescriptionFactory.get(ChaosGameType.SIERPINSKI);
      assertEquals(expectedSierpinski, sierpinskiResult);
    }
  }

  @Nested
  class BarnsleyTests {
    @Test
    void testBarnsleyNotNull() {
      ChaosGameDescription barnsleyResult =
              ChaosGameDescriptionFactory.get(ChaosGameType.BARNSLEY);
      assertNotNull(barnsleyResult);
    }

    @Test
    void testBarnsleyInstanceOf() {
      ChaosGameDescription barnsleyResult = ChaosGameDescriptionFactory.get(ChaosGameType.BARNSLEY);
      assertInstanceOf(ChaosGameDescription.class, barnsleyResult);
    }

    @Test
    void testBarnsleyEquals() {
      ChaosGameDescription barnsleyResult = ChaosGameDescriptionFactory.get(ChaosGameType.BARNSLEY);
      assertEquals(expectedBarnsley, barnsleyResult);
    }
  }

  @Test
  void testInvalidType() {
    assertThrows(IllegalArgumentException.class, () -> ChaosGameDescriptionFactory.get(null));
  }

  @AfterAll
  static void tearDown() {
    expectedJulia = null;
    expectedSierpinski = null;
    expectedBarnsley = null;
  }
}