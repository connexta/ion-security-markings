/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.service.impl;

import static org.springframework.util.StringUtils.isEmpty;

import com.connexta.security.markings.jblocks.ClassificationFactory;
import com.connexta.security.markings.rest.models.SecurityMarkings;
import com.connexta.security.markings.service.api.SecurityMarkingsService;
import com.google.common.annotations.VisibleForTesting;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import jblocks.dataheaders.classification.Classification;
import jblocks.dataheaders.classification.MarkingsDefinition;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityMarkingsServiceImpl implements SecurityMarkingsService {
  private ClassificationFactory classificationFactory;

  // system high
  private Classification systemHighClassification;

  public SecurityMarkingsServiceImpl(
      MarkingsDefinition markingsDefinition, String systemHighBannerMarkings) {

    this(markingsDefinition, systemHighBannerMarkings, null);
  }

  @VisibleForTesting
  SecurityMarkingsServiceImpl(
      MarkingsDefinition markingsDefinition,
      String systemHighBannerMarkings,
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
            () -> new ClassificationFactory(markingsDefinition, systemHighBannerMarkings));

    Optional<Classification> systemHighClassification =
        this.classificationFactory.create(systemHighBannerMarkings);

    if (systemHighClassification.isPresent()) {
      this.systemHighClassification = systemHighClassification.get();
    } else {
      String msg =
          String.format(
              "Received empty Classification when attempting to create System High Classification from configured System High Banner Markings [%s]. Cannot start up service.",
              systemHighBannerMarkings);
      log.error(msg);
      throw new IllegalArgumentException(msg);
    }
  }

  @Override
  public void validate(SecurityMarkings securityMarkings) {
    log.trace("Entering validate method.");

    inputValidation(securityMarkings);

    Optional<Classification> optionalClassification =
        classificationFactory.create(securityMarkings.getISM());

    Classification classification;
    if (optionalClassification.isPresent()) {
      classification = optionalClassification.get();
    } else {
      String msg = "Classification factory returned empty Classification. Cannot validate.";
      log.warn(msg);
      throw new RuntimeException(msg + " Check logs for more details.");
    }

    boolean coveredBySystemHigh = systemHighClassification.covers(classification);

    if (!coveredBySystemHigh) {
      String msg = "Given Security Markings are not covered by the System High Markings.";
      log.warn(msg);
      throw new RuntimeException(msg + " Check logs for more details.");
    }

    log.trace("Exiting validate method.");
  }

  private void inputValidation(SecurityMarkings securityMarkings) {
    if (securityMarkings == null) {
      throw new IllegalArgumentException("Given SecurityMarkings are null. Cannot validate.");
    }

    if (securityMarkings.getISM() == null) {
      throw new IllegalArgumentException(
          "Given SecurityMarkings contain null ISM. Cannot validate.");
    }
  }
}
