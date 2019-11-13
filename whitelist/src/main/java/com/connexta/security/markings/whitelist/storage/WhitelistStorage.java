package com.connexta.security.markings.whitelist.storage;

import com.connexta.security.markings.whitelist.Whitelist;
import java.util.function.Consumer;

public interface WhitelistStorage {
  Whitelist.WhitelistMap getWhitelistMap();
}
