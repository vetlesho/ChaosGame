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
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


public class ChaosGameFileHandler {
  private final List<Transform2D> transforms = new ArrayList<>();

  public ChaosGameDescription readFromFile(String path) throws IOException {
    Scanner scanner = new Scanner(new File(path));

    //check if the file is empty
    if (!scanner.hasNextLine()) {
      throw new IOException("File is empty");
    }
    //read the name of the file
    Vector2D minCoords;
    Vector2D maxCoords;
    String name = scanner.nextLine();
    if (name.equals("#Affine2D")) {
      minCoords = parseVector(scanner.nextLine());
      maxCoords = parseVector(scanner.nextLine());
      while (scanner.hasNextLine()) {
        transforms.add(parseAffine(scanner.nextLine()));
      }

    } else if (name.equals("#Julia")) {
      minCoords = parseVector(scanner.nextLine());
      maxCoords = parseVector(scanner.nextLine());
      transforms.add(parseJulia(scanner.nextLine()));
    } else {
      throw new IOException("Invalid file format");
    }
    return new ChaosGameDescription(minCoords, maxCoords, transforms);
  }

  public void writeToFile(ChaosGameDescription description, String path) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(path));

    // Skriv type transformasjon
    if (description.getTransforms().getFirst() instanceof AffineTransform2D) {
      writer.write("#Affine2D\n");
    } else if (description.getTransforms().getFirst() instanceof JuliaTransform) {
      writer.write("#Julia\n");
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
