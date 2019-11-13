/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.jblocks.factory;

import com.connexta.security.markings.api.openapi.models.ISM;
import com.google.common.annotations.VisibleForTesting;
import java.util.Optional;
import jblocks.dataheaders.classification.AccessControls;
import jblocks.dataheaders.classification.CaBlock;
import jblocks.dataheaders.classification.Classification;
import jblocks.dataheaders.classification.MarkingsDefinition;
import lombok.extern.slf4j.Slf4j;

import static com.connexta.security.markings.util.StringUtils.isEmpty;

@Slf4j
public class ClassificationFactory {

  private MarkingsDefinition markingsDefinition;
  private CaBlockFactory caBlockFactory;
  private AccessControlsFactory accessControlsFactory;

  public ClassificationFactory(MarkingsDefinition markingsDefinition) {
    this(markingsDefinition, new AccessControlsFactory(markingsDefinition), new CaBlockFactory(markingsDefinition));
  }

  @VisibleForTesting
  ClassificationFactory(
      MarkingsDefinition markingsDefinition,
      AccessControlsFactory accessControlsFactory,
      CaBlockFactory caBlockFactory) {
    if (markingsDefinition == null) {
      String msg =
          "Received null MarkingsDefinition in ClassificationFactory constructor. Cannot continue";
      log.error(msg);
      throw new IllegalArgumentException(msg);
    }

    this.markingsDefinition = markingsDefinition;
    this.accessControlsFactory = accessControlsFactory;
    this.caBlockFactory = caBlockFactory;
  }

  public Optional<Classification> create(ISM ism, String banner) {
    log.trace("Entering create method given ISM.");

    if (ism == null) {
      throw new IllegalArgumentException("ClassificationFactory received null ISM.");
    }
    if (isEmpty(banner)) {
      throw new IllegalArgumentException("ClassificationFactory received null Banner.");
    }

    Classification.Builder builder = createBuilder();

    log.trace("Parse banner.");
    builder.parse(banner);

    Optional<CaBlock> caBlock = caBlockFactory.create(ism);
    if (caBlock.isPresent()) {
      builder.setCaBlock(caBlock.get());
    } else {
      log.debug("CaBlockFactory returned empty optional. Not adding to Classification builder.");
    }

    Optional<AccessControls> accessControls = accessControlsFactory.create(ism);
    if (accessControls.isPresent()) {
      builder.setAccessControls(accessControls.get());
    } else {
      log.debug(
          "AccessControlsFactory returned empty optional. Not adding to Classification builder.");
    }

    log.trace("Exiting create method given ISM.");
    return Optional.of(builder.build());
  }

  @VisibleForTesting
  protected Classification.Builder createBuilder() {
    return Classification.newBuilder(markingsDefinition);
  }
}
