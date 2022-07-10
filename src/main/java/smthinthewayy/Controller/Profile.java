package smthinthewayy.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import smthinthewayy.Model.Main;
import smthinthewayy.Service.DataSource;
import smthinthewayy.Service.Role;
import smthinthewayy.Service.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Controller - interprets the user's actions, notifying the model when changes are needed
 *
 * @author smthinthewayy
 */
public class Profile {
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
   * The field responsible for displaying the user's role
   */
  @FXML
  private Label roleField;

  /**
   * Field responsible for entering the group number
   */
  @FXML
  private TextField studyGroupField;

  /**
   * The field responsible for outputting the number of quotes
   */
  @FXML
  private Label numberOfQuotesButton;

  /**
   * Changes the scene to the main menu window
   *
   * @see Main#changeScene(String)
   */
  @FXML
  public void moveToMenu() {
    Main.changeScene("/menu.fxml");
  }

  /**
   * Fills the window with the data of the current user.
   * Establishes a connection to the database, sends a
   * parameterized SQL-query to find out the number of
   * citations of the user
   */
  public void init() {
    loginField.setText(DataSource.user.getLogin());
    studyGroupField.setText(DataSource.user.getStudyGroup());

    int numberOfQuotes = 0;

    try {
      Connection connection = Main.createConnection();

      String query = "SELECT COUNT(id) FROM quotes_teachers WHERE id_user = ?";
      PreparedStatement statement = connection.prepareStatement(query);

      statement.setInt(1, DataSource.user.getId());

      ResultSet result = statement.executeQuery();

      while (result.next()) {
        numberOfQuotes = result.getInt("COUNT(id)");
      }

      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    numberOfQuotesButton.setText("Number of quotes: " + numberOfQuotes);
    roleField.setText("Role: " + String.valueOf(DataSource.user.getRole()).toLowerCase());
  }

  /**
   * Establishes a connection to the database, sends a
   * parameterized SQL-query to change user data
   *
   * @see User#makeMD5(String)
   */
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
