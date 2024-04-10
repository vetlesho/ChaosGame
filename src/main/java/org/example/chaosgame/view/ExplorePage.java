package org.example.chaosgame.view;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.chaosgame.chaos.*;
import org.example.chaosgame.linalg.Complex;
import org.example.chaosgame.linalg.Vector2D;
import org.example.chaosgame.transformations.ExploreJulia;
import org.example.chaosgame.transformations.JuliaTransform;
import org.example.chaosgame.transformations.Transform2D;

import java.util.List;
import java.util.stream.IntStream;

public class ExplorePage {
  private final StackPane exploreContent;
  private ExploreGame exploreGame;
  private ChaosCanvas chaosCanvas;
  private Complex c = new Complex(-0.835, 0.2321);
  private final Canvas canvas;
  private final GraphicsContext gc;
  private final List<Transform2D> trans = List.of(
                  new ExploreJulia(c)
          );
  private ChaosGameDescription description = new ChaosGameDescription(
          new Vector2D(-1.6, -1),
          new Vector2D(1.6, 1), trans);
          
  private final Button zoomInButton;
  private final Button zoomOutButton;

  private Vector2D initialMousePosition;


  private WritableImage offScreenImage;
  private PixelWriter pixelWriter;


  public ExplorePage() {

    exploreContent = new StackPane();

    
    exploreGame = new ExploreGame(description, 1200, 800);
    chaosCanvas = exploreGame.getCanvas();
    canvas = new Canvas(chaosCanvas.getWidth(), chaosCanvas.getHeight());
    canvas.requestFocus();
    offScreenImage = new WritableImage(chaosCanvas.getWidth(), chaosCanvas.getHeight());
    pixelWriter = offScreenImage.getPixelWriter();
    gc = canvas.getGraphicsContext2D();
    exploreGame.exploreFractals();
    updateCanvas();

    Button removeImage = MenuView.shadedButton("Remove Image");
    removeImage.setOnAction(event -> {
      gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    });


    zoomInButton = MenuView.shadedButton("Zoom In");
    zoomOutButton = MenuView.shadedButton("Zoom Out");



    zoomInButton.setOnMousePressed(event -> {
      double scaleFactor = 1.05;
      Vector2D newMinCoords = description.getMinCoords().scale(1 / scaleFactor);
      Vector2D newMaxCoords = description.getMaxCoords().scale(1 / scaleFactor);
        description = new ChaosGameDescription(newMinCoords, newMaxCoords, trans);
        exploreGame = new ExploreGame(description, 1200, 800);
//
        exploreGame.exploreFractals();
        updateCanvas();

    });

    zoomOutButton.setOnAction(event -> {
      double scaleFactor = 1 / 1.05;
      Vector2D newMinCoords = description.getMinCoords().scale(1 / scaleFactor);
      Vector2D newMaxCoords = description.getMaxCoords().scale(1 / scaleFactor);
          description = new ChaosGameDescription(newMinCoords, newMaxCoords, trans);
          exploreGame = new ExploreGame(description, 1200, 800);
//          chaosCanvas = exploreGame.getCanvas();
          exploreGame.exploreFractals();
          updateCanvas();

    });



    VBox buttons = new VBox(zoomInButton, zoomOutButton, removeImage);
    buttons.setAlignment(Pos.CENTER_RIGHT);
    buttons.setSpacing(10);

    exploreContent.getChildren().addAll(canvas, buttons);

    exploreContent.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
      try {
        double mouseX = event.getX() - canvas.getLayoutX();
        double mouseY = event.getY() - canvas.getLayoutY();
        initialMousePosition = new Vector2D(mouseX, canvas.getHeight() - mouseY);
      } catch (Exception e) {
        e.printStackTrace();
    }
});

exploreContent.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
    Vector2D currentMousePosition = new Vector2D(event.getX(),canvas.getHeight() - event.getY());
    Vector2D dragDistance = currentMousePosition.subtract(initialMousePosition);

    // Adjust the drag distance based on the zoom level
// Convert the drag distance from canvas coordinates to fractal coordinates
  Vector2D fractalRange = description.getMaxCoords().subtract(description.getMinCoords());
  Vector2D adjustedDragDistance = dragDistance.multiply(fractalRange).divide(new Vector2D(canvas.getWidth(), canvas.getHeight()));

    Vector2D newMinCoords = description.getMinCoords().subtract(adjustedDragDistance);
    Vector2D newMaxCoords = description.getMaxCoords().subtract(adjustedDragDistance);

    description = new ChaosGameDescription(newMinCoords, newMaxCoords, trans);
    exploreGame = new ExploreGame(description, 1200, 800);
    exploreGame.exploreFractals();
    updateCanvas();

    initialMousePosition = currentMousePosition;
});
  }

  public StackPane getExploreContent() {
    return exploreContent;
  }

  public void updateCanvas() {
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
        if (color > 0 && color < 255) {
          pixelWriter.setColor(j, i, Color.rgb((int) color, (int) color, (int) color));
        }
      }
    }

    // Draw the off-screen image on the canvas
    gc.drawImage(offScreenImage, 0, 0, cellWidth * chaosCanvas.getWidth(), cellHeight * chaosCanvas.getHeight());

    long end = System.currentTimeMillis();
    System.out.println("Time taken to display: " + (end - start) + "ms");
  }
}
