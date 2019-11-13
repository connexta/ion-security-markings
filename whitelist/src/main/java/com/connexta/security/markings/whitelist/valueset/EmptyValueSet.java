package com.connexta.security.markings.whitelist.valueset;

import java.util.Set;

public class EmptyValueSet implements WhitelistValueSet {
  private static final Set<String> EMPTY_SET = Set.of("");

  public static boolean isEmptySet(Set<String> set) {
    return EMPTY_SET.equals(set);
  }

  public boolean isWhitelisted(String value) {
      return false;
    }
}
