package org.example.chaosgame.controller;

import javafx.scene.Node;
import org.example.chaosgame.model.chaos.ChaosGameDescription;
import org.example.chaosgame.model.chaos.ExploreGame;
import org.example.chaosgame.model.linalg.Complex;
import org.example.chaosgame.model.linalg.Vector2D;
import org.example.chaosgame.model.transformations.ExploreJulia;
import org.example.chaosgame.model.transformations.Transform2D;
import org.example.chaosgame.observer.Observer;
import org.example.chaosgame.observer.PageObserver;
import org.example.chaosgame.observer.PageSubject;
import org.example.chaosgame.view.ExplorePage;

import java.util.ArrayList;
import java.util.List;

public class ExploreGameController implements Observer, PageSubject {
  private final ExploreGame exploreGame;
  private final ExplorePage explorePage;
  private final List<PageObserver> pageObservers;

  public ExploreGameController() {
    Complex c = new Complex(-0.835, 0.2321);
    List<Transform2D> trans = List.of(new ExploreJulia(c));
    ChaosGameDescription description = new ChaosGameDescription(
            new Vector2D(-1.6, -1),
            new Vector2D(1.6, 1), trans);
    this.exploreGame = new ExploreGame(description, 1200, 800);
    this.explorePage = new ExplorePage(this);
    this.pageObservers = new ArrayList<>();
  }

  public void homeButtonClicked() {
    notifyObservers(explorePage);
  }
  public ExplorePage getExplorePage() {
    return explorePage;
  }
  @Override
  public void update() {
  }

  @Override
  public void registerObserver(PageObserver observer) {
    pageObservers.add(observer);
  }

  @Override
  public void removeObserver(PageObserver observer) {
    pageObservers.remove(observer);
  }

  @Override
  public void notifyObservers(Node explorePage) {
    for (PageObserver pageObserver : pageObservers) {
      pageObserver.update(explorePage);
    }
  }
}
