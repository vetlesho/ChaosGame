package org.example.chaosgame.model.chaos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.example.chaosgame.controller.ChaosGameFileHandler;
import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.linalg.Matrix2x2;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.AffineTransform2D;
import org.example.chaosgame.model.transformations.JuliaTransform;
import org.example.chaosgame.model.transformations.Transform2D;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;





class ChaosGameFileHandlerTest {
  private static ChaosGameFileHandler fileHandler;
  private static String validAffineContent;
  private static String validJuliaContent;
  private static String invalidNumbersContent;
  private static String invalidTypeContent;

  @BeforeAll
  static void setUp() {
    fileHandler = new ChaosGameFileHandler();
    validAffineContent = """
            Affine2D # Type of Transformation
            -2.65, 0.0 # Min-coordinate
            2.65, 10.0 # Max-coordinate
            0.0, 0.0, 0.0, 0.16, 0.0, 0.0 # 1 transformation
            0.85, 0.04, -0.04, 0.85, 0.0, 1.6 # 2 transformation
            0.2, -0.26, 0.23, 0.22, 0.0, 1.6 # 3 transformation
            -0.15, 0.28, 0.26, 0.24, 0.0, 0.44 # 4 transformation
            """;

    validJuliaContent = """
            Julia # Type of Transformation
            -1.6, -1.0    # Min-coordinate
            1.6, 1.0    # Max-coordinate
            -0.70176, -0.3842 # Real and imaginary part of the complex number
            """;

    invalidNumbersContent = """
            Affine2D # Type of Transformation
            INVALID, 0.0 # Min-coordinate
            2.65, 10.0 # Max-coordinate
            0.0, 0.0, 0.0, WRONG, 0.0, 0.0 # 1 transformation
            0.85, 0.04, -0.04, 0.85, 0.0, 1.6 # 2 transformation
            0.2, WRONG, 0.23, 0.22, 0.0, 1.6 # 3 transformation
            -0.15, 0.28, 0.26, 0.24ERROROOROR, 0.44 # 4 transformation
            """;

    invalidTypeContent = """
            Unknown # Type of Transformation
            -2.65, 0.0 # Min-coordinate
            2.65, 10.0 # Max-coordinate
            0.0, 0.0, 0.0, 0.16, 0.0, 0.0 # 1 transformation
            0.85, 0.04, -0.04, 0.85, 0.0, 1.6 # 2 transformation
            0.2, -0.26, 0.23, 0.22, 0.0, 1.6 # 3 transformation
            -0.15, 0.28, 0.26, 0.24, 0.0, 0.44 # 4 transformation
            """;

  }

