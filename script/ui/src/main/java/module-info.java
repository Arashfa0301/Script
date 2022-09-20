module script.ui {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;

    opens ui to javafx.graphics, javafx.fxml;
  }