package org.example.chaosgame.chaos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      String typeOfTransformation = skipComments(reader.readLine());
      System.out.println("Parsing type of transformation: " + typeOfTransformation);

      minCoords = parseVector(reader.readLine().trim());
      maxCoords = parseVector(reader.readLine().trim());

      transforms.clear();

      String line;
      while ((line = reader.readLine()) != null) {
        transforms.add(selectTransformation(typeOfTransformation, line));
      }
    }
    return new ChaosGameDescription(minCoords, maxCoords, transforms);
  }


  /**
   * Writes a chaos game description to a file.
   *
   * <p>The text files will have the following format:
   *
   * <p>First line: type of transformation (Affine2D or Julia)
   *
   * <p>Second line: minimum coordinates of the canvas (x, y)
   *
   * <p>Third line: maximum coordinates of the canvas (x, y)
   *
   * <p>Fourth line and onwards: the transformations (Affine) or complex number (Julia)
   *
   * @param description the chaos game description
   * @param path the path to the file
   * @throws IOException if the file cannot be written
   * @throws IllegalArgumentException if the type of transformation is unknown
   */
  public void writeToFile(ChaosGameDescription description, String path)
          throws IOException, IllegalArgumentException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
      String type = description.getTransforms().getFirst().getClass().getSimpleName();
      if (type.equals("AffineTransform2D")) {
        writer.write("Affine2D");
      } else if (type.equals("JuliaTransform")) {
        writer.write("Julia");
      } else {
        throw new IllegalArgumentException("Unknown type of transformation: " + type);
      }
      writer.write("    # Type of transformation");
      writer.newLine();

      writer.write(description.getMinCoords().getX()
              + ", " + description.getMinCoords().getY()
              + "    # Min-coordinate");
      writer.newLine();

      writer.write(description.getMaxCoords().getX()
              + ", " + description.getMaxCoords().getY()
              + "    # Max-coordinate");
      writer.newLine();

      int count = 0;
      for (Transform2D transform : description.getTransforms()) {
        if (transform instanceof AffineTransform2D affine) {
          count++;
          Matrix2x2 matrix = affine.getMatrix();
          Vector2D vector = affine.getVector();
          writer.write(matrix.getA() + ", " + matrix.getB() + ", "
                  + matrix.getC() + ", " + matrix.getD() + ", "
                  + vector.getX() + ", " + vector.getY()
                  + "    # " + count + " transformation");
        } else if (transform instanceof JuliaTransform julia) {
          writer.write(julia.getComplex().getX() + ", " + julia.getComplex().getY()
                  + "    # Real and imaginary part of the complex number");
        } else {
          throw new IllegalArgumentException("Unknown type of transformation: "
                  + transform.getClass().getSimpleName());
        }
        writer.newLine();
      }
    } catch (IOException e) {
      throw new IOException("Could not write to file: " + path, e);
    }
  }

  /**
   * Selects the transformation to parse based on the type of transformation.
   *
   * @param typeOfTransformation the type of transformation
   * @param line a line of text
   * @return the transformation
   * @throws IllegalArgumentException if the type of transformation is unknown
   */
  private Transform2D selectTransformation(String typeOfTransformation, String line)
          throws IllegalArgumentException {
    return switch (typeOfTransformation) {
      case "Affine2D" -> parseAffine(line);
      case "Julia" -> parseJulia(line);
      default -> throw new IllegalArgumentException("Unknown type of transformation: "
              + typeOfTransformation);
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
    String[] parts = numbers.split(",");
    double r = Double.parseDouble(parts[0].trim());
    double i = Double.parseDouble(parts[1].trim());
    return new JuliaTransform(new Complex(r, i), 1);
  }
}
