package semiotic;

import java.io.IOException;

public class AfterLogin {
  public void userLogOut() throws IOException {
    Main m = new Main();
    m.changeScene("authorization.fxml");
  }
}
