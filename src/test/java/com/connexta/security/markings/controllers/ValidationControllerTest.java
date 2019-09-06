/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import com.connexta.security.markings.rest.models.SecurityMarkings;
import com.connexta.security.markings.service.api.SecurityMarkingsService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ValidationControllerTest {
  private static final String DEFAULT_ACCEPT_VERSION = "0.1.0-SNAPSHOT";
  private static final SecurityMarkings DEFAULT_SECURITY_MARKINGS = new SecurityMarkings();

  private SecurityMarkingsService service = mock(SecurityMarkingsService.class);

  @Test
  public void testValidate() throws Exception {
    ValidationController controller = new ValidationController(service);
    assertEquals(
        HttpStatus.NO_CONTENT,
        controller.validate(DEFAULT_ACCEPT_VERSION, DEFAULT_SECURITY_MARKINGS).getStatusCode());
  }

  @Test
  public void testValidateExpectingIllegalArgumentException() throws Exception {
    doThrow(IllegalArgumentException.class).when(service).validate(DEFAULT_SECURITY_MARKINGS);

    ValidationController controller = new ValidationController(service);
    assertEquals(
        HttpStatus.BAD_REQUEST,
        controller.validate(DEFAULT_ACCEPT_VERSION, DEFAULT_SECURITY_MARKINGS).getStatusCode());
  }

  @Test
  public void testValidateExpectingGeneralException() throws Exception {
    doThrow(RuntimeException.class).when(service).validate(DEFAULT_SECURITY_MARKINGS);

    ValidationController controller = new ValidationController(service);
    assertEquals(
        HttpStatus.INTERNAL_SERVER_ERROR,
        controller.validate(DEFAULT_ACCEPT_VERSION, DEFAULT_SECURITY_MARKINGS).getStatusCode());
  }
}
