package org.example.chaosgame.controller;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.example.chaosgame.controller.interfaces.GameController;
import org.example.chaosgame.controller.interfaces.Observer;
import org.example.chaosgame.controller.interfaces.Subject;
import org.example.chaosgame.model.chaos.ChaosCanvas;
import org.example.chaosgame.model.chaos.ChaosGameDescription;
import org.example.chaosgame.model.chaos.ExploreGame;
import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.ExploreJulia;
import org.example.chaosgame.model.transformations.Transform2D;
import org.example.chaosgame.view.ExplorePage;

/**
 * Controller class for the ExploreGame.
 * The controller is responsible for handling events from the view and updating the model.
 * The controller is also responsible for updating the view when the model changes.
 *
 * <p>The controller implements the GameController, and is a Subject and Observer.
 */
public class ExploreGameController implements Observer, Subject, GameController {
  private ExploreGame exploreGame;
  private final ExplorePage explorePage;
  private ChaosCanvas chaosCanvas;
  private Canvas canvas;
  private List<Transform2D> trans;
  private ChaosGameDescription description;
  private final List<Observer> pageObservers;
  private Vector2D dragStart;
  private Vector2D dragStartTemp;
  private double cumulativeScaleFactor = 1;
  private static final int WIDTH = 1200;
  private static final int HEIGHT = 800;

  private Vector2D minCoords = new Vector2D(-1.6, -1);
  private Vector2D maxCoords = new Vector2D(1.6, 1);

  /**
   * Constructor for ExploreGameController.
   * Initializes the ExploreGame and ExplorePage.
   */
  public ExploreGameController() {
    ExploreJulia exploreJulia = new ExploreJulia(new Complex(-0.835, 0.2321));
    this.trans = List.of(exploreJulia);
    this.description = new ChaosGameDescription(
            minCoords,
            maxCoords, trans);
    this.exploreGame = new ExploreGame(description, WIDTH, HEIGHT);
    this.chaosCanvas = exploreGame.getCanvas();
    this.pageObservers = new ArrayList<>();
    exploreGame.registerObserver(this);
    this.explorePage = new ExplorePage(this);
  }

  public void setCanvas(Canvas canvas) {
    this.canvas = canvas;
  }

  /**
   * Method for handling mouse pressed events.
   * Saves the starting position of the drag.
   *
   * @param event MouseEvent
   */
  public void mousePressed(MouseEvent event) {
    double mouseX = event.getX();
    double mouseY = event.getY();
    dragStart = new Vector2D(mouseX, mouseY);
    dragStartTemp = new Vector2D(mouseX, canvas.getHeight() - mouseY);
  }

  /**
   * Method for handling mouse dragged events.
   * Moves the canvas based on the distance dragged.
   *
   * @param event MouseEvent
   */
  public void mouseDragged(MouseEvent event) {
    Vector2D dragEnd = new Vector2D(event.getX(), event.getY());
    Vector2D dragDistance = dragEnd.subtract(dragStart);

    this.canvas.setTranslateX(canvas.getTranslateX() + dragDistance.getX());
    this.canvas.setTranslateY(canvas.getTranslateY() + dragDistance.getY());

    dragStart = dragEnd;
  }

  /**
   * Method for handling mouse released events.
   * Updates the position of the canvas based on the drag distance.
   *
   * @param event MouseEvent
   */
  public void mouseReleased(MouseEvent event) {
    Vector2D dragDistance = new Vector2D(event.getX(),
            canvas.getHeight() - event.getY()).subtract(dragStartTemp);
    Vector2D fractalRange = description.getMaxCoords().subtract(description.getMinCoords());
    Vector2D adjustedDragDistance = dragDistance.multiply(fractalRange)
            .divide(new Vector2D(canvas.getWidth(), canvas.getHeight()));
    minCoords = description.getMinCoords().subtract(adjustedDragDistance);
    maxCoords = description.getMaxCoords().subtract(adjustedDragDistance);
    updateExplorePage();
  }


  /**
   * Method for handling zoom button clicks.
   * Zooms in or out based on the scale factor.
   *
   * @param scaleFactor Scale factor
   */
  public void zoomButtonClicked(double scaleFactor) {
    cumulativeScaleFactor *= scaleFactor;
    Vector2D canvasCenter = chaosCanvas.transformIndicesToCoords(
            chaosCanvas.getWidth() / 2, chaosCanvas.getHeight() / 2);
    minCoords = canvasCenter.subtract(canvasCenter.subtract(
            description.getMinCoords()).scale(scaleFactor));
    maxCoords = canvasCenter.add(description.getMaxCoords()
            .subtract(canvasCenter).scale(scaleFactor));

    updateExplorePage();
  }

