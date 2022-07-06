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

public class Menu {
  @FXML
  private Button CreateButton;

  @FXML
  private Button ReadButton;

  @FXML
  private Button DeleteButton;

  @FXML
  private Button UpdateButton;

  @FXML
  public void moveToAuthorization() {
    Main.changeScene("authorization.fxml");
  }

  @FXML
  public void moveToRead() {
    Read.filling();
    Main.changeScene("read.fxml");
  }

}
