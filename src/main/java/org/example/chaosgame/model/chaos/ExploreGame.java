package org.example.chaosgame.model.chaos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.example.chaosgame.controller.interfaces.Observer;
import org.example.chaosgame.controller.interfaces.Subject;
import org.example.chaosgame.model.linalg.Vector2D;

/**
 * Class for exploring julia sets.
 */
public class ExploreGame implements Subject {
  private static final int MAX_ITER = 256;
  private final ChaosCanvas canvas;
  private ChaosGameDescription description;
  private Vector2D currentPoint = new Vector2D(0.0, 0.0);
  private final List<Observer> gameObservers;

  /**
   * Constructor for ExploreGame.
   *
   * @param description Description of the chaos game
   *
   * @param width Width of the canvas
   *
   * @param height Height of the canvas
   */
  public ExploreGame(ChaosGameDescription description, int width, int height) {
    this.description = description;
    this.canvas = new ChaosCanvas(width, height,
            description.getMinCoords(), description.getMaxCoords());
    this.gameObservers = new ArrayList<>();
  }

  public ChaosCanvas getCanvas() {
    return canvas;
  }

  public ChaosGameDescription getDescription() {
    return description;
  }

  public void setExploreGame(ChaosGameDescription description, int width, int height) {
    this.description = description;
    setChaosCanvas(description.getMinCoords(), description.getMaxCoords(), width, height);
  }

  /**
   * Method for setting the chaos canvas.
   *
   * @param minCoords Minimum coordinates of the canvas
   *
   * @param maxCoords Maximum coordinates of the canvas
   *
   * @param width Width of the canvas
   *
   * @param height Height of the canvas
   */
  public void setChaosCanvas(Vector2D minCoords, Vector2D maxCoords, int width, int height) {
    this.canvas.clearCanvas();
    this.canvas.setMaxCoords(maxCoords);
    this.canvas.setMinCoords(minCoords);
    this.canvas.setWidth(width);
    this.canvas.setHeight(height);
  }

  /**
   * Method for exploring fractals. Iterates over all pixels in the canvas
   * and applies the transformation to the current point.
   * Inspiration from <a href="https://www.youtube.com/watch?v=uc2yok_pLV4">Pezzza's Work</a>
   * and <a href="https://github.com/majidrouhani/idatt2003-gui-demo-mandelbrot">idatt2003-gui-demo-mandelbrot</a>
   *
   */
  public void exploreFractals() {
    IntStream heightStream = IntStream.range(0, canvas.getHeight());
    heightStream.parallel().forEach(height -> {
      for (int width = 0; width < canvas.getWidth(); width++) {
        int iter = 0;
        currentPoint = canvas.transformIndicesToCoords(width, height);
        Vector2D tempPoint = currentPoint;
        while (iter < MAX_ITER && tempPoint.lengthSq() < 4) {
          tempPoint = description.getTransforms().getFirst().transform(tempPoint);
          iter++;
        }
        double abs = Math.sqrt(tempPoint.lengthSq());
        double smooth = iter - Math.log(Math.log(abs)) / Math.log(2);

        canvas.putPixelExplore(width, height, smooth);

      }
    });
    notifyObservers();
  }

  /**
   * Method for registering gameObservers.
   *
   * @param gameObserver gameObserver to register
   */
  @Override
  public void registerObserver(Observer gameObserver) {
    gameObservers.add(gameObserver);
  }

  /**
   * Method for removing gameObservers.
   *
   * @param gameObserver gameObserver to remove
   */
  @Override
  public void removeObserver(Observer gameObserver) {
    gameObservers.remove(gameObserver);
  }

  /**
   * Method for notifying gameObservers.
   * Need to create a new list to avoid concurrent modification exception.
   */
  @Override
  public void notifyObservers() {
    List<Observer> gameObservers = new ArrayList<>(this.gameObservers);
    for (Observer gameObserver : gameObservers) {
      gameObserver.update();
    }
  }
}
