package org.example.chaosgame.model.chaos;

import org.example.chaosgame.model.linalg.Matrix2x2;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.Transform2D;
import org.example.chaosgame.model.transformations.AffineTransform2D;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

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
    transforms = Arrays.asList(new AffineTransform2D(matrixA, vectorPartA), new AffineTransform2D(matrixB, vectorPartB));
    probabilities = Arrays.asList(50, 50);
    chaosGameDescription = new ChaosGameDescription(minCoords, maxCoords, transforms, probabilities);
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

  @Test
  void testSetTransforms() {
    Matrix2x2 newMatrix = new Matrix2x2(9.9, 9.9, 9.9, 9.9);
    Vector2D newVector = new Vector2D(9.9, 9.9);
    List<Transform2D> newTransforms = Arrays.asList(new AffineTransform2D(newMatrix, newVector));
    chaosGameDescription.setTransforms(newTransforms);
    assertEquals(newTransforms, chaosGameDescription.getTransforms());
  }

  @Test
  void testSetMinCoords() {
    Vector2D newMinCoords = new Vector2D(-1, -1);
    chaosGameDescription.setMinCoords(newMinCoords);
    assertEquals(newMinCoords, chaosGameDescription.getMinCoords());
  }

  @Test
  void testSetMaxCoords() {
    Vector2D newMaxCoords = new Vector2D(2, 2);
    chaosGameDescription.setMaxCoords(newMaxCoords);
    assertEquals(newMaxCoords, chaosGameDescription.getMaxCoords());
  }

  @Test
  void testEquals() {
    ChaosGameDescription sameChaosGameDescription = new ChaosGameDescription(minCoords, maxCoords, transforms, probabilities);
    assertTrue(chaosGameDescription.equals(sameChaosGameDescription));
    assertFalse(chaosGameDescription == null);
    assertNotEquals(chaosGameDescription, new Object());
    ChaosGameDescription differentChaosGameDescription = new ChaosGameDescription(new Vector2D(1, 1), new Vector2D(2, 2), transforms, probabilities);
    assertFalse(chaosGameDescription.equals(differentChaosGameDescription));
  }

  @Test
  void testHashCode() {
    ChaosGameDescription sameChaosGameDescription = new ChaosGameDescription(minCoords, maxCoords, transforms, probabilities);
    assertEquals(chaosGameDescription.hashCode(), sameChaosGameDescription.hashCode());
  }
}
