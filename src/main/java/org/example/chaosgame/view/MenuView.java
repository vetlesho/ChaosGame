package org.example.chaosgame.view;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

public class MenuView {
  private final HBox menuBox;
  private final ComboBox<String> comboBox;

  public MenuView() {
    // Create a combo box (dropdown menu)
    this.comboBox = new ComboBox<>();
    this.comboBox.getItems().addAll("Julia", "Sierpinski", "Barnsley");

    // Add the combo box to the HBox
    this.menuBox = new HBox();
    this.menuBox.getChildren().add(this.comboBox);
  }

  public HBox getMenuBox() {
    return this.menuBox;
  }

  public ComboBox<String> getComboBox() {
    return this.comboBox;
  }
}
