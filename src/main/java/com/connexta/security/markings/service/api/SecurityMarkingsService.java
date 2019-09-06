/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.service.api;

import com.connexta.security.markings.rest.models.SecurityMarkings;

/** Provides clients with a way to ingest Products for processing and storage */
public interface SecurityMarkingsService {

  void validate(SecurityMarkings securityMarkings);
}
