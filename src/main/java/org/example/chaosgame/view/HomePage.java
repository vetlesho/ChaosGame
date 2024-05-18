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
import org.example.chaosgame.controller.PageController;
import org.example.chaosgame.model.chaos.ChaosGameDescription;
import org.example.chaosgame.model.chaos.ChaosGameFileHandler;
import org.example.chaosgame.view.components.ChooseGameButton;
import org.example.chaosgame.view.components.ExitButton;
import org.example.chaosgame.view.components.GameHeader;
import javafx.scene.media.Media;

import java.io.File;
import java.io.FileReader;
import java.util.Stack;


public class HomePage extends StackPane {

  private final String explorePath = getClass().getResource("/ExploreVideoFinal.mp4").toString();
  private String chaosPath = getClass().getResource("/ChaosVideoFinal.mp4").toString();
  private MediaPlayer exploreVideo;
  private MediaPlayer chaosVideo;

  private MediaView exploreView;
  private MediaView chaosView;
  private StackPane explorePane;
  private StackPane chaosPane;
  public HomePage(PageController pageController) {
    HBox videoBox = new HBox();
    videoBox.prefWidthProperty().bind(this.prefWidthProperty());
    videoBox.prefHeightProperty().bind(this.prefHeightProperty());
    /*videoBox.setStyle("-fx-background-color: black;");*/
    Text header = new GameHeader("Welcome to ChaosGame");
    Button exitButton = new ExitButton();

    exitButton.setOnAction(e -> pageController.exitGame());
    ColorAdjust colorAdjust = new ColorAdjust();
    colorAdjust.setBrightness(-0.8);

    chaosVideo = new MediaPlayer(new Media(chaosPath));

    exploreVideo = new MediaPlayer(new Media(explorePath));

    chaosView = new MediaView(chaosVideo);
    chaosView.setEffect(colorAdjust);

    exploreView = new MediaView(exploreVideo);
    exploreView.setEffect(colorAdjust);
//    exploreView.setViewport(new Rectangle2D(100, 100, 100, 100));


    chaosPane = new StackPane();
    chaosPane.setStyle("-fx-background-color: transparent;");
    Text chaosHeader = new GameHeader("Chaos Game");
    chaosPane.getChildren().addAll(chaosView, chaosHeader);
    chaosPane.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> pageController.goToPage("chaos"));
    chaosPane.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
      chaosVideo.play();
      chaosView.setEffect(null);
      chaosHeader.setEffect(null);
      chaosHeader.setOpacity(0.5);
    });
    chaosPane.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
      chaosVideo.seek(Duration.seconds(0));
      chaosVideo.pause();
//      chaosView.setEffect(colorAdjust);
      chaosHeader.setEffect(colorAdjust);
      chaosHeader.setOpacity(1);
    });

    explorePane = new StackPane();
    Text exploreHeader = new GameHeader("Explore Game");
    explorePane.getChildren().addAll(exploreView, exploreHeader);
    explorePane.setStyle("-fx-background-color: black;");

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

  public void setBind(Pane pane) {
    explorePane.prefWidthProperty().bind(pane.widthProperty());
    chaosPane.prefWidthProperty().bind(pane.widthProperty());
    exploreView.fitHeightProperty().bind(pane.heightProperty());
    chaosView.fitHeightProperty().bind(pane.heightProperty());
  }
}