  /**
   * Method for handling scroll events.
   * Zooms in or out based on the scroll direction.
   *
   * @param event ScrollEvent
   */
  public void onScroll(ScrollEvent event) {
    double mouseX = event.getX();
    double mouseY = event.getY();
    double scaleBase = event.isControlDown() ? 2 : 1.1;
    double scaleFactor = 1;
    double zoomInLimit = Math.pow(10, -15);
    double zoomOutLimit = 8;
    if (event.getDeltaY() > 0 && cumulativeScaleFactor > zoomInLimit) {
      // Zoom in
      scaleFactor = 1 / scaleBase;
    } else if (event.getDeltaY() < 0 && cumulativeScaleFactor < zoomOutLimit) {
      // Zoom out
      scaleFactor = scaleBase;
    }

    cumulativeScaleFactor *= scaleFactor;
    double middleMouseX = mouseX - (double) chaosCanvas.getWidth() / 2;
    double middleMouseY = mouseY - (double) chaosCanvas.getHeight() / 2;
    double translateX = canvas.getTranslateX();
    double translateY = canvas.getTranslateY();

    canvas.setScaleX(canvas.getScaleX() * scaleFactor);
    canvas.setScaleY(canvas.getScaleY() * scaleFactor);

    double newTranslateX = (middleMouseX - translateX) * (scaleFactor - 1);
    double newTranslateY = (middleMouseY - translateY) * (scaleFactor - 1);
    double setTranslateX = translateX - newTranslateX;
    double setTranslateY = translateY - newTranslateY;
    canvas.setTranslateX(setTranslateX);
    canvas.setTranslateY(setTranslateY);

    Vector2D canvasCenter = chaosCanvas.transformIndicesToCoords((int) mouseX, (int) mouseY);
    minCoords = canvasCenter.subtract(canvasCenter.subtract(
            description.getMinCoords()).scale(scaleFactor));
    maxCoords = canvasCenter.add(description.getMaxCoords()
            .subtract(canvasCenter).scale(scaleFactor));
    updateExplorePage();
  }


  private void updateExplorePage() {
    this.description.setMinCoords(minCoords);
    this.description.setMaxCoords(maxCoords);
    this.description.setTransforms(trans);
    this.exploreGame = new ExploreGame(
            description, (int) canvas.getWidth(), (int) canvas.getHeight());
    this.exploreGame.registerObserver(this);
    this.chaosCanvas = exploreGame.getCanvas();
    exploreGame.exploreFractals();
    explorePage.updateCanvas(exploreGame.getCanvas());

    this.canvas.setTranslateX(0);
    this.canvas.setTranslateY(0);
    this.canvas.setScaleX(1);
    this.canvas.setScaleY(1);
  }


  public void homeButtonClicked() {
    notifyObservers();
  }

  public ExplorePage getGamePage() {
    return explorePage;
  }

  /**
   * Method for updating the fractal color.
   *
   * @param color New fractal color
   */
  @Override
  public void updateFractalColor(Color color) {
    explorePage.setFractalColor(color);
    explorePage.updateCanvas(exploreGame.getCanvas());
  }

  /**
   * Method for resetting the image.
   * Resets the image to the default position and scale.
   */
  public void resetImage() {
    minCoords = new Vector2D(-1.6, -1);
    maxCoords = new Vector2D(1.6, 1);
    description = new ChaosGameDescription(
            minCoords,
            maxCoords, trans);

    cumulativeScaleFactor = 1;
    updateExplorePage();
  }

  @Override
  public void setBind(StackPane mainPane) {
    canvas.widthProperty().bind(mainPane.widthProperty().multiply(0.85));
    canvas.heightProperty().bind(mainPane.heightProperty().multiply(0.85));
    mainPane.heightProperty().addListener((observable, oldValue, newValue) -> {
      // Update the canvas height here
      if (mainPane.getHeight() > 0 && mainPane.getWidth() > 0) {
        updateExplorePage();
      }
    });
    mainPane.widthProperty().addListener((observable, oldValue, newValue) -> {
      // Update the canvas width here
      if (mainPane.getHeight() > 0 && mainPane.getWidth() > 0) {
        updateExplorePage();
      }
    });
  }

  @Override
  public void updateJuliaValue(String partType, double value) {
    ExploreJulia exploreTransform =
            (ExploreJulia) exploreGame.getDescription().getTransforms().getFirst();
    double realPart = partType.equals("real")
            ? value : exploreTransform.getComplex().getX();
    double imaginaryPart = partType.equals("imaginary")
            ? value : exploreTransform.getComplex().getY();

    trans = List.of(new ExploreJulia(new Complex(realPart, imaginaryPart)));
    updateExplorePage();
  }

  /**
   * Method for updating the view.
   * Notified from the model when the model changes.
   */
  @Override
  public void update() {
    explorePage.updateCanvas(exploreGame.getCanvas());
    explorePage.updateInformation(
            description.getTransforms().getFirst(),
            description.getMinCoords(), description.getMaxCoords());
  }

  /**
   * Method for registering an observer.
   *
   * @param observer Observer
   */
  @Override
  public void registerObserver(Observer observer) {
    pageObservers.add(observer);
  }

  /**
   * Method for removing an observer.
   *
   * @param observer Observer
   */
  @Override
  public void removeObserver(Observer observer) {
    pageObservers.remove(observer);
  }

  /**
   * Method for notifying observers.
   */
  @Override
  public void notifyObservers() {
    for (Observer pageObserver : pageObservers) {
      pageObserver.update();
    }
  }
}
