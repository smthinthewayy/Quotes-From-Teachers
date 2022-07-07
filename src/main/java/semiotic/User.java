package semiotic;

import java.math.BigInteger;
import java.security.MessageDigest;

public class User {
  private final int id;
  private String login;
  private final String studyGroup;
  private final String hashPassword;
  private int role;

  public User(int id, String login, String studyGroup, String hashPassword, int role) {
    this.id = id;
    this.login = login;
    this.studyGroup = studyGroup;
    this.hashPassword = hashPassword;
    this.role = role;
  }

  public int getId() {
    return id;
  }

  public String getStudyGroup() {
    return studyGroup;
  }

  public int getRole() {
    return role;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public void setRole(int role) {
    this.role = role;
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
