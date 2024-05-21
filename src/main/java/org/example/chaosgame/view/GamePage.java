package org.example.chaosgame.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;

/**
 * Abstract class for the GamePage, extends BorderPane.
 * The GamePage is used for displaying the game.
 */
public abstract class GamePage extends BorderPane {
  private static final int CANVAS_WIDTH = 1250;
  private static final int CANVAS_HEIGHT = 805;
  protected final GraphicsContext gc;


  /**
   * Constructor for the GamePage.
   * Initializes the canvas and makes a new GraphicsContext
   * available for subclasses.
   */
  public GamePage() {
    Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    this.gc = canvas.getGraphicsContext2D();
  }
}
