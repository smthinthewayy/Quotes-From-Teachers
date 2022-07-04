package semiotic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class QFTController {

  @FXML
  private TextField loginField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Button singInButton;

  @FXML
  private Label wrongLogin;

  @FXML
  private Button singUpButton;

  @FXML
  public void userLogin(ActionEvent event) throws IOException {
    checkLogin();
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

      Connection connection = DriverManager.getConnection("jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2034_quotes", "std_2034_quotes", "Quotes123");

      Statement statement = connection.createStatement();
      String query = "SELECT * FROM users WHERE login='" + loginField.getText().toString() + "' AND hash_password='" + makeMD5(passwordField.getText()) + "'";
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
}
