package org.idatt2003.model.chaos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.idatt2003.controller.interfaces.Observer;
import org.idatt2003.controller.interfaces.Subject;
import org.idatt2003.model.linalg.Vector2D;


/**
 * Class for exploring fractal sets.
 * Currently, it is only set to calculate julia fractals,
 * can be expanded to work with other fractals.
 */
public class ExploreGame implements Subject {
  private static final int MAX_ITER = 256;
  private ChaosCanvas canvas;
  private ChaosGameDescription description;
  private Vector2D currentPoint = new Vector2D(0.0, 0.0);
  private final List<Observer> gameObservers;

  /**
   * Constructor for ExploreGame.
   * Creates a new ExploreGame object with the
   * given description, width, and height.
   * Creates a new ChaosCanvas object with the
   * minCoords and maxCoords in the ChaosGameDescription.
   *
   * @param description Description of the chaos game
   *
   * @param width Width of the canvas
   *
   * @param height Height of the canvas
   */
  public ExploreGame(ChaosGameDescription description, int width, int height) {
    this.description = description;
    this.canvas = new ChaosCanvas(
            width, height,
            description.getMinCoords(),
            description.getMaxCoords());
    this.gameObservers = new ArrayList<>();
  }

  /**
   * Method for setting the chaos game.
   *
   * @param description Description of the chaos game
   *
   * @param canvas ChaosCanvas object
   */
  public void setExploreGame(ChaosGameDescription description, ChaosCanvas canvas) {
    this.description = description;
    this.canvas = canvas;
    this.currentPoint = new Vector2D(0.0, 0.0);

  }

  public ChaosCanvas getCanvas() {
    return canvas;
  }

  public ChaosGameDescription getDescription() {
    return description;
  }


  /**
   * Method for exploring fractals. Iterates over all pixels in the canvas
   * and applies the transformation to the current point.
   * Inspiration from <a href="https://www.youtube.com/watch?v=uc2yok_pLV4">Pezzza's Work</a>
   * for the smoothing algorithm.
   * And also <a href="https://github.com/majidrouhani/idatt2003-gui-demo-mandelbrot">idatt2003-gui-demo-mandelbrot</a>
   * for the parallel stream.
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
