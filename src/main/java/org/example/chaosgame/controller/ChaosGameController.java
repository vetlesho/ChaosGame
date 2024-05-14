package org.example.chaosgame.controller;

import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import org.example.chaosgame.model.chaos.*;
import org.example.chaosgame.view.ChaosPage;

import java.io.File;
import java.io.IOException;

public class ChaosGameController implements Observer{
  private ChaosGame chaosGame;
  private final PageController pageController;
  private ChaosCanvas chaosCanvas;
  private final ChaosPage chaosPage;




  public ChaosGameController(PageController pageController) {
    this.chaosGame = new ChaosGame(ChaosGameDescriptionFactory.get(ChaosGameType.JULIA), 1200, 800);
    this.chaosCanvas = chaosGame.getCanvas();
    this.pageController = pageController;
    chaosGame.registerObserver(this);
    this.chaosPage = new ChaosPage(this);
  }

  @Override
  public void update() {
    chaosPage.updateCanvas();
    System.out.println("ChaosGameController.update");
  }

  public ChaosPage getChaosPage() {
    return chaosPage;
  }
  public ChaosCanvas getChaosCanvas(){
    return chaosCanvas;
  }
  public void homeButtonClicked() {
    pageController.homeButtonClicked();
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


  public void zoomIn(ScrollEvent event, Canvas canvas) {
    double scaleFactor = (event.getDeltaY() > 0) ? (1.0 / 1.05) : 1.05;
    double mouseX = event.getX() - (double) chaosCanvas.getWidth() / 2;
    double mouseY = event.getY() - (double) chaosCanvas.getHeight() / 2;
    double translateX = canvas.getTranslateX();
    double translateY = canvas.getTranslateY();


    canvas.setScaleX(canvas.getScaleX() * scaleFactor);
    canvas.setScaleY(canvas.getScaleY() * scaleFactor);


    double newTranslateX = (mouseX - translateX) * (scaleFactor - 1);
    double newTranslateY = (mouseY - translateY) * (scaleFactor - 1);
    double setTranslateX = translateX - newTranslateX;
    double setTranslateY = translateY - newTranslateY;
    canvas.setTranslateX(setTranslateX);
    canvas.setTranslateY(setTranslateY);
  }
}
