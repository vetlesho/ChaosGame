package org.example.chaosgame;

import org.example.chaosgame.chaos.ChaosCanvas;
import org.example.chaosgame.chaos.ChaosGame;
import org.example.chaosgame.chaos.ChaosGameDescription;
import org.example.chaosgame.linalg.Complex;
import org.example.chaosgame.linalg.Vector2D;
import org.example.chaosgame.transformations.AffineTransform2D;
import org.example.chaosgame.transformations.JuliaTransform;
import org.example.chaosgame.transformations.Transform2D;
import org.example.chaosgame.linalg.Matrix2x2;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    JuliaTransform juliaTransform = new JuliaTransform(new Complex(-0.84, -0.2), 1);
    AffineTransform2D transform = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5),
            new Vector2D(0, 0));
    AffineTransform2D transform2 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5),
            new Vector2D(0.5, 0));
    AffineTransform2D transform3 = new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5),
            new Vector2D(0.25, 0.5));
    List<Transform2D> transforms = List.of(transform, transform2, transform3);
    List<Transform2D> transforms2 = List.of(juliaTransform);

    ChaosGameDescription description = new ChaosGameDescription(
            new Vector2D(0, 0),
            new Vector2D(1, 1),
            transforms2
    );

    ChaosGame game = new ChaosGame(description, 100, 49);

    ChaosCanvas chaosCanvas = game.getCanvas();

    game.runSteps(10000000);
    int[][] canvasArray = chaosCanvas.getCanvasArray();
    for (int[] ints : canvasArray) {
      for (int anInt : ints) {
        if (anInt == 1) {
          System.out.print("X");
        } else {
          System.out.print(" ");
        }

      }
      System.out.println();
    }
  }

}