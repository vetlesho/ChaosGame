module org.example.chaosgame {
  requires javafx.controls;
  requires javafx.fxml;


  opens org.example.chaosgame to javafx.fxml;
  exports org.example.chaosgame;
}