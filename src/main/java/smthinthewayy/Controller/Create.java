package smthinthewayy.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import smthinthewayy.Model.Main;
import smthinthewayy.Service.DataSource;

import java.sql.*;

/**
 * Controller - interprets the user's actions, notifying the model when changes are needed
 *
 * @author smthinthewayy
 */
public class Create {
  /**
   * The field responsible for entering the text of the quote
   */
  @FXML
  private TextArea quoteTextArea;

  /**
   * The field responsible for entering the teacher's name
   */
  @FXML
  private TextField teacherTextField;

  /**
   * The field responsible for entering the name of the subject
   */
  @FXML
  private TextField subjectTextField;

  /**
   * The picker responsible for entering the date
   */
  @FXML
  private DatePicker datePicker;

  /**
   * The field responsible for the error/success output
   */
  @FXML
  private Label output;

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
   * Establishes a connection to the database. Sends a
   * parameterized SQl query with the data entered by
   * the user and inserts them into the table
   *
   * @see DataSource#setMargins(String)
   */
  public void userCreateQuote() {
    try {
      Connection connection = Main.createConnection();

      String quoteText = DataSource.setMargins(quoteTextArea.getText());
      String teacher = teacherTextField.getText();
      String subject = subjectTextField.getText();
      String date = "";
      if (datePicker.getValue() != null) date = String.valueOf(datePicker.getValue()).trim();

      if (quoteText.isEmpty() || teacher.isEmpty() || subject.isEmpty() || date.isEmpty()) {
        output.setTextFill(Paint.valueOf("RED"));
        output.setText("Please enter the full details");
      } else {
        String query = "INSERT INTO quotes_teachers(id_user, quote, teacher, subject, date) VALUES (?, ?, ?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, DataSource.user.getId());
        statement.setString(2, quoteText);
        statement.setString(3, teacher);
        statement.setString(4, subject);
        statement.setDate(5, Date.valueOf(datePicker.getValue()));

        try {
          statement.execute();
          output.setTextFill(Paint.valueOf("GREEN"));
          output.setText("You have successfully added a quote");
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
