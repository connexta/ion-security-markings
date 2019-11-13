package com.connexta.security.markings.spring.config;

import com.connexta.security.markings.spring.whitelist.storage.SpringWhitelistStorage;
import com.connexta.security.markings.whitelist.Whitelist;
import com.connexta.security.markings.whitelist.storage.WhitelistStorage;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:whitelist.properties")
public class WhitelistConfiguration {
  @Autowired
  private Environment env;

  @Bean public WhitelistStorage whitelistStorage() throws IOException {
    return new SpringWhitelistStorage(env);
  }

  @Bean public Whitelist whitelist() throws IOException {
    return new Whitelist(whitelistStorage());
  }
}
