package smthinthewayy.Service;

/**
 * Auxiliary class to store user, quote ID and auxiliary methods
 */
public class DataSource {
  public static User user;
  public static int id_quote;

  /**
   * Spreads indents by the width of the table column
   */
  public static String setMargins(String temp) {
    StringBuilder s = new StringBuilder(temp);
    for (int i = 54; i <= temp.length(); i += 54)
      s.insert(i, "\n");
    return s.toString();
  }
}
