package org.example.chaosgame.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.model.chaos.*;

import org.example.chaosgame.view.components.HomeButton;


public class ChaosPage extends StackPane{
  private ChaosGameController chaosGameController;
  private ChaosCanvas chaosCanvas;
  private final Button runStepsButton = new Button("Run Steps");
  private final Canvas canvas = new Canvas(1200, 800);
  private final GraphicsContext gc;
  private final VBox runStepsBox = new VBox();

  private final Button homeButton = new HomeButton();


  public ChaosPage(ChaosGameController chaosGameController) {
    this.chaosGameController = chaosGameController;
    gc = canvas.getGraphicsContext2D();
    TextField stepsField = new TextField();
    stepsField.setMaxWidth(150);
    stepsField.setPromptText("Enter number of steps");

    ComboBox<String> contextMenu = new ComboBox<>();
    contextMenu.setPromptText("Select chaos game");

    contextMenu.getItems().addAll("Julia", "Sierpinski", "Barnsley", "Make your own");

    contextMenu.setOnAction(event -> {
      String selectedGame = contextMenu.getValue().trim().toUpperCase();
      chaosGameController.gameSelection(selectedGame);
    });

    runStepsButton.setOnAction(event -> chaosGameController.runSteps(stepsField));


Button openFileButton = new Button("Open File");
openFileButton.setOnAction(e -> chaosGameController.openFromFile());

    runStepsBox.getChildren().addAll(contextMenu, stepsField, runStepsButton, openFileButton);
    runStepsBox.setSpacing(10);
    runStepsBox.setPadding(new Insets(10));
    runStepsBox.setAlignment(Pos.CENTER_RIGHT);
    setAlignment(homeButton, Pos.TOP_LEFT);

    homeButton.setOnAction(e -> chaosGameController.homeButtonClicked());
    this.getChildren().addAll(canvas, runStepsBox, homeButton);

  }

  public StackPane getChaosContent() {
    return this;
  }

  public void updateCanvas() {
    double[][] canvasArray = chaosCanvas.getCanvasArray();
    double cellWidth = gc.getCanvas().getWidth() / chaosCanvas.getWidth();
    double cellHeight = gc.getCanvas().getHeight() / chaosCanvas.getHeight();
    long start = System.currentTimeMillis();

    // Create an off-screen image
    WritableImage offScreenImage = new WritableImage(chaosCanvas.getWidth(), chaosCanvas.getHeight());
    PixelWriter pixelWriter = offScreenImage.getPixelWriter();

    for (int i = 0; i < chaosCanvas.getHeight(); i++) {
      for (int j = 0; j < chaosCanvas.getWidth(); j++) {
        double color = Math.min(canvasArray[i][j] * 3, 255);
        if (color != 0) {
          pixelWriter.setColor(j, i, Color.rgb((int) (color), 0, 0));
        }
      }
    }

    // Draw the off-screen image on the canvas
    gc.drawImage(offScreenImage, 0, 0, cellWidth * chaosCanvas.getWidth(), cellHeight * chaosCanvas.getHeight());

    long end = System.currentTimeMillis();
    System.out.println("Time to draw: " + (end - start) + " ms");
  }

//  public Button getHomeButton() {
//    return homeButton;
//  }
}
