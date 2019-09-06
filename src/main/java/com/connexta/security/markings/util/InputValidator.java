/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.util;

import static org.springframework.util.StringUtils.isEmpty;

import com.connexta.security.markings.api.rest.models.ISM;
import com.connexta.security.markings.api.rest.models.SecurityMarkings;
import com.connexta.security.markings.common.exceptions.DetailedResponseStatusException;
import org.springframework.http.HttpStatus;

public class InputValidator {

  public static void validateIsm(ISM ism) {
    if (ism == null) {
      throw new DetailedResponseStatusException(
          HttpStatus.UNPROCESSABLE_ENTITY,
          HttpStatus.UNPROCESSABLE_ENTITY.value(),
          "Given ISM is null. Cannot continue.");
    }

    if (isEmpty(ism.getClassification())) {
      throw new DetailedResponseStatusException(
          HttpStatus.UNPROCESSABLE_ENTITY,
          HttpStatus.UNPROCESSABLE_ENTITY.value(),
          "Given Classification in ISM is null or blank. Cannot continue.");
    }

    if (isEmpty(ism.getOwnerProducer())) {
      throw new DetailedResponseStatusException(
          HttpStatus.UNPROCESSABLE_ENTITY,
          HttpStatus.UNPROCESSABLE_ENTITY.value(),
          "Given Owner Producer in ISM is null or blank. Cannot continue.");
    }
  }

  public static void validateBanner(String bannerMarkings) {
    if (isEmpty(bannerMarkings)) {
      throw new DetailedResponseStatusException(
          HttpStatus.UNPROCESSABLE_ENTITY,
          HttpStatus.UNPROCESSABLE_ENTITY.value(),
          "Given Banner is null or empty. Cannot continue.");
    }
  }

  public static void validateSecurityMarkings(SecurityMarkings securityMarkings) {
    if (securityMarkings == null) {
      throw new DetailedResponseStatusException(
          HttpStatus.UNPROCESSABLE_ENTITY,
          HttpStatus.UNPROCESSABLE_ENTITY.value(),
          "Given SecurityMarkings are null. Cannot continue.");
    }

    if (securityMarkings.getISM() == null) {
      throw new DetailedResponseStatusException(
          HttpStatus.UNPROCESSABLE_ENTITY,
          HttpStatus.UNPROCESSABLE_ENTITY.value(),
          "Given SecurityMarkings contain null ISM. Cannot continue.");
    }
  }
}
