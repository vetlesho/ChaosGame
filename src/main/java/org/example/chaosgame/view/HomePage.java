package org.example.chaosgame.view;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.controller.HomeController;
import org.example.chaosgame.controller.PageController;
import org.example.chaosgame.model.chaos.ChaosGameDescription;
import org.example.chaosgame.model.chaos.ChaosGameFileHandler;

import org.example.chaosgame.view.components.ExitButton;
import org.example.chaosgame.view.components.GameHeader;
import javafx.scene.media.Media;

import java.io.File;
import java.io.FileReader;
import java.util.Objects;
import java.util.Stack;


public class HomePage extends StackPane {
  private final String EXPLORE_PATH = Objects.requireNonNull(getClass().getResource("/ExploreVideoFinal.mp4")).toString();
  private final String CHAOS_PATH = Objects.requireNonNull(getClass().getResource("/ChaosVideoFinal.mp4")).toString();
  private final MediaPlayer exploreVideo;
  private final MediaPlayer chaosVideo;

  private final MediaView exploreView;
  private final MediaView chaosView;
  private final StackPane explorePane;
  private final StackPane chaosPane;

  //private final HomeController homeController;
  public HomePage(PageController pageController, HomeController homeController) {
    //this.homeController = homeController;
    HBox videoBox = new HBox();
    videoBox.prefWidthProperty().bind(this.prefWidthProperty());
    videoBox.prefHeightProperty().bind(this.prefHeightProperty());
    /*videoBox.setStyle("-fx-background-color: black;");*/
    Text header = new GameHeader("Welcome to ChaosGame");
    Button exitButton = new ExitButton();

    exitButton.setOnAction(e -> pageController.exitGame());
    ColorAdjust colorAdjust = new ColorAdjust();
    colorAdjust.setBrightness(-0.8);

    chaosVideo = new MediaPlayer(new Media(CHAOS_PATH));

    exploreVideo = new MediaPlayer(new Media(EXPLORE_PATH));

    chaosView = new MediaView(chaosVideo);
    chaosView.setEffect(null);

    exploreView = new MediaView(exploreVideo);
    exploreView.setEffect(colorAdjust);


    chaosPane = new StackPane();
    chaosPane.setStyle("-fx-background-color: transparent;");
    Text chaosHeader = new GameHeader("Chaos Game");
    chaosPane.getChildren().addAll(chaosView, chaosHeader);
    chaosPane.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> pageController.goToPage("chaos"));
    chaosPane.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> homeController.mouseEvent(MouseEvent.MOUSE_ENTERED,
            chaosVideo, chaosView, chaosHeader, null, null));
    chaosPane.addEventFilter(MouseEvent.MOUSE_EXITED, e -> homeController.mouseEvent(MouseEvent.MOUSE_EXITED,
            chaosVideo, chaosView, chaosHeader, null, colorAdjust));

    explorePane = new StackPane();
    Text exploreHeader = new GameHeader("Explore Game");
    explorePane.getChildren().addAll(exploreView, exploreHeader);
    explorePane.setStyle("-fx-background-color: black;");

    explorePane.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> pageController.goToPage("explore"));
    explorePane.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> homeController.mouseEvent(MouseEvent.MOUSE_ENTERED,
            exploreVideo, exploreView, exploreHeader, null, colorAdjust));
    explorePane.addEventFilter(MouseEvent.MOUSE_EXITED, e -> homeController.mouseEvent(MouseEvent.MOUSE_EXITED,
            exploreVideo, exploreView, exploreHeader, colorAdjust, null));
    // Play the video
    StackPane.setAlignment(header, Pos.TOP_CENTER);
    StackPane.setAlignment(exitButton, Pos.BOTTOM_CENTER);

    videoBox.getChildren().addAll(chaosPane, explorePane);
    videoBox.setAlignment(Pos.CENTER);
    getChildren().addAll(videoBox, header, exitButton);
  }

  public void setBind(Pane pane) {
    explorePane.prefWidthProperty().bind(pane.widthProperty());
    chaosPane.prefWidthProperty().bind(pane.widthProperty());
    exploreView.fitHeightProperty().bind(pane.heightProperty());
    chaosView.fitHeightProperty().bind(pane.heightProperty());
  }
}
