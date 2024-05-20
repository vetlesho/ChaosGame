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

  /**
   * Returns a ChaosGameDescription object based on the ChaosGameType-enum.
   *
   * @param type The description of the chaos game
   * @return A ChaosGameDescription object
   * @throws IllegalArgumentException if the ChaosGameType is null
   */
  public static ChaosGameDescription get(ChaosGameType type) throws IllegalArgumentException {
    if (type == null) {
      throw new IllegalArgumentException("ChaosGameType cannot be null");
    }
    return switch (type) {
      case JULIA -> createJulia();
      case BARNSLEY -> createBarnsley();
      case SIERPINSKI -> createSierpinski();
    };
  }

  /**
   * Creates a ChaosGameDescription object for the Julia set.
   *
   * @return A ChaosGameDescription object
   */
  private static ChaosGameDescription createJulia() {
    return new ChaosGameDescription(
            new Vector2D(-1.6, -1),
            new Vector2D(1.6, 1),
            List.of(
                    new JuliaTransform(new Complex(-0.70176, -0.3842), 1)
            )
    );
  }

  /**
   * Creates a ChaosGameDescription object for the Sierpinski triangle.
   *
   * @return A ChaosGameDescription object
   */
  private static ChaosGameDescription createSierpinski() {
    return new ChaosGameDescription(
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
  }

  /**
   * Creates a ChaosGameDescription object for the Barnsley fern.
   * Includes probabilities for the transformations.
   *
   * @return A ChaosGameDescription object
   */
  private static ChaosGameDescription createBarnsley() {
    return new ChaosGameDescription(
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
}

