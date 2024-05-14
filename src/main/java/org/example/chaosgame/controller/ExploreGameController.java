package org.example.chaosgame.controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.example.chaosgame.model.chaos.ChaosCanvas;
import org.example.chaosgame.model.chaos.ChaosGame;
import org.example.chaosgame.model.chaos.ChaosGameDescription;
import org.example.chaosgame.model.chaos.ExploreGame;
import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.ExploreJulia;
import org.example.chaosgame.model.transformations.Transform2D;
import org.example.chaosgame.view.ChaosPage;
import org.example.chaosgame.view.ExplorePage;

import java.util.List;

public class ExploreGameController implements Observer{
  private final ExploreGame exploreGame;
  private final PageController pageController;
  private final ExplorePage explorePage;
  private ChaosCanvas chaosCanvas;
  private Complex c;
  private final List<Transform2D> trans;
  private ChaosGameDescription description;



  public ExploreGameController(PageController pageController) {
    this.pageController = pageController;
    Complex c = new Complex(-0.835, 0.2321);
    List<Transform2D> trans = List.of(new ExploreJulia(c));
    ChaosGameDescription description = new ChaosGameDescription(
            new Vector2D(-1.6, -1),
            new Vector2D(1.6, 1), trans);
    this.exploreGame = new ExploreGame(description, 1200, 800);
    this.explorePage = new ExplorePage(this);
    this.description  = new ChaosGameDescription(
            new Vector2D(-1.6, -1),
            new Vector2D(1.6, 1), trans);
    this.c = new Complex(-0.835, 0.2321);
    this.trans = List.of(new ExploreJulia(c));
  }

  public void homeButtonClicked() {
    pageController.homeButtonClicked();
  }
  public ExplorePage getExplorePage() {
    return explorePage;
  }
  @Override
  public void update() {
  }

  public void updateCanvas(GraphicsContext gc, WritableImage offScreenImage, Canvas canvas, PixelWriter pixelWriter) {
    chaosCanvas = exploreGame.getCanvas();
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    double[][] canvasArray = chaosCanvas.getCanvasArray();
    double cellWidth = gc.getCanvas().getWidth() / chaosCanvas.getWidth();
    double cellHeight = gc.getCanvas().getHeight() / chaosCanvas.getHeight();
    long start = System.currentTimeMillis();

    // Create an off-screen image
    offScreenImage = new WritableImage(chaosCanvas.getWidth(), chaosCanvas.getHeight());
    pixelWriter = offScreenImage.getPixelWriter();

    for (int i = 0; i < chaosCanvas.getHeight(); i++) {
      for (int j = 0; j < chaosCanvas.getWidth(); j++) {
        double color = Math.min(canvasArray[i][j] * 3, 255);
        if (color >= 0 && color <= 255) {
          pixelWriter.setColor(j, i, Color.rgb((int) color, 0, 0));
        }

      }
    }

    // Draw the off-screen image on the canvas
    gc.drawImage(offScreenImage, 0, 0, cellWidth * chaosCanvas.getWidth(), cellHeight * chaosCanvas.getHeight());

    long end = System.currentTimeMillis();
    /*System.out.println("Time taken to display: " + (end - start) + "ms");*/
  }
  public ExploreGame getExploreGame() {
    return exploreGame;
  }
}
