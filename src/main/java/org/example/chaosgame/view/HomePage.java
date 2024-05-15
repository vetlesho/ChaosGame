package org.example.chaosgame.view;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.example.chaosgame.controller.ChaosGameController;
import org.example.chaosgame.controller.PageController;
import org.example.chaosgame.model.chaos.ChaosGameDescription;
import org.example.chaosgame.model.chaos.ChaosGameFileHandler;
import org.example.chaosgame.view.components.ChooseGameButton;
import org.example.chaosgame.view.components.ChooseGameButton;
import org.example.chaosgame.view.components.GameButton;
import org.example.chaosgame.view.components.GameHeader;
import javafx.scene.media.Media;

import java.io.File;
import java.io.FileReader;
import java.util.Stack;


public class HomePage extends StackPane {

  private final String explorePath = getClass().getResource("/ExploreVideo.mp4").toString();;
  private String chaosPath;
  private MediaPlayer exploreVideo;
  private MediaPlayer chaosVideo;
  public HomePage(PageController pageController) {
    HBox videoBox = new HBox();
    videoBox.prefWidthProperty().bind(this.prefWidthProperty());
    videoBox.prefHeightProperty().bind(this.prefHeightProperty());
    /*videoBox.setStyle("-fx-background-color: black;");*/
    Text header = new GameHeader("Welcome to ChaosGame");
    Button exitButton = new ChooseGameButton("Exit");

    exitButton.setOnAction(e -> pageController.exitGame());
    ColorAdjust colorAdjust = new ColorAdjust();
    colorAdjust.setBrightness(-0.8);

    chaosVideo = new MediaPlayer(new Media(explorePath));
    chaosVideo.setAutoPlay(true);

    exploreVideo = new MediaPlayer(new Media(explorePath));
    chaosVideo.setAutoPlay(true);

    MediaView chaosView = new MediaView(chaosVideo);
    chaosView.setEffect(colorAdjust);
    System.out.printf("Width: %f, Height: %f\n", this.getWidth() / 2, this.getHeight());
    chaosView.setViewport(new Rectangle2D(1150, 700, 600, 800));//this.getWidth() / 2, this.getHeight()));

    MediaView exploreView = new MediaView(exploreVideo);
    exploreView.setEffect(colorAdjust);
    exploreView.setViewport(new Rectangle2D(1150, 700, 600, 800));//this.getWidth() / 2, this.getHeight()));


    StackPane chaosPane = new StackPane();
    Text chaosHeader = new GameHeader("Chaos Game");
    chaosPane.getChildren().addAll(chaosView, chaosHeader);
    chaosPane.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> pageController.goToPage("chaos"));
    chaosPane.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
      chaosVideo.play();
      chaosView.setEffect(null);
      chaosHeader.setEffect(colorAdjust);
      chaosHeader.setOpacity(0.5);
    });
    chaosPane.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
      chaosVideo.seek(Duration.seconds(0));
      chaosVideo.pause();
      chaosView.setEffect(colorAdjust);
      chaosHeader.setEffect(null);
      chaosHeader.setOpacity(1);
    });

    StackPane explorePane = new StackPane();
    Text exploreHeader = new GameHeader("Explore Game");
    explorePane.getChildren().addAll(exploreView, exploreHeader);

    explorePane.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> pageController.goToPage("explore"));
    explorePane.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
      exploreVideo.play();
      exploreView.setEffect(null);
      exploreHeader.setEffect(colorAdjust);
      exploreHeader.setOpacity(0.5);
    });
    explorePane.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
      exploreVideo.seek(Duration.seconds(0));
      exploreVideo.pause();
      exploreView.setEffect(colorAdjust);
      exploreHeader.setEffect(null);
      exploreHeader.setOpacity(1);
    });
    // Play the video
    StackPane.setAlignment(header, Pos.TOP_CENTER);
    StackPane.setAlignment(exitButton, Pos.BOTTOM_CENTER);

    videoBox.getChildren().addAll(chaosPane, explorePane);
    videoBox.setAlignment(Pos.CENTER);
    getChildren().addAll(videoBox, header, exitButton);
  }
}
