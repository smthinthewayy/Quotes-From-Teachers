package semiotic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class QFTController {

  @FXML
  private TextField loginField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Button singInButton;

  @FXML
  private Label wrongLogin;

  @FXML
  private Button singUpButton;

  @FXML
  public void userLogin(ActionEvent event) throws IOException {
    checkLogin();
  }

  private void checkLogin() throws IOException {
    Main m = new Main();
    if (loginField.getText().toString().equals("semiotic") && passwordField.getText().toString().equals("123")) {
      wrongLogin.setText("Success");
      m.changeScene("afterLogin.fxml");
    } else if (loginField.getText().isEmpty() && passwordField.getText().isEmpty()) {
      wrongLogin.setText("Please enter your data");
    } else {
      wrongLogin.setText("Wrong username or password");
    }
  }
}
