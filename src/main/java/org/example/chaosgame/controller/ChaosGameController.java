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

  public ChaosPage getChaosPage() {
    return chaosPage;
  }

  private void updateChaosGame(ChaosGameDescription description){
    chaosGame.setChaosGameDescription(description);
    chaosGame.setChaosCanvas(description.getMinCoords(), description.getMaxCoords());
  }

  public void gameSelection(String selectedGame){
    if(selectedGame.equals("MAKE YOUR OWN")) {
      updateChaosGame(ChaosGameDescriptionFactory.get(ChaosGameType.MAKE_YOUR_OWN));
    } else {
      updateChaosGame(ChaosGameDescriptionFactory.get(ChaosGameType.valueOf(selectedGame)));
    }
  }

  public void runSteps(TextField stepsField){
    if (!stepsField.getText().isEmpty()) {
      try {
        chaosGame.runSteps(Integer.parseInt(stepsField.getText()));
        stepsField.getStyleClass().remove("text-field-invalid");
        stepsField.getStyleClass().add("text-field");
      } catch (NumberFormatException ex) {
        stepsField.clear();
        stepsField.getStyleClass().add("text-field-invalid");
        stepsField.setPromptText("Invalid input. Please enter a valid number.");
      }
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
