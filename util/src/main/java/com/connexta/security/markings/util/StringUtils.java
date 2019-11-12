package com.connexta.security.markings.util;

public class StringUtils {
  public static boolean isEmpty(String string) {
    return string == null || string.trim().length() == 0;
  }
}
