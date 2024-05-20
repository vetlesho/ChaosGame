package org.example.chaosgame.view;

import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import org.example.chaosgame.controller.HomeController;
import org.example.chaosgame.controller.PageController;
import org.example.chaosgame.view.components.ExitButton;
import org.example.chaosgame.view.components.GameHeader;

/**
 * Class for the home page, extends StackPane.
 * The home page is the first page the user sees when starting the application.
 * The home page contains two videos, one for the ChaosGame and one for the ExploreGame.
 * The user can click on the videos to go to the corresponding game.
 */
public class HomePage extends StackPane {
  private final MediaPlayer exploreVideo;
  private final MediaPlayer chaosVideo;
  private final MediaView exploreView;
  private final MediaView chaosView;
  private final StackPane explorePane;
  private final StackPane chaosPane;

  /**
   * Constructor for the HomePage.
   * Initializes the videos, media views and panes for the videos.
   * The user can click on the videos to go to the corresponding game.
   *
   * @param pageController the page controller
   * @param homeController the home controller
   */
  public HomePage(PageController pageController, HomeController homeController) {
    HBox videoBox = new HBox();
    videoBox.prefWidthProperty().bind(this.prefWidthProperty());
    videoBox.prefHeightProperty().bind(this.prefHeightProperty());
    Button exitButton = new ExitButton();

    exitButton.setOnAction(e -> pageController.exitGame());
    ColorAdjust colorAdjust = new ColorAdjust();
    colorAdjust.setBrightness(-0.8);

    String chaosPath = Objects.requireNonNull(getClass()
            .getResource("/media/ChaosVideoFinal.mp4")).toString();
    chaosVideo = new MediaPlayer(new Media(chaosPath));

    String explorePath = Objects.requireNonNull(getClass()
            .getResource("/media/ExploreVideoFinal.mp4")).toString();
    exploreVideo = new MediaPlayer(new Media(explorePath));

    chaosView = new MediaView(chaosVideo);
    chaosView.setEffect(null);

    exploreView = new MediaView(exploreVideo);
    exploreView.setEffect(colorAdjust);


    chaosPane = new StackPane();
    chaosPane.setStyle("-fx-background-color: transparent;");
    Text chaosHeader = new GameHeader("Chaos Game");
    chaosPane.getChildren().addAll(chaosView, chaosHeader);
    chaosPane.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> pageController.goToPage("chaos"));
    chaosPane.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> homeController.mouseEvent(
            MouseEvent.MOUSE_ENTERED, chaosVideo, chaosView, chaosHeader, null, null));
    chaosPane.addEventFilter(MouseEvent.MOUSE_EXITED, e -> homeController.mouseEvent(
            MouseEvent.MOUSE_EXITED, chaosVideo, chaosView, chaosHeader, null, colorAdjust));

    explorePane = new StackPane();
    Text exploreHeader = new GameHeader("Explore Game");
    explorePane.getChildren().addAll(exploreView, exploreHeader);
    explorePane.setStyle("-fx-background-color: black;");

    explorePane.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> pageController.goToPage("explore"));
    explorePane.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> homeController.mouseEvent(
            MouseEvent.MOUSE_ENTERED, exploreVideo, exploreView, exploreHeader, null, colorAdjust));
    explorePane.addEventFilter(MouseEvent.MOUSE_EXITED, e -> homeController.mouseEvent(
            MouseEvent.MOUSE_EXITED, exploreVideo, exploreView, exploreHeader, colorAdjust, null));

    Text header = new GameHeader("Welcome to ChaosGame");
    StackPane.setAlignment(header, Pos.TOP_CENTER);
    StackPane.setAlignment(exitButton, Pos.BOTTOM_CENTER);

    videoBox.getChildren().addAll(chaosPane, explorePane);
    videoBox.setAlignment(Pos.CENTER);
    getChildren().addAll(videoBox, header, exitButton);
  }

  /**
   * Binds the width and height of the panes to the width and height of the main pane.
   *
   * @param pane the main pane
   */
  public void setBind(Pane pane) {
    explorePane.prefWidthProperty().bind(pane.widthProperty());
    chaosPane.prefWidthProperty().bind(pane.widthProperty());
    exploreView.fitHeightProperty().bind(pane.heightProperty());
    chaosView.fitHeightProperty().bind(pane.heightProperty());
  }
}
