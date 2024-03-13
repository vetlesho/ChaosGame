package org.example.chaosgame.chaos;

import org.example.chaosgame.linalg.Complex;
import org.example.chaosgame.linalg.Matrix2x2;
import org.example.chaosgame.linalg.Vector2D;
import org.example.chaosgame.transformations.AffineTransform2D;
import org.example.chaosgame.transformations.JuliaTransform;
import org.example.chaosgame.transformations.Transform2D;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


public class ChaosGameFileHandler {

  public ChaosGameDescription readFromFile(String path) throws IOException {
    Scanner scanner = new Scanner(new File(path));

    Vector2D minCoords = null;
    Vector2D maxCoords = null;
    List<Transform2D> transforms = new ArrayList<>();

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line.isEmpty() || line.startsWith("#")) {
        continue; //Skip empty lines and comments
      }

      if (minCoords == null) {
        minCoords = parseVector(line);
      } else if (maxCoords == null) {
        maxCoords = parseVector(line);
      } else {

        //Change this if it is a Julia or Affine transformation
        transforms.add(parseAffine(line));
      }
    }

    scanner.close();
    return new ChaosGameDescription(minCoords, maxCoords, transforms);
  }

  public void writeToFile(ChaosGameDescription description, String path){

  }

  private Vector2D parseVector(String line) {
    System.out.println("Parsing vector: " + line);
    String[] parts = line.split(",");
    double x = Double.parseDouble(parts[0].trim());
    double y = Double.parseDouble(parts[1].trim());
    return new Vector2D(x, y);
  }

  private Transform2D parseAffine(String line) {
    System.out.println("Parsing transform: " + line);
    String[] parts = line.split(",");
    double a = Double.parseDouble(parts[0].trim());
    double b = Double.parseDouble(parts[1].trim());
    double c = Double.parseDouble(parts[2].trim());
    double d = Double.parseDouble(parts[3].trim());
    double x = Double.parseDouble(parts[4].trim());
    double y = Double.parseDouble(parts[5].trim());
    return new AffineTransform2D(new Matrix2x2(a, b, c, d), new Vector2D(x, y));
  }

  private Transform2D parseJulia(String line) {
    System.out.println("Parsing transform: " + line);
    String[] parts = line.split(",");
    double a = Double.parseDouble(parts[0].trim());
    double b = Double.parseDouble(parts[1].trim());
    return new JuliaTransform(new Complex(a, b), 1);
  }
}
