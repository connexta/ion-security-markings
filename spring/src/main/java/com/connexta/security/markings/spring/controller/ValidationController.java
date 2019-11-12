/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.spring.controller;

import com.connexta.security.markings.api.rest.models.ErrorMessage;
import com.connexta.security.markings.api.rest.models.SecurityMarkings;
import com.connexta.security.markings.api.rest.servers.spring.ValidateApi;
import com.connexta.security.markings.spring.exceptions.DetailedResponseStatusException;
import com.connexta.security.markings.spring.service.api.ValidationService;
import java.time.Instant;
import java.util.Date;
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
  private ValidationService validationService;

  @Autowired
  ValidationController(ValidationService validationService) {
    this.validationService = validationService;
  }

  @Override
  public ResponseEntity<ErrorMessage> validate(
      String acceptVersion, SecurityMarkings securityMarkings) {
    log.debug("Validate request received. Security markings: {}.", securityMarkings);

    try {
      validationService.validate(securityMarkings);
    } catch (DetailedResponseStatusException e) {
      log.error(
          "Problem validating security markings. Returning " + e.getStatus() + " error response.",
          e);
      ErrorMessage errorMessage = new ErrorMessage();
      errorMessage.setCode(e.getCode());
      errorMessage.setError(e.getReason());
      errorMessage.setMessage(e.getMessage());
      errorMessage.setPath("/validation/validate");
      errorMessage.setStatus(e.getCode());
      errorMessage.setTimestamp(Date.from(Instant.now()));
      return ResponseEntity.status(e.getStatus()).body(errorMessage);
    } catch (Exception e) { // default; should never get here
      log.error(
          "Problem validating security markings. Returning \"Internal Server Error\" error response.",
          e);
      ErrorMessage errorMessage = new ErrorMessage();
      errorMessage.setCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
      errorMessage.setError(
          "Exception occurred while validating markings. See logs for more details.");
      errorMessage.setMessage(e.getMessage());
      errorMessage.setPath("/validation/validate");
      errorMessage.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
      errorMessage.setTimestamp(Date.from(Instant.now()));
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorMessage);
    }

    return ResponseEntity.noContent().build();
  }
}
