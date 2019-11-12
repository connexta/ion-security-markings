/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.spring.config;

import com.connexta.security.markings.spring.service.api.ValidationService;
import com.connexta.security.markings.spring.service.impl.ValidationServiceImpl;
import com.connexta.security.markings.util.banner.BannerCreator;
import jblocks.dataheaders.classification.MarkingsDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityMarkingsServiceConfiguration {

  private MarkingsDefinition markingsDefinition;
  private String systemHighBannerMarkings;
  private BannerCreator bannerCreator;

  @Bean
  public ValidationService securityMarkingsService() {
    return new ValidationServiceImpl(markingsDefinition, systemHighBannerMarkings, bannerCreator);
  }

  @Autowired
  public void setMarkingsDefinition(MarkingsDefinition markingsDefinition) {
    this.markingsDefinition = markingsDefinition;
  }

  @Autowired
  public void setSystemHighBannerMarkings(String systemHighBannerMarkings) {
    this.systemHighBannerMarkings = systemHighBannerMarkings;
  }

  @Autowired
  public void setBannerCreator(BannerCreator bannerCreator) {
    this.bannerCreator = bannerCreator;
  }
}
