package semiotic;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;

import java.sql.*;

public class Create {
  @FXML
  private TextArea quoteTextArea;

  @FXML
  private TextField teacherTextField;

  @FXML
  private TextField subjectTextField;

  @FXML
  private DatePicker datePicker;

  @FXML
  private Label output;

  @FXML
  public void moveToMenu() {
    Main.changeScene("menu.fxml");
  }

  public void userCreateQuote() {
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

  private String setMargins(String temp) {
    StringBuilder s = new StringBuilder(temp);
    for (int i = 54; i <= temp.length(); i += 54)
      s.insert(i, "\n");
    return s.toString();
  }
}
