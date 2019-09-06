/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.util;

import static org.springframework.util.StringUtils.isEmpty;

import com.connexta.security.markings.rest.models.ISM;

public class InputValidator {

  public static void validateIsm(ISM ism) {
    if (ism == null) {
      throw new IllegalArgumentException("Given ISM was null. Cannot continue.");
    }

    if (isEmpty(ism.getClassification())) {
      throw new IllegalArgumentException(
          "Given Classification in ISM was null or blank. Cannot continue.");
    }

    if (isEmpty(ism.getOwnerProducer())) {
      throw new IllegalArgumentException(
          "Given Owner Producer in ISM was null or blank. Cannot continue.");
    }
  }

  public static void validateBanner(String bannerMarkings) {
    if (isEmpty(bannerMarkings)) {
      throw new IllegalArgumentException("Given Banner was null or empty. Cannot continue.");
    }
  }
}
