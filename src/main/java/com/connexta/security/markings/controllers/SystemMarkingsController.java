/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.controllers;

import com.connexta.security.markings.rest.models.SecurityMarking;
import com.connexta.security.markings.rest.spring.SystemMarkingsApi;
import com.connexta.security.markings.service.api.SecurityMarkingsService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@SpringBootApplication
public class SystemMarkingsController implements SystemMarkingsApi {
  @Autowired public SecurityMarkingsService securityMarkingsService;

  public SystemMarkingsController() {}

  @Override
  public ResponseEntity<List<SecurityMarking>> systemMarkings(String acceptVersion) {
    log.info(
        "System markings request received. Returning with OK response with \"Classification\" set to \"U\".");

    List<SecurityMarking> body = securityMarkingsService.systemMarkings();

    return ResponseEntity.ok(body);
  }
}
