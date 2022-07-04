package semiotic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
  private Button buttonForgotPassword;

  @FXML
  private Button guestButton;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Button singInButton;

  @FXML
  private Button changeButton;

  @FXML
  private Label output;

  @FXML
  private Label wrongLogin;

  @FXML
  private Button singUpButton;

  @FXML
  private Button backBotton;

  @FXML
  public void userLogin(ActionEvent event) throws IOException {
    checkLogin();
  }

  @FXML
  public void userBack(ActionEvent event) throws IOException {
    Main m = new Main();
    m.changeScene("sample.fxml");
  }

  @FXML
  public void moveToRegistration(ActionEvent event) throws IOException {
    Main m = new Main();
    m.changeScene("registration.fxml");
  }

  @FXML
  public void moveToRecovery(ActionEvent event) throws IOException {
    Main m = new Main();
    m.changeScene("recovery.fxml");
  }

  @FXML
  public void moveToAfterLogin(ActionEvent event) throws IOException {
    Main m = new Main();
    m.changeScene("afterLogin.fxml");
  }

  @FXML
  public void userSignUp(ActionEvent event) throws IOException {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");

      Connection connection = DriverManager.getConnection("jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2034_quotes", "std_2034_quotes", "password");

      Statement statement = connection.createStatement();
      String query = "INSERT INTO users(login, hash_password) VALUES ('" + loginField.getText() + "', '" + makeMD5(passwordField.getText()) + "');";

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
      System.out.println(e);
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

  private void checkLogin() throws IOException {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");

      Connection connection = DriverManager.getConnection("jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2034_quotes", "std_2034_quotes", "password");

      Statement statement = connection.createStatement();
      String query = "SELECT * FROM users WHERE login='" + loginField.getText() + "' AND hash_password='" + makeMD5(passwordField.getText()) + "'";
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
      System.out.println(e);
    }
  }

  @FXML
  void userChangePassword(ActionEvent event) {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");

      Connection connection = DriverManager.getConnection("jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2034_quotes", "std_2034_quotes", "password");

      Statement statement = connection.createStatement();
      String query = "UPDATE users SET hash_password='" + makeMD5(passwordField.getText()) + "' WHERE login='" + loginField.getText() + "';";

      if (statement.executeUpdate(query) == 1) {
        output.setText("You have successfully changed your password");
        output.setTextFill(Paint.valueOf("GREEN"));
      } else {
        output.setText("This login does not exist");
        output.setTextFill(Paint.valueOf("RED"));
      }

      connection.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
