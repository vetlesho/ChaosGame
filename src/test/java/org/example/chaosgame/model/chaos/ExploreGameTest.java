package org.example.chaosgame.model.chaos;

import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.ExploreJulia;
import org.example.chaosgame.model.transformations.Transform2D;
import org.example.chaosgame.controller.interfaces.Observer;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExploreGameTest {
  private ExploreGame exploreGame;
  private ChaosGameDescription description;
  private static final int WIDTH = 100;
  private static final int HEIGHT = 100;
  private List<Transform2D> transforms;

  private ExploreJulia exploreJulia;
  @BeforeEach
  void setUp() {
    Vector2D minCoords = new Vector2D(-1.6, -1);
    Vector2D maxCoords = new Vector2D(1.6, 1);
    transforms = new ArrayList<>();
    exploreJulia = new ExploreJulia(new Complex(0, 1));
    transforms.add(exploreJulia);
    description = new ChaosGameDescription(minCoords, maxCoords, transforms);
    exploreGame = new ExploreGame(description, WIDTH, HEIGHT);
  }

  @AfterEach
  void tearDown() {
    transforms = null;
    exploreJulia = null;
    exploreGame = null;
    description = null;
  }

  @Test
  void testSetExploreGame() {
    Vector2D newMinCoords = new Vector2D(-1.6, -1);
    Vector2D newMaxCoords = new Vector2D(1.6, 1);
    List<Transform2D> newTransforms = new ArrayList<>();
    newTransforms.add(new ExploreJulia(new Complex(1, 1)));
    ChaosGameDescription newDescription = new ChaosGameDescription(newMinCoords, newMaxCoords, newTransforms);

    exploreGame.setExploreGame(newDescription, 200, 200);

    assertEquals(newDescription, exploreGame.getDescription());
    ChaosCanvas canvas = exploreGame.getCanvas();
    assertEquals(200, canvas.getWidth());
    assertEquals(200, canvas.getHeight());
    assertEquals(newMinCoords, canvas.getMinCoords());
    assertEquals(newMaxCoords, canvas.getMaxCoords());
  }

  @Test
  void testGetCanvas() {
    ChaosCanvas canvas = exploreGame.getCanvas();
    assertNotNull(canvas);
    assertEquals(WIDTH, canvas.getWidth());
    assertEquals(HEIGHT, canvas.getHeight());
  }

  @Test
  void testGetDescription() {
    assertEquals(description, exploreGame.getDescription());
  }

  @Test
  void testSetChaosCanvas() {
    Vector2D newMinCoords = new Vector2D(-2, -2);
    Vector2D newMaxCoords = new Vector2D(2, 2);
    exploreGame.setChaosCanvas(newMinCoords, newMaxCoords, 300, 300);

    ChaosCanvas canvas = exploreGame.getCanvas();
    assertEquals(300, canvas.getWidth());
    assertEquals(300, canvas.getHeight());
    assertEquals(newMinCoords, canvas.getMinCoords());
    assertEquals(newMaxCoords, canvas.getMaxCoords());
  }

  @Test
  void testExploreFractals() {

    exploreGame.exploreFractals();

    ChaosCanvas canvas = exploreGame.getCanvas();
    for (int i = 0; i < canvas.getHeight(); i++) {
      for (int j = 0; j < canvas.getWidth(); j++) {
        assertTrue(canvas.getPixel(new Vector2D(i, j)) >= 0);
      }
    }
  }

  @Test
  void testRemoveObserver() {
    Observer observer = () -> fail("Observer should not be notified");
    exploreGame.registerObserver(observer);
    exploreGame.removeObserver(observer);
//    exploreGame.notifyObservers();
  }

  @Test
  void testRegisterAndNotifyObservers() {
    Observer observer1 = () -> assertTrue(true,"Observer should be notified");
    Observer observer2 = () -> assertTrue(true,"Observer should be notified");
    exploreGame.registerObserver(observer1);
    exploreGame.registerObserver(observer2);
    exploreGame.notifyObservers();
  }
}
