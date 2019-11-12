/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.util.validation;

import com.connexta.security.markings.api.rest.models.ISM;
import com.connexta.security.markings.api.rest.models.SecurityMarkings;
import com.connexta.security.markings.util.StringUtils;

public class InputValidator {

  public static void validateIsm(ISM ism) {
    if (ism == null) {
      throw new IllegalArgumentException("Given ISM is null. Cannot continue.");
    }

    if (StringUtils.isEmpty(ism.getClassification())) {
      throw new IllegalArgumentException("Given Classification in ISM is null or blank. Cannot continue.");
    }

    if (StringUtils.isEmpty(ism.getOwnerProducer())) {
      throw new IllegalArgumentException("Given Owner Producer in ISM is null or blank. Cannot continue.");
    }
  }

  public static void validateBanner(String bannerMarkings) {
    if (StringUtils.isEmpty(bannerMarkings)) {
      throw new IllegalArgumentException("Given Banner is null or empty. Cannot continue.");
    }
  }

  public static void validateSecurityMarkings(SecurityMarkings securityMarkings) {
    if (securityMarkings == null) {
      throw new IllegalArgumentException("Given SecurityMarkings are null. Cannot continue.");
    }

    if (securityMarkings.getISM() == null) {
      throw new IllegalArgumentException("Given SecurityMarkings contain null ISM. Cannot continue.");
    }
  }
}
