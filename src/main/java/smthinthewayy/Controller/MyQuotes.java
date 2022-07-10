package smthinthewayy.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import smthinthewayy.Model.Main;
import smthinthewayy.Service.DataSource;
import smthinthewayy.Service.Item;
import smthinthewayy.Service.Role;
import smthinthewayy.Service.User;

import java.sql.*;

/**
 * Controller - interprets the user's actions, notifying the model when changes are needed
 *
 * @author smthinthewayy
 */
public class MyQuotes {
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
   * Changes the scene to the main menu window
   *
   * @see Main#changeScene(String)
   */
  @FXML
  public void moveToMenu() {
    Main.changeScene("/menu.fxml");
  }

  /**
   * Establishes a connection to the database and fills the table only:
   * - quotes of the current user if the user has the <b>STUDENT</b> role;
   * - quotes from a user group if the current user has the <b>VERIFIER</b> role
   * - all quotes of all users if the current user has the <b>SUPERUSER</b> role
   *
   * @see User#getRole()
   * @see Item
   */
  public void fillingOnlyMyQuotes() {
    try {
      Connection connection = Main.createConnection();

      String query;
      PreparedStatement statement = null;

      if (DataSource.user.getRole().equals(Role.STUDENT)) {
        query = "SELECT * FROM quotes_teachers WHERE id_user = ?;";
        statement = connection.prepareStatement(query);
        statement.setInt(1, DataSource.user.getId());
      } else if (DataSource.user.getRole().equals(Role.VERIFIER)) {
        query = "SELECT * FROM quotes_teachers WHERE id_user IN (SELECT id FROM users WHERE study_group = ?);";
        statement = connection.prepareStatement(query);
        statement.setString(1, DataSource.user.getStudyGroup());
      } else if (DataSource.user.getRole().equals(Role.SUPERUSER)) {
        query = "SELECT * FROM quotes_teachers";
        statement = connection.prepareStatement(query);
      }

      assert statement != null;
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

  /**
   * Establishes a connection to the database and sends a
   * parameterized SQL query to change the currently selected quote
   *
   * @see Item
   * @see Update#init(Item)
   */
  @FXML
  public void botUpdate() {
    Item item = tableQuotes.getSelectionModel().getSelectedItem();

    try {
      Connection connection = Main.createConnection();

      String query = "SELECT id FROM quotes_teachers WHERE (quote = ?) AND (teacher = ?) AND (subject = ?) AND (date = ?);";
      PreparedStatement statement = connection.prepareStatement(query);

      statement.setString(1, item.getQuote());
      statement.setString(2, item.getTeacher());
      statement.setString(3, item.getSubject());
      statement.setDate(4, item.getDate());

      ResultSet result = statement.executeQuery();

      while (result.next()) DataSource.id_quote = result.getInt("id");

      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    Object obj = Main.changeScene("/update.fxml");
    assert obj != null;
    ((Update) obj).init(item);
  }

  /**
   * Establishes a connection to the database and sends a
   * parameterized SQL query to delete the currently selected quote
   *
   * @see Item
   * @see MyQuotes#fillingOnlyMyQuotes()
   */
  @FXML
  public void deleteQuote() {
    Item item = tableQuotes.getSelectionModel().getSelectedItem();

    try {
      Connection connection = Main.createConnection();

      String query = "DELETE FROM quotes_teachers WHERE (quote = ?) AND (teacher = ?) AND (subject = ?) AND (date = ?);";
      PreparedStatement statement = connection.prepareStatement(query);

      statement.setString(1, item.getQuote());
      statement.setString(2, item.getTeacher());
      statement.setString(3, item.getSubject());
      statement.setDate(4, item.getDate());

      try {
        statement.execute();
      } catch (SQLIntegrityConstraintViolationException e) {
        e.printStackTrace();
      }

      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    columnQuote.setCellValueFactory(new PropertyValueFactory<>("Quote"));
    columnTeacher.setCellValueFactory(new PropertyValueFactory<>("Teacher"));
    columnSubject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
    columnDate.setCellValueFactory(new PropertyValueFactory<>("Date"));

    Object obj = Main.changeScene("/myQuotes.fxml");
    assert obj != null;
    ((MyQuotes) obj).fillingOnlyMyQuotes();
  }
}
