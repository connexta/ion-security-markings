/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.config;

import com.connexta.security.markings.service.api.SecurityMarkingsService;
import com.connexta.security.markings.service.impl.SecurityMarkingsServiceImpl;
import jblocks.dataheaders.classification.MarkingsDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityMarkingsServiceConfiguration {

  private MarkingsDefinition markingsDefinition;
  private String systemHighBannerMarkings;

  @Bean
  public SecurityMarkingsService securityMarkingsService() {
    return new SecurityMarkingsServiceImpl(markingsDefinition, systemHighBannerMarkings);
  }

  @Autowired
  public void setMarkingsDefinition(MarkingsDefinition markingsDefinition) {
    this.markingsDefinition = markingsDefinition;
  }

  @Autowired
  public void setSystemHighBannerMarkings(String systemHighBannerMarkings) {
    this.systemHighBannerMarkings = systemHighBannerMarkings;
  }
}
