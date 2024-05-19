package org.example.chaosgame.view.components;

import javafx.scene.control.Alert;

/**
 * Utility class for showing alert dialogs.
 */
public class AlertUtility {
  /**
   * Shows an error dialog with the given title and message.
   *
   * @param title   The title of the dialog.
   * @param message The message of the dialog.
   */
  public static void showErrorDialog(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
