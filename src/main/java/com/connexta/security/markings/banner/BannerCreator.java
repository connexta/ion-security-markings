/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.banner;

import static org.springframework.util.StringUtils.isEmpty;

import com.connexta.security.markings.api.rest.models.ISM;
import com.connexta.security.markings.util.InputValidator;
import com.google.common.collect.ImmutableList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BannerCreator {
  public static final String DELIMITER = "//";
  public static final String SUB_DELIMITER = "/";
  public static final String SPACE = " ";
  private static List<String> SPECIAL_CLASSIFICATIONS =
      ImmutableList.of("NU", "NR", "NC", "NS", "CTS");

  public String create(ISM ism) {
    log.trace("Entering create given ISM.");

    log.trace("Input validation on ISM.");
    InputValidator.validateIsm(ism);

    StringBuilder builder = new StringBuilder();

    log.trace("Append banner components.");
    appendClassification(builder, ism);
    appendNonUsControls(builder, ism);
    appendFgi(builder, ism);
    appendSap(builder, ism);
    appendAea(builder, ism);
    appendOtherDisseminationControls(builder, ism);
    appendDisseminationControls(builder, ism);
    appendSciControls(builder, ism);
    log.debug("Banner Marking string: {}.", builder.toString());

    log.trace("Exiting create given ISM.");
    return builder.toString();
  }

  private void appendClassification(StringBuilder builder, ISM ism) {
    String classification = ism.getClassification();
    String ownerProducer = ism.getOwnerProducer();

    boolean isJoint = Boolean.TRUE.equals(ism.isJoint());
    boolean isFGI = !"US".equals(ownerProducer) && !"USA".equals(ownerProducer);

    if (isJoint) {
      builder.append(DELIMITER);
      builder.append("JOINT");
      builder.append(SPACE);
      builder.append(classification);
      builder.append(SPACE);
      builder.append(ownerProducer);
    } else if (SPECIAL_CLASSIFICATIONS.contains(classification)) {
      builder.append(DELIMITER);
      builder.append(classification);
    } else if (isFGI) {
      builder.append(DELIMITER);
      builder.append(ownerProducer);
      builder.append(SPACE);
      builder.append(classification);
    } else { // is US
      builder.append(classification);
    }
  }

  private void appendNonUsControls(StringBuilder builder, ISM ism) {
    String nonUsControls = ism.getNonUSControls();

    if (!isEmpty(nonUsControls)) {
      builder.append(DELIMITER);
      builder.append(nonUsControls);
    }
  }

  private void appendFgi(StringBuilder builder, ISM ism) {
    String fgiSourceOpen = ism.getFgIsourceOpen();
    String fgiSourceProtected = ism.getFgIsourceProtected();

    boolean hasFgiSources = !isEmpty(fgiSourceOpen) || !isEmpty(fgiSourceProtected);

    if (hasFgiSources) {
      builder.append(DELIMITER);

      if (!isEmpty(fgiSourceOpen)) {
        builder.append(fgiSourceOpen);
      }

      if (!isEmpty(fgiSourceProtected)) {
        builder.append(SPACE);
        builder.append(fgiSourceProtected);
      }
    }
  }

  private void appendSap(StringBuilder builder, ISM ism) {
    String sar = ism.getSaRIdentifier();

    if (!isEmpty(sar)) {
      builder.append(DELIMITER);

      if (!"HVSACO".equals(sar)) {
        builder.append("SPECIAL ACCESS REQUIRED-");
      }

      builder.append(sar);
    }
  }

  private void appendAea(StringBuilder builder, ISM ism) {
    String atomicEnergyMarkings = ism.getAtomicEnergyMarkings();

    if (!isEmpty(atomicEnergyMarkings)) {
      builder.append(DELIMITER);
      builder.append(atomicEnergyMarkings);
    }
  }

  private void appendOtherDisseminationControls(StringBuilder builder, ISM ism) {
    String releasableTo = ism.getReleasableTo();
    String displayOnlyTo = ism.getDisplayOnlyTo();

    if (!isEmpty(releasableTo)) {
      builder.append(DELIMITER);
      builder.append("REL TO ");
      builder.append(releasableTo);
    }

    if (!isEmpty(displayOnlyTo)) {
      if (!isEmpty(releasableTo)) {
        builder.append(SUB_DELIMITER);
      } else {
        builder.append(DELIMITER);
      }
      builder.append("DISPLAY ONLY ");
      builder.append(displayOnlyTo);
    }
  }

  private void appendDisseminationControls(StringBuilder builder, ISM ism) {
    String disseminationControls = ism.getDisseminationControls();

    if (!isEmpty(disseminationControls)) {
      builder.append(DELIMITER);
      builder.append(disseminationControls);
    }
  }

  private void appendSciControls(StringBuilder builder, ISM ism) {
    String sciControls = ism.getScIControls();
  }
}
