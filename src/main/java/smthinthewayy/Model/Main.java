package smthinthewayy.Model;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import smthinthewayy.Service.DataSource;
import smthinthewayy.Service.Role;
import smthinthewayy.Service.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

/**
 * Model - provides data and responds to controller commands by changing its state
 *
 * @author smthinthewayy
 */
public class Main extends Application {
  /**
   * Container, in which all the components of the interface are placed
   */
  private static Stage stg;

  /**
   * Create a "zero" user and run the JavaFX runtime environment and the JavaFX application.
   */
  public static void main(String[] args) {
    DataSource.user = new User(0, "", "", "", Role.GUEST);
    launch();
  }

  /**
   * It is the main entry point for all JavaFX applications.
   * Update the stage, load the initial window, set the name and size.
   *
   * @param primaryStage Primary Stage
   */
  @Override
  public void start(Stage primaryStage) throws IOException {
    stg = primaryStage;
    primaryStage.setResizable(false);
    Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/authorization.fxml")));
    primaryStage.setTitle("QFT");
    primaryStage.setScene(new Scene(root, 700, 400));
    primaryStage.show();
  }

  /**
   * Changes the current window to the next
   *
   * @param fxml Next window
   * @return Object or null
   */
  public static Object changeScene(String fxml) {
    try {
      FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
      Parent pane = loader.load();
      stg.setScene(new Scene(pane));
      return loader.getController();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Establishes a connection to the database
   *
   * @return Object of type Connection
   */
  public static Connection createConnection() {
    Connection connection = null;
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection("jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2034_quotes", "std_2034_quotes", "qwertyuiop");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return connection;
  }
}