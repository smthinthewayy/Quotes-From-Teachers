package semiotic;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;

public class QFTController {
  @FXML
  private TextField loginField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Label output;

  @FXML
  private Label wrongLogin;

  @FXML
  public void userLogin() {
    checkLogin();
  }

  @FXML
  public void moveToAuthorization() throws IOException {
    Main m = new Main();
    m.changeScene("authorization.fxml");
  }

  @FXML
  public void moveToRegistration() throws IOException {
    Main m = new Main();
    m.changeScene("registration.fxml");
  }

  @FXML
  public void moveToRecovery() throws IOException {
    Main m = new Main();
    m.changeScene("recovery.fxml");
  }

  @FXML
  public void moveToAfterLogin() throws IOException {
    Main m = new Main();
    m.changeScene("afterLogin.fxml");
  }

  private Connection createConnection() throws SQLException {
    return DriverManager.getConnection("jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2034_quotes", "std_2034_quotes", "123qwerty");
  }

  @FXML
  public void userSignUp() {
    try {
      Connection connection = createConnection();
      Statement statement = connection.createStatement();
      String query = String.format("INSERT INTO users(login, hash_password) VALUES ('%s', '%s');", loginField.getText(), makeMD5(passwordField.getText()));
      try {
        statement.execute(query);
        output.setTextFill(Paint.valueOf("GREEN"));
        output.setText("You have successfully registered");
      } catch (SQLIntegrityConstraintViolationException e) {
        output.setText("This login already exists");
        output.setTextFill(Paint.valueOf("RED"));
      }
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static String makeMD5(String password) {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("MD5");
      md.update(password.getBytes());
      String pwd = new BigInteger(1, md.digest()).toString(16);
      return pwd.toUpperCase();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return password;
  }

  private void checkLogin() {
    try {
      Connection connection = createConnection();
      Statement statement = connection.createStatement();
      String query = String.format("SELECT * FROM users WHERE login = '%s' AND hash_password = '%s';", loginField.getText(), makeMD5(passwordField.getText()));
      ResultSet result = statement.executeQuery(query);
      Main m = new Main();
      if (result.next()) {
        wrongLogin.setText("Success");
        m.changeScene("afterLogin.fxml");
      } else if (loginField.getText().isEmpty() && passwordField.getText().isEmpty()) {
        wrongLogin.setText("Please enter your data");
      } else {
        wrongLogin.setText("Wrong username or password");
      }
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void userChangePassword() {
    try {
      Connection connection = createConnection();
      Statement statement = connection.createStatement();
      String query = String.format("UPDATE users SET hash_password = '%s' WHERE login = '%s';", makeMD5(passwordField.getText()), loginField.getText());
      if (statement.executeUpdate(query) == 1) {
        output.setText("You have successfully changed your password");
        output.setTextFill(Paint.valueOf("GREEN"));
      } else {
        output.setText("This login does not exist");
        output.setTextFill(Paint.valueOf("RED"));
      }
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
