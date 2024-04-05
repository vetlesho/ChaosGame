package org.example.chaosgame.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.chaosgame.model.chaos.ChaosCanvas;

public class ChaosGameView {
  private final Canvas canvas;
  private final GraphicsContext gc;

  public ChaosGameView(int width, int height) {
    this.canvas = new Canvas(width, height);
    this.gc = canvas.getGraphicsContext2D();
  }

  public void updateView(ChaosCanvas chaosCanvas) {
    int[][] canvasArray = chaosCanvas.getCanvasArray();
    double cellWidth = canvas.getWidth() / chaosCanvas.getWidth();
    double cellHeight = canvas.getHeight() / chaosCanvas.getHeight();
    for (int i = 0; i < chaosCanvas.getHeight(); i++) {
      for (int j = 0; j < chaosCanvas.getWidth(); j++) {
        int color = Math.min(canvasArray[i][j] * 3, 255);
        if (color == 0) {
          gc.setFill(Color.BLACK);
        } else {
          gc.setFill(Color.rgb(color, 255, 255));
        }
        gc.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
      }
    }
  }

  public Canvas getCanvas() {
    return canvas;
  }
}