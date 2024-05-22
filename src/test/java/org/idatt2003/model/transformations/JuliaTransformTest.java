package org.idatt2003.model.transformations;

import org.idatt2003.model.linalg.Vector2D;
import org.idatt2003.model.linalg.Complex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JuliaTransformTest {
  private final Complex point = new Complex(0.3, 0.6);
  private final int sign = 1;

  private final JuliaTransform juliaTransform = new JuliaTransform(point, sign);
  @Test
  void transform() {
    Vector2D testJulia = juliaTransform.transform(new Vector2D(0.4, 0.2));
    assertEquals(Math.abs(0.5061178531536732), Math.abs(testJulia.getX()));
    assertEquals(Math.abs(-0.3951648786024423), Math.abs(testJulia.getY()));
  }
}