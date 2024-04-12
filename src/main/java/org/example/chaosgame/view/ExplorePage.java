package org.example.chaosgame.view;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.chaosgame.model.chaos.ChaosCanvas;
import org.example.chaosgame.model.chaos.ChaosGameDescription;
import org.example.chaosgame.model.chaos.ExploreGame;
import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.ExploreJulia;
import org.example.chaosgame.model.transformations.Transform2D;

import java.util.List;

public class ExplorePage {
  private final StackPane exploreContent;
  private ExploreGame exploreGame;
  private ChaosCanvas chaosCanvas;
  private Complex c = new Complex(-0.835, 0.2321);
  private final Canvas canvas;
  private final GraphicsContext gc;
  private Vector2D dragStart;
  private Vector2D dragStartTemp;

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
  private Vector2D dragDistance;


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

    Button removeImage = new Button("Remove Image");
    removeImage.setOnAction(event -> {
      gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    });


    zoomInButton = new Button("Zoom In");
    zoomOutButton = new Button("Zoom Out");



    zoomInButton.setOnMousePressed(event -> {
      AnimationTimer zoomInTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
          double scaleFactor = 1.05;
          Vector2D canvasCenter = new Vector2D(canvas.getWidth() / 2, canvas.getHeight() / 2);
          Vector2D fractalCenter = chaosCanvas.transformIndicesToCoords((int)canvasCenter.getX(), (int)canvasCenter.getY());
          Vector2D newMinCoords = fractalCenter.subtract(fractalCenter.subtract(description.getMinCoords()).scale(1 / scaleFactor));
          Vector2D newMaxCoords = fractalCenter.add(description.getMaxCoords().subtract(fractalCenter).scale(1 / scaleFactor));
          description = new ChaosGameDescription(newMinCoords, newMaxCoords, trans);
          exploreGame = new ExploreGame(description, 1200, 800);
          exploreGame.exploreFractals();
          updateCanvas();
        }
      };
      zoomInTimer.start();
      zoomInButton.setUserData(zoomInTimer); // Store the timer in the button's user data
    });

    zoomInButton.setOnMouseReleased(event -> {
      AnimationTimer zoomInTimer = (AnimationTimer) zoomInButton.getUserData();
      if (zoomInTimer != null) {
        zoomInTimer.stop();
      }
    });

    zoomOutButton.setOnMousePressed(event -> {
      AnimationTimer zoomOutTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
          double scaleFactor = 1.0 / 1.05;
          Vector2D canvasCenter = new Vector2D(canvas.getWidth() / 2, canvas.getHeight() / 2);
          Vector2D fractalCenter = chaosCanvas.transformIndicesToCoords((int)canvasCenter.getX(), (int)canvasCenter.getY());
          Vector2D newMinCoords = fractalCenter.subtract(fractalCenter.subtract(description.getMinCoords()).scale(1 / scaleFactor));
          Vector2D newMaxCoords = fractalCenter.add(description.getMaxCoords().subtract(fractalCenter).scale(1 / scaleFactor));
          description = new ChaosGameDescription(newMinCoords, newMaxCoords, trans);
          exploreGame = new ExploreGame(description, 1200, 800);
          exploreGame.exploreFractals();
          updateCanvas();
        }
      };
      zoomOutTimer.start();
      zoomOutButton.setUserData(zoomOutTimer); // Store the timer in the button's user data
    });

    zoomOutButton.setOnMouseReleased(event -> {
      AnimationTimer zoomOutTimer = (AnimationTimer) zoomOutButton.getUserData();
      if (zoomOutTimer != null) {
        zoomOutTimer.stop();
      }
    });



    VBox buttons = new VBox(zoomInButton, zoomOutButton, removeImage);
    buttons.setAlignment(Pos.CENTER_RIGHT);
    buttons.setSpacing(10);

    exploreContent.getChildren().addAll(canvas, buttons);

    Platform.runLater(() -> {
      buttons.setLayoutX(canvas.getWidth() - buttons.getWidth());
      buttons.setLayoutY(0);
    });

    exploreContent.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
      try {
        dragStart = new Vector2D(event.getX(), event.getY());

        double mouseX = event.getX() - canvas.getLayoutX();
        double mouseY = event.getY() - canvas.getLayoutY();
        dragStartTemp = new Vector2D(mouseX, canvas.getHeight() - mouseY);
//        initialMousePosition = new Vector2D(mouseX, canvas.getHeight() - mouseY);
      } catch (Exception e) {
        e.printStackTrace();
    }
});

