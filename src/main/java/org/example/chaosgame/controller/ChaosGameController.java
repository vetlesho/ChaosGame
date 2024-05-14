package org.example.chaosgame.controller;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.example.chaosgame.model.chaos.*;
import org.example.chaosgame.observer.Observer;
import org.example.chaosgame.observer.PageObserver;
import org.example.chaosgame.observer.PageSubject;
import org.example.chaosgame.view.ChaosPage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChaosGameController implements Observer, PageSubject {
  private ChaosGame chaosGame;
  private ChaosCanvas chaosCanvas;
  private final ChaosPage chaosPage;
  private final List<PageObserver> pageObservers;

  public ChaosGameController() {
    this.chaosGame = new ChaosGame(ChaosGameDescriptionFactory.get(ChaosGameType.JULIA), 1200, 800);
    this.chaosCanvas = chaosGame.getCanvas();
    this.chaosPage = new ChaosPage(this);
    this.pageObservers = new ArrayList<>();
    chaosGame.registerObserver(this);
  }

  public ChaosPage getChaosPage() {
    return chaosPage;
  }
  public ChaosCanvas getChaosCanvas(){
    return chaosCanvas;
  }

  private void updateChaosGame(ChaosGameDescription description){
    chaosGame = new ChaosGame(description, 1200, 800);
    chaosCanvas = chaosGame.getCanvas();
  }

  public void gameSelection(String selectedGame){
    if(selectedGame.equals("MAKE YOUR OWN")) {
      updateChaosGame(ChaosGameDescriptionFactory.get(ChaosGameType.MAKE_YOUR_OWN));
    } else {
      updateChaosGame(ChaosGameDescriptionFactory.get(ChaosGameType.valueOf(selectedGame)));
    }
    chaosPage.updateCanvas();
  }

  public void runSteps(TextField stepsField){
    if (!stepsField.getText().isEmpty()) {
      try {
        chaosGame.runSteps(Integer.parseInt(stepsField.getText()));
        chaosPage.updateCanvas();
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
    chaosPage.updateCanvas();
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
