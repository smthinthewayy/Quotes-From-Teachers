package semiotic;

import java.math.BigInteger;
import java.security.MessageDigest;

public class User {
  private String login;
  private String hashPassword;

  public void setLogin(String login) {
    this.login = login;
  }

  public String getLogin() {
    return login;
  }

  public String getHashPassword() {
    return hashPassword;
  }

  public static String makeMD5(String password) {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("MD5");
      md.update(password.getBytes());
      String pwd = new BigInteger(1, md.digest()).toString(16);
      return pwd.toUpperCase();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return password;
  }
}
