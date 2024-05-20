package org.example.chaosgame.model.chaos;

import org.example.chaosgame.controller.interfaces.Observer;
import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.linalg.Matrix2x2;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.AffineTransform2D;
import org.example.chaosgame.model.transformations.JuliaTransform;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChaosGameTest {
  private static ChaosGameDescription juliaDescription;
  private static ChaosGameDescription affineDescriptionWithProb;
  private static ChaosGame instance;

  @BeforeEach
  void init() {
    juliaDescription = new ChaosGameDescription(
            new Vector2D(-1.6, -1),
            new Vector2D(1.6, 1),
            List.of(
                    new JuliaTransform(new Complex(-0.70176, -0.3842), 1)
            ));

    affineDescriptionWithProb = new ChaosGameDescription(
            new Vector2D(-2.65, 0.0),
            new Vector2D(2.65, 10.0),
            List.of(
                    new AffineTransform2D(new Matrix2x2(0.0, 0.0, 0.0, 0.16),
                            new Vector2D(0.0, 0.0)),
                    new AffineTransform2D(new Matrix2x2(0.85, 0.04, -0.04, 0.85),
                            new Vector2D(0.0, 1.60)),
                    new AffineTransform2D(new Matrix2x2(0.20, -0.26, 0.23, 0.22),
                            new Vector2D(0.0, 1.60)),
                    new AffineTransform2D(new Matrix2x2(-0.15, 0.28, 0.26, 0.24),
                            new Vector2D(0.0, 0.44))
            ), List.of(2, 84, 7, 7));
    instance = ChaosGame.getInstance(juliaDescription, 500, 500);
    instance.setChaosGameDescription(juliaDescription);
    instance.setSteps(0);
    instance.setTotalSteps(0);
  }

  @Nested
  class SingletonTests {
    @Test
    void testSingletonInstanceIsNull() {
      assertNotNull(instance, "The singleton instance should not be null");
    }

    @Test
    void testSingletonInstanceIsNotNull() {
      ChaosGame instance2 = ChaosGame.getInstance(juliaDescription, 500, 500);
      assertEquals(instance, instance2, "The singleton instance should be the same");
    }
  }


  @Nested
  class gettersTest {
    @Test
    void getCanvas() {
      assertNotNull(instance.getCanvas(), "The canvas should not be null");
    }

    @Test
    void getDescription() {
      assertEquals(juliaDescription, instance.getDescription(), "The description should be the same");
    }

    @Test
    void getSteps() {
      assertEquals(0, instance.getSteps(), "The steps should be 0");
    }

    @Test
    void getTotalSteps() {
      assertEquals(0, instance.getTotalSteps(), "The total steps should be 0");
    }
  }

  @Nested
  class TestSteps {
    @Test
    void setSteps() {
      instance.setSteps(10);
      assertEquals(10, instance.getSteps(), "The steps should be 10");
    }

    @Test
    void setStepsNegative() {
      assertThrows(IllegalArgumentException.class, () -> instance.setSteps(-10),
              "The steps should be positive");
    }

    @Test
    void setStepsTooLarge() {
      assertThrows(IllegalArgumentException.class, () -> instance.setSteps(1000001),
              "The steps should be less than 1000000");
    }

    @Test
    void setTotalSteps() {
      instance.setTotalSteps(10);
      assertEquals(10, instance.getTotalSteps(), "The total steps should be 10");
    }

    @Test
    void addTotalStepsNegative() {
      assertThrows(IllegalArgumentException.class, () -> instance.addTotalSteps(-10),
              "The steps should be positive");
    }

    @Test
    void addTotalSteps() {
      instance.addTotalSteps(10);
      instance.addTotalSteps(10);
      assertEquals(20, instance.getTotalSteps(), "The total steps should be 10");
    }

    @Test
    void resetTotalSteps() {
      instance.addTotalSteps(10);
      instance.resetTotalSteps();
      assertEquals(0, instance.getTotalSteps(), "The total steps should be 0");
    }

    @Test
    void runSteps() {
      instance.setSteps(10);
      instance.runSteps();
      assertEquals(10, instance.getSteps(), "The total steps should be 10");
    }

    @Test
    void runStepsUniform() {
      instance = ChaosGame.getInstance(juliaDescription, 500, 500);
      instance.setSteps(10);
      assertEquals(10, instance.getSteps());
    }

    @Test
    void runStepsWithProbabilities() {
      instance.setChaosGameDescription(affineDescriptionWithProb);
      instance.setSteps(10);
      instance.runSteps();
      assertEquals(10, instance.getSteps());
    }
  }

  @Nested
  class setDescription {
    @Test
    void setChaosGameDescription() {
      instance.setChaosGameDescription(affineDescriptionWithProb);
      assertEquals(affineDescriptionWithProb, instance.getDescription(), "The description should be the same");
      assertEquals(0, instance.getTotalSteps(), "The total steps should be 0");
      assertEquals(500, instance.getCanvas().getWidth(), "The width should be 500");
      assertEquals(500, instance.getCanvas().getHeight(), "The height should be 500");
    }

    @Test
    void setChaosGameDescriptionNull() {
      assertThrows(IllegalArgumentException.class, () -> instance.setChaosGameDescription(null),
              "The description should not be null");
    }
  }

  @Test
  void setChaosCanvas() {
    instance.setChaosCanvas(new Vector2D(-1.6, -1), new Vector2D(1.6, 1));
    assertEquals(new Vector2D(-1.6, -1), instance.getDescription().getMinCoords(), "The min coords should be the same");
    assertEquals(new Vector2D(1.6, 1), instance.getDescription().getMaxCoords(), "The max coords should be the same");
  }

  @Test
  void registerAndNotifyObservers() {
    Observer observer = () -> assertTrue(true, "Observer should be notified");
    instance.registerObserver(observer);
    instance.notifyObservers();
  }

  @Test
  void removeObserver() {
    Observer observer = () -> fail("Observer should not be notified");
    instance.registerObserver(observer);
    instance.removeObserver(observer);
    instance.notifyObservers();
  }

  @AfterEach
  void tearDown() {
    instance = null;
  }

}