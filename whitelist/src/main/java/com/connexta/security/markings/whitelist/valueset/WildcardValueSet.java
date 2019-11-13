package com.connexta.security.markings.whitelist.valueset;

import java.util.Set;

public class WildcardValueSet implements WhitelistValueSet {
  private static final Set<String> WILDCARD_SET = Set.of("*");

  public static boolean isWildcardSet(Set<String> set) {
    return WILDCARD_SET.equals(set);
  }

  public boolean isWhitelisted(String value) {
    return true;
  }
}