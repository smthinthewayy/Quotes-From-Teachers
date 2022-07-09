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
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.regex.Pattern;

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
    Main.changeScene("/authorization.fxml");
  }

  @FXML
  public void userSignUp() {
    try {
      Connection connection = Main.createConnection();

      String login = loginField.getText();
      String hashPassword = User.makeMD5(passwordField.getText());
      String studyGroup = studyGroupField.getText();
      int role = 1;

      Pattern pattern = Pattern.compile("[1-9]\\d{2}-\\d{3}");

      if (login.isEmpty() || hashPassword.isEmpty() || studyGroup.isEmpty()) {
        output.setTextFill(Paint.valueOf("RED"));
        output.setText("Please enter your data");
      } else if (!pattern.matcher(studyGroup).matches()) {
        output.setTextFill(Paint.valueOf("RED"));
        output.setText("Wrong study group format");
      } else {
        String query = "INSERT INTO users(login, study_group, hash_password, role) VALUES (?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, login);
        statement.setString(2, studyGroup);
        statement.setString(3, hashPassword);
        statement.setInt(4, role);

        try {
          statement.execute();
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
