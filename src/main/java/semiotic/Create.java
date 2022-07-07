package semiotic;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;

import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

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
      Statement statement = connection.createStatement();

      String quoteText = setMargins(quoteTextArea.getText());
      String teacher = teacherTextField.getText();
      String subject = subjectTextField.getText();
      String date = "";
      if (datePicker.getValue() != null) date = String.valueOf(datePicker.getValue()).trim();

      if (quoteText.isEmpty() || teacher.isEmpty() || subject.isEmpty() || date.isEmpty()) {
        output.setTextFill(Paint.valueOf("RED"));
        output.setText("Please enter the full details");
      } else {
        String query = "INSERT INTO quotes_teachers(id_user, quote, teacher, subject, date) VALUES (" + DataSource.user.getId() + ", '" + quoteText + "', '" + teacher + "', '" + subject + "', STR_TO_DATE('" + date + "', '%Y-%m-%d'));";
        try {
          statement.execute(query);
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
    String result = "";
    for (int i = 60; i < temp.length(); i += 60)
      result += temp.substring(i - 60, i) + "\n";
    return result;
  }
}
