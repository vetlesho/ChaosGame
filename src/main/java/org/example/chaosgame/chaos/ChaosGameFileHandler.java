package org.example.chaosgame.chaos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import org.example.chaosgame.linalg.Complex;
import org.example.chaosgame.linalg.Matrix2x2;
import org.example.chaosgame.linalg.Vector2D;
import org.example.chaosgame.transformations.AffineTransform2D;
import org.example.chaosgame.transformations.JuliaTransform;
import org.example.chaosgame.transformations.Transform2D;

/**
 * Class for reading and writing chaos game descriptions from and to files.
 */
public class ChaosGameFileHandler {
  private final List<Transform2D> transforms = new ArrayList<>();

  /**
   * Reads a chaos game description from a file.
   *
   * <p>The text files should have the following format:
   *
   * <p>First line: type of transformation (Affine2D or Julia)
   *
   * <p>Second line: minimum coordinates of the canvas (x, y)
   *
   * <p>Third line: maximum coordinates of the canvas (x, y)
   *
   * <p>Fourth line and onwards: the transformations
   *
   * @param path the path to the file
   * @return the chaos game description
   * @throws IOException if the file cannot be read
   */
  public ChaosGameDescription readFromFile(String path) throws IOException {
    Vector2D minCoords;
    Vector2D maxCoords;
    try (Scanner scanner = new Scanner(new File(path))) {
      scanner.useLocale(Locale.ENGLISH);

      String typeOfTransformation = skipComments(scanner.nextLine());
      System.out.println("Parsing type of transformation: " + typeOfTransformation);

      minCoords = parseVector(scanner.nextLine().trim());
      maxCoords = parseVector(scanner.nextLine().trim());

      transforms.clear();

      while (scanner.hasNextLine()) {
        transforms.add(selectTransformation(typeOfTransformation, scanner));
      }

    }
    return new ChaosGameDescription(minCoords, maxCoords, transforms);
  }

  public void writeToFile(ChaosGameDescription description, String path) throws IOException {

  }

  /**
   * Selects the correct transformation based on the type of transformation.
   *
   * @param typeOfTransformation a string with the name of the transformation
   * @param scanner the scanner to read the transformation from
   * @return the transformation
   */
  private Transform2D selectTransformation(String typeOfTransformation, Scanner scanner) {
    return switch (typeOfTransformation) {
      case "Affine2D" -> parseAffine(scanner.nextLine());
      case "Julia" -> parseJulia(scanner.nextLine());
      default -> throw new IllegalArgumentException(
              "Unknown type of transformation: " + typeOfTransformation);
    };
  }

  /**
   * Skips everything after the first # in a line.
   *
   * @param line a line of text
   * @return the first part of the line
   */
  private String skipComments(String line) {
    String[] parts = line.split("#");
    return parts[0].trim();
  }

  /**
   * Parses a vector from a string.
   *
   * @param line a line of text
   * @return the vector
   */
  private Vector2D parseVector(String line) {
    String numbers = skipComments(line);
    System.out.println("Parsing vector: " + numbers);
    String[] vectorParts = numbers.split(",");
    double x = Double.parseDouble(vectorParts[0].trim());
    double y = Double.parseDouble(vectorParts[1].trim());
    return new Vector2D(x, y);
  }

  /**
   * Parses an affine transformation from a string.
   *
   * @param line a line of text
   * @return the transformation
   */
  private Transform2D parseAffine(String line) {
    String numbers = skipComments(line);
    System.out.println("Parsing transform: " + numbers);
    String[] transformParts = numbers.split(",");
    double a = Double.parseDouble(transformParts[0].trim());
    double b = Double.parseDouble(transformParts[1].trim());
    double c = Double.parseDouble(transformParts[2].trim());
    double d = Double.parseDouble(transformParts[3].trim());
    double x = Double.parseDouble(transformParts[4].trim());
    double y = Double.parseDouble(transformParts[5].trim());
    return new AffineTransform2D(new Matrix2x2(a, b, c, d), new Vector2D(x, y));
  }

  /**
   * Parses a Julia transformation from a string.
   *
   * @param line a line of text
   * @return the transformation
   */
  private Transform2D parseJulia(String line) {
    String numbers = skipComments(line);
    System.out.println("Parsing transform: " + numbers);
    String[] transformParts = numbers.split(",");
    double r = Double.parseDouble(transformParts[0].trim());
    double i = Double.parseDouble(transformParts[1].trim());
    return new JuliaTransform(new Complex(r, i), 1);
  }
}
