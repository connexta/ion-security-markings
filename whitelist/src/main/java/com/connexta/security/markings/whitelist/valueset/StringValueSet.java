package com.connexta.security.markings.whitelist.valueset;

import java.util.Set;

public class StringValueSet implements WhitelistValueSet {
  protected Set<String> values;

  public StringValueSet(Set<String> values) {
    this.values = values;
  }

  public boolean isWhitelisted(String value) {
    return containsIgnoreCase(values, value);
  }

  private boolean containsIgnoreCase(Set<String> values, String value) {
    for (String string : values) {
      if (string.equalsIgnoreCase(value)) {
        return true;
      }
    }
    return false;
  }
}
