package semiotic;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.*;

public class Read {
  @FXML
  private static TableView<Item> table;

  @FXML
  private static TableColumn<Item, String> columnQoute;

  @FXML
  private static TableColumn<Item, String> columnDate;

  @FXML
  private static TableColumn<Item, String> columnSubject;

  @FXML
  private static TableColumn<Item, String> columnTeacher;

  public static void filling() {
    try {
      table = new TableView<>();
      Connection connection = Main.createConnection();
      Statement statement = connection.createStatement();

      String query = "SELECT * FROM quotes_teachers";
      ResultSet result = statement.executeQuery(query);

      while (result.next()) {
        String quote = result.getString("quote");
        String teacher = result.getString("teacher");
        String subject = result.getString("subject");
        Date date = result.getDate("date");

        table.getItems().addAll(new Item(quote, teacher, subject, date));

//        System.out.print(", quote = \"" + quote + "\"");
//        System.out.print(", teacher = \"" + teacher + "\"");
//        System.out.print(", subject = \"" + subject + "\".");
//        System.out.println(", date = " + date);
      }

      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
