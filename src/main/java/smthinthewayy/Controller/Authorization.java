package smthinthewayy.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import smthinthewayy.Model.Main;
import smthinthewayy.Controller.Read;
import smthinthewayy.Service.DataSource;
import smthinthewayy.Service.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

      String login = loginField.getText();
      String hashPassword = User.makeMD5(passwordField.getText());

      String query = "SELECT * FROM users WHERE login = ? AND hash_password = ?;";
      PreparedStatement statement = connection.prepareStatement(query);

      statement.setString(1, login);
      statement.setString(2, hashPassword);

      ResultSet result = statement.executeQuery();
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
