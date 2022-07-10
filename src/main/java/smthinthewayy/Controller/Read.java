package smthinthewayy.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import smthinthewayy.Model.Main;
import smthinthewayy.Service.DataSource;
import smthinthewayy.Service.Item;
import smthinthewayy.Service.Permission;
import smthinthewayy.Service.Role;

import java.sql.*;

/**
 * Controller - interprets the user's actions, notifying the model when changes are needed
 *
 * @author smthinthewayy
 */
public class Read {
  /**
   * Table for displaying quotes
   */
  @FXML
  private TableView<Item> tableQuotes;

  /**
   * Table column to display the text of the quote
   */
  @FXML
  private TableColumn<Item, String> columnQuote;

  /**
   * Table column to display the date
   */
  @FXML
  private TableColumn<Item, String> columnDate;

  /**
   * Table column to display the subject name
   */
  @FXML
  private TableColumn<Item, String> columnSubject;

  /**
   * Table column to display the teacher's name
   */
  @FXML
  private TableColumn<Item, String> columnTeacher;

  /**
   * Changes the scene to the authorization window if
   * the current user has the role of GUEST, otherwise
   * to the main menu window
   *
   * @see Main#changeScene(String)
   */
  @FXML
  public void moveToMenu() {
    if (DataSource.user.getRole() == Role.GUEST) Main.changeScene("/authorization.fxml");
    else Main.changeScene("/menu.fxml");
  }

  /**
   * Establishes a connection to the database, sends a
   * parameterized SQL-query, fills the table with data
   * from the SQL-query
   *
   * @see Item
   */
  public void filling() {
    try {
      Connection connection = Main.createConnection();

      String query = "SELECT * FROM quotes_teachers";
      PreparedStatement statement = connection.prepareStatement(query);

      ResultSet result = statement.executeQuery();

      while (result.next()) {
        String quote = result.getString("quote");
        String teacher = result.getString("teacher");
        String subject = result.getString("subject");
        Date date = result.getDate("date");

        tableQuotes.getItems().addAll(new Item(quote, teacher, subject, date));
      }

      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    columnQuote.setCellValueFactory(new PropertyValueFactory<>("Quote"));
    columnTeacher.setCellValueFactory(new PropertyValueFactory<>("Teacher"));
    columnSubject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
    columnDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
  }
}
