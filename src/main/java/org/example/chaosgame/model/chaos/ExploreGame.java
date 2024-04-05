package org.example.chaosgame.model.chaos;

import org.example.chaosgame.model.linalg.Vector2D;

import java.util.Random;

public class ExploreGame {
  private final int MAX_ITER = 256;

  private final ChaosCanvas canvas;
  private final ChaosGameDescription description;
  private Vector2D currentPoint = new Vector2D(0.0, 0.0);

  public final Random random = new Random();

  public ExploreGame(ChaosGameDescription description, int width, int height) {
    this.description = description;
    this.canvas = new ChaosCanvas(width, height, description.getMinCoords(), description.getMaxCoords());
  }

  public void exploreFractals(){

    int iter = 0;
    for (int y = 0; y < canvas.getHeight(); y++) {
      for (int x = 0; x < canvas.getWidth(); x++) {

        currentPoint = canvas.transformIndicesToCoords(x, y);
        Vector2D tempPoint = currentPoint;
        while (iter < MAX_ITER && tempPoint.getX() >= description.getMinCoords().getX() && tempPoint.getX() <= description.getMaxCoords().getX() &&
                tempPoint.getY() >= description.getMinCoords().getY() && tempPoint.getY() <= description.getMaxCoords().getY()){
          tempPoint = description.getTransforms().getFirst().transform(tempPoint);
          iter++;
        }
        canvas.putPixel(x, y, iter);
        iter = 0;
      }
    }
  }

  public ChaosCanvas getCanvas() {
    return canvas;
  }
}
