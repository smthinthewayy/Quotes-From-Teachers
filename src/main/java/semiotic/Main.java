package semiotic;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
  private static Stage stg;

  @Override
  public void start(Stage primaryStage) throws IOException {
    stg = primaryStage;
    primaryStage.setResizable(false);
    Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("authorization.fxml")));
    primaryStage.setTitle("QFT");
    primaryStage.setScene(new Scene(root, 700, 400));
    primaryStage.show();
  }

  public void changeScene(String fxml) throws IOException {
    Parent pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
    stg.getScene().setRoot(pane);
  }

  public static void main(String[] args) {
    launch();
  }
}