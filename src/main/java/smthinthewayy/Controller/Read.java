package smthinthewayy.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import smthinthewayy.Model.Main;
import smthinthewayy.Service.DataSource;
import smthinthewayy.Service.Item;

import java.sql.*;

public class Read {
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
    if (DataSource.user.getRole() != 0) Main.changeScene("menu.fxml");
    else Main.changeScene("authorization.fxml");
  }

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
