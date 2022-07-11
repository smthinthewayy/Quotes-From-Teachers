package smthinthewayy.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Responsible for representing the user.
 */
public class User {
  /**
   * User id
   */
  private int id;

  /**
   * User login
   */
  private String login;

  /**
   * User password hash
   */
  private String hashPassword;

  /**
   * User study group
   */
  private String studyGroup;

  /**
   * Role of the user
   */
  private Role role;

  /**
   * Constructor - creating a new object with certain values
   *
   * @param id           user id
   * @param login        user login
   * @param studyGroup   user study group
   * @param hashPassword user password hash
   * @param role         user role
   */
  public User(int id, String login, String studyGroup, String hashPassword, Role role) {
    this.id = id;
    this.login = login;
    this.studyGroup = studyGroup;
    this.hashPassword = hashPassword;
    this.role = role;
  }

  /**
   * Checks if the user has the right
   *
   * @param perm right
   * @return Boolean value, has or has not
   */
  public boolean hasPermisson(Permission perm) {
    return role.hasPermisson(perm);
  }

  /**
   * Getter for user id
   *
   * @return user id
   */
  public int getId() {
    return id;
  }

  /**
   * Getter for user login
   *
   * @return user login
   */
  public String getLogin() {
    return login;
  }

  /**
   * Getter for the user role
   *
   * @return user role
   */
  public Role getRole() {
    return role;
  }

  /**
   * Setter for the user role
   */
  public void setRole(Role role) {
    this.role = role;
  }

  /**
   * Getter for the user study group
   *
   * @return user study group
   */
  public String getStudyGroup() {
    return studyGroup;
  }

  /**
   * Password hashing function, based on MD5 algorithm
   *
   * @param password user password
   * @return password hash
   */
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

  /**
   * Determines the role by the number
   *
   * @param result SQL-query result
   * @return Role type object
   */
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