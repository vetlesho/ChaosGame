package org.example.chaosgame.transformations;

import org.example.chaosgame.linalg.Matrix2x2;
import org.example.chaosgame.linalg.Vector2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AffineTransform2DTest {
  private final Matrix2x2 matrix = new Matrix2x2(0.5, 1.0, 1.0, 0.5);
  private final Vector2D vector = new Vector2D(3, 1);

  private final AffineTransform2D affineTransform2D = new AffineTransform2D(matrix, vector);
  @Test
  void transform() {
    Vector2D testAffine = affineTransform2D.transform(new Vector2D(1, 2));
    assertEquals(5.5, testAffine.getX());
    assertEquals(3.0, testAffine.getY());
  }
}