package org.example.chaosgame.model.chaos;

import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.AffineTransform2D;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ChaosCanvasTest {
  private static ChaosCanvas chaosCanvas;
  private static Vector2D minCoords;
  private static Vector2D maxCoords;
  private static final int WIDTH = 100;
  private static final int HEIGHT = 100;

  @BeforeEach
  void setUp() {
    minCoords = new Vector2D(0, 0);
    maxCoords = new Vector2D(1, 1);
    chaosCanvas = new ChaosCanvas(WIDTH, HEIGHT, minCoords, maxCoords);
  }

  @AfterEach
  void tearDown() {
    minCoords = null;
    maxCoords = null;
    chaosCanvas = null;
  }

  @Nested
  @DisplayName("Test getPixel")
  class TestGetPixel {
    @Test
    @DisplayName("Test positive getPixel")
    void getPixel() {
      double pixel = chaosCanvas.getPixel(new Vector2D(0, 0));
      assertEquals(0.0, pixel);
    }

    @Test
    @DisplayName("Test negative getPixel")
    void getPixelFail() {
      double pixel = chaosCanvas.getPixel(new Vector2D(0, 0));
      assertNotEquals(1.0, pixel);
    }
  }

  @Nested
  @DisplayName("Test putPixel")
  class TestPutPixel {
    @Test
    @DisplayName("Test positive putPixel(Vector2D)")
    void putPixel() {
      chaosCanvas.putPixel(new Vector2D(0, 0));
      assertEquals(1.0, chaosCanvas.getPixel(new Vector2D(0, 0)));
    }

    @Test
    @DisplayName("Test negative putPixel(Vector2D)")
    void putPixelFail() {
      chaosCanvas.putPixel(new Vector2D(0, 0));
      assertNotEquals(0.0, chaosCanvas.getPixel(new Vector2D(0, 0)));
    }

    @Test
    @DisplayName("Test positive putPixel(int, int, double)")
    void putPixelInt() {
      chaosCanvas.putPixel(0, 0, 1.0);
      assertEquals(1.0, chaosCanvas.getPixel(new Vector2D(0, 1)));
    }

    @Test
    @DisplayName("Test negative putPixel(int, int, double)")
    void putPixelIntFail() {
      chaosCanvas.putPixel(0, 0, 1.0);
      assertNotEquals(0.0, chaosCanvas.getPixel(new Vector2D(0, 1)));
    }
  }

  @Nested
  @DisplayName("Test transformIndicesToCoords")
  class TestTransformIndicesToCoords {
    @Test
    @DisplayName("Test positive transformIndicesToCoords")
    void transformIndicesToCoords() {
      Vector2D coords = chaosCanvas.transformIndicesToCoords(0, 0);
      assertEquals(0.0, coords.getX());
      assertEquals(1.0, coords.getY());
    }

    @Test
    @DisplayName("Test negative transformIndicesToCoords")
    void transformIndicesToCoordsFail() {
      Vector2D coords = chaosCanvas.transformIndicesToCoords(0, 0);
      assertNotEquals(1.0, coords.getX());
      assertNotEquals(0.0, coords.getY());
    }
  }

  @Nested
  @DisplayName("Test clearCanvas")
  class TestClearCanvas {
    @Test
    @DisplayName("Test positive clearCanvas")
    void clearCanvas() {
      chaosCanvas.putPixel(new Vector2D(0, 0));
      chaosCanvas.clearCanvas();
      assertEquals(0.0, chaosCanvas.getPixel(new Vector2D(0, 0)));
    }

    @Test
    @DisplayName("Test negative clearCanvas")
    void clearCanvasFail() {
      chaosCanvas.putPixel(new Vector2D(0, 0));
      chaosCanvas.clearCanvas();
      double[][] canvas = chaosCanvas.getCanvasArray();
      for (double[] row : canvas) {
        for (double value : row) {
          assertNotEquals(1.0, value);
        }
      }
    }
  }

  @Test
  void getWidth() {
    assertEquals(WIDTH, chaosCanvas.getWidth());
  }

  @Test
  void getHeight() {
    assertEquals(HEIGHT, chaosCanvas.getHeight());
  }

  @Test
  void getMinCoords() {
    assertEquals(minCoords, chaosCanvas.getMinCoords());
  }

  @Test
  void getMaxCoords() {
    assertEquals(maxCoords, chaosCanvas.getMaxCoords());
  }

  @Test
  void setMinCoords() {
    Vector2D newMinCoords = new Vector2D(-1, -1);
    chaosCanvas.setMinCoords(newMinCoords);
    assertEquals(newMinCoords, chaosCanvas.getMinCoords());
  }

  @Test
  void setMaxCoords() {
    Vector2D newMaxCoords = new Vector2D(2, 2);
    chaosCanvas.setMaxCoords(newMaxCoords);
    assertEquals(newMaxCoords, chaosCanvas.getMaxCoords());
  }

  @Test
  void setTransformCoordsToIndices() {
    Vector2D newMinCoords = new Vector2D(-1, -1);
    Vector2D newMaxCoords = new Vector2D(2, 2);
    chaosCanvas.setMinCoords(newMinCoords);
    chaosCanvas.setMaxCoords(newMaxCoords);
    chaosCanvas.setTransformCoordsToIndices();
    AffineTransform2D transform = chaosCanvas.getTransformCoordsToIndices();
    assertNotNull(transform);
    assertEquals(transform, chaosCanvas.getTransformCoordsToIndices());
  }

  @Test
  void setWidth() {
    int newWidth = 200;
    chaosCanvas.setWidth(newWidth);
    assertEquals(newWidth, chaosCanvas.getWidth());
  }

  @Test
  void setHeight() {
    int newHeight = 200;
    chaosCanvas.setHeight(newHeight);
    assertEquals(newHeight, chaosCanvas.getHeight());
  }
}
