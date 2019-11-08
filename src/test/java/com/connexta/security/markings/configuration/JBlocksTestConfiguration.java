/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.configuration;

import java.io.IOException;
import jblocks.dataheaders.classification.CaBlock;
import jblocks.dataheaders.classification.MarkingsDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
  @Autowired
  public CaBlock caBlockTestInstance(MarkingsDefinition markingsDefinition) throws IOException {
    return CaBlock.newBuilder(markingsDefinition).build();
  }
}
