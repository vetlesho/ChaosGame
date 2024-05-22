module org.idatt2003 {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.media;


  opens org.idatt2003 to javafx.fxml;
  exports org.idatt2003;
}