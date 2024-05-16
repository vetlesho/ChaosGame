package org.example.chaosgame.controller;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import org.example.chaosgame.model.chaos.*;
import org.example.chaosgame.controller.observer.GameObserver;
import org.example.chaosgame.controller.observer.PageObserver;
import org.example.chaosgame.controller.observer.PageSubject;
import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.AffineTransform2D;
import org.example.chaosgame.model.transformations.JuliaTransform;
import org.example.chaosgame.model.transformations.Transform2D;
import org.example.chaosgame.view.ChaosPage;
import org.example.chaosgame.view.components.CreateAffineDialog;
import org.example.chaosgame.view.components.CreateJuliaDialog;
import org.example.chaosgame.view.components.DialogUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ChaosGameController implements GameObserver, PageSubject {
  private final ChaosGame chaosGame;
  private final ChaosPage chaosPage;
  private final List<PageObserver> pageObservers;

  public ChaosGameController() {
    this.chaosGame = new ChaosGame(Objects.requireNonNull(ChaosGameDescriptionFactory.get(ChaosGameType.SIERPINSKI)), 1200, 800);
    this.chaosPage = new ChaosPage(this);
    this.pageObservers = new ArrayList<>();
    chaosGame.registerObserver(this);
  }

  public ChaosGame getChaosGame() {
    return chaosGame;
  }

  public ChaosPage getChaosPage() {
    return chaosPage;
  }

  private void updateChaosGame(ChaosGameDescription description) {
    chaosGame.setChaosGameDescription(description);
  }

  public void gameSelection(String selectedGame) {
    updateChaosGame(ChaosGameDescriptionFactory.get(ChaosGameType.valueOf(selectedGame)));
  }

  public void runStepsValidation(TextField stepsField) {
    String input = stepsField.getText();
    try {
      int steps = Integer.parseInt(input);
      if (steps < 1 || steps > 10000000) {
        throw new NumberFormatException();
      }
      chaosGame.runSteps(steps);
      stepsField.getStyleClass().remove("text-field-invalid");
    } catch (NumberFormatException ex) {
      stepsField.clear();
      stepsField.getStyleClass().add("text-field-invalid");
      DialogUtils.showErrorDialog("Invalid input", "Please enter a number between 1 - 10 000 000.");
      //stepsField.setPromptText("Write a number between 1 - 10 000 000.");
    }
  }

  public void openFromFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
      try {
        ChaosGameFileHandler fileHandler = new ChaosGameFileHandler();
        chaosGame.setChaosGameDescription(fileHandler.readFromFile(selectedFile.getAbsolutePath()));
      } catch (NumberFormatException ex) {
        DialogUtils.showErrorDialog("Error", "Invalid input in the file. Try another file.");
      } catch (IOException e) {
        DialogUtils.showErrorDialog("Error", "Could not read the file. Try another file.");
      }
    }
}

  public void updateFractalColor(Color color) {
    chaosPage.setFractalColor(color);
    chaosPage.updateCanvas(chaosGame.getCanvas());
  }

  public void createOwnJuliaFractal() {
    CreateJuliaDialog dialog = new CreateJuliaDialog();
    Optional<Pair<String, String>> result = dialog.showAndWait();
    String errorTitle = "Invalid input";
    String errorMessage = "Please enter a double between -1 and 1. No letters are allowed.";

    if (result.isPresent()) {
      Pair<String, String> userInput = result.get();
      try {
        double real = Double.parseDouble(userInput.getKey());
        double imaginary = Double.parseDouble(userInput.getValue());

        if (real < -1 || real > 1 || imaginary < -1 || imaginary > 1) {
          DialogUtils.showErrorDialog(errorTitle, errorMessage);
        } else {
          chaosGame.setChaosGameDescription(new ChaosGameDescription(
                  new Vector2D(-1.6, -1),
                  new Vector2D(1.6, 1.0),
                  List.of(new JuliaTransform(new Complex(real, imaginary), 1))));
        }
      } catch (NumberFormatException ex) {
        DialogUtils.showErrorDialog(errorTitle, errorMessage);
      }
    }
  }

  public void createOwnAffineFractal() {
    CreateAffineDialog dialog = new CreateAffineDialog();
    Optional<List<AffineTransform2D>> result = dialog.showAndWait();
    String errorTitle = "Invalid input";
    String errorMessage = "Please enter a valid number.";

    if (result.isPresent()) {
      List<AffineTransform2D> transformations = result.get();
      List<Transform2D> transforms = new ArrayList<>(transformations);
      chaosGame.setChaosGameDescription(new ChaosGameDescription(
              new Vector2D(0, 0),
              new Vector2D(1.0, 1.0),
              transforms));
    } else {
      DialogUtils.showErrorDialog(errorTitle, errorMessage);
    }
  }

  public void updateJuliaValue(double reValue, double imValue) {
    chaosGame.setChaosGameDescription(new ChaosGameDescription(
            new Vector2D(-1.6, -1),
            new Vector2D(1.6, 1.0),
            List.of(new JuliaTransform(new Complex(reValue, imValue), 1))));
    runStepsValidation(new TextField("10000"));
  }

  public boolean isJuliaShowing() {
    return chaosGame.getDescription().getTransforms().getFirst() instanceof JuliaTransform;
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
        DialogUtils.showErrorDialog("Error", "Could not save file. Try again.");
      }
    }
  }

  public void homeButtonClicked() {
    notifyObservers(chaosPage);
  }

  @Override
  public void update() {
    chaosPage.setSliderVisibility(isJuliaShowing());
    chaosPage.updateCanvas(chaosGame.getCanvas());
  }

  @Override
  public void registerObserver(PageObserver observer) {
    pageObservers.add(observer);
  }

  @Override
  public void removeObserver(PageObserver observer) {
    pageObservers.remove(observer);
  }

  @Override
  public void notifyObservers(Node chaosPage) {
    for (PageObserver pageObserver : pageObservers) {
      pageObserver.update(chaosPage);
    }
  }
}
