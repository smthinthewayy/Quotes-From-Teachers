package semiotic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class AfterLogin {

  @FXML
  private Button logOutButton;

  public void userLogOut(ActionEvent event) throws IOException {
    Main m = new Main();
    m.changeScene("sample.fxml");
  }
}
