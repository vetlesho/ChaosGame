package org.example.chaosgame.controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import org.example.chaosgame.controller.observer.Observer;
import org.example.chaosgame.controller.observer.Subject;
import org.example.chaosgame.controller.observer.GameController;
import org.example.chaosgame.model.chaos.*;
import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.linalg.Matrix2x2;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.AffineTransform2D;
import org.example.chaosgame.model.transformations.JuliaTransform;
import org.example.chaosgame.model.transformations.Transform2D;
import org.example.chaosgame.view.ChaosPage;
import org.example.chaosgame.view.components.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ChaosGameController implements Observer, Subject, GameController {
  private final ChaosGame chaosGame;
  private final ChaosPage chaosPage;
  private final List<Observer> pageObservers;
  private static final int WIDTH = 1200;
  private static final int HEIGHT = 800;
  private Canvas canvas;

  public ChaosGameController() {
    this.chaosGame = ChaosGame.getInstance(Objects.requireNonNull(
                    ChaosGameDescriptionFactory.get(ChaosGameType.JULIA)),
            WIDTH, HEIGHT);
    this.chaosPage = new ChaosPage(this);
    setCanvas(chaosPage.getGraphicsContext().getCanvas());
    this.pageObservers = new ArrayList<>();
    chaosGame.registerObserver(this);
  }

  public ChaosGame getChaosGame() {
    return chaosGame;
  }

  public ChaosPage getGamePage() {
    return chaosPage;
  }

  private void updateChaosGame(ChaosGameDescription description) {
    chaosGame.setChaosGameDescription(description);
  }

  public void gameSelection(String selectedGame) {
    try {
      ChaosGameType gameType = ChaosGameType.valueOf(selectedGame);
      updateChaosGame(ChaosGameDescriptionFactory.get(gameType));
    } catch (IllegalArgumentException e) {
      AlertUtility.showErrorDialog("Invalid input", "Please select a valid game.");
    }
  }

  public void runStepsValidation(TextField stepsField) {
    String input = stepsField.getText();
    try {
      int steps = Integer.parseInt(input);
      if (steps < 1 || steps > 10000000) {
        throw new NumberFormatException();
      }
      if (chaosGame.getDescription().getTransforms().getFirst() instanceof JuliaTransform && steps > 250000) {
        AlertUtility.showErrorDialog("Invalid input", "Please enter a lower amount of steps for Julia transformations.");
        return;
      }
      if (chaosGame.getTotalSteps() > Math.pow(10, 8)) {
        AlertUtility.showErrorDialog("Invalid input", "The total number of steps is too high. Please reset the game.");
        return;
      }
      chaosGame.setSteps(steps);
      chaosGame.addTotalSteps(steps);
      chaosGame.runSteps();
      stepsField.getStyleClass().remove("text-field-invalid");
    } catch (NumberFormatException ex) {
      stepsField.clear();
      stepsField.getStyleClass().add("text-field-invalid");
      AlertUtility.showErrorDialog("Invalid input", "Please enter a number between 1 - 10 000 000.");
    }
  }

  public void setMaxMinCoords() {
    MinMaxDialog dialog = new MinMaxDialog();
    Optional<List<String>> result = dialog.showAndWait();

    if (result.isPresent()) {
      try {
        List<String> coords = result.get();
        Vector2D min = new Vector2D(Double.parseDouble(coords.get(0)), Double.parseDouble(coords.get(1)));
        Vector2D max = new Vector2D(Double.parseDouble(coords.get(2)), Double.parseDouble(coords.get(3)));

        if (validateCoordinates(min) && validateCoordinates(max)) {
          updateChaosGame(new ChaosGameDescription(
                  min, max,
                  chaosGame.getDescription().getTransforms()));
        } else {
          AlertUtility.showErrorDialog("Invalid input", "Please enter a double between -50 and 50.");
        }
      } catch (NumberFormatException e) {

        AlertUtility.showErrorDialog("Invalid input", "Please enter a valid number.");
      } catch (IndexOutOfBoundsException e) {
        AlertUtility.showErrorDialog("Invalid input", "Please enter all coordinates.");
      }

    }
  }

  private boolean validateCoordinates(Vector2D vector) {
    try {
      System.out.println("parsing" + vector.getX() + " " + vector.getY());
      double x = vector.getX();
      double y = vector.getY();
      if (x < -50 || x > 50 || y < -50 || y > 50) {
        return false;
      }
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  public void openFromFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
      try {
        ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
        updateChaosGame(fileHandler.readFromFile(selectedFile.getAbsolutePath()));
      } catch (NumberFormatException ex) {
        AlertUtility.showErrorDialog("Error", "Invalid input in the file. Try another file.");
      } catch (IOException e) {
        AlertUtility.showErrorDialog("Error", "Could not read the file. Try another file.");
      }
    }
  }

  public void updateFractalColor(Color color) {
    chaosPage.setFractalColor(color);
    chaosPage.updateCanvas(chaosGame.getCanvas());
  }

  public void createOwnFractal() {
    CreateFractalDialog dialog = new CreateFractalDialog();
    Optional<Object> result = dialog.showAndWait();

    if (result.isPresent()) {
      Object fractalData = result.get();
      List<Transform2D> transforms = new ArrayList<>();
      if (fractalData instanceof List) {
        List<List<String>> userInput = (List<List<String>>) fractalData;
        for(List<String> input : userInput) {
          try {
            double a = Double.parseDouble(input.get(0));
            double b = Double.parseDouble(input.get(1));
            double c = Double.parseDouble(input.get(2));
            double d = Double.parseDouble(input.get(3));
            double x = Double.parseDouble(input.get(4));
            double y = Double.parseDouble(input.get(5));

            if (a < -5 || a > 5 || b < -5 || b > 5 || c < -5 || c > 5 ||
                    d < -5 || d > 5 || x < -5 || x > 5 || y < -5 || y > 5) {
              throw new NumberFormatException();
            } else {
              transforms.add(new AffineTransform2D(new Matrix2x2(a, b, c, d), new Vector2D(x, y)));
            }
            updateChaosGame(new ChaosGameDescription(
                    chaosGame.getDescription().getMinCoords(),
                    chaosGame.getDescription().getMaxCoords(),
                    transforms));
          } catch (NumberFormatException e) {
            AlertUtility.showErrorDialog("Invalid input", "Please enter a valid number.");
          }
        }
//        List<Transform2D> transforms = new ArrayList<>(transformations);
      } else if (fractalData instanceof Pair) {
        Pair<String, String> userInput = (Pair<String, String>) fractalData;
        try { // Check if the input is a valid number
          double real = Double.parseDouble(userInput.getKey());
          double imaginary = Double.parseDouble(userInput.getValue());

          if (real < -1 || real > 1 || imaginary < -1 || imaginary > 1) {
            AlertUtility.showErrorDialog("Invalid input", "Please enter a double between -1 and 1. No letters are allowed.");
          } else {
            updateChaosGame(new ChaosGameDescription(
                    chaosGame.getDescription().getMinCoords(),
                    chaosGame.getDescription().getMaxCoords(),
                    List.of(new JuliaTransform(new Complex(real, imaginary), 1))));
          }
        } catch (NumberFormatException e) {
          AlertUtility.showErrorDialog("Invalid input", "Please enter a valid number.");
        }
      }
    }
  }

  public void resetGame() {
    chaosGame.resetTotalSteps();
    update();
    chaosPage.clearCanvas();
  }

  @Override
  public void updateJuliaValue(String partType, double value) {
    JuliaTransform juliaTransform = (JuliaTransform) chaosGame.getDescription().getTransforms().getFirst();
    double realPart = partType.equals("real") ? value : juliaTransform.getComplex().getX();
    double imaginaryPart = partType.equals("imaginary") ? value : juliaTransform.getComplex().getY();

    updateChaosGame(new ChaosGameDescription(
            new Vector2D(chaosGame.getDescription().getMinCoords().getX(), chaosGame.getDescription().getMinCoords().getY()),
            new Vector2D(chaosGame.getDescription().getMaxCoords().getX(), chaosGame.getDescription().getMaxCoords().getY()),
            List.of(new JuliaTransform(new Complex(realPart, imaginaryPart), 1))));
    chaosGame.setTotalSteps(chaosGame.getSteps());
    chaosGame.runSteps();
  }

  public void saveFractal() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
    File selectedFile = fileChooser.showSaveDialog(null);

    if (selectedFile != null) {
      try {
        ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
        ChaosGameDescription description = chaosGame.getDescription();
        fileHandler.writeToFile(description, selectedFile.getAbsolutePath());
      } catch (IOException ex) {
        AlertUtility.showErrorDialog("Error", "Could not save file. Try again.");
      }
    }
  }

  @Override
  public void homeButtonClicked() {
    notifyObservers();
  }

  @Override
  public void update() {
    chaosPage.updateInformation(chaosGame.getDescription().getTransforms().getFirst(),
            chaosGame.getTotalSteps(),
            chaosGame.getDescription().getMinCoords(),
            chaosGame.getDescription().getMaxCoords());
    chaosPage.updateCanvas(chaosGame.getCanvas());
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

  public void setCanvas(Canvas canvas) {
    this.canvas = canvas;
  }

  public void setBind(StackPane mainPane) {
    canvas.widthProperty().bind(mainPane.widthProperty().multiply(0.85));
    canvas.heightProperty().bind(mainPane.heightProperty().multiply(0.85));
    mainPane.heightProperty().addListener((observable, oldValue, newValue) -> {
      // Update the canvas height here
      if (mainPane.getHeight() > 0 && mainPane.getWidth() > 0) {
        chaosGame.notifyObservers();
      }
    });
    // Add a change listener to the width property
    mainPane.widthProperty().addListener((observable, oldValue, newValue) -> {
      // Update the canvas width here
      if (mainPane.getHeight() > 0 && mainPane.getWidth() > 0) {
        chaosGame.notifyObservers();
      }
    });
  }
}
