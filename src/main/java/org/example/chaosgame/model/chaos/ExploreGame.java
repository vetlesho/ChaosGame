package org.example.chaosgame.model.chaos;

import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import org.example.chaosgame.controller.observer.Observer;
import org.example.chaosgame.controller.observer.Subject;
import org.example.chaosgame.model.linalg.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Class for exploring julia sets.
 */
public class ExploreGame extends Task implements Subject {
  private final int MAX_ITER = 256;

  private ChaosCanvas canvas;
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
    this.canvas = new ChaosCanvas(width, height, description.getMinCoords(), description.getMaxCoords());
    this.gameObservers = new ArrayList<>();
  }

  /**
   * Method for exploring fractals. Iterates over all pixels in the canvas
   * and applies the transformation to the current point.
   */
  public void exploreFractals(){
//    stopTask();

//    long start = System.currentTimeMillis();

    IntStream yStream = IntStream.range(0, canvas.getHeight());
    yStream.parallel().forEach(y -> {
//    for (int y = 0; y < canvas.getHeight(); y++) {
      for (int x = 0; x < canvas.getWidth(); x++) {
        if (isCancelled()) {
          break;
        }
        int iter = 0;
        currentPoint = canvas.transformIndicesToCoords(x, y);
        Vector2D tempPoint = currentPoint;
        while (iter < MAX_ITER && tempPoint.lengthSQ() < 4){
          tempPoint = description.getTransforms().getFirst().transform(tempPoint);
          iter++;
        }
        double abs = Math.sqrt(tempPoint.lengthSQ());
        double smooth = iter - Math.log(Math.log(abs)) / Math.log(2);

        canvas.putPixel(x, y, smooth);

      }
    });
    notifyObservers();
//    long end = System.currentTimeMillis();
//    System.out.println("Time taken: " + (end - start) + "ms");
  }


  public void setChaosCanvas(Vector2D minCoords, Vector2D maxCoords) {
    this.canvas.setMaxCoords(maxCoords);
    this.canvas.setMinCoords(minCoords);
  }
  public void setGameDescription(ChaosGameDescription description) {
    this.description = description;
  }

  public ChaosCanvas getCanvas() {
    return canvas;
  }

  public ChaosGameDescription getDescription() {
    return description;
  }

  @Override
  public void registerObserver(Observer gameObserver) {
    gameObservers.add(gameObserver);
    System.out.println("Observer added 2");
  }

  @Override
  public void removeObserver(Observer gameObserver) {
    gameObservers.remove(gameObserver);
    System.out.println("Observer removed");
  }

  @Override
  public void notifyObservers() {
    for (Observer gameObserver : gameObservers) {
      gameObserver.update();
      System.out.println("Observer notified");
    }
  }

  public Task call(Canvas canvas) throws Exception {
    exploreFractals();
    return this;
  }
  protected Task call() {
    exploreFractals();
    return this;
  }
  public void stopTask() {
    if (this != null) {

      this.cancel();
    }
  }
}
