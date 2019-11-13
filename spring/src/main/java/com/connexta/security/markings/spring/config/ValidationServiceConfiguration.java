/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.spring.config;

import com.connexta.security.markings.api.openapi.models.ISM;
import com.connexta.security.markings.spring.service.ValidationService;
import com.connexta.security.markings.whitelist.Whitelist;
import jblocks.dataheaders.classification.MarkingsDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationServiceConfiguration {

  @Autowired private MarkingsDefinition markingsDefinition;
  @Autowired private ISM systemHighIsm;
  @Autowired private Whitelist whitelist;

  @Bean
  public ValidationService securityMarkingsService() {
    return new ValidationService(markingsDefinition, systemHighIsm, whitelist);
  }
}
