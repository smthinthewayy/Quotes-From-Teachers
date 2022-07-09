package smthinthewayy.Controller;

import javafx.fxml.FXML;
import smthinthewayy.Model.Main;


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

  @FXML
  public void moveToMyQuotes() {
    Object obj = Main.changeScene("myQuotes.fxml");
    assert obj != null;
    ((MyQuotes) obj).fillingOnlyMyQuotes();
  }

  @FXML
  public void moveToProfile() {
    Object obj = Main.changeScene("profile.fxml");
    assert obj != null;
    ((Profile) obj).init();
  }
}
