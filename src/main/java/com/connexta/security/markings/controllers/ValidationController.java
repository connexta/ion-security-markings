/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.controllers;

import com.connexta.security.markings.rest.models.ErrorMessage;
import com.connexta.security.markings.rest.models.SecurityMarkings;
import com.connexta.security.markings.rest.spring.ValidateApi;
import com.connexta.security.markings.service.api.SecurityMarkingsService;
import java.time.OffsetDateTime;
import jblocks.dataheaders.classification.MarkingsDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@SpringBootApplication
public class ValidationController implements ValidateApi {
  private MarkingsDefinition markingsDefinition;
  private SecurityMarkingsService securityMarkingsService;

  @Autowired
  ValidationController(SecurityMarkingsService securityMarkingsService) {
    this.securityMarkingsService = securityMarkingsService;
  }

  @Override
  public ResponseEntity<ErrorMessage> validate(
      String acceptVersion, SecurityMarkings securityMarkings) {
    log.debug("Validate request received. Security markings: {}.", securityMarkings);

    try {
      securityMarkingsService.validate(securityMarkings);
    } catch (IllegalArgumentException e) {
      log.error("Problem with request input. Returning \"Bad Request\" error response.", e);
      ErrorMessage errorMessage = new ErrorMessage();
      errorMessage.setCode(HttpStatus.BAD_REQUEST.value());
      errorMessage.setError("Problem with request. See logs for more details.");
      errorMessage.setMessage(e.getMessage());
      errorMessage.setPath("/validation/validate");
      errorMessage.setStatus(HttpStatus.BAD_REQUEST.value());
      errorMessage.setTimestamp(OffsetDateTime.now());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    } catch (Exception e) { // TODO make exception more specific
      log.error(
          "Problem validating security markings. Returning \"Internal Server Error\" error response.",
          e);
      ErrorMessage errorMessage = new ErrorMessage();
      errorMessage.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
      errorMessage.setError(
          "Exception occurred while validating markings. See logs for more details.");
      errorMessage.setMessage(e.getMessage());
      errorMessage.setPath("/validation/validate");
      errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      errorMessage.setTimestamp(OffsetDateTime.now());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    return ResponseEntity.noContent().build();
  }
}
