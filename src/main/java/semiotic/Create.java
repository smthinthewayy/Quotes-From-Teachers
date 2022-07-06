package semiotic;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;

import java.sql.Connection;
import java.sql.Date;
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
  private TextField dateTextField;

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

      String quoteText = quoteTextArea.getText();
      String teacher = teacherTextField.getText();
      String subject = subjectTextField.getText();
      Date date = Date.valueOf(dateTextField.getText());


      if (quoteText.isEmpty() || teacher.isEmpty() || subject.isEmpty() || dateTextField.getText().isEmpty()) {
        output.setTextFill(Paint.valueOf("RED"));
        output.setText("Please enter the full details");
      } else {
        String query = "INSERT INTO quotes_teachers VALUES (8, 1, '" + quoteText + "', '" + teacher + "', '" + subject + "', STR_TO_DATE('" + date + "', '%Y-%m-%d'));";
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
}
