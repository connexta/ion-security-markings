/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.controllers;

import com.connexta.security.markings.rest.models.SecurityMarkingsWithOverwrites;
import com.connexta.security.markings.rest.spring.ValidateApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ValidateController implements ValidateApi {
  public ValidateController() {}

  @Override
  public ResponseEntity<Void> validate(
      String acceptVersion, SecurityMarkingsWithOverwrites securityMarkingsWithOverwrites) {
    log.info(
        "Validate markings request received. Returning with OK response.\n" + "Given markings: {}",
        securityMarkingsWithOverwrites);

    return ResponseEntity.ok().build();
  }
}
