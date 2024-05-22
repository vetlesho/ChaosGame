package org.idatt2003.view.components;

import javafx.scene.text.Text;

/**
 * Class for the game header, extends Text.
 * The header is used for displaying the game title.
 */
public class GameHeader extends Text {
  /**
   * Constructor for the GameHeader.
   *
   * @param text the text to be displayed on the header
   */
  public GameHeader(String text) {
    super(text);
    this.getStyleClass().add("game-header");
  }
}
