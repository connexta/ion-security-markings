/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.spring.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.connexta.security.markings.api.rest.models.SecurityMarkings;
import com.connexta.security.markings.spring.exceptions.DetailedResponseStatusException;
import com.connexta.security.markings.spring.service.api.ValidationService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ValidationControllerTest {
  private static final String DEFAULT_ACCEPT_VERSION = "0.1.0-SNAPSHOT";
  private static final SecurityMarkings DEFAULT_SECURITY_MARKINGS = new SecurityMarkings();

  private DetailedResponseStatusException detailedResponseStatusExceptionMock =
      mock(DetailedResponseStatusException.class);
  private ValidationService serviceMock = mock(ValidationService.class);

  @Test
  public void testValidate() {
    ValidationController controller = new ValidationController(serviceMock);
    assertEquals(
        HttpStatus.NO_CONTENT,
        controller.validate(DEFAULT_ACCEPT_VERSION, DEFAULT_SECURITY_MARKINGS).getStatusCode());
  }

  @Test
  public void testValidateExpectingBadRequestDetailedResponseStatusException() {
    when(detailedResponseStatusExceptionMock.getStatus()).thenReturn(HttpStatus.BAD_REQUEST);

    doThrow(detailedResponseStatusExceptionMock)
        .when(serviceMock)
        .validate(DEFAULT_SECURITY_MARKINGS);

    ValidationController controller = new ValidationController(serviceMock);
    assertEquals(
        HttpStatus.BAD_REQUEST,
        controller.validate(DEFAULT_ACCEPT_VERSION, DEFAULT_SECURITY_MARKINGS).getStatusCode());
  }

  @Test
  public void testValidateExpectingUnprocessableEntityDetailedResponseStatusException() {
    when(detailedResponseStatusExceptionMock.getStatus())
        .thenReturn(HttpStatus.UNPROCESSABLE_ENTITY);

    doThrow(detailedResponseStatusExceptionMock)
        .when(serviceMock)
        .validate(DEFAULT_SECURITY_MARKINGS);

    ValidationController controller = new ValidationController(serviceMock);
    assertEquals(
        HttpStatus.UNPROCESSABLE_ENTITY,
        controller.validate(DEFAULT_ACCEPT_VERSION, DEFAULT_SECURITY_MARKINGS).getStatusCode());
  }

  @Test
  public void testValidateExpectingGeneralException() {
    doThrow(RuntimeException.class).when(serviceMock).validate(DEFAULT_SECURITY_MARKINGS);

    ValidationController controller = new ValidationController(serviceMock);
    assertEquals(
        HttpStatus.UNPROCESSABLE_ENTITY,
        controller.validate(DEFAULT_ACCEPT_VERSION, DEFAULT_SECURITY_MARKINGS).getStatusCode());
  }
}
