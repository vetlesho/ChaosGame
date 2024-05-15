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
    Text header = new GameHeader("Welcome to ChaosGame");
//    Button chaosGameButton = new ChooseGameButton("Chaos Game");
//    Button exploreGameButton = new ChooseGameButton("Explore Game");
    Button exitButton = new ChooseGameButton("Exit");

//    chaosGameButton.setOnAction(e -> pageController.goToPage("chaos"));
//    exploreGameButton.setOnAction(e -> pageController.goToPage("explore"));
    exitButton.setOnAction(e -> pageController.exitGame());


    ColorAdjust colorAdjust = new ColorAdjust();
    colorAdjust.setBrightness(-0.5);

    chaosVideo = new MediaPlayer(new Media(explorePath));
    chaosVideo.setOnEndOfMedia(() -> chaosVideo.seek(Duration.ZERO));

    exploreVideo = new MediaPlayer(new Media(explorePath));
    exploreVideo.setOnStopped(() -> exploreVideo.seek(Duration.ZERO));

    MediaView chaosView = new MediaView(chaosVideo);
    chaosView.setEffect(colorAdjust);
    chaosView.setViewport(new Rectangle2D(1150, 700, 600, 800));




    MediaView exploreView = new MediaView(exploreVideo);
    exploreView.setEffect(colorAdjust);
    exploreView.setViewport(new Rectangle2D(1150, 700, 600, 800));
    

    

    StackPane chaosPane = new StackPane();
    Text chaosHeader = new GameHeader("Chaos Game");
    chaosPane.getChildren().addAll(chaosView, chaosHeader);
    chaosPane.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> pageController.goToPage("chaos"));
    chaosPane.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
      chaosVideo.play();
      chaosView.setEffect(null);
    });
    chaosPane.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
      chaosVideo.pause();
      chaosView.setEffect(colorAdjust);
    });

    StackPane explorePane = new StackPane();
    Text exploreHeader = new GameHeader("Explore Game");
    explorePane.getChildren().addAll(exploreView, exploreHeader);

    explorePane.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> pageController.goToPage("explore"));
    explorePane.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
      exploreVideo.play();
      exploreView.setEffect(null);
    });
    explorePane.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
      exploreVideo.pause();
      exploreView.setEffect(colorAdjust);
    });
    // Play the video
    StackPane.setAlignment(header, Pos.TOP_CENTER);
    StackPane.setAlignment(exitButton, Pos.BOTTOM_CENTER);
    HBox videoBox = new HBox();
    videoBox.getChildren().addAll(chaosPane, explorePane);
    videoBox.setAlignment(Pos.CENTER);
    getChildren().addAll(videoBox, header, exitButton);
  }
}