exploreContent.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
//    Vector2D currentMousePosition = new Vector2D(event.getX(),canvas.getHeight() - event.getY());
//    Vector2D dragDistance = currentMousePosition.subtract(initialMousePosition);
//
//    // Adjust the drag distance based on the zoom level
//// Convert the drag distance from canvas coordinates to fractal coordinates
//  Vector2D fractalRange = description.getMaxCoords().subtract(description.getMinCoords());
//  Vector2D adjustedDragDistance = dragDistance.multiply(fractalRange).divide(new Vector2D(canvas.getWidth(), canvas.getHeight()));
//
//    Vector2D newMinCoords = description.getMinCoords().subtract(adjustedDragDistance);
//    Vector2D newMaxCoords = description.getMaxCoords().subtract(adjustedDragDistance);
//    description.setMinCoords(newMinCoords);
//    description.setMaxCoords(newMaxCoords);
//    exploreGame = new ExploreGame(description, 1200, 800);
//    exploreGame.exploreFractals();
//    updateCanvas();
//
//    initialMousePosition = currentMousePosition;

  Vector2D dragEnd = new Vector2D(event.getX(), event.getY());
  dragDistance = dragEnd.subtract(dragStart);

  canvas.setTranslateX(canvas.getTranslateX() + dragDistance.getX());
  canvas.setTranslateY(canvas.getTranslateY() + dragDistance.getY());

  dragStart = dragEnd;




});

    exploreContent.setOnMouseReleased(event -> {
      dragDistance = new Vector2D(event.getX(), canvas.getHeight() - event.getY()).subtract(dragStartTemp);
//       Reset the position where the drag started
      Vector2D fractalRange = description.getMaxCoords().subtract(description.getMinCoords());
      System.out.println("drag distance: " + dragDistance.getX() + ", " + dragDistance.getY());
    Vector2D adjustedDragDistance = dragDistance.multiply(fractalRange).divide(new Vector2D(canvas.getWidth(), canvas.getHeight()));
      System.out.println("Adjusted drag distance: " + adjustedDragDistance.getX() + ", " + adjustedDragDistance.getY());
    Vector2D newMinCoords = description.getMinCoords().subtract(adjustedDragDistance);
    Vector2D newMaxCoords = description.getMaxCoords().subtract(adjustedDragDistance);
    description.setMinCoords(newMinCoords);
    description.setMaxCoords(newMaxCoords);
    exploreGame = new ExploreGame(description, 1200, 800);
    exploreGame.exploreFractals();
    updateCanvas();
      System.out.println("Mouse released");

      dragStart = null;
      canvas.setTranslateX(0);
      canvas.setTranslateY(0);
    });

//exploreContent.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
//    Vector2D currentMousePosition = new Vector2D(event.getX(), canvas.getHeight() - event.getY());
//    Vector2D dragDistance = currentMousePosition.subtract(initialMousePosition);
//
//    // Adjust the drag distance based on the zoom level
//    Vector2D fractalRange = description.getMaxCoords().subtract(description.getMinCoords());
//    Vector2D adjustedDragDistance = dragDistance.multiply(fractalRange).divide(new Vector2D(canvas.getWidth(), canvas.getHeight()));
//
//    Vector2D newMinCoords = description.getMinCoords().subtract(adjustedDragDistance);
//    Vector2D newMaxCoords = description.getMaxCoords().subtract(adjustedDragDistance);
//
//    description.setMinCoords(newMinCoords);
//    description.setMaxCoords(newMaxCoords);
//    exploreGame.setDescription(description);
//    exploreGame.exploreFractals();
//    updateCanvas();
//  });
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
//        if (color >= 0 && color <= 255) {
          pixelWriter.setColor(j, i, Color.rgb((int) color, 0, 0));
//        }

      }
    }

    // Draw the off-screen image on the canvas
    gc.drawImage(offScreenImage, 0, 0, cellWidth * chaosCanvas.getWidth(), cellHeight * chaosCanvas.getHeight());

    long end = System.currentTimeMillis();
    System.out.println("Time taken to display: " + (end - start) + "ms");
  }
}
