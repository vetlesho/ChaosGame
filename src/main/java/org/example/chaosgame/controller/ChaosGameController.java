package org.example.chaosgame.controller;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.example.chaosgame.model.chaos.*;
import org.example.chaosgame.controller.observer.GameObserver;
import org.example.chaosgame.controller.observer.PageObserver;
import org.example.chaosgame.controller.observer.PageSubject;
import org.example.chaosgame.view.ChaosPage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChaosGameController implements GameObserver, PageSubject {
  private final ChaosGame chaosGame;
  private final ChaosPage chaosPage;
  private final List<PageObserver> pageObservers;

  public ChaosGameController() {
    this.chaosGame = new ChaosGame(Objects.requireNonNull(ChaosGameDescriptionFactory.get(ChaosGameType.JULIA)), 1200, 800);
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

  private void updateChaosGame(ChaosGameDescription description){
    chaosGame.setChaosGameDescription(description);
    chaosGame.setChaosCanvas(description.getMinCoords(), description.getMaxCoords());
  }

  public void gameSelection(String selectedGame){
    updateChaosGame(ChaosGameDescriptionFactory.get(ChaosGameType.valueOf(selectedGame)));
  }

  public void runStepsValidation(TextField stepsField){
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
      stepsField.setPromptText("Write a number between 1 - 10 000 000.");
    }
  }

  public void openFromFile(){
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
  }

  public void homeButtonClicked() {
    notifyObservers(chaosPage);
  }

  @Override
  public void update() {
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
