module com.example.quotes {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires validatorfx;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.bootstrapfx.core;
  requires eu.hansolo.tilesfx;
  requires com.almasb.fxgl.all;
  requires java.sql;
  requires mysql.connector.java;

  opens smthinthewayy.Controller to javafx.fxml;
  opens smthinthewayy.Model to javafx.fxml;
  opens smthinthewayy.Service to javafx.fxml;

  exports smthinthewayy.Controller;
  exports smthinthewayy.Model;
  exports smthinthewayy.Service;
}