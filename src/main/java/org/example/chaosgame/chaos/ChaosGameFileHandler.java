package org.example.chaosgame.chaos;

import org.example.chaosgame.linalg.Complex;
import org.example.chaosgame.linalg.Matrix2x2;
import org.example.chaosgame.linalg.Vector2D;
import org.example.chaosgame.transformations.AffineTransform2D;
import org.example.chaosgame.transformations.JuliaTransform;
import org.example.chaosgame.transformations.Transform2D;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


public class ChaosGameFileHandler {
  private Vector2D minCoords;
  private Vector2D maxCoords;
  private final List<Transform2D> transforms = new ArrayList<>();

  public ChaosGameDescription readFromFile(String path) throws IOException {
    Vector2D minCoords;
    Vector2D maxCoords;
    try (Scanner scanner = new Scanner(new File(path))) {
      scanner.useLocale(Locale.ENGLISH);
      String typeOfTransformation = scanner.nextLine();
      minCoords = parseVector(scanner.nextLine().trim());
      maxCoords = parseVector(scanner.nextLine().trim());

      //delimeter for kmt
      transforms.clear();
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (typeOfTransformation.equals("Affine2D")) {
          transforms.add(parseAffine(line));
        } else if (typeOfTransformation.equals("Julia")) {
          transforms.add(parseJulia(line));
        } else {
          throw new IOException("Invalid transform type");
        }
      }
    }
    return new ChaosGameDescription(minCoords, maxCoords, transforms);
  }

  public void writeToFile(ChaosGameDescription description, String path) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(path));

    // Skriv type transformasjon
    if (description.getTransforms().getFirst() instanceof AffineTransform2D) {
      writer.write("Affine2D\n");
    } else if (description.getTransforms().getFirst() instanceof JuliaTransform) {
      writer.write("Julia\n");
    } else {
      throw new IOException("Invalid transform type");
    }
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
    double r = Double.parseDouble(parts[0].trim());
    double i = Double.parseDouble(parts[1].trim());
    return new JuliaTransform(new Complex(r, i), 1);
  }
}
