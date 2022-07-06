package semiotic;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu extends Application {

//  @FXML
//  private Button CreateButton;
//
//  @FXML
//  private Button DeleteButton;
//
//  @FXML
//  private Button ReadButton;
//
//  @FXML
//  private Button UpdateButton;
//
//  @FXML
//  private Button logOutButton;

//  @FXML
//  private TableView<Item> tblItems;

//  @FXML
//  private TableColumn<?, ?> colQuote;
//
//  @FXML
//  private TableColumn<?, ?> colTeacher;
//
//  @FXML
//  private TableColumn<?, ?> colSubject;
//
//  @FXML
//  private TableColumn<?, ?> colDate;

  public void userLogOut() throws IOException {
    Main m = new Main();
    m.changeScene("authorization.fxml");
  }

  @Override
  public void start(Stage stage) {
    TableView<Item> table = new TableView<>();

    TableColumn<Item, String> colQuote = new TableColumn<>("Quote");
    TableColumn<Item, String> colTeacher = new TableColumn<>("Teacher");
    TableColumn<Item, Float> colSubject = new TableColumn<>("Subject");
    TableColumn<Item, Boolean> colDate = new TableColumn<>("Date");

    table.getColumns().addAll(colQuote, colTeacher, colSubject, colDate);

    table.getItems().addAll(new Item("Если ваш парень зовёт вас в ЗАГС - дайте ему семейный кодекс, если все равно зовёт - подумайте, зачем вам дебил.", "Богданов М.Р.", "Математический анализ", "18-04-2022"), new Item("Это кардиоида, от латинского слова кардио - задница.", "Богданов М.Р.", "Математический анализ", "16-03-2022"), new Item("Не знаю как рассуждать, поэтому рассуждаем как феминистки.", "Богданов М.Р.", "Математический анализ", "16-03-2022"));

    colQuote.setCellValueFactory(new PropertyValueFactory<>("Quote"));
    colTeacher.setCellValueFactory(new PropertyValueFactory<>("Teacher"));
    colSubject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
    colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));

    StackPane root = new StackPane();
    root.setPadding(new Insets(5));
    root.getChildren().add(table);

    stage.setTitle("TableView (o7planning.org)");

    Scene scene = new Scene(root, 450, 300);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
