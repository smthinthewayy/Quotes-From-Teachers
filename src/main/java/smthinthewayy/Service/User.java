package smthinthewayy.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
  private int id = 0;
  private String login = "";
  private String hashPassword = "";
  private String studyGroup = "";
  private Role role = Role.STUDENT;

  public User(int id, String login, String studyGroup, String hashPassword, Role role) {
    this.id = id;
    this.login = login;
    this.studyGroup = studyGroup;
    this.hashPassword = hashPassword;
    this.role = role;
  }

  public boolean hasPermisson(Permission perm) {
    return role.hasPermisson(perm);
  }

  public int getId() {
    return id;
  }

  public String getLogin() {
    return login;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getStudyGroup() { return studyGroup; }

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

  public static Role initRole(ResultSet result) {
    Role role = Role.GUEST;
    try {
      if (result.getInt("role") == 1) role = Role.STUDENT;
      if (result.getInt("role") == 2) role = Role.VERIFIER;
      if (result.getInt("role") == 3) role = Role.SUPERUSER;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return role;
  }
}