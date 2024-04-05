package org.example.chaosgame.model.chaos;

import java.util.List;
import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.linalg.Matrix2x2;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.AffineTransform2D;
import org.example.chaosgame.model.transformations.JuliaTransform;

/**
 * Factory class for creating ChaosGameDescription objects.
 */
public class ChaosGameDescriptionFactory {
  enum ChaosGameType {
    JULIA,
    BARNSLEY,
    SIERPINSKI
  }

  public static ChaosGameDescription get(String description) {
    ChaosGameType type = ChaosGameType.valueOf(description.toUpperCase());
    return switch (type) {
      case JULIA -> createJulia();
      case BARNSLEY-> createBarnsley();
      case SIERPINSKI -> createSierpinski();
    };
  }

  private static ChaosGameDescription createJulia() {
    return new ChaosGameDescription(
            new Vector2D(-1.6, -1),
            new Vector2D(1.6, 1),
            List.of(
                    new JuliaTransform(new Complex(-0.835, 0.2321), 1)
            )
    );
  }

  private static ChaosGameDescription createSierpinski() {
    return new ChaosGameDescription(
            new Vector2D(0.0, 0.0),
            new Vector2D(1.0, 1.0),
            List.of(
                    new AffineTransform2D(new Matrix2x2(0.5, 0.0, 0.0, 0.5), new Vector2D(0.0, 0.0)),
                    new AffineTransform2D(new Matrix2x2(0.5, 0.0, 0.0, 0.5), new Vector2D(0.25, 0.50)),
                    new AffineTransform2D(new Matrix2x2(0.5, 0.0, 0.0, 0.5), new Vector2D(0.5, 0.0))
            )
    );
  }

  private static ChaosGameDescription createBarnsley() {
    return new ChaosGameDescription(
            new Vector2D(-2.65, 0.0),
            new Vector2D(2.65, 10.0),
            List.of(
                    new AffineTransform2D(new Matrix2x2(0.0, 0.0, 0.0, 0.16), new Vector2D(0.0, 0.0)),
                    new AffineTransform2D(new Matrix2x2(0.85, 0.04, -0.04, 0.85), new Vector2D(0.0, 1.60)),
                    new AffineTransform2D(new Matrix2x2(0.20, -0.26, 0.23, 0.22), new Vector2D(0.0, 1.60)),
                    new AffineTransform2D(new Matrix2x2(-0.15, 0.28, 0.26, 0.24), new Vector2D(0.0, 0.44))
            )
    );
  }
}

