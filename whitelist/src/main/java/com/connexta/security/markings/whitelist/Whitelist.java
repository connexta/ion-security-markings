package com.connexta.security.markings.whitelist;

import com.connexta.security.markings.container.MarkingsContainer;
import com.connexta.security.markings.whitelist.storage.WhitelistStorage;
import com.connexta.security.markings.whitelist.valueset.WhitelistValueSet;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class Whitelist {
  private WhitelistStorage whitelistStorage;
  private WhitelistMap whitelistMap;

  public Whitelist(WhitelistStorage whitelistStorage) {
    if (whitelistStorage == null) {
      throw new IllegalArgumentException("Whitelist constructor received null WhitelistStorage. Cannot create Whitelist.");
    }
    this.whitelistStorage = whitelistStorage;
    setWhitelistMap(whitelistStorage.getWhitelistMap());
  }

  public @Nullable Map<String, String> getNonWhitelistedMarkings(MarkingsContainer markingsContainer) {
    if (markingsContainer == null) {
      throw new IllegalArgumentException("Whitelist received null MarkingsContainer.");
    }

    Map<String, String> ismMap = markingsContainer.getCorrectedIsmMap();

    if (whitelistMap == null || whitelistMap.isEmpty()) {
      return ismMap;
    }

    return ismMap.entrySet()
        .stream()
        .filter(entry -> !isWhitelisted(entry.getKey(), entry.getValue()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  private boolean isWhitelisted(String key, String value) {
    WhitelistValueSet valueSet = whitelistMap.get(key);

    return valueSet != null && valueSet.isWhitelisted(value);
  }

  public void setWhitelistMap(WhitelistMap whitelistMap) {
    this.whitelistMap = whitelistMap;
  }

  public static class WhitelistMap extends HashMap<String, WhitelistValueSet> {}
}
