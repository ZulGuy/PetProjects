package com.studying.fintrack.domain.utils;

import com.studying.fintrack.domain.entities.User;

public class AuthoritiesUtil {

  public static boolean isAdmin(User user) {
    return user.getAuthorities().contains("Admin");
  }

}
