package semiotic;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.sql.*;
import java.time.LocalDate;

public class Update {
  @FXML
  private DatePicker datePicker;

  @FXML
  private Label output;

  @FXML
  private TextArea quoteTextArea;

  @FXML
  private TextField subjectTextField;

  @FXML
  private TextField teacherTextField;

  @FXML
  public void moveToMyQuotes() {
    Object obj = Main.changeScene("myQuotes.fxml");
    assert obj != null;
    ((MyQuotes) obj).fillingOnlyMyQuotes();
  }

  public void init(Item item) {
    quoteTextArea.setText(item.getQuote());
    teacherTextField.setText(item.getTeacher());
    subjectTextField.setText(item.getSubject());
    datePicker.setValue(LocalDate.of(Integer.parseInt(item.getDate().toString().substring(0, 4)), Integer.parseInt(item.getDate().toString().substring(5, 7)), Integer.parseInt(item.getDate().toString().substring(8, 10))));
  }

  @FXML
  public void saveChanges() {
    try {
      Connection connection = Main.createConnection();

      String quoteText = setMargins(quoteTextArea.getText());
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

  private String setMargins(String temp) {
    StringBuilder s = new StringBuilder(temp);
    for (int i = 54; i <= temp.length(); i += 54)
      s.insert(i, "\n");
    return s.toString();
  }
}
