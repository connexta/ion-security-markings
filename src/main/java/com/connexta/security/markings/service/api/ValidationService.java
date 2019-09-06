/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.service.api;

import com.connexta.security.markings.api.rest.models.SecurityMarkings;

public interface ValidationService {

  void validate(SecurityMarkings securityMarkings);
}
