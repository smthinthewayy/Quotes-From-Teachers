package smthinthewayy.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import smthinthewayy.Model.Main;
import smthinthewayy.Service.DataSource;
import smthinthewayy.Service.Role;
import smthinthewayy.Service.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Controller - interprets the user's actions, notifying the model when changes are needed
 *
 * @author smthinthewayy
 */
public class Authorization {
  /**
   * The field responsible for entering a login
   */
  @FXML
  private TextField loginField;

  /**
   * The field responsible for entering a password
   */
  @FXML
  private PasswordField passwordField;

  /**
   * The field responsible for the error/success output
   */
  @FXML
  private Label output;

  /**
   * Changes the scene to the password recovery window
   *
   * @see Main#changeScene(String)
   */
  @FXML
  public void moveToRecovery() {
    Main.changeScene("/recovery.fxml");
  }

  /**
   * Changes the scene to the main menu window
   *
   * @see Main#changeScene(String)
   */
  @FXML
  public void moveToMenu() {
    Main.changeScene("/menu.fxml");
  }

  /**
   * Changes the scene to a quotes view window.
   * Give the user the GUEST role and fill out the table
   *
   * @see User#setRole(Role)
   * @see Main#changeScene(String)
   * @see Read#filling()
   */
  @FXML
  public void moveToRead() {
    DataSource.user.setRole(Role.GUEST);
    Object obj = Main.changeScene("/read.fxml");
    assert obj != null;
    ((Read) obj).filling();
  }

  /**
   * Changes the scene to the registration window
   *
   * @see Main#changeScene(String)
   */
  @FXML
  public void moveToRegistration() {
    Main.changeScene("/registration.fxml");
  }

  /**
   * Establishes a connection to the database. Creates a password hash.
   * Sends a parameterized SQl-request with the data entered by the user, and if such
   * a user exists, we create an object of type User and move to the main menu.
   *
   * @see User#makeMD5(String)
   * @see User#User(int, String, String, String, Role)  User
   * @see User#initRole(ResultSet)
   * @see Authorization#moveToMenu()
   */
  @FXML
  public void userLogIn() {
    try {
      Connection connection = Main.createConnection();

      String login = loginField.getText();
      String hashPassword = User.makeMD5(passwordField.getText());

      String query = "SELECT * FROM users WHERE login = ? AND hash_password = ?;";
      PreparedStatement statement = connection.prepareStatement(query);

      statement.setString(1, login);
      statement.setString(2, hashPassword);

      ResultSet result = statement.executeQuery();
      if (result.next()) {
        DataSource.user = new User(result.getInt("id"), login, result.getString("study_group"), hashPassword, User.initRole(result));
        moveToMenu();
      } else if (loginField.getText().isEmpty() && passwordField.getText().isEmpty()) {
        output.setText("Please enter your data");
      } else {
        output.setText("Wrong username or password");
      }
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
