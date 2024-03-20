package org.example.chaosgame.chaos;

import javafx.application.Platform;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.example.chaosgame.linalg.Matrix2x2;
import org.example.chaosgame.linalg.Vector2D;
import org.example.chaosgame.transformations.AffineTransform2D;
import org.example.chaosgame.transformations.Transform2D;

import java.util.List;
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
    for (int i = 0; i < steps; i++){
      int transformIndex = random.nextInt(description.getTransforms().size());
      currentPoint = description.getTransforms().get(transformIndex).transform(currentPoint);
      canvas.putPixel(currentPoint);
    }
  }

  public void runStepsBarnsley (int steps){
    for (int i = 0; i < steps; i++){
      int test = random.nextInt(100);
      if (test < 1){
        currentPoint = description.getTransforms().getFirst().transform(currentPoint);
      } else if (test < 86){
        currentPoint = description.getTransforms().get(1).transform(currentPoint);
      } else if (test < 93){
        currentPoint = description.getTransforms().get(2).transform(currentPoint);
      } else {
        currentPoint = description.getTransforms().get(3).transform(currentPoint);
      }
      canvas.putPixel(currentPoint);
    }
  }
}
