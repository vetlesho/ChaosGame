package org.example.chaosgame.view;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.chaosgame.chaos.ChaosCanvas;
import org.example.chaosgame.chaos.ChaosGame;
import org.example.chaosgame.chaos.ChaosGameDescriptionFactory;
import org.example.chaosgame.linalg.Complex;

public class ChaosPage {
  private final StackPane chaosContent;
  private final ChaosGame chaosGame;
  private final ChaosCanvas chaosCanvas;
  private Complex c = new Complex(-0.835, 0.2321);
  private final Button runStepsButton = new Button("Run Steps");
  private final Canvas canvas;
  private final GraphicsContext gc;
  private final Label errorLabel = new Label("Invalid input. Please enter a valid number.");

  private final VBox runStepsBox = new VBox();

  public ChaosPage() {
    chaosContent = new StackPane();
    chaosGame = new ChaosGame(ChaosGameDescriptionFactory.get("Julia", c), 1200, 800);
    chaosCanvas = chaosGame.getCanvas();
    canvas = new Canvas(chaosCanvas.getWidth(), chaosCanvas.getHeight());
    gc = canvas.getGraphicsContext2D();


    TextField stepsField = new TextField();
    stepsField.setMaxWidth(150);
    stepsField.setPromptText("Enter number of steps");

    runStepsButton.setOnAction(e -> {
      if (!stepsField.getText().isEmpty()) {
        try {
          chaosGame.runSteps(Integer.parseInt(stepsField.getText()));
          updateCanvas();
          runStepsBox.getChildren().remove(errorLabel);
        } catch (NumberFormatException ex) {
          if (!runStepsBox.getChildren().contains(errorLabel)) {
            runStepsBox.getChildren().add(errorLabel);
          }
        }

      }

    });


    runStepsBox.getChildren().addAll(stepsField, runStepsButton);
    runStepsBox.setAlignment(Pos.CENTER_RIGHT);
    chaosContent.getChildren().addAll(canvas, runStepsBox);

  }

  public StackPane getChaosContent() {
    return chaosContent;
  }

  public void updateCanvas() {
    int[][] canvasArray = chaosCanvas.getCanvasArray();
    double cellWidth = gc.getCanvas().getWidth() / chaosCanvas.getWidth();
    double cellHeight = gc.getCanvas().getHeight() / chaosCanvas.getHeight();
    long start = System.currentTimeMillis();

    // Create an off-screen image
    WritableImage offScreenImage = new WritableImage(chaosCanvas.getWidth(), chaosCanvas.getHeight());
    PixelWriter pixelWriter = offScreenImage.getPixelWriter();

    for (int i = 0; i < chaosCanvas.getHeight(); i++) {
      for (int j = 0; j < chaosCanvas.getWidth(); j++) {
        int color = Math.min(canvasArray[i][j] * 3, 255);
        if (color != 0) {
          pixelWriter.setColor(j, i, Color.rgb(color, 0, 0));
        }
      }
    }

    // Draw the off-screen image on the canvas
    gc.drawImage(offScreenImage, 0, 0, cellWidth * chaosCanvas.getWidth(), cellHeight * chaosCanvas.getHeight());

    long end = System.currentTimeMillis();
    System.out.println("Time taken to display: " + (end - start) + "ms");
  }
}
