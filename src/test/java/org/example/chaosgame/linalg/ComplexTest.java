package org.example.chaosgame.linalg;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;


import static org.junit.jupiter.api.Assertions.*;

class ComplexTest {
  private final double x = 0.1;
  private final double y = -0.4;

  Complex complex = new Complex(x, y);

  @Test
  void sqrt() {
    Complex z = complex.sqrt(x, y);
    assertEquals(0.5061178531536732, z.getX());
    assertEquals(-0.3951648786024424, z.getY());

  }
}