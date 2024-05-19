package org.example.chaosgame.model.transformations;

import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.JuliaTransform;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JuliaTransformTest {
  private final Complex point = new Complex(0.3, 0.6);
  private final int sign = 1;

  private final JuliaTransform juliaTransform = new JuliaTransform(point, sign);
  @Test
  void transform() {
    Vector2D testJulia = juliaTransform.transform(new Vector2D(0.4, 0.2));
    assertEquals(0.5061178531536732, testJulia.getX());
    assertEquals(-0.3951648786024423, testJulia.getY());

  }
}