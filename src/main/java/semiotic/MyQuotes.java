package semiotic;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class MyQuotes {
  @FXML
  private TableView<Item> tableQuotes;

  @FXML
  private TableColumn<Item, String> columnQuote;

  @FXML
  private TableColumn<Item, String> columnDate;

  @FXML
  private TableColumn<Item, String> columnSubject;

  @FXML
  private TableColumn<Item, String> columnTeacher;

  @FXML
  public void moveToMenu() {
    Main.changeScene("menu.fxml");
  }

  public void fillingOnlyMyQuotes() {
    try {
      Connection connection = Main.createConnection();

      String query = "SELECT * FROM quotes_teachers WHERE id_user = ?;";
      PreparedStatement statement = connection.prepareStatement(query);

      statement.setInt(1, DataSource.user.getId());

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

    Object obj = Main.changeScene("update.fxml");
    assert obj != null;
    ((Update) obj).init(item);
  }

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
        System.out.println(e);
      }

      connection.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    columnQuote.setCellValueFactory(new PropertyValueFactory<>("Quote"));
    columnTeacher.setCellValueFactory(new PropertyValueFactory<>("Teacher"));
    columnSubject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
    columnDate.setCellValueFactory(new PropertyValueFactory<>("Date"));

    Object obj = Main.changeScene("myQuotes.fxml");
    assert obj != null;
    ((MyQuotes) obj).fillingOnlyMyQuotes();
  }
}
