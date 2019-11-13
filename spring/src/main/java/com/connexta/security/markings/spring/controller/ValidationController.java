/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.spring.controller;

import com.connexta.security.markings.api.openapi.models.ErrorMessage;
import com.connexta.security.markings.api.openapi.models.ISM;
import com.connexta.security.markings.api.openapi.servers.spring.ValidateApi;
import com.connexta.security.markings.spring.exceptions.DetailedResponseStatusException;
import com.connexta.security.markings.spring.service.ValidationService;
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
    if (validationService == null) {
      throw new IllegalArgumentException("ValidationController received null ValidationService in its constructor.");
    }
    this.validationService = validationService;
  }

  @Override
  public ResponseEntity<ErrorMessage> validate(
      String acceptVersion, ISM ism) {
    log.debug("Validate request received. ISM: {}.", ism);

    try {
      validationService.validate(ism);
    } catch (DetailedResponseStatusException e) {
      log.error(
          "Problem validating ISM. Returning \"" + e.getStatus() + "\" error response.",
          e);
      ErrorMessage errorMessage = new ErrorMessage();
      errorMessage.setCode(e.getCode());
      errorMessage.setError(e.getReason());
      errorMessage.setMessage(e.getMessage());
      errorMessage.setPath("/validation/validate");
      errorMessage.setStatus(e.getCode());
      errorMessage.setTimestamp(Date.from(Instant.now()));
      return ResponseEntity.status(e.getStatus()).body(errorMessage);
    } catch (IllegalArgumentException e) {
      log.error(
          "Problem validating ISM. Returning \"Unprocessable Entity\" error response.",
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
    catch (Exception e) { // default; ideally should never get here
      log.error(
          "Problem validating ISM. Returning \"Internal Server Error\" error response.",
          e);
      ErrorMessage errorMessage = new ErrorMessage();
      errorMessage.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
      errorMessage.setError(
          "Exception occurred while validating markings. See logs for more details.");
      errorMessage.setMessage(e.getMessage());
      errorMessage.setPath("/validation/validate");
      errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      errorMessage.setTimestamp(Date.from(Instant.now()));
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    return ResponseEntity.noContent().build();
  }
}
