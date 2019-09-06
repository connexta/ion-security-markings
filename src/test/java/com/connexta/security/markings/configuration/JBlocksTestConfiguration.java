/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.configuration;

import jblocks.dataheaders.classification.CaBlock;
import jblocks.dataheaders.classification.MarkingsDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestConfiguration
@Slf4j
public class JBlocksTestConfiguration {
  @Bean
  public MarkingsDefinition markingsDefinitionTestInstance() {
    return MarkingsDefinition.buildLatest();
  }

  @Bean
  public CaBlock caBlockTestInstance() {
    return CaBlock.newBuilder(markingsDefinitionTestInstance()).build();
  }
}
