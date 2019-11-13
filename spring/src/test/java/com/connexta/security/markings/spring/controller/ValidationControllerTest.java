/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.spring.controller;

import com.connexta.security.markings.api.openapi.models.ISM;
import com.connexta.security.markings.spring.exceptions.DetailedResponseStatusException;
import com.connexta.security.markings.spring.service.ValidationService;
import com.connexta.security.markings.test.resources.data.ValidIsm;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValidationControllerTest {
  private static final String DEFAULT_ACCEPT_VERSION = "0.1.0-SNAPSHOT";
  private static final ISM DEFAULT_ISM = ValidIsm.SECRET.getIsm();

  private ValidationService serviceMock = mock(ValidationService.class);
  private ValidationController validationController = new ValidationController(serviceMock);
  private DetailedResponseStatusException detailedResponseStatusExceptionMock =
      mock(DetailedResponseStatusException.class);

  @Test
  public void testConstructWithNullValidationService() {
    ValidationService nullValidationService = null;

    assertThrows(IllegalArgumentException.class, () -> new ValidationController(nullValidationService));
  }

  @Test
  public void testValidate() {
    assertEquals(
        HttpStatus.NO_CONTENT,
        validationController.validate(DEFAULT_ACCEPT_VERSION, DEFAULT_ISM).getStatusCode());
  }

  @Test
  public void testValidateExpectingBadRequestDetailedResponseStatusException() {
    when(detailedResponseStatusExceptionMock.getStatus()).thenReturn(HttpStatus.BAD_REQUEST);

    doThrow(detailedResponseStatusExceptionMock)
        .when(serviceMock)
        .validate(DEFAULT_ISM);

    assertEquals(
        HttpStatus.BAD_REQUEST,
        validationController.validate(DEFAULT_ACCEPT_VERSION, DEFAULT_ISM).getStatusCode());
  }

  @Test
  public void testValidateExpectingUnprocessableEntityDetailedResponseStatusException() {
    when(detailedResponseStatusExceptionMock.getStatus())
        .thenReturn(HttpStatus.UNPROCESSABLE_ENTITY);

    doThrow(detailedResponseStatusExceptionMock)
        .when(serviceMock)
        .validate(DEFAULT_ISM);

    assertEquals(
        HttpStatus.UNPROCESSABLE_ENTITY,
        validationController.validate(DEFAULT_ACCEPT_VERSION, DEFAULT_ISM).getStatusCode());
  }

  @Test
  public void testValidateExpectingGeneralRuntimeException() {
    doThrow(RuntimeException.class).when(serviceMock).validate(DEFAULT_ISM);

    assertEquals(
        HttpStatus.INTERNAL_SERVER_ERROR,
        validationController.validate(DEFAULT_ACCEPT_VERSION, DEFAULT_ISM).getStatusCode());
  }
}