  private Path createTempFileWithContent(String content) throws IOException {
    Path tempFile = Files.createTempFile("chaosGame", ".txt");
    try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
      writer.write(content);
    }
    return tempFile;
  }

  @Nested
  class ReadFromFileTests {
    @Test
    void testReadValidAffine() throws IOException {
      Path tempFile = createTempFileWithContent(validAffineContent);
      ChaosGameDescription description = fileHandler.readFromFile(tempFile.toString());

      Vector2D expectedMin = new Vector2D(-2.65, 0.0);
      Vector2D expectedMax = new Vector2D(2.65, 10.0);
      List<Transform2D> expectedTransforms = List.of(
              new AffineTransform2D(new Matrix2x2(0.0, 0.0, 0.0, 0.16), new Vector2D(0.0, 0.0)),
              new AffineTransform2D(new Matrix2x2(0.85, 0.04, -0.04, 0.85), new Vector2D(0.0, 1.6)),
              new AffineTransform2D(new Matrix2x2(0.2, -0.26, 0.23, 0.22), new Vector2D(0.0, 1.6)),
              new AffineTransform2D(new Matrix2x2(-0.15, 0.28, 0.26, 0.24), new Vector2D(0.0, 0.44))
      );

      assertEquals(expectedMin, description.getMinCoords());
      assertEquals(expectedMax, description.getMaxCoords());
      assertEquals(expectedTransforms, description.getTransforms());

      Files.delete(tempFile);
    }

    @Test
    void testReadValidJulia() throws IOException {
      Path tempFile = createTempFileWithContent(validJuliaContent);
      ChaosGameDescription description = fileHandler.readFromFile(tempFile.toString());

      Vector2D expectedMin = new Vector2D(-1.6, -1.0);
      Vector2D expectedMax = new Vector2D(1.6, 1.0);
      List<Transform2D> expectedTransforms = List.of(
              new JuliaTransform(new Complex(-0.70176, -0.3842), 1)
      );

      assertEquals(expectedMin, description.getMinCoords());
      assertEquals(expectedMax, description.getMaxCoords());
      assertEquals(expectedTransforms, description.getTransforms());

      Files.delete(tempFile);
    }

    @Test
    void testReadUnknownType() throws IOException {
      Path tempFile = createTempFileWithContent(invalidTypeContent);
      assertThrows(IllegalArgumentException.class,
              () -> fileHandler.readFromFile(tempFile.toString()),
              "Unknown type of transformation should throw IllegalArgumentException");

      Files.delete(tempFile);
    }

    @Test
    void testReadInvalidVector() throws IOException {
      Path tempFile = createTempFileWithContent(invalidNumbersContent);
      assertThrows(NumberFormatException.class, () -> fileHandler.readFromFile(tempFile.toString()),
              "Invalid vector data should throw NumberFormatException");

      Files.delete(tempFile);
    }
  }

  @Nested
  class WriteToFileTests {
    @Test
    void testWriteValidAffine() throws IOException {
      ChaosGameDescription description = new ChaosGameDescription(
              new Vector2D(-2.65, 0.0),
              new Vector2D(2.65, 10.0),
              List.of(
                      new AffineTransform2D(new Matrix2x2(0.0, 0.0, 0.0, 0.16),
                              new Vector2D(0.0, 0.0)),
                      new AffineTransform2D(new Matrix2x2(0.85, 0.04, -0.04, 0.85),
                              new Vector2D(0.0, 1.6)),
                      new AffineTransform2D(new Matrix2x2(0.2, -0.26, 0.23, 0.22),
                              new Vector2D(0.0, 1.6)),
                      new AffineTransform2D(new Matrix2x2(-0.15, 0.28, 0.26, 0.24),
                              new Vector2D(0.0, 0.44))
              )
      );

      Path tempFile = Files.createTempFile("chaosGame", ".txt");
      fileHandler.writeToFile(description, tempFile.toString());

      String expectedContent =
              """
                      Affine2D    # Type of transformation
                      -2.65, 0.0    # Min-coordinate
                      2.65, 10.0    # Max-coordinate
                      0.0, 0.0, 0.0, 0.16, 0.0, 0.0    # 1 transformation
                      0.85, 0.04, -0.04, 0.85, 0.0, 1.6    # 2 transformation
                      0.2, -0.26, 0.23, 0.22, 0.0, 1.6    # 3 transformation
                      -0.15, 0.28, 0.26, 0.24, 0.0, 0.44    # 4 transformation
                      """;

      StringBuilder actualContent = new StringBuilder();
      try (BufferedReader reader = Files.newBufferedReader(tempFile)) {
        String line;
        while ((line = reader.readLine()) != null) {
          actualContent.append(line).append("\n");
        }
      }

      assertEquals(expectedContent.trim(), actualContent.toString().trim());

      Files.delete(tempFile);
    }

    @Test
    void testWriteValidJulia() throws IOException {
      ChaosGameDescription description = new ChaosGameDescription(
              new Vector2D(-1.6, -1.0),
              new Vector2D(1.6, 1.0),
              List.of(new JuliaTransform(new Complex(-0.70176, -0.3842), 1))
      );

      Path tempFile = Files.createTempFile("chaosGame", ".txt");
      fileHandler.writeToFile(description, tempFile.toString());

      String expectedContent =
              """
                      Julia    # Type of transformation
                      -1.6, -1.0    # Min-coordinate
                      1.6, 1.0    # Max-coordinate
                      -0.70176, -0.3842    # Real and imaginary part of the complex number
                      """;

      StringBuilder actualContent = new StringBuilder();
      try (BufferedReader reader = Files.newBufferedReader(tempFile)) {
        String line;
        while ((line = reader.readLine()) != null) {
          actualContent.append(line).append("\n");
        }
      }

      assertEquals(expectedContent.trim(), actualContent.toString().trim());

      Files.delete(tempFile);
    }
  }

  @Nested
  class SkipCommentsTests {
    @Test
    void testSkipCommentsWithComment() {
      String line = "0.5, 0.0, 0.0, 0.5, 0.0, 0.0 # This is a comment";
      String result = fileHandler.skipComments(line);
      assertEquals("0.5, 0.0, 0.0, 0.5, 0.0, 0.0", result);
    }

    @Test
    void testSkipCommentsWithoutComment() {
      String line = "0.5, 0.0, 0.0, 0.5, 0.0, 0.0";
      String result = fileHandler.skipComments(line);
      assertEquals(line, result);
    }

    @Test
    void testSkipCommentsEmptyLine() {
      String line = "# This is a comment";
      String result = fileHandler.skipComments(line);
      assertEquals("", result);
    }
  }

  @Nested
  class SelectTransformationTests {
    @Test
    void testSelectAffineTransformation() {
      String line = "0.5, 0.0, 0.0, 0.5, 0.0, 0.0";
      Transform2D transform = fileHandler.selectTransformation("Affine2D", line);
      assertInstanceOf(AffineTransform2D.class, transform);
    }

    @Test
    void testSelectJuliaTransformation() {
      String line = "-0.70176, -0.3842";
      Transform2D transform = fileHandler.selectTransformation("Julia", line);
      assertInstanceOf(JuliaTransform.class, transform);
    }

    @Test
    void testSelectUnknownTransformation() {
      String line = "0.5, 0.0, 0.0, 0.5, 0.0, 0.0";
      assertThrows(IllegalArgumentException.class, () ->
                      fileHandler.selectTransformation("Unknown", line),
              "Unknown type of transformation should throw IllegalArgumentException");
    }
  }

  @Nested
  class ParseVectorTests {
    @Test
    void testParseValidVector() {
      String line = "0.0, 1.0";
      Vector2D vector = fileHandler.parseVector(line);
      assertEquals(new Vector2D(0.0, 1.0), vector);
    }

    @Test
    void testParseInvalidVector() {
      String line = "invalid, data";
      assertThrows(NumberFormatException.class, () -> fileHandler.parseVector(line));
    }

    @Test
    void testParseVectorWithComments() {
      String line = "0.0, 1.0 # Comment";
      Vector2D vector = fileHandler.parseVector(line);
      assertEquals(new Vector2D(0.0, 1.0), vector);
    }
  }

  @Nested
  class ParseAffineTests {
    @Test
    void testParseValidAffine() {
      String line = "0.5, 0.0, 0.0, 0.5, 0.0, 0.0";
      AffineTransform2D affine = (AffineTransform2D) fileHandler.parseAffine(line);
      assertEquals(new Matrix2x2(0.5, 0.0, 0.0, 0.5), affine.matrix());
      assertEquals(new Vector2D(0.0, 0.0), affine.vector());
    }

    @Test
    void testParseInvalidAffine() {
      String line = "invalid, data, 0.0, 0.5, 0.0, 0.0, invalid here too";
      assertThrows(NumberFormatException.class, () -> fileHandler.parseAffine(line));
    }

    @Test
    void testParseAffineWithComments() {
      String line = "0.5, 0.0, 0.0, 0.5, 0.0, 0.0 # Comment";
      AffineTransform2D affine = (AffineTransform2D) fileHandler.parseAffine(line);
      assertEquals(new Matrix2x2(0.5, 0.0, 0.0, 0.5), affine.matrix());
      assertEquals(new Vector2D(0.0, 0.0), affine.vector());
    }
  }

  @Nested
  class ParseJuliaTests {
    @Test
    void testParseValidJulia() {
      String line = "-0.70176, -0.3842";
      JuliaTransform julia = (JuliaTransform) fileHandler.parseJulia(line);
      assertEquals(new Complex(-0.70176, -0.3842), julia.getComplex());
    }

    @Test
    void testParseInvalidJulia() {
      String line = "invalid, data";
      assertThrows(NumberFormatException.class, () -> fileHandler.parseJulia(line));
    }

    @Test
    void testParseJuliaWithComments() {
      String line = "-0.70176, -0.3842 # Comment";
      JuliaTransform julia = (JuliaTransform) fileHandler.parseJulia(line);
      assertEquals(new Complex(-0.70176, -0.3842), julia.getComplex());
    }
  }

  @AfterAll
  static void tearDown() {
    fileHandler = null;
    validAffineContent = null;
    validJuliaContent = null;
    invalidNumbersContent = null;
    invalidTypeContent = null;
  }
}