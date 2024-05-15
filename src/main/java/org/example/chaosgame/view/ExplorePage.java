package org.example.chaosgame.view;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.example.chaosgame.controller.ExploreGameController;
import org.example.chaosgame.controller.PageController;
import org.example.chaosgame.model.chaos.ChaosCanvas;
import org.example.chaosgame.model.chaos.ChaosGameDescription;
import org.example.chaosgame.model.chaos.ExploreGame;
import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.ExploreJulia;
import org.example.chaosgame.model.transformations.Transform2D;
import org.example.chaosgame.view.components.HomeButton;

import java.util.List;


public class ExplorePage extends StackPane {

  private final ExploreGameController exploreGameController;
  private ExploreGame exploreGame;
  private ChaosCanvas chaosCanvas;
  private ChaosGameDescription description;
  private final Canvas canvas;
  private final GraphicsContext gc;
  private final Button zoomInButton;
  private final Button zoomOutButton;
  private WritableImage offScreenImage;
  private PixelWriter pixelWriter;


  public ExplorePage(ExploreGameController exploreGameController) {
    this.setStyle("-fx-background-color: black;");
    this.exploreGameController = exploreGameController;
    this.setPrefWidth(1200);
    this.setPrefHeight(800);
    Button homeButton = new HomeButton();
    homeButton.setOnAction(e -> exploreGameController.homeButtonClicked());

    exploreGame = exploreGameController.getExploreGame();
    description = exploreGame.getDescription();
    chaosCanvas = exploreGame.getCanvas();
    canvas = exploreGameController.getCanvas();

    canvas.widthProperty().bind(this.prefWidthProperty());
    canvas.heightProperty().bind(this.prefWidthProperty().multiply((float) 800 / 1200));


    offScreenImage = new WritableImage(chaosCanvas.getWidth(), chaosCanvas.getHeight());
    pixelWriter = offScreenImage.getPixelWriter();
    gc = canvas.getGraphicsContext2D();
    exploreGame.exploreFractals();
    updateCanvas(this.chaosCanvas);

    Button removeImage = new Button("Reset");
    removeImage.setOnAction(exploreGameController::resetImage);

    zoomInButton = new Button("Zoom In");
    zoomOutButton = new Button("Zoom Out");

    zoomInButton.setOnMousePressed(event -> {
      AnimationTimer zoomInTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
          double scaleFactor = 1.0 / 1.05;
          Vector2D canvasCenter = chaosCanvas.transformIndicesToCoords(chaosCanvas.getWidth() / 2, chaosCanvas.getHeight() / 2);
          Vector2D newMinCoords = canvasCenter.subtract(canvasCenter.subtract(description.getMinCoords()).scale(scaleFactor));
          Vector2D newMaxCoords = canvasCenter.add(description.getMaxCoords().subtract(canvasCenter).scale(scaleFactor));

          description.setMinCoords(newMinCoords);
          description.setMaxCoords(newMaxCoords);
          exploreGame = new ExploreGame(description, (int) canvas.getWidth(), (int) canvas.getHeight());
          exploreGame.exploreFractals();
          updateCanvas(chaosCanvas);

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

          double scaleFactor = 1.05;
          Vector2D canvasCenter = chaosCanvas.transformIndicesToCoords((int) chaosCanvas.getWidth() / 2, (int) chaosCanvas.getHeight() / 2);
          Vector2D newMinCoords = canvasCenter.subtract(canvasCenter.subtract(description.getMinCoords()).scale(scaleFactor));
          Vector2D newMaxCoords = canvasCenter.add(description.getMaxCoords().subtract(canvasCenter).scale(scaleFactor));
          description.setMinCoords(newMinCoords);
          description.setMaxCoords(newMaxCoords);
          exploreGame = new ExploreGame(description, chaosCanvas.getWidth(), chaosCanvas.getHeight());
          exploreGame.exploreFractals();
          updateCanvas(chaosCanvas);
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

    this.setOnScroll(exploreGameController::onScroll);
    VBox buttons = new VBox(zoomInButton, zoomOutButton, removeImage);
    buttons.setAlignment(Pos.CENTER_RIGHT);
    buttons.setSpacing(10);
    setAlignment(homeButton, Pos.TOP_LEFT);
    this.getChildren().addAll(canvas, buttons, homeButton);
    this.setOnMousePressed(exploreGameController::mousePressed);
    this.setOnMouseDragged(exploreGameController::mouseDragged);
    this.setOnMouseReleased(exploreGameController::mouseReleased);


  }

  public void updateCanvas(ChaosCanvas chaosCanvas) {
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    double[][] canvasArray = chaosCanvas.getCanvasArray();
    double cellWidth = gc.getCanvas().getWidth() / chaosCanvas.getWidth();
    double cellHeight = gc.getCanvas().getHeight() / chaosCanvas.getHeight();

    // Create an off-screen image
    offScreenImage = new WritableImage(chaosCanvas.getWidth(), chaosCanvas.getHeight());
    pixelWriter = offScreenImage.getPixelWriter();

    for (int i = 0; i < chaosCanvas.getHeight(); i++) {
      for (int j = 0; j < chaosCanvas.getWidth(); j++) {
        double color = Math.min(canvasArray[i][j] * 3, 255);
        if (color >= 0 && color <= 255) {
          pixelWriter.setColor(j, i, Color.rgb((int) color, 0, 0));
        }else{
          pixelWriter.setColor(j, i, Color.rgb(0, 0, 0));
        }
      }
    }

    // Draw the off-screen image on the canvas
    gc.drawImage(offScreenImage, 0, 0, cellWidth * chaosCanvas.getWidth(),
            cellHeight * chaosCanvas.getHeight());
  }
}
