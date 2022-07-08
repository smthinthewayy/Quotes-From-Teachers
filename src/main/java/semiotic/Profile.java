package semiotic;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

public class Profile {
  @FXML
  private TextField loginField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Label output;

  @FXML
  private TextField studyGroupField;

  @FXML
  public void moveToMenu() {
    Main.changeScene("menu.fxml");
  }

  public void init() {
    loginField.setText(DataSource.user.getLogin());
    studyGroupField.setText(DataSource.user.getStudyGroup());
  }

  @FXML
  public void saveChanges() {
    try {
      Connection connection = Main.createConnection();

      String login = loginField.getText();
      String hashPassword = User.makeMD5(passwordField.getText());
      String studyGroup = studyGroupField.getText();

      if (login.isEmpty() || studyGroup.isEmpty()) {
        output.setTextFill(Paint.valueOf("RED"));
        output.setText("Please enter your data");
      } else if (studyGroup.length() > 7) {
        output.setTextFill(Paint.valueOf("RED"));
        output.setText("Wrong study group format");
      } else {
        String query;
        PreparedStatement statement;
        if (passwordField.getText().isEmpty()) {
          query = "UPDATE users SET login = ?, study_group = ? WHERE id = ?";
          statement = connection.prepareStatement(query);

          statement.setString(1, login);
          statement.setString(2, studyGroup);
          statement.setInt(3, DataSource.user.getId());
        } else {
          query = "UPDATE users SET login = ?, hash_password = ?, study_group = ? WHERE id = ?";
          statement = connection.prepareStatement(query);

          statement.setString(1, login);
          statement.setString(2, hashPassword);
          statement.setString(3, studyGroup);
          statement.setInt(4, DataSource.user.getId());
        }

        try {
          statement.execute();
          output.setTextFill(Paint.valueOf("GREEN"));
          output.setText("You have successfully changed your data");
        } catch (SQLIntegrityConstraintViolationException e) {
          output.setTextFill(Paint.valueOf("RED"));
          output.setText("Error");
        }
      }
      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
