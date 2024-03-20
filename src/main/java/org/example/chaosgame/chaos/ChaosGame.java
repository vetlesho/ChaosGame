package org.example.chaosgame.chaos;

import javafx.application.Platform;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.example.chaosgame.linalg.Matrix2x2;
import org.example.chaosgame.linalg.Vector2D;
import org.example.chaosgame.transformations.AffineTransform2D;

import java.util.Random;

public class ChaosGame {
  private final ChaosCanvas canvas;
  private final ChaosGameDescription description;
  private Vector2D currentPoint = new Vector2D(0.0, 0.0);
  public final Random random = new Random();
  public ChaosGame(ChaosGameDescription description, int width, int height) {
    this.description = description;
    this.canvas = new ChaosCanvas(width, height, description.getMinCoords(), description.getMaxCoords());
  }


  public ChaosCanvas getCanvas() {
    return canvas;
  }
  public void runSteps (int steps){
    canvas.putPixel(currentPoint); //first point may need to be drawn
    System.out.println("Transforms: " + description.getTransforms());
    for (int i = 0; i < steps; i++){

      int transformIndex = random.nextInt(description.getTransforms().size());
      System.out.println("transformIndex: " + transformIndex);
      currentPoint = description.getTransforms().get(transformIndex).transform(currentPoint);
      canvas.putPixel(currentPoint);
    }
  }
}
