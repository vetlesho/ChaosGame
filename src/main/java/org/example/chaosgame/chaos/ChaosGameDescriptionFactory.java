package org.example.chaosgame.chaos;

import java.util.List;
import org.example.chaosgame.linalg.Complex;
import org.example.chaosgame.linalg.Matrix2x2;
import org.example.chaosgame.linalg.Vector2D;
import org.example.chaosgame.transformations.AffineTransform2D;
import org.example.chaosgame.transformations.JuliaTransform;

/**
 * Factory class for creating ChaosGameDescription objects.
 */
public class ChaosGameDescriptionFactory {
  public static ChaosGameDescription get(String description) {
    return switch (description) {
      case "Julia" -> new ChaosGameDescription(
              new Vector2D(-1.6, -1),
              new Vector2D(1.6, 1),
              List.of(
                      new JuliaTransform(new Complex(-0.70176, -0.3842), 1)
              )
      );
      case "Barnsley" -> new ChaosGameDescription(
              new Vector2D(-2.65, 0.0),
              new Vector2D(2.65, 10.0),
              List.of(
                      new AffineTransform2D(new Matrix2x2(0.0, 0.0, 0.0, 0.16), new Vector2D(0.0, 0.0)),
                      new AffineTransform2D(new Matrix2x2(0.85, 0.04, -0.04, 0.85), new Vector2D(0.0, 1.60)),
                      new AffineTransform2D(new Matrix2x2(0.20, -0.26, 0.23, 0.22), new Vector2D(0.0, 1.60)),
                      new AffineTransform2D(new Matrix2x2(-0.15, 0.28, 0.26, 0.24), new Vector2D(0.0, 0.44))
              )
      );
      case "Sierpinski" -> new ChaosGameDescription(
              new Vector2D(0.0, 0.0),
              new Vector2D(1.0, 1.0),
              List.of(
                      new AffineTransform2D(new Matrix2x2(0.5, 0.0, 0.0, 0.5), new Vector2D(0.0, 0.0)),
                      new AffineTransform2D(new Matrix2x2(0.5, 0.0, 0.0, 0.5), new Vector2D(0.25, 0.50)),
                      new AffineTransform2D(new Matrix2x2(0.5, 0.0, 0.0, 0.5), new Vector2D(0.5, 0.0))
              )
      );
      default -> throw new IllegalArgumentException("Unknown chaos game description: " + description);
    };
  }
}

