/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.config;

import java.io.IOException;
import java.io.InputStream;
import jblocks.dataheaders.classification.MarkingsDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:system-high-markings.properties")
public class JBlocksConfiguration {
  @Bean
  public MarkingsDefinition markingsDefinition() throws IOException {
    InputStream inputStream =
        this.getClass().getClassLoader().getResourceAsStream("markings-definition-extension.txt");

    // TODO eventually replace with a custom markings definition to do separate steps like validate
    // and translate
    MarkingsDefinition.Builder markingsDefinitionBuilder = MarkingsDefinition.loadLatest();
    markingsDefinitionBuilder.parse(inputStream);

    return markingsDefinitionBuilder.build();
  }
}
