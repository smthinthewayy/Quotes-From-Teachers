package smthinthewayy.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import smthinthewayy.Model.Main;
import smthinthewayy.Service.DataSource;
import smthinthewayy.Service.Item;

import java.sql.*;
import java.time.LocalDate;

/**
 * Controller - interprets the user's actions, notifying the model when changes are needed
 *
 * @author smthinthewayy
 */
public class Update {
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
   * Changes the scene to a window to view my quotes
   *
   * @see MyQuotes#fillingOnlyMyQuotes()
   */
  @FXML
  public void moveToMyQuotes() {
    Object obj = Main.changeScene("/myQuotes.fxml");
    assert obj != null;
    ((MyQuotes) obj).fillingOnlyMyQuotes();
  }

  /**
   * Fills the window with the data of the selected quote
   *
   * @param item table row
   */
  public void init(Item item) {
    quoteTextArea.setText(item.getQuote());
    teacherTextField.setText(item.getTeacher());
    subjectTextField.setText(item.getSubject());
    datePicker.setValue(LocalDate.of(Integer.parseInt(item.getDate().toString().substring(0, 4)), Integer.parseInt(item.getDate().toString().substring(5, 7)), Integer.parseInt(item.getDate().toString().substring(8, 10))));
  }

  /**
   * Establishes a connection to the database, sends a
   * parameterized SQL query, updates the selected quote
   *
   * @see DataSource#setMargins(String)
   */
  @FXML
  public void saveChanges() {
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
        String query = "UPDATE quotes_teachers SET quote = ?, teacher = ?, subject = ?, date = ? WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, quoteText);
        statement.setString(2, teacher);
        statement.setString(3, subject);
        statement.setDate(4, Date.valueOf(datePicker.getValue()));
        statement.setInt(5, DataSource.id_quote);

        try {
          statement.execute();
          output.setTextFill(Paint.valueOf("GREEN"));
          output.setText("You have successfully changed the quote");
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
