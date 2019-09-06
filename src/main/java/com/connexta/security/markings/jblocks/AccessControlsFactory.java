/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.jblocks;

import com.connexta.security.markings.rest.models.ISM;
import com.connexta.security.markings.util.InputValidator;
import java.util.Optional;
import jblocks.dataheaders.classification.AccessControls;
import jblocks.dataheaders.classification.MarkingsDefinition;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccessControlsFactory {
  private MarkingsDefinition markingsDefinition;

  public AccessControlsFactory(MarkingsDefinition markingsDefinition) {
    if (markingsDefinition == null) {
      throw new IllegalArgumentException(
          "AccessControlsFactory received null MarkingsDefinition in its constructor. Cannot continue.");
    }

    this.markingsDefinition = markingsDefinition;
  }

  public Optional<AccessControls> create(ISM ism) {
    log.trace("Entering create method given ISM.");

    log.trace("Input validation on ISM.");
    InputValidator.validateIsm(ism);

    log.trace("Exiting create method given ISM.");
    return Optional.empty(); // no-op
  }
}
