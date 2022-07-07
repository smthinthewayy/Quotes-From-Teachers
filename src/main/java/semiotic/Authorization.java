package semiotic;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Authorization {
  @FXML
  private TextField loginField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Label output;

  @FXML
  public void moveToRecovery() {
    Main.changeScene("recovery.fxml");
  }

  @FXML
  public void moveToMenu() {
    Main.changeScene("menu.fxml");
  }

  @FXML
  public void moveToRead() {
    DataSource.user = new User(0, "", "", "", 0);
    Object obj = Main.changeScene("read.fxml");
    assert obj != null;
    ((Read) obj).filling();
  }

  @FXML
  public void moveToRegistration() {
    Main.changeScene("registration.fxml");
  }

  @FXML
  public void userLogIn() {
    try {
      Connection connection = Main.createConnection();
      Statement statement = connection.createStatement();

      String login = loginField.getText();
      String hashPassword = User.makeMD5(passwordField.getText());

      String query = String.format("SELECT * FROM users WHERE login = '%s' AND hash_password = '%s';", login, hashPassword);
      ResultSet result = statement.executeQuery(query);
      if (result.next()) {
        DataSource.user = new User(result.getInt("id"), login, result.getString("study_group"), hashPassword, result.getInt("role"));
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
