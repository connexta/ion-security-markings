/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.service.api;

import com.connexta.security.markings.exceptions.SecurityMarkingsException;
import com.connexta.security.markings.rest.models.SecurityMarking;
import com.connexta.security.markings.rest.models.SecurityMarkingsWithOverwrites;
import java.util.List;

/** Provides clients with a way to ingest Products for processing and storage */
public interface SecurityMarkingsService {

  List<SecurityMarking> systemMarkings() throws SecurityMarkingsException;

  void validate(SecurityMarkingsWithOverwrites securityMarkingsWithOverwrites)
      throws SecurityMarkingsException;
}
