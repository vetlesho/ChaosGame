package org.example.chaosgame.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.chaosgame.model.chaos.ChaosCanvas;
import org.example.chaosgame.model.chaos.ChaosGame;
import org.example.chaosgame.model.chaos.ChaosGameDescriptionFactory;
import org.example.chaosgame.model.linalg.Complex;

public class ChaosPage {
  private final StackPane chaosContent;
  private ChaosGame chaosGame;
  private ChaosCanvas chaosCanvas;
  private Complex c = new Complex(-0.70176, -0.3842);
  private final Button runStepsButton = new Button("Run Steps");
  private final Canvas canvas;
  private final GraphicsContext gc;
  private final Label errorLabel = new Label("Invalid input. Please enter a valid number.");

  private final VBox runStepsBox = new VBox();

  public ChaosPage() {
    chaosContent = new StackPane();
    updateChaosGame("Julia");
    chaosCanvas = chaosGame.getCanvas();
    canvas = new Canvas(chaosCanvas.getWidth(), chaosCanvas.getHeight());
    gc = canvas.getGraphicsContext2D();


    TextField stepsField = new TextField();
    stepsField.setMaxWidth(150);
    stepsField.setPromptText("Enter number of steps");



    ComboBox<String> contextMenu = new ComboBox<>();
    contextMenu.setPromptText("Select chaos game");
    ColorAdjust shade = new ColorAdjust();
    shade.setBrightness(0.4);
    contextMenu.addEventHandler(MouseEvent.MOUSE_ENTERED,
            e -> contextMenu.setEffect(shade));
    contextMenu.addEventHandler(MouseEvent.MOUSE_EXITED,
            e -> contextMenu.setEffect(null));


    contextMenu.getItems().addAll("Julia", "Sierpinski", "Barnsley", "Make your own");

    contextMenu.setOnAction(e -> {
      String selectedGame = contextMenu.getValue();
      if(selectedGame.equals("Make your own")) {

        chaosGame = new ChaosGame(ChaosGameDescriptionFactory.get("Julia", c), 1200, 800);
      } else {
        updateChaosGame(selectedGame);
      }
      gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
      updateCanvas();
    });
    runStepsButton.setOnAction(e5 -> {
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

    runStepsBox.getChildren().addAll(contextMenu,stepsField, runStepsButton);
    runStepsBox.setSpacing(10);
    runStepsBox.setPadding(new Insets(10));
    runStepsBox.setAlignment(Pos.CENTER_RIGHT);
    chaosContent.getChildren().addAll(canvas, runStepsBox);

  }

  public StackPane getChaosContent() {
    return chaosContent;
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
    System.out.println("Time taken to display: " + (end - start) + "ms");
  }

  private void updateChaosGame(String chaosGameType) {
    chaosGame = new ChaosGame(ChaosGameDescriptionFactory.get(chaosGameType, c), 1200, 800);
    chaosCanvas = chaosGame.getCanvas();
  }
}
