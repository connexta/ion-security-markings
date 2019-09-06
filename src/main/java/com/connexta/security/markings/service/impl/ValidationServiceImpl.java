/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.service.impl;

import static org.springframework.util.StringUtils.isEmpty;

import com.connexta.security.markings.api.rest.models.SecurityMarkings;
import com.connexta.security.markings.banner.BannerCreator;
import com.connexta.security.markings.common.exceptions.DetailedResponseStatusException;
import com.connexta.security.markings.jblocks.ClassificationFactory;
import com.connexta.security.markings.service.api.ValidationService;
import com.connexta.security.markings.util.InputValidator;
import com.google.common.annotations.VisibleForTesting;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import jblocks.dataheaders.classification.Classification;
import jblocks.dataheaders.classification.MarkingsDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class ValidationServiceImpl implements ValidationService {
  private ClassificationFactory classificationFactory;

  // system high
  private Classification systemHighClassification;

  public ValidationServiceImpl(
      MarkingsDefinition markingsDefinition,
      String systemHighBannerMarkings,
      BannerCreator bannerCreator) {

    this(markingsDefinition, systemHighBannerMarkings, bannerCreator, null);
  }

  @VisibleForTesting
  ValidationServiceImpl(
      MarkingsDefinition markingsDefinition,
      String systemHighBannerMarkings,
      BannerCreator bannerCreator,
      @Nullable ClassificationFactory classificationFactory) {
    if (markingsDefinition == null) {
      String msg = "Given MarkingsDefinition is null. Cannot start up service.";
      log.error(msg);
      throw new IllegalArgumentException(msg);
    }

    if (isEmpty(systemHighBannerMarkings)) {
      String msg = "Given System High Banner Markings is null or blank. Cannot start up service.";
      log.error(msg);
      throw new IllegalArgumentException(msg);
    }

    this.classificationFactory =
        Objects.requireNonNullElseGet(
            classificationFactory,
            () ->
                new ClassificationFactory(
                    markingsDefinition, systemHighBannerMarkings, bannerCreator));

    Optional<Classification> systemHighClassification =
        this.classificationFactory.create(systemHighBannerMarkings);

    if (systemHighClassification.isPresent()) {
      this.systemHighClassification = systemHighClassification.get();
    } else {
      String msg =
          String.format(
              "Received empty Classification when attempting to create System High Classification from configured System High Banner Markings [%s]. Cannot start up validation service.",
              systemHighBannerMarkings);
      log.error(msg);
      throw new IllegalArgumentException(msg);
    }
  }

  @Override
  public void validate(SecurityMarkings securityMarkings) {
    log.trace("Entering validate method.");

    log.trace("Input validation on Security Markings.");
    InputValidator.validateSecurityMarkings(securityMarkings);

    log.trace("Create Classification from given Security Markings.");
    Optional<Classification> optionalClassification =
        classificationFactory.create(securityMarkings.getISM());

    Classification classification;
    if (optionalClassification.isPresent()) {
      classification = optionalClassification.get();
    } else {
      String msg =
          "Classification factory returned empty Classification from given Security Markings. Validation failed.";
      log.warn(msg);
      throw new DetailedResponseStatusException(
          HttpStatus.UNPROCESSABLE_ENTITY, HttpStatus.UNPROCESSABLE_ENTITY.value(), msg);
    }

    log.trace("Check if given Security Markings are covered by System High Markings.");
    if (!systemHighClassification.covers(classification)) {
      String msg =
          "Given Security Markings are not covered by System High Markings. Validation failed.";
      log.info(msg);
      throw new DetailedResponseStatusException(
          HttpStatus.UNPROCESSABLE_ENTITY, HttpStatus.UNPROCESSABLE_ENTITY.value(), msg);
    }

    log.trace("Exiting validate method.");
  }
}
