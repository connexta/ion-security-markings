/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.spring.service;

import com.connexta.security.markings.api.openapi.models.ISM;
import com.connexta.security.markings.container.MarkingsContainer;
import com.connexta.security.markings.container.factory.MarkingsContainerFactory;
import com.connexta.security.markings.spring.exceptions.DetailedResponseStatusException;
import com.connexta.security.markings.whitelist.Whitelist;
import com.google.common.annotations.VisibleForTesting;
import java.util.Map;
import jblocks.dataheaders.classification.MarkingsDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class ValidationService {
  private MarkingsContainerFactory markingsContainerFactory;
  private Whitelist whitelist;
  private MarkingsContainer systemHighMarkings;

  public ValidationService(
      MarkingsDefinition markingsDefinition,
      ISM systemHighIsm,
      Whitelist whitelist) {

    this(markingsDefinition, systemHighIsm, whitelist, new MarkingsContainerFactory(markingsDefinition));
  }

  @VisibleForTesting
  ValidationService(
      MarkingsDefinition markingsDefinition,
      ISM systemHighIsm,
      Whitelist whitelist,
      MarkingsContainerFactory markingsContainerFactory) {
    if (markingsDefinition == null) {
      String msg = "Given MarkingsDefinition is null. Cannot start up service.";
      log.error(msg);
      throw new IllegalArgumentException(msg);
    }

    if (systemHighIsm == null) {
      String msg = "Given System High ISM is null. Cannot start up service.";
      log.error(msg);
      throw new IllegalArgumentException(msg);
    }

    if (whitelist == null) {
      String msg = "Given Whitelist is null. Cannot start up service.";
      log.error(msg);
      throw new IllegalArgumentException(msg);
    }

    if (markingsContainerFactory == null) {
      String msg = "Given MarkingsContainerFactory is null. Cannot start up service.";
      log.error(msg);
      throw new IllegalArgumentException(msg);
    }

    this.markingsContainerFactory = markingsContainerFactory;
    this.systemHighMarkings = markingsContainerFactory.create(systemHighIsm);
    this.whitelist = whitelist;

    // do this here in order to catch system high correction errors in the constructor
    systemHighMarkings.getCorrectedIsm();
  }

  public void validate(ISM ism) {
    log.trace("Entering validate method.");

    log.trace("Create MarkingsContainer from given ISM.");
    MarkingsContainer markingsContainer;
    try {
      markingsContainer = markingsContainerFactory.create(ism);
    } catch (Exception e) { // TODO make exception more specific
      String msg = "Problem creating MarkingsContainer from given ISM. Validation failed.";
      log.error(msg);
      throw new DetailedResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, HttpStatus.UNPROCESSABLE_ENTITY.value(), msg, e);
    }

    log.trace("Check if given ISM is covered by System High Markings.");
    if (!systemHighMarkings.getClassification().covers(markingsContainer.getClassification())) {
      String msg =
          "Given ISM is not covered by System High. Validation failed.";
      log.info(msg);
      throw new DetailedResponseStatusException(
          HttpStatus.UNPROCESSABLE_ENTITY, HttpStatus.UNPROCESSABLE_ENTITY.value(), msg);
    }

    log.trace("Check if given ISM is whitelisted.");
    Map<String, String> whitelistErrorMap = whitelist.getNonWhitelistedMarkings(markingsContainer);
    boolean isWhitelisted = whitelistErrorMap == null || whitelistErrorMap.isEmpty();
    if (!isWhitelisted) {
      String msg = createWhitelistErrorMessage(whitelistErrorMap);
      log.warn(msg);
      throw new DetailedResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, HttpStatus.UNPROCESSABLE_ENTITY.value(), msg);
    }

    log.trace("Exiting validate method.");
  }

  private String createWhitelistErrorMessage(Map<String, String> whitelistErrorMap) {
    final StringBuilder builder = new StringBuilder("Given ISM attribute(s) contained a non-whitelisted value:\n");

    whitelistErrorMap.forEach((ismAttribute, value) -> builder.append(String.format("\t\t%s: %s\n", ismAttribute, value)));

    builder.append("\tValidation failed.");

    return builder.toString();
  }
}
