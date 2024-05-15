package org.example.chaosgame.model.chaos;

import javafx.concurrent.Task;
import org.example.chaosgame.controller.observer.GameObserver;
import org.example.chaosgame.controller.observer.GameSubject;
import org.example.chaosgame.model.linalg.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Class for exploring julia sets.
 */
public class ExploreGame extends Task<Task> implements GameSubject {
  private final int MAX_ITER = 256;

  private ChaosCanvas canvas;
  private ChaosGameDescription description;
  private Vector2D currentPoint = new Vector2D(0.0, 0.0);
  private final List<GameObserver> gameObservers;

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


  public void setChaosCanvas(int width, int height, Vector2D minCoords, Vector2D maxCoords) {
    this.canvas = new ChaosCanvas(width, height, minCoords, maxCoords);
  }
  public void setGameDescription(ChaosGameDescription description, int width, int height) {
    this.description = description;
    setChaosCanvas(width, height, description.getMinCoords(), description.getMaxCoords());
    notifyObservers();
  }

  public ChaosCanvas getCanvas() {
    return canvas;
  }

  public ChaosGameDescription getDescription() {
    return description;
  }

  @Override
  public void registerObserver(GameObserver gameObserver) {
    gameObservers.add(gameObserver);
    System.out.println("Observer added 2");
  }

  @Override
  public void removeObserver(GameObserver gameObserver) {
    gameObservers.remove(gameObserver);
    System.out.println("Observer removed");
  }

  @Override
  public void notifyObservers() {
    for (GameObserver gameObserver : gameObservers) {
      gameObserver.update();
      System.out.println("Observer notified");
    }
  }

  @Override
  public Task<Void> call() throws Exception {
    exploreFractals();
    return null;
  }
  public void stopTask() {
    if (this != null) {
      System.out.println("Task cancelled");
      this.cancel();
    }
  }
}
