package semiotic;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class Main extends Application {
  private static Stage stg;

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    stg = primaryStage;
    primaryStage.setResizable(false);
    Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("authorization.fxml")));
    primaryStage.setTitle("QFT");
    primaryStage.setScene(new Scene(root, 700, 400));
    primaryStage.show();
  }

  public static void changeScene(String fxml) {
    try {
      Parent pane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));
      stg.getScene().setRoot(pane);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Connection createConnection() {
    Connection connection = null;
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection("jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2034_quotes", "std_2034_quotes", "#pJbP|av9QHopFRk");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return connection;
  }
}