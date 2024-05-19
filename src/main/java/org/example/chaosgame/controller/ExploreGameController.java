package org.example.chaosgame.controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;

import org.example.chaosgame.controller.observer.Observer;
import org.example.chaosgame.controller.observer.Subject;
import org.example.chaosgame.controller.observer.GameController;
import org.example.chaosgame.model.chaos.ChaosCanvas;
import org.example.chaosgame.model.chaos.ChaosGame;
import javafx.scene.Node;
import org.example.chaosgame.model.chaos.ChaosGameDescription;
import org.example.chaosgame.model.chaos.ExploreGame;
import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.ExploreJulia;
import org.example.chaosgame.model.transformations.JuliaTransform;
import org.example.chaosgame.model.transformations.Transform2D;
import org.example.chaosgame.view.ExplorePage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;


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

  private double mouseX;
  private double mouseY;
  private double scaleFactor;
  private Task task;
  private ExploreJulia exploreJulia;

  private static final int WIDTH = 1200;
  private static final int HEIGHT = 800;

  public ExploreGameController() {
    this.exploreJulia = new ExploreJulia(new Complex(-0.835, 0.2321));
    this.trans = List.of(exploreJulia);
    this.description  = new ChaosGameDescription(
            new Vector2D(-1.6, -1),
            new Vector2D(1.6, 1), trans);
    this.exploreGame = new ExploreGame(description, WIDTH, HEIGHT);
    this.chaosCanvas = exploreGame.getCanvas();
    this.pageObservers = new ArrayList<>();
    exploreGame.registerObserver(this);
    this.explorePage = new ExplorePage(this);
//    exploreGame.exploreFractals();
//    updateExploreGame();

  }
  public void mousePressed(MouseEvent event) {
    double mouseX = event.getX();
    double mouseY = event.getY();
    dragStart = new Vector2D(mouseX, mouseY);
    dragStartTemp = new Vector2D(mouseX, canvas.getHeight() - mouseY);
  }

  public void mouseDragged(MouseEvent event) {
    Vector2D dragEnd = new Vector2D(event.getX(), event.getY());
    Vector2D dragDistance = dragEnd.subtract(dragStart);

    this.canvas.setTranslateX(canvas.getTranslateX() + dragDistance.getX());
    this.canvas.setTranslateY(canvas.getTranslateY() + dragDistance.getY());

    dragStart = dragEnd;
  }
  public void mouseReleased(MouseEvent event) {
    Vector2D dragDistance = new Vector2D(event.getX(), canvas.getHeight() - event.getY()).subtract(dragStartTemp);
//       Reset the position where the drag started
    Vector2D fractalRange = description.getMaxCoords().subtract(description.getMinCoords());
    Vector2D adjustedDragDistance = dragDistance.multiply(fractalRange).divide(new Vector2D(canvas.getWidth(), canvas.getHeight()));
    Vector2D newMinCoords = description.getMinCoords().subtract(adjustedDragDistance);
    Vector2D newMaxCoords = description.getMaxCoords().subtract(adjustedDragDistance);
    description = new ChaosGameDescription(newMinCoords, newMaxCoords, trans);
//   /* exploreGame.setGameDescription(new ChaosGameDescription(newMinCoords, newMaxCoords, trans),
//            (int) canvas.getWidth(),(int) canvas.getHeight());*/
//    exploreGame = new ExploreGame(description, (int) canvas.getWidth(),(int) canvas.getHeight());
//    exploreGame.exploreFractals();
//    this.chaosCanvas = exploreGame.getCanvas();

    updateExplorePage();
//    description = exploreGame.getDescription();
  }

  public void zoomButtonClicked(double scaleFactor) {
    cumulativeScaleFactor *= scaleFactor;
    Vector2D canvasCenter = chaosCanvas.transformIndicesToCoords(chaosCanvas.getWidth() / 2, chaosCanvas.getHeight() / 2);
    Vector2D newMinCoords = canvasCenter.subtract(canvasCenter.subtract(description.getMinCoords()).scale(scaleFactor));
    Vector2D newMaxCoords = canvasCenter.add(description.getMaxCoords().subtract(canvasCenter).scale(scaleFactor));

    description = new ChaosGameDescription(newMinCoords, newMaxCoords, trans);
    updateExplorePage();
//    description = exploreGame.getDescription();
  }

  private void stopTask() {
    if (task != null) {
      task.cancel();
      task = null;
    }
  }
  public void onScroll(ScrollEvent event) throws Exception {
//    stopTask();
    mouseX = event.getX();
    mouseY = event.getY();
    double scaleBase = event.isControlDown() ? 2 : 1.1;
    scaleFactor = 1;
    double zoomInLimit = Math.pow(10, -15);
    double zoomOutLimit = 8;
    if(event.getDeltaY() > 0 && cumulativeScaleFactor > zoomInLimit){
      // Zoom in
      scaleFactor = 1 / scaleBase;
    }else if(event.getDeltaY() < 0 && cumulativeScaleFactor < zoomOutLimit){
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
    Vector2D newMinCoords = canvasCenter.subtract(canvasCenter.subtract(description.getMinCoords()).scale(scaleFactor));
    Vector2D newMaxCoords = canvasCenter.add(description.getMaxCoords().subtract(canvasCenter).scale(scaleFactor));

//    updateExploreGame(newMinCoords, newMaxCoords,trans);
//    juliaTask = new SetJuliaTask(real, img, exploreGame, explorePage);

//    task = exploreGame.call(canvas);
    description = new ChaosGameDescription(newMinCoords, newMaxCoords, trans);
    updateExplorePage();
//    description = exploreGame.getDescription();
  }

//  private class SetJuliaTask extends Task<Task> {
//    private final double real;
//    private final double img;
//    private final ExploreGame exploreGame;
//    private final ExplorePage explorePage;
//
//    public SetJuliaTask(double real, double img, ExploreGame exploreGame, ExplorePage explorePage) {
//      this.real = real;
//      this.img = img;
//      this.exploreGame = exploreGame;
//      this.explorePage = explorePage;
//    }
//
////    @Override
//    protected Task call() throws Exception {
////      exploreGame.setJuliaValue(real, img);
//      exploreGame.exploreFractals();
//      explorePage.updateCanvas(this.exploreGame.getCanvas());
//
//      return this;
//  }
//  }


  private void updateExplorePage() {
//    System.out.println("Update Explore Game");
//    exploreGame.setExploreGame(description, (int) canvas.getWidth(),(int) canvas.getHeight());
    exploreGame.removeObserver(this);
    exploreGame = new ExploreGame(description, (int) canvas.getWidth(),(int) canvas.getHeight());
    exploreGame.registerObserver(this);
    this.chaosCanvas = exploreGame.getCanvas();
    exploreGame.exploreFractals();
    explorePage.updateCanvas(this.chaosCanvas);
//    explorePage.updateCanvas(this.chaosCanvas);
    canvas.setTranslateX(0);
    canvas.setTranslateY(0);
    canvas.setScaleY(1);
    canvas.setScaleX(1);
  }
  public void homeButtonClicked() {
    notifyObservers();
  }
  public ExplorePage getGamePage() {
    return explorePage;
  }

  public void updateFractalColor(Color color){
    explorePage.setFractalColor(color);
    explorePage.updateCanvas(exploreGame.getCanvas());
  }
  @Override
  public void update() {
    explorePage.updateCanvas(exploreGame.getCanvas());
    explorePage.updateInformation(description.getTransforms().getFirst(), description.getMinCoords(), description.getMaxCoords());
  }

  @Override
  public void registerObserver(Observer observer) {
    pageObservers.add(observer);
  }

  @Override
  public void removeObserver(Observer observer) {
    pageObservers.remove(observer);
  }

  @Override
  public void notifyObservers() {
    for (Observer pageObserver : pageObservers) {
      pageObserver.update();
    }
  }

  public void resetImage() {
    Vector2D newMinCoords = new Vector2D(-1.6, -1);
    Vector2D newMaxCoords = new Vector2D(1.6, 1);
    description = new ChaosGameDescription(
            newMinCoords,
            newMaxCoords, trans);
//    exploreGame.removeObserver(this);
//    exploreGame = new ExploreGame(description, (int) canvas.getWidth(),(int) canvas.getHeight());
//    exploreGame.registerObserver(this);
//    updateExploreGame(description);
    cumulativeScaleFactor = 1;
    updateExplorePage();
//    description = exploreGame.getDescription();
  }

  public void setCanvas(Canvas canvas) {
    this.canvas = canvas;
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
    // Add a change listener to the width property
    mainPane.widthProperty().addListener((observable, oldValue, newValue) -> {
      // Update the canvas width here
      if (mainPane.getHeight() > 0 && mainPane.getWidth() > 0) {
        updateExplorePage();
      }
    });
  }

  @Override
  public void updateJuliaValue(String partType, double value) {
    ExploreJulia exploreTransform = (ExploreJulia) exploreGame.getDescription().getTransforms().getFirst();
    double realPart = partType.equals("real") ? value : exploreTransform.getComplex().getX();
    double imaginaryPart = partType.equals("imaginary") ? value : exploreTransform.getComplex().getY();

    trans = List.of(new ExploreJulia(new Complex(realPart, imaginaryPart)));
    description.setTransforms(trans);
    updateExplorePage();
//    exploreGame = new ExploreGame(description, (int) canvas.getWidth(),(int) canvas.getHeight());
//    this.chaosCanvas = exploreGame.getCanvas();
//    exploreGame.exploreFractals();
//    explorePage.updateCanvas(this.chaosCanvas);
  }
}
