package smthinthewayy.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import smthinthewayy.Model.Main;
import smthinthewayy.Service.User;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Controller - interprets the user's actions, notifying the model when changes are needed
 *
 * @author smthinthewayy
 */
public class Recovery {
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
   * Changes the scene to an authorization window
   *
   * @see Main#changeScene(String)
   */
  @FXML
  public void moveToAuthorization() {
    Main.changeScene("/authorization.fxml");
  }

  /**
   * Establishes a connection to the database, sends
   * a parameterized SQL query, updates the user password
   *
   * @see User#makeMD5(String)
   */
  @FXML
  public void userChangePassword() {
    try {
      Connection connection = Main.createConnection();

      String newLogin = loginField.getText();
      String newHashPassword = User.makeMD5(passwordField.getText());

      if (newLogin.isEmpty() || newHashPassword.isEmpty()) {
        output.setTextFill(Paint.valueOf("RED"));
        output.setText("Please enter your data");
      } else {
        String query = "UPDATE users SET hash_password = ? WHERE login = ?;";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, newHashPassword);
        statement.setString(2, newLogin);

        if (statement.executeUpdate() == 1) {
          output.setText("You have successfully changed your password");
          output.setTextFill(Paint.valueOf("GREEN"));
        } else {
          output.setText("This login does not exist");
          output.setTextFill(Paint.valueOf("RED"));
        }
      }

      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
