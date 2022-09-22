module script.ui {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires script.core.main;
    requires script.data;

    opens ui to javafx.graphics, javafx.fxml;
  }