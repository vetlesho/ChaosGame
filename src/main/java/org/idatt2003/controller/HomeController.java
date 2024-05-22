package org.idatt2003.controller;

import javafx.event.EventType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.idatt2003.view.HomePage;

/**
 * Controller for the home page.
 * Handles mouse events for the home page.
 */
public class HomeController {
  private final HomePage homePage;

  /**
   * Constructor for the HomeController.
   *
   * @param pageController the page controller
   */
  public HomeController(PageController pageController) {
    this.homePage = new HomePage(pageController, this);
  }

  public HomePage getHomePage() {
    return homePage;
  }

  /**
   * Handles MOUSE_ENTERED or MOUSE_EXITED events
   * for the home page. Either plays or pauses video and adds
   * opacity to the text and color adjusts the MediaView.
   *
   * @param mouseEvent   the mouse event
   * @param video        the video
   * @param view         the media view
   * @param header       the header text
   * @param colorAdjust  the color adjust effect
   * @param headerAdjust the header adjust effect
   */
  public void mouseEvent(EventType<MouseEvent> mouseEvent, MediaPlayer video, MediaView view,
                         Text header, ColorAdjust colorAdjust, ColorAdjust headerAdjust) {
    if (mouseEvent == MouseEvent.MOUSE_ENTERED) {
      video.play();
      view.setEffect(colorAdjust);
      header.setEffect(headerAdjust);
      header.setOpacity(0.2);
    } else if (mouseEvent == MouseEvent.MOUSE_EXITED) {
      video.seek(Duration.seconds(0));
      video.pause();
      view.setEffect(colorAdjust);
      header.setEffect(headerAdjust);
      header.setOpacity(1.0);
    }
  }
}
