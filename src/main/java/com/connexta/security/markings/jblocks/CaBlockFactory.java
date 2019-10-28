/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.jblocks;

import static org.springframework.util.StringUtils.isEmpty;

import com.connexta.security.markings.api.rest.models.ISM;
import com.connexta.security.markings.util.InputValidator;
import java.util.Optional;
import jblocks.dataheaders.classification.CaBlock;
import jblocks.dataheaders.classification.ControlBit;
import jblocks.dataheaders.classification.DateUtils;
import jblocks.dataheaders.classification.MarkingsDefinition;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CaBlockFactory {
  private MarkingsDefinition markingsDefinition;

  public CaBlockFactory(MarkingsDefinition markingsDefinition) {
    if (markingsDefinition == null) {
      throw new IllegalArgumentException(
          "CaBlockFactory received null MarkingsDefinition in its constructor. Cannot continue.");
    }
    this.markingsDefinition = markingsDefinition;
  }

  public Optional<CaBlock> create(ISM ism) {
    log.trace("Entering create method given ISM.");

    log.trace("Input validation on ISM.");
    InputValidator.validateIsm(ism);

    log.trace("Input relevancy check on ISM.");
    if (!isInputRelevant(ism)) {
      return Optional.empty();
    }

    CaBlock.Builder builder = createBuilder();

    String classificationReason = ism.getClassificationReason();
    String classifiedBy = ism.getClassifiedBy();
    String derivedFrom = ism.getDerivedFrom();
    String declassDate = ism.getDeclassDate();

    log.trace("Add ISM to CA Block builder.");
    if (!isEmpty(classificationReason)) {
      builder.setClassificationReason(classificationReason);
    }
    if (!isEmpty(classifiedBy)) {
      builder.setClassifiedBy(classifiedBy);
    }
    if (!isEmpty(derivedFrom)) {
      builder.setDerivedFrom(derivedFrom);
    }
    if (declassDate != null) {
      builder.setDeclassifyOn(DateUtils.toYYYYMMDD(declassDate));
    }

    log.trace("Exiting create method given ISM.");
    return Optional.of(builder.build());
  }

  private boolean isInputRelevant(ISM ism) {
    String classificationLevel = ism.getClassification();
    ControlBit unclassifiedControlBit = markingsDefinition.getBit("U").getControlBit();

    if (isEmpty(classificationLevel)
        || unclassifiedControlBit == null
        || unclassifiedControlBit.getName().equals(classificationLevel)
        || unclassifiedControlBit.getAbbreviation().equals(classificationLevel)
        || unclassifiedControlBit.getPortionMarking().equals(classificationLevel)) {
      String msg =
          "Passed in ISM has null, blank, or Unclassified classification. CaBlock is not applicable in this case.";
      log.info(msg);
      return false;
    }
    return true;
  }

  protected CaBlock.Builder createBuilder() {
    return CaBlock.newBuilder(markingsDefinition);
  }
}
