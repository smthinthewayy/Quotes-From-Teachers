package semiotic;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class Registration {
  @FXML
  private TextField loginField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private TextField studyGroupField;

  @FXML
  private Label output;

  @FXML
  public void moveToAuthorization() {
    Main.changeScene("authorization.fxml");
  }

  @FXML
  public void userSignUp() {
    try {
      Connection connection = Main.createConnection();
      Statement statement = connection.createStatement();

      String login = loginField.getText();
      String hashPassword = User.makeMD5(passwordField.getText());
      String studyGroup = studyGroupField.getText();
      int role = 3;

      if (login.isEmpty() || hashPassword.isEmpty() || studyGroup.isEmpty()) {
        output.setTextFill(Paint.valueOf("RED"));
        output.setText("Please enter your data");
      } else if (studyGroup.length() > 7) {
        output.setTextFill(Paint.valueOf("RED"));
        output.setText("Wrong study group format");
      } else {
        String query = String.format("INSERT INTO users(login, study_group, hash_password, role) VALUES ('%s', '%s', '%s', %d);", login, studyGroup, hashPassword, role);
        try {
          statement.execute(query);
          output.setTextFill(Paint.valueOf("GREEN"));
          output.setText("You have successfully registered");
        } catch (SQLIntegrityConstraintViolationException e) {
          output.setTextFill(Paint.valueOf("RED"));
          output.setText("This login already exists");
        }
      }
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
