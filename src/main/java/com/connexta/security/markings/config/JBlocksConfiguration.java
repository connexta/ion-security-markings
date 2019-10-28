/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.config;

import jblocks.dataheaders.classification.MarkingsDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JBlocksConfiguration {
  @Bean
  public MarkingsDefinition markingsDefinition() {
    // TODO eventually replace with a custom markings definition to do separate steps like validate
    // and translate
    return MarkingsDefinition.buildLatest();
  }
}
