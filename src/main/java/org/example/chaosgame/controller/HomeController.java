package org.example.chaosgame.controller;

import javafx.event.EventType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.chaosgame.view.HomePage;

public class HomeController {
    private final HomePage homePage;

    public HomeController(PageController pageController) {
        this.homePage = new HomePage(pageController, this);
    }

    public HomePage getHomePage() {
        return homePage;
    }


    public void mouseEvent(EventType<MouseEvent> mouseEvent, MediaPlayer video, MediaView view, Text header, ColorAdjust colorAdjust, ColorAdjust headerAdjust) {
        if (mouseEvent == MouseEvent.MOUSE_ENTERED) {
            video.play();
            view.setEffect(colorAdjust);
            header.setEffect(headerAdjust);
            header.setOpacity(0.2);
        } else {
            video.seek(Duration.seconds(0));
            video.pause();
            view.setEffect(colorAdjust);
            header.setEffect(headerAdjust);
            header.setOpacity(1.0);
        }
    }
}
