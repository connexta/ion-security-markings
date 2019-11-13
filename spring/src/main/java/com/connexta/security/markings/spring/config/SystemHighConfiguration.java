/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Slf4j
@Configuration
@PropertySource("classpath:system-high-markings.properties")
public class SystemHighConfiguration {

  // TODO it is recommended not to put @Autowired on fields. See if this can be refactored with a
  // setter.
  @Autowired private Environment env;

  @Bean
  public String systemHighBannerMarkings() {
    return env.getProperty("BannerMarkings");
  }
}
