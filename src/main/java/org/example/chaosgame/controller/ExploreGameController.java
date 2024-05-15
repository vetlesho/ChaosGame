package org.example.chaosgame.controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.example.chaosgame.model.chaos.ChaosCanvas;
import org.example.chaosgame.model.chaos.ChaosGame;
import javafx.scene.Node;
import org.example.chaosgame.model.chaos.ChaosGameDescription;
import org.example.chaosgame.model.chaos.ExploreGame;
import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.ExploreJulia;
import org.example.chaosgame.model.transformations.Transform2D;
import org.example.chaosgame.view.ChaosPage;
import org.example.chaosgame.controller.observer.GameObserver;
import org.example.chaosgame.controller.observer.PageObserver;
import org.example.chaosgame.controller.observer.PageSubject;
import org.example.chaosgame.view.ExplorePage;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ExploreGameController implements GameObserver, PageSubject {
  private ExploreGame exploreGame;
  private final ExplorePage explorePage;
  private ChaosCanvas chaosCanvas;
  private Canvas canvas;
  private Complex c;
  private final List<Transform2D> trans;
  private ChaosGameDescription description;
  private final List<PageObserver> pageObservers;

  private Vector2D dragStart;
  private Vector2D dragStartTemp;

  private double cumulativeScaleFactor = 1;

  private double mouseX;
  private double mouseY;
  private double scaleFactor;

  public ExploreGameController() {
    this.canvas = new Canvas(1200, 800);
    this.c = new Complex(-0.835, 0.2321);
    this.trans = List.of(new ExploreJulia(c));
    this.description  = new ChaosGameDescription(
            new Vector2D(-1.6, -1),
            new Vector2D(1.6, 1), trans);

    exploreGame = new ExploreGame(description, (int) canvas.getWidth(),(int) canvas.getHeight());
    exploreGame.registerObserver(this);
    this.chaosCanvas = exploreGame.getCanvas();
    this.explorePage = new ExplorePage(this);
    this.pageObservers = new ArrayList<>();

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
    description.setMinCoords(newMinCoords);
    description.setMaxCoords(newMaxCoords);
    exploreGame.removeObserver(this);
    exploreGame = new ExploreGame(description, (int) canvas.getWidth(),(int) canvas.getHeight());
    exploreGame.registerObserver(this);
    exploreGame.exploreFractals();
    this.chaosCanvas = exploreGame.getCanvas();
    explorePage.updateCanvas(this.chaosCanvas);
    this.canvas.setTranslateX(0);
    this.canvas.setTranslateY(0);
  }

  public void zoomButtonClicked(double scaleFactor) {
    cumulativeScaleFactor *= scaleFactor;
    Vector2D canvasCenter = chaosCanvas.transformIndicesToCoords(chaosCanvas.getWidth() / 2, chaosCanvas.getHeight() / 2);
    Vector2D newMinCoords = canvasCenter.subtract(canvasCenter.subtract(description.getMinCoords()).scale(scaleFactor));
    Vector2D newMaxCoords = canvasCenter.add(description.getMaxCoords().subtract(canvasCenter).scale(scaleFactor));

    description.setMinCoords(newMinCoords);
    description.setMaxCoords(newMaxCoords);
    exploreGame = new ExploreGame(description, (int) canvas.getWidth(),(int) canvas.getHeight());
    this.chaosCanvas = exploreGame.getCanvas();
    exploreGame.exploreFractals();
    canvas.setTranslateX(0);
    canvas.setTranslateY(0);
    canvas.setScaleY(1);
    canvas.setScaleX(1);
    explorePage.updateCanvas(this.chaosCanvas);
  }

  public void onScroll(ScrollEvent event) throws Exception {
//    exploreGame.stopTask();
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
    description.setMinCoords(newMinCoords);
    description.setMaxCoords(newMaxCoords);
    exploreGame = new ExploreGame(description, (int) canvas.getWidth(),(int) canvas.getHeight());

    this.chaosCanvas = exploreGame.getCanvas();
    canvas.setTranslateX(0);
    canvas.setTranslateY(0);
    canvas.setScaleY(1);
    canvas.setScaleX(1);
    exploreGame.exploreFractals();
    explorePage.updateCanvas(this.chaosCanvas);
  }

  public void homeButtonClicked() {
    notifyObservers(explorePage);
  }
  public ExplorePage getExplorePage() {
    return explorePage;
  }
  @Override
  public void update() {
    explorePage.updateCanvas(exploreGame.getCanvas());
  }

  @Override
  public void registerObserver(PageObserver observer) {
    pageObservers.add(observer);
  }

  @Override
  public void removeObserver(PageObserver observer) {
    pageObservers.remove(observer);
  }

  @Override
  public void notifyObservers(Node explorePage) {
    for (PageObserver pageObserver : pageObservers) {
      pageObserver.update(explorePage);
    }
  }

  public void updateExploreGame(ChaosGameDescription description) {
    this.exploreGame.setGameDescription(description);
    this.exploreGame.setChaosCanvas(description.getMinCoords(), description.getMaxCoords()
            , (int) canvas.getWidth(), (int) canvas.getHeight());
  }

  public void resetImage() {
    description = new ChaosGameDescription(
            new Vector2D(-1.6, -1),
            new Vector2D(1.6, 1), trans);
//    exploreGame.removeObserver(this);
    exploreGame = new ExploreGame(description, (int) canvas.getWidth(),(int) canvas.getHeight());
//    exploreGame.registerObserver(this);
//    updateExploreGame(description);
    cumulativeScaleFactor = 1;
    this.chaosCanvas = exploreGame.getCanvas();
    exploreGame.exploreFractals();
    explorePage.updateCanvas(this.chaosCanvas);
  }

  public void setCanvas(Canvas canvas) {
    this.canvas = canvas;
  }
}
