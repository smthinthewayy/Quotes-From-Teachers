package smthinthewayy.Service;

import java.util.List;

/**
 * Enum, with all possible roles and their rights
 */
public enum Role {
  GUEST(
      Permission.READ_ALL_QUOTES
  ),
  STUDENT(
      Permission.CREATE,
      Permission.READ_ALL_QUOTES,
      Permission.UPDATE_ONLY_MY_QUOTES,
      Permission.DELETE_ONLY_MY_QUOTES
  ),
  VERIFIER(
      Permission.CREATE,
      Permission.READ_ALL_QUOTES,
      Permission.UPDATE_CLASSMATES_QUOTES,
      Permission.DELETE_CLASSMATES_QUOTES
  ),
  SUPERUSER(
      Permission.CREATE,
      Permission.READ_ALL_QUOTES,
      Permission.UPDATE_ALL_QUOTES,
      Permission.DELETE_ALL_QUOTES);

  /**
   * Rights list for the role
   */
  final List<Permission> permissions;

  /**
   * Constructor - creating a new object with certain values
   *
   * @param perms Infinite rights
   */
  Role(Permission... perms) {
    permissions = List.of(perms);
  }

  /**
   * Checks if there is a right in the list of rights of the role
   *
   * @param perm right
   * @return Boolean value, contains or does not contain
   */
  public boolean hasPermisson(Permission perm) {
    return permissions.contains(perm);
  }
}
