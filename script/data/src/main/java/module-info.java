module script.data {
    requires java.sql;  
    requires java.net.http;

    opens data to javafx.graphics, javafx.fxml;
  }