package org.idatt2003.controller;

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
import org.idatt2003.controller.interfaces.GameController;
import org.idatt2003.controller.interfaces.Observer;
import org.idatt2003.controller.interfaces.Subject;
import org.idatt2003.model.chaos.ChaosGame;
import org.idatt2003.model.chaos.ChaosGameDescription;
import org.idatt2003.model.chaos.ChaosGameType;
import org.idatt2003.model.linalg.Complex;
import org.idatt2003.model.linalg.Matrix2x2;
import org.idatt2003.model.linalg.Vector2D;
import org.idatt2003.model.transformations.AffineTransform2D;
import org.idatt2003.model.transformations.JuliaTransform;
import org.idatt2003.model.transformations.Transform2D;
import org.idatt2003.view.ChaosPage;
import org.idatt2003.view.components.AlertUtility;
import org.idatt2003.view.components.CreateFractalDialog;
import org.idatt2003.view.components.MinMaxDialog;

/**
 * Controller class for the chaos game.
 * Handles the logic from the view to the model.
 *
 * <p>Implements the Observer and Subject interfaces for the observer pattern.
 *
 * <p>Implements the GameController interface.
 */
public class ChaosGameController extends CanvasPainter
        implements Observer, Subject, GameController {
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
   * Registers the controller as an observer of the
   * chaos game
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
   * Uses the ChaosGameDescriptionFactory to change the game
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
      chaosGame.setSteps(steps);
      if (chaosGame.getDescription().getTransforms().getFirst()
              instanceof JuliaTransform && steps > 250000) {
        throw new IllegalArgumentException(
                "Please enter a lower amount of steps for Julia transformations.");
      }
      if (chaosGame.getTotalSteps() > Math.pow(10, 8)) {
        throw new IllegalArgumentException(
                "The total amount of steps is too high. Choose a lower amount.");
      }
      chaosGame.addTotalSteps(steps);
      chaosGame.runSteps();
      stepsField.getStyleClass().remove("text-field-invalid");

    } catch (NumberFormatException e) {
      stepsField.clear();
      stepsField.getStyleClass().add("text-field-invalid");
      AlertUtility.showErrorDialog("Invalid input", "Please enter a valid number.");
    } catch (IllegalArgumentException ex) {
      stepsField.clear();
      stepsField.getStyleClass().add("text-field-invalid");
      AlertUtility.showErrorDialog(
              "Invalid input", ex.getMessage());
    }
  }

  /**
   * Method for setting the min and max coordinates for the chaos game.
   * Updates the ChaosGameDescription to the new coordinate values.
   * Catches different exceptions and displays a fitting message to user.
   *
   * <p>Opens a dialog for the user to enter the coordinates.
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

        updateChaosGame(new ChaosGameDescription(min, max,
                chaosGame.getDescription().getTransforms(),
                chaosGame.getDescription().getProbabilities()));
      } catch (NumberFormatException e) {
        AlertUtility.showErrorDialog("Invalid input",
                "Please enter a valid number.");
      } catch (IndexOutOfBoundsException e) {
        AlertUtility.showErrorDialog("Invalid input",
                "Please enter all coordinates.");
      } catch (IllegalArgumentException e) {
        AlertUtility.showErrorDialog("Invalid input",
                e.getMessage());
      }

    }
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
   * Method for user creation of fractal.
   * {@link CreateFractalDialog} returns either a
   * {@link java.util.List}<{@link java.util.List}<{@link java.lang.String}>>
   * or a {@link javafx.util.Pair}<{@link java.lang.String}, {@link java.lang.String}>.
   *
   * <p>Opens a dialog for the user to create a new fractal.
   */
  public void createOwnFractal() {
    CreateFractalDialog dialog = new CreateFractalDialog();
    Optional<Object> result = dialog.showAndWait();

    if (result.isPresent()) {
      Object fractalData = result.get();
      List<Transform2D> transforms = new ArrayList<>();
      if (fractalData instanceof List) {
        List<List<String>> userInput = (List<List<String>>) fractalData;
        for (List<String> input : userInput) {
          try {
            double a = Double.parseDouble(input.get(0));
            double b = Double.parseDouble(input.get(1));
            double c = Double.parseDouble(input.get(2));
            double d = Double.parseDouble(input.get(3));
            double x = Double.parseDouble(input.get(4));
            double y = Double.parseDouble(input.get(5));

            if (a < -5 || a > 5 || b < -5 || b > 5 || c < -5 || c > 5
                    || d < -5 || d > 5 || x < -5 || x > 5 || y < -5 || y > 5) {
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
   * Resets the total steps.
   * Updates the information on the chaos page's top bar
   * and clears both the ChaosCanvas and the canvas
   */
  public void resetGame() {
    chaosGame.resetTotalSteps();
    chaosPage.updateInformation(chaosGame.getDescription().getTransforms().getFirst(),
            chaosGame.getTotalSteps(),
            chaosGame.getDescription().getMinCoords(),
            chaosGame.getDescription().getMaxCoords());
    chaosGame.getCanvas().clearCanvas();
    clearCanvas(chaosPage.getGraphicsContext());
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

  /**
   * Method for updating the fractal color.
   * Sets the color than updates the canvas with the new color.
   *
   * @param color the new color.
   */
  @Override
  public void updateFractalColor(Color color) {
    setFractalColor(color);
    updateCanvas(chaosGame.getCanvas(), chaosPage.getGraphicsContext());
  }

  /**
   * Notifies the page observers when the button is clicked.
   */
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
    updateCanvas(chaosGame.getCanvas(), chaosPage.getGraphicsContext());
  }

  /**
   * Method for registering page observers.
   *
   * @param observer Observer to register
   */
  @Override
  public void registerObserver(Observer observer) {
    pageObservers.add(observer);
  }

  /**
   * Method for removing page observers.
   *
   * @param observer Observer to remove
   */
  @Override
  public void removeObserver(Observer observer) {
    pageObservers.remove(observer);
  }

  /**
   * Method for notifying page observers.
   * Notifies if the displaying pages should
   * be changed.
   *
   */
  @Override
  public void notifyObservers() {
    for (Observer pageObserver : pageObservers) {
      pageObserver.update();
    }
  }
}
