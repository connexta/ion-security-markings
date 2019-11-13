/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.spring.config;

import com.connexta.security.markings.api.openapi.models.ISM;
import com.connexta.security.markings.api.util.IsmConstants;
import com.connexta.security.markings.util.IsmUtils;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Slf4j
@Configuration
@PropertySource("classpath:system-high-ism.properties")
public class SystemHighConfiguration {
  private static String SYSTEM_HIGH_PREFIX = "system.";

  @Autowired private Environment env;

  @Bean
  public ISM systemHighIsm() {
    Map<String, String> ismMap = new HashMap<>();

    for (String ismAttribute : IsmConstants.ISM_ATTRIBUTE_SET) {
      String systemHighAttribute = SYSTEM_HIGH_PREFIX + ismAttribute;

      if (env.containsProperty(systemHighAttribute)) {
        ismMap.put(ismAttribute, env.getProperty(systemHighAttribute));
      }
    }

    return IsmUtils.createIsmFromMap(ismMap);
  }
}
