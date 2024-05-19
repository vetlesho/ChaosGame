package org.example.chaosgame.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import org.example.chaosgame.controller.interfaces.GameController;
import org.example.chaosgame.controller.interfaces.Observer;
import org.example.chaosgame.controller.interfaces.Subject;
import org.example.chaosgame.model.chaos.ChaosGame;
import org.example.chaosgame.model.chaos.ChaosGameDescription;
import org.example.chaosgame.model.chaos.ChaosGameDescriptionFactory;
import org.example.chaosgame.model.chaos.ChaosGameFileHandler;
import org.example.chaosgame.model.chaos.ChaosGameType;
import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.AffineTransform2D;
import org.example.chaosgame.model.transformations.JuliaTransform;
import org.example.chaosgame.model.transformations.Transform2D;
import org.example.chaosgame.view.ChaosPage;
import org.example.chaosgame.view.components.AlertUtility;
import org.example.chaosgame.view.components.CreateFractalDialog;
import org.example.chaosgame.view.components.MinMaxDialog;

/**
 * Controller class for the chaos game.
 * Handles the logic from the view to the model.
 *
 * <p>Implements the Observer and Subject interfaces for the observer pattern.
 *
 * <p>Implements the GameController interface.
 */
public class ChaosGameController implements Observer, Subject, GameController {
  private final ChaosGame chaosGame;
  private final ChaosPage chaosPage;
  private final List<Observer> pageObservers;
  private static final int WIDTH = 1200;
  private static final int HEIGHT = 800;
  private Canvas canvas;

  /**
   * Constructor for the ChaosGameController.
   *
   * <p>Initializes the ChaosGame and ChaosPage.
   * Register the ChaosGame as an observer.
   *
   */
  public ChaosGameController() {
    this.chaosGame = ChaosGame.getInstance(Objects.requireNonNull(
                    ChaosGameDescriptionFactory.get(ChaosGameType.JULIA)),
            WIDTH, HEIGHT);
    this.chaosPage = new ChaosPage(this);
    setCanvas(chaosPage.getGraphicsContext().getCanvas());
    this.pageObservers = new ArrayList<>();
    chaosGame.registerObserver(this);
  }

  public ChaosPage getGamePage() {
    return chaosPage;
  }

  public void setCanvas(Canvas canvas) {
    this.canvas = canvas;
  }

  /**
   * Method responsible for changing ChaosGameDescription.
   *
   * @param description Description of the chaos game
   */
  private void updateChaosGame(ChaosGameDescription description) {
    chaosGame.setChaosGameDescription(description);
  }

  /**
   * Method for selecting a new ChaosGameDescription.
   *
   * @param selectedGame Name of the selected game
   */
  public void gameSelection(String selectedGame) {
    try {
      ChaosGameType gameType = ChaosGameType.valueOf(selectedGame);
      updateChaosGame(ChaosGameDescriptionFactory.get(gameType));
    } catch (IllegalArgumentException e) {
      AlertUtility.showErrorDialog("Invalid input", "Please select a valid game.");
    }
  }

  /**
   * Method for running the chaos game.
   * Validates the input from the user.
   *
   * @param stepsField TextField for the number of steps
   */
  public void runStepsValidation(TextField stepsField) {
    String input = stepsField.getText();
    try {
      int steps = Integer.parseInt(input);
      if (steps < 1 || steps > 10000000) {
        throw new NumberFormatException();
      }
      chaosGame.setSteps(steps);
      chaosGame.addTotalSteps(steps);
      chaosGame.runSteps();
      stepsField.getStyleClass().remove("text-field-invalid");
    } catch (NumberFormatException ex) {
      stepsField.clear();
      stepsField.getStyleClass().add("text-field-invalid");
      AlertUtility.showErrorDialog(
              "Invalid input", "Please enter a number between 1 - 10 000 000.");
    }
  }

  /**
   * Method for setting the min and max coordinates for the chaos game.
   *
   * <p>Opens a dialog for the user to enter the coordinates.
   *
   */
  public void setMaxMinCoords() {
    MinMaxDialog dialog = new MinMaxDialog();
    Optional<List<String>> result = dialog.showAndWait();

    if (result.isPresent()) {
      try {
        List<String> coords = result.get();
        Vector2D min = new Vector2D(Double.parseDouble(coords.get(0)),
                Double.parseDouble(coords.get(1)));
        Vector2D max = new Vector2D(Double.parseDouble(coords.get(2)),
                Double.parseDouble(coords.get(3)));

        if (validateCoordinates(min) && validateCoordinates(max)) {
          updateChaosGame(new ChaosGameDescription(
                  min, max,
                  chaosGame.getDescription().getTransforms()));
        } else {
          AlertUtility.showErrorDialog("Invalid input",
                  "Please enter a double between -50 and 50.");
        }
      } catch (NumberFormatException e) {

        AlertUtility.showErrorDialog("Invalid input",
                "Please enter a valid number.");
      } catch (IndexOutOfBoundsException e) {
        AlertUtility.showErrorDialog("Invalid input",
                "Please enter all coordinates.");
      }

    }
  }

  /**
   * Method for validating the coordinates.
   *
   * @param vector Vector2D with the coordinates
   * @return boolean True if the coordinates are valid, false otherwise
   */
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

  /**
   * Method for opening a file with a chaos game description.
   *
   * <p>Opens a file chooser dialog for the user to select a file.
   * Read the file and update the chaos game.
   *
   */
  public void openFromFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
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

