/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.config;

import com.connexta.security.markings.banner.BannerCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BannerCreatorConfiguration {
  @Bean
  public BannerCreator bannerCreator() {
    return new BannerCreator();
  }
}
