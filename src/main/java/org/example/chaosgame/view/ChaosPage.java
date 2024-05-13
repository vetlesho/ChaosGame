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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.controller.MainController;
import org.example.chaosgame.model.chaos.*;
import org.example.chaosgame.model.linalg.Complex;
import java.io.File;
import java.io.IOException;
import javafx.stage.FileChooser;
import org.example.chaosgame.view.components.HomeButton;


public class ChaosPage extends StackPane{
  private ChaosGameController chaosGameController;
  private ChaosGame chaosGame;
  private ChaosCanvas chaosCanvas;
  private final Button runStepsButton = new Button("Run Steps");
  private final Canvas canvas = new Canvas(1200, 800);
  private final GraphicsContext gc ;
  private final Label errorLabel = new Label("Invalid input. Please enter a valid number.");
  private final VBox runStepsBox = new VBox();

  private final Button homeButton = new HomeButton();


  public ChaosPage(MainController mainController) {

    gc = canvas.getGraphicsContext2D();
    updateChaosGame(ChaosGameType.JULIA);
    chaosCanvas = chaosGame.getCanvas();
    chaosGame.registerObserver(chaosGameController);


    TextField stepsField = new TextField();
    stepsField.setMaxWidth(150);
    stepsField.setPromptText("Enter number of steps");


    ComboBox<String> contextMenu = new ComboBox<>();
    contextMenu.setPromptText("Select chaos game");

    contextMenu.getItems().addAll("Julia", "Sierpinski", "Barnsley", "Make your own");

    contextMenu.setOnAction(event -> {
      String selectedGame = contextMenu.getValue().trim().toUpperCase();

      if(selectedGame.equals("MAKE YOUR OWN")) {

        chaosGame = new ChaosGame(ChaosGameDescriptionFactory.get(ChaosGameType.MAKE_YOUR_OWN), 1200, 800);
      } else {
        updateChaosGame(ChaosGameType.valueOf(selectedGame));
      }
      updateCanvas();
    });

    runStepsButton.setOnAction(event -> {
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


Button openFileButton = new Button("Open File");
openFileButton.setOnAction(e -> {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
        try {
          ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
          ChaosGameDescription description = fileHandler.readFromFile(selectedFile.getAbsolutePath());
          updateChaosGame(description);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
});

    runStepsBox.getChildren().addAll(contextMenu, stepsField, runStepsButton, openFileButton);
    runStepsBox.setSpacing(10);
    runStepsBox.setPadding(new Insets(10));
    runStepsBox.setAlignment(Pos.CENTER_RIGHT);
    setAlignment(homeButton, Pos.TOP_LEFT);

    homeButton.setOnAction(e -> mainController.homeButtonClicked());
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

  private void updateChaosGame(ChaosGameType type) {
    chaosGame = new ChaosGame(ChaosGameDescriptionFactory.get(type), 1200, 800);
    chaosCanvas = chaosGame.getCanvas();
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }
  private void updateChaosGame(ChaosGameDescription description){
    chaosGame = new ChaosGame(description, 1200, 800);
    chaosCanvas = chaosGame.getCanvas();
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }

  public Button getHomeButton() {
    return homeButton;
  }
}