  /**
   * Method for creating a new fractal.
   *
   * <p>Opens a dialog for the user to create a new fractal.
   *
   */
  public void createOwnFractal() {
    CreateFractalDialog dialog = new CreateFractalDialog();
    Optional<Object> result = dialog.showAndWait();

    if (result.isPresent()) {
      Object fractalData = result.get();

      if (fractalData instanceof List) {
        List<AffineTransform2D> transformations = (List<AffineTransform2D>) fractalData;
        List<Transform2D> transforms = new ArrayList<>(transformations);
        updateChaosGame(new ChaosGameDescription(
                new Vector2D(0, 0),
                new Vector2D(1.0, 1.0),
                transforms));
      } else if (fractalData instanceof Pair) {
        Pair<String, String> userInput = (Pair<String, String>) fractalData;
        try { // Check if the input is a valid number
          double real = Double.parseDouble(userInput.getKey());
          double imaginary = Double.parseDouble(userInput.getValue());

          if (real < -1 || real > 1 || imaginary < -1 || imaginary > 1) {
            AlertUtility.showErrorDialog("Invalid input",
                    "Please enter a double between -1 and 1. No letters are allowed.");
          } else {
            updateChaosGame(new ChaosGameDescription(
                    new Vector2D(-1.6, -1),
                    new Vector2D(1.6, 1.0),
                    List.of(new JuliaTransform(new Complex(real, imaginary), 1))));
          }
        } catch (NumberFormatException e) {
          AlertUtility.showErrorDialog("Invalid input", "Please enter a valid number.");
        }
        double real = Double.parseDouble(userInput.getKey());
        double imaginary = Double.parseDouble(userInput.getValue());

        if (real < -1 || real > 1 || imaginary < -1 || imaginary > 1) {
          AlertUtility.showErrorDialog("Invalid input",
                  "Please enter a double between -1 and 1. No letters are allowed.");
        } else {
          updateChaosGame(new ChaosGameDescription(
                  new Vector2D(-1.6, -1),
                  new Vector2D(1.6, 1.0),
                  List.of(new JuliaTransform(new Complex(real, imaginary), 1))));
        }
      }
    }
  }

  @Override
  public void setBind(StackPane mainPane) {
    canvas.widthProperty().bind(mainPane.widthProperty().multiply(0.85));
    canvas.heightProperty().bind(mainPane.heightProperty().multiply(0.85));
    mainPane.heightProperty().addListener((observable, oldValue, newValue) -> {
      if (mainPane.getHeight() > 0 && mainPane.getWidth() > 0) {
        chaosGame.notifyObservers();
      }
    });
    mainPane.widthProperty().addListener((observable, oldValue, newValue) -> {
      if (mainPane.getHeight() > 0 && mainPane.getWidth() > 0) {
        chaosGame.notifyObservers();
      }
    });
  }

  /**
   * Method for resetting the chaos game.
   */
  public void resetGame() {
    chaosGame.resetTotalSteps();
    chaosPage.updateInformation(chaosGame.getDescription().getTransforms().getFirst(),
            chaosGame.getTotalSteps(),
            chaosGame.getDescription().getMinCoords(),
            chaosGame.getDescription().getMaxCoords());
    chaosPage.clearCanvas();
  }

  @Override
  public void updateJuliaValue(String partType, double value) {
    JuliaTransform juliaTransform =
            (JuliaTransform) chaosGame.getDescription().getTransforms().getFirst();
    double realPart = partType.equals("real")
            ? value : juliaTransform.getComplex().getX();
    double imaginaryPart = partType.equals("imaginary")
            ? value : juliaTransform.getComplex().getY();

    updateChaosGame(new ChaosGameDescription(
            new Vector2D(chaosGame.getDescription().getMinCoords().getX(),
                    chaosGame.getDescription().getMinCoords().getY()),
            new Vector2D(chaosGame.getDescription().getMaxCoords().getX(),
                    chaosGame.getDescription().getMaxCoords().getY()),
            List.of(new JuliaTransform(new Complex(realPart, imaginaryPart), 1))));
    chaosGame.setTotalSteps(chaosGame.getSteps());
    chaosGame.runSteps();
  }

  /**
   * Method for saving the fractal to a file.
   *
   * <p>Opens a file chooser dialog for the user to select a file.
   * Write the fractal to the file using FileHandler class.
   */
  public void saveFractal() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
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
  public void updateFractalColor(Color color) {
    chaosPage.setFractalColor(color);
    chaosPage.updateCanvas(chaosGame.getCanvas());
  }

  @Override
  public void homeButtonClicked() {
    notifyObservers();
  }

  /**
   * Method for updating the view.
   * Notified from the ChaosGame (model).
   */
  @Override
  public void update() {
    chaosPage.updateInformation(chaosGame.getDescription().getTransforms().getFirst(),
            chaosGame.getTotalSteps(),
            chaosGame.getDescription().getMinCoords(),
            chaosGame.getDescription().getMaxCoords());
    chaosPage.updateCanvas(chaosGame.getCanvas());
  }

  /**
   * Method for registering observers.
   *
   * @param observer Observer to register
   */
  @Override
  public void registerObserver(Observer observer) {
    pageObservers.add(observer);
  }

  /**
   * Method for removing observers.
   *
   * @param observer Observer to remove
   */
  @Override
  public void removeObserver(Observer observer) {
    pageObservers.remove(observer);
  }

  /**
   * Method for notifying observers.
   */
  @Override
  public void notifyObservers() {
    for (Observer pageObserver : pageObservers) {
      pageObserver.update();
    }
  }
}
