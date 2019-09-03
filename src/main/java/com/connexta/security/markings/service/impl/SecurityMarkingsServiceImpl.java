/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.service.impl;

import com.connexta.security.markings.exceptions.SecurityMarkingsException;
import com.connexta.security.markings.rest.models.SecurityMarking;
import com.connexta.security.markings.rest.models.SecurityMarkingsWithOverwrites;
import com.connexta.security.markings.service.api.SecurityMarkingsService;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityMarkingsServiceImpl implements SecurityMarkingsService {

  public List<SecurityMarking> systemMarkings() throws SecurityMarkingsException {
    SecurityMarking securityMarking = new SecurityMarking();
    securityMarking.setMarkingName("Classification");
    securityMarking.setMarkingValue("U");

    return Arrays.asList(securityMarking);
  }

  public void validate(SecurityMarkingsWithOverwrites securityMarkingsWithOverwrites)
      throws SecurityMarkingsException {
    // no-op
  }
}
