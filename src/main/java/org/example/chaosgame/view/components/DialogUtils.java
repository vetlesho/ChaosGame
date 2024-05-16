package org.example.chaosgame.view.components;

import javafx.scene.control.Alert;

public class DialogUtils {
  public static void showErrorDialog(String title, String message){
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
