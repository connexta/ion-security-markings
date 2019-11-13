package com.connexta.security.markings.spring.whitelist.storage;

import com.connexta.security.markings.api.util.IsmConstants;
import com.connexta.security.markings.util.IsmUtils;
import com.connexta.security.markings.whitelist.Whitelist;
import com.connexta.security.markings.whitelist.storage.WhitelistStorage;
import com.connexta.security.markings.whitelist.valueset.EmptyValueSet;
import com.connexta.security.markings.whitelist.valueset.StringValueSet;
import com.connexta.security.markings.whitelist.valueset.WildcardValueSet;
import com.google.common.annotations.VisibleForTesting;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

@Slf4j
public class SpringWhitelistStorage implements WhitelistStorage {
  private static final String WHITELIST_PREFIX = "whitelist.";

  private Environment env;
  private Whitelist.WhitelistMap whitelistMap;

  public SpringWhitelistStorage(Environment env) {
    this(env, IsmConstants.ISM_ATTRIBUTE_SET);
  }

  @VisibleForTesting
  SpringWhitelistStorage(Environment env, Set<String> ismAttributeSet) {
    this.env = env;

    whitelistMap = createWhitelistMap(ismAttributeSet);
  }

  private Whitelist.WhitelistMap createWhitelistMap(Set<String> ismAttributeSet) {
    Whitelist.WhitelistMap whitelistMap = new Whitelist.WhitelistMap();

    // iterate through the ism attribute names and find the corresponding entries in the Environment
    for (String ismAttribute : ismAttributeSet) {
      String whitelistAttribute = WHITELIST_PREFIX + ismAttribute;

      Set<String> whitelistedValues = new HashSet<>();

      // check properties file for the .values entry
      if (env.containsProperty(whitelistAttribute)) {
        whitelistedValues = Set.of(env.getProperty(whitelistAttribute).split(","));
        whitelistedValues = whitelistedValues.stream().map(String::trim).collect(Collectors.toSet());
        log.info("Set of whitelisted values for the \"{}\" ISM attribute: {}", ismAttribute, whitelistedValues);
      }

      if (!whitelistedValues.isEmpty()) {
        if (WildcardValueSet.isWildcardSet(whitelistedValues)) {
          log.debug("Adding wildcard value set to whitelist map under key {}", ismAttribute);
          whitelistMap.put(ismAttribute, new WildcardValueSet());
        } else if (EmptyValueSet.isEmptySet(whitelistedValues)) {
          log.debug("Adding empty value set to whitelist map under key {}", ismAttribute);
          whitelistMap.put(ismAttribute, new EmptyValueSet());
        } else {
          log.debug("Adding set of values to whitelist map under key {}: {}", ismAttribute, whitelistedValues);
          whitelistMap.put(ismAttribute, new StringValueSet(whitelistedValues));
        }
      }
    }

    log.info("Created whitelist map: {}.", whitelistMap);
    return whitelistMap;
  }

  @Override
  public Whitelist.WhitelistMap getWhitelistMap() {
   return whitelistMap;
  }
}
