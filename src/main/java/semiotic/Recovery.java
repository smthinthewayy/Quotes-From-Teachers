package semiotic;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.sql.Connection;
import java.sql.Statement;

public class Recovery {
  @FXML
  private TextField loginField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Label output;

  @FXML
  public void moveToAuthorization() {
    Main.changeScene("authorization.fxml");
  }

  @FXML
  public void userChangePassword() {
    try {
      Connection connection = Main.createConnection();
      Statement statement = connection.createStatement();

      String newLogin = loginField.getText();
      String newHashPassword = User.makeMD5(passwordField.getText());

      if (newLogin.isEmpty() || newHashPassword.isEmpty()) {
        output.setTextFill(Paint.valueOf("RED"));
        output.setText("Please enter your data");
      } else {
        String query = String.format("UPDATE users SET hash_password = '%s' WHERE login = '%s';", newHashPassword, newLogin);
        if (statement.executeUpdate(query) == 1) {
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
