package semiotic;

import javafx.fxml.FXML;


public class Menu {
  @FXML
  public void moveToAuthorization() {
    Main.changeScene("authorization.fxml");
  }

  @FXML
  public void moveToRead() {
    Object obj = Main.changeScene("read.fxml");
    assert obj != null;
    ((Read) obj).filling();
  }

  @FXML
  public void moveToCreate() {
    Main.changeScene("create.fxml");
  }
}
