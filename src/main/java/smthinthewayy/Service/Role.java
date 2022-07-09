package smthinthewayy.Service;

import java.util.List;

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

  final List<Permission> permissions;

  Role(Permission... perms) {
    permissions = List.of(perms);
  }

  public boolean hasPermisson(Permission perm) {
    return permissions.contains(perm);
  }
}
