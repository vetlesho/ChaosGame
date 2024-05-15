package org.example.chaosgame.view.components;

import javafx.scene.control.Slider;
import org.example.chaosgame.controller.ExploreGameController;

public class JuliaSlider extends Slider {
    public JuliaSlider() {
        super(-1, 1, 0.5);
        this.setShowTickLabels(true);
        this.setShowTickMarks(true);
        this.setMajorTickUnit(0.1);
        this.setMinorTickCount(5);
        this.setBlockIncrement(0.01);
    }
}
