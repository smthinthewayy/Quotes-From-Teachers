package smthinthewayy.Controller;

import javafx.fxml.FXML;
import smthinthewayy.Model.Main;
import smthinthewayy.Service.Role;
import smthinthewayy.Service.User;

/**
 * Controller - interprets the user's actions, notifying the model when changes are needed
 *
 * @author smthinthewayy
 */
public class Menu {
  /**
   * Changes the scene to an authorization window
   *
   * @see Main#changeScene(String)
   */
  @FXML
  public void moveToAuthorization() {
    Main.changeScene("/authorization.fxml");
  }

  /**
   * Changes the scene to a quotes view window and fill out the table
   *
   * @see Main#changeScene(String)
   * @see Read#filling()
   */
  @FXML
  public void moveToRead() {
    Object obj = Main.changeScene("/read.fxml");
    assert obj != null;
    ((Read) obj).filling();
  }

  /**
   * Changes the scene to the quote creation window
   *
   * @see Main#changeScene(String)
   */
  @FXML
  public void moveToCreate() {
    Main.changeScene("/create.fxml");
  }

  /**
   * Changes the scene to a window to view my quotes
   *
   * @see Main#changeScene(String)
   * @see MyQuotes#fillingOnlyMyQuotes()
   */
  @FXML
  public void moveToMyQuotes() {
    Object obj = Main.changeScene("/myQuotes.fxml");
    assert obj != null;
    ((MyQuotes) obj).fillingOnlyMyQuotes();
  }

  /**
   * Changes the scene to a profile view window
   *
   * @see Main#changeScene(String)
   * @see Profile#init()
   */
  @FXML
  public void moveToProfile() {
    Object obj = Main.changeScene("/profile.fxml");
    assert obj != null;
    ((Profile) obj).init();
  }
}
