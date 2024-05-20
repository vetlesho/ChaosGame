package org.example.chaosgame.model.chaos;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.example.chaosgame.model.linalg.Matrix2x2;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.AffineTransform2D;
import org.example.chaosgame.model.transformations.Transform2D;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ChaosGameDescriptionTest {
  private ChaosGameDescription chaosGameDescription;
  private Vector2D minCoords;
  private Vector2D maxCoords;
  private Matrix2x2 matrixA;
  private Vector2D vectorPartA;
  private Matrix2x2 matrixB;
  private Vector2D vectorPartB;
  private List<Transform2D> transforms;
  private List<Integer> probabilities;

  @BeforeEach
  void setUp() {
    minCoords = new Vector2D(0, 0);
    maxCoords = new Vector2D(1, 1);

    matrixA = new Matrix2x2(0.5, 0.0, 0.0, 0.5);
    vectorPartA = new Vector2D(0.0, 0.0);
    matrixB = new Matrix2x2(0.5, 0.0, 0.0, 0.5);
    vectorPartB = new Vector2D(0.25, 0.50);
    transforms = Arrays.asList(new AffineTransform2D(matrixA, vectorPartA),
            new AffineTransform2D(matrixB, vectorPartB));
    probabilities = Arrays.asList(50, 50);
    chaosGameDescription = new ChaosGameDescription(
            minCoords, maxCoords, transforms, probabilities);
  }

  @AfterEach
  void tearDown() {
    minCoords = null;
    maxCoords = null;
    transforms = null;
    probabilities = null;
    chaosGameDescription = null;
  }

  @Test
  void testGetMinCoords() {
    assertEquals(minCoords, chaosGameDescription.getMinCoords());
  }

  @Test
  void testGetMaxCoords() {
    assertEquals(maxCoords, chaosGameDescription.getMaxCoords());
  }

  @Test
  void testGetTransforms() {
    assertEquals(transforms, chaosGameDescription.getTransforms());
  }

  @Test
  void testGetProbabilities() {
    assertEquals(probabilities, chaosGameDescription.getProbabilities());
  }

  @Nested
  @DisplayName("SetTransforms")
  class SetTransforms {
    @Test
    @DisplayName("Set null transforms")
    void testSetNullTransforms() {
      assertThrows(IllegalArgumentException.class, () -> chaosGameDescription.setTransforms(null));
    }

    @Test
    @DisplayName("Set empty transforms")
    void testSetEmptyTransforms() {
      assertThrows(IllegalArgumentException.class,
              () -> chaosGameDescription.setTransforms(List.of()));
    }

    @Test
    @DisplayName("Set too many transforms")
    void testSetTooManyTransforms() {
      List<Transform2D> tooManyTransforms = Arrays.asList(
              new AffineTransform2D(matrixA, vectorPartA),
              new AffineTransform2D(matrixB, vectorPartB),
              new AffineTransform2D(matrixA, vectorPartA),
              new AffineTransform2D(matrixB, vectorPartB),
              new AffineTransform2D(matrixA, vectorPartA)
      );
      assertThrows(IllegalArgumentException.class,
              () -> chaosGameDescription.setTransforms(tooManyTransforms));
    }

    @Test
    @DisplayName("Set valid transforms")
    void testSetValidTransforms() {
      Matrix2x2 newMatrix = new Matrix2x2(9.9, 9.9, 9.9, 9.9);
      Vector2D newVector = new Vector2D(9.9, 9.9);
      List<Transform2D> newTransforms = List.of(new AffineTransform2D(newMatrix, newVector));
      chaosGameDescription.setTransforms(newTransforms);
      assertEquals(newTransforms, chaosGameDescription.getTransforms());
    }
  }

  @Nested
  @DisplayName("Set coordinates")
  class SetCoordinates {
    @Test
    @DisplayName("Set min coordinates")
    void testSetMinCoords() {
      Vector2D newMinCoords = new Vector2D(-1, -1);
      chaosGameDescription.setMinCoords(newMinCoords);
      assertEquals(newMinCoords, chaosGameDescription.getMinCoords());
    }

    @Test
    @DisplayName("Set max coordinates")
    void testSetMaxCoords() {
      Vector2D newMaxCoords = new Vector2D(2, 2);
      chaosGameDescription.setMaxCoords(newMaxCoords);
      assertEquals(newMaxCoords, chaosGameDescription.getMaxCoords());
    }

    @Test
    @DisplayName("Set min and max coordinates")
    void testSetMinAndMaxCoords() {
      Vector2D newMinCoords = new Vector2D(-99, -99);

      assertThrows(IllegalArgumentException.class,
              () -> new ChaosGameDescription(newMinCoords, maxCoords, transforms, probabilities));
      assertThrows(IllegalArgumentException.class,
              () -> new ChaosGameDescription(maxCoords, minCoords, transforms, probabilities));
      assertThrows(IllegalArgumentException.class,
              () -> new ChaosGameDescription(minCoords, minCoords, transforms, probabilities));
    }
  }

  @Test
  void testEquals() {
    ChaosGameDescription sameChaosGameDescription =
            new ChaosGameDescription(minCoords, maxCoords, transforms, probabilities);
    assertEquals(chaosGameDescription, sameChaosGameDescription);
    assertNotNull(chaosGameDescription);
    assertNotEquals(chaosGameDescription, new Object());
    ChaosGameDescription differentChaosGameDescription =
            new ChaosGameDescription(
                    new Vector2D(1, 1),
                    new Vector2D(2, 2),
                    transforms, probabilities);
    assertNotEquals(chaosGameDescription, differentChaosGameDescription);
  }

  @Test
  void testHashCode() {
    ChaosGameDescription sameChaosGameDescription
            = new ChaosGameDescription(
                    minCoords, maxCoords,
            transforms, probabilities);
    assertEquals(chaosGameDescription.hashCode(), sameChaosGameDescription.hashCode());
  }
}
