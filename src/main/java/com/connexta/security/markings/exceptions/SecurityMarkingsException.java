/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.exceptions;

import com.connexta.security.markings.common.exceptions.DetailedResponseStatusException;
import org.springframework.http.HttpStatus;

public class SecurityMarkingsException extends DetailedResponseStatusException {
  public SecurityMarkingsException(String reason, Throwable cause) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
    addDetail("Caused by " + cause.getMessage());
  }
}
