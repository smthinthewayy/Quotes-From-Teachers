package com.example.quotes;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class QFTApplication extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(QFTApplication.class.getResource("sample.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 700, 400);
    stage.setTitle("Quotes From Teachers");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}