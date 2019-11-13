package com.connexta.security.markings.banner;

import com.connexta.security.markings.api.openapi.models.ISM;
import com.connexta.security.markings.api.util.IsmConstants;
import com.connexta.security.markings.util.IsmUtils;
import com.google.common.collect.ImmutableSet;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

import static com.connexta.security.markings.util.StringUtils.isEmpty;

@Slf4j
public class BannerUtil {
  private static final String DELIMITER = "//";
  private static final String SUB_DELIMITER = "/";
  private static final String SPACE = " ";

  private static final String JOINT = "JOINT";
  private static final String NATO_FGI = "NATO";
  private static final String COSMIC_FGI = "COSMIC";
  private static Set<String> SPECIAL_CLASSIFICATIONS =
      ImmutableSet.of("NU", "NR", "NC", "NS", "CTS");
  private static Set<String> AEA_SET =
      ImmutableSet.of("RESTRICTED DATA", "RD", "FORMERLY RESTRICTED DATA", "FRD", "DOD UNCLASSIFIED CONTROLLED NUCLEAR INFORMATION", "DOD UCNI", "DCNI", "DOE UNCLASSIFIED CONTROLLED NUCLEAR INFORMATION", "DOE UCNI", "TRANSCLASSIFIED FOREIGN NUCLEAR INFORMATION", "TFNI");
  private static Set<String> DISSEMINATION_CONTROLS_SET =
      ImmutableSet.of("RSEN", "RISK SENSITIVE", "RS", "IMCON", "CONTROLLED IMAGERY", "IMC", "NOFORN", "NOT RELEASABLE TO FOREIGN NATIONALS", "NF", "PROPIN", "CAUTION-PROPRIETARY INFORMATION INVOLVED", "PR", "RELEASABLE BY INFORMATION DISCLOSURE OFFICIAL", "RELIDO", "FISA", "FOREIGN INTELLIGENCE SURVEILLANCE ACT", "ORCON", "ORIGINATOR CONTROLLED", "OC", "DEA SENSITIVE", "DSEN", "FOR OFFICIAL USE ONLY", "FOUO", "EYES ONLY", "EYES", "WAIVED");
  private static Set<String> OTHER_DISSEMINATION_CONTROLS_SET =
      ImmutableSet.of("ACCM", "EXDIS", "EXCLUSIVE DISTRIBUTION", "XD", "LIMDIS", "LIMITED DISTRIBUTION", "DS", "NODIS", "NO DISTRIBUTION", "ND", "SENSITIVE BUT UNCLASSIFIED", "SBU", "SBU NOFORN", "SENSITIVE BUT UNCLASSIFIED NOFORN", "SBU-NF", "LAW ENFORCEMENT SENSITIVE", "LES", "LES NOFORN", "LAW ENFORCEMENT SENSITIVE NOFORN", "LES-NF", "SENSITIVE SECURITY INFORMATION", "SSI");

  public static Set<String> BANNER_RELEVANT_ISM_ATTRIBUTE_SET = Set.of(
      IsmConstants.ATOMIC_ENERGY_MARKINGS,
      IsmConstants.CLASSIFICATION,
      IsmConstants.DISPLAY_ONLY_TO,
      IsmConstants.DISSEMINATION_CONTROLS,
      IsmConstants.FGI_SOURCE_OPEN,
      IsmConstants.FGI_SOURCE_PROTECTED,
      IsmConstants.JOINT,
      IsmConstants.NON_IC_MARKINGS,
      // IsmConstants.NON_US_CONTROLS,
      IsmConstants.OWNER_PRODUCER,
      IsmConstants.RELEASABLE_TO,
      IsmConstants.SAR_IDENTIFIER,
      IsmConstants.SCI_CONTROLS
  );

  public String createBannerFromIsm(ISM ism) {
    if (ism == null) {
      throw new IllegalArgumentException("BannerUtil received null ISM");
    }

    log.trace("Entering create given ISM.");
    StringBuilder builder = new StringBuilder();

    log.trace("Append banner components.");
    appendClassification(builder, ism);
    appendSciControls(builder, ism);
    appendSap(builder, ism);
    appendAea(builder, ism);
    appendFgi(builder, ism);
    appendDisseminationControls(builder, ism);
    appendOtherDisseminationControls(builder, ism);
    log.debug("Banner Marking string: {}.", builder.toString());

    log.trace("Exiting create given ISM.");
    return builder.toString();
  }

  private void appendClassification(StringBuilder builder, ISM ism) {
    if (isEmpty(ism.getClassification())) {
      throw new IllegalArgumentException("BannerUtil received ISM with null or empty classification level.");
    }
    if (isEmpty(ism.getOwnerProducer())) {
      throw new IllegalArgumentException("BannerUtil received ISM with null or empty owner producer.");
    }

    String classification = ism.getClassification().toUpperCase();
    String ownerProducer = ism.getOwnerProducer().toUpperCase();

    boolean isJoint = "true".equalsIgnoreCase(ism.getJoint());
    boolean isUSA = "USA".equals(ownerProducer.toUpperCase());

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
    } else if (isUSA) {
      builder.append(classification);
    } else { // is FGI
      builder.append(DELIMITER);
      builder.append(ownerProducer);
      builder.append(SPACE);
      builder.append(classification);
    }
  }

  private void appendSciControls(StringBuilder builder, ISM ism) {
    String sciControls = ism.getScIControls();

    if (!isEmpty(sciControls)) {
      builder.append(DELIMITER);
      builder.append(sciControls.toUpperCase());
    }
  }

  private void appendSap(StringBuilder builder, ISM ism) {
    String sar = ism.getSaRIdentifier();

    if (!isEmpty(sar)) {
      builder.append(DELIMITER);

      if (!"HVSACO".equals(sar.toUpperCase())) {
        builder.append("SPECIAL ACCESS REQUIRED-");
      }

      builder.append(sar.toUpperCase());
    }
  }

  private void appendAea(StringBuilder builder, ISM ism) {
    String atomicEnergyMarkings = ism.getAtomicEnergyMarkings();

    if (!isEmpty(atomicEnergyMarkings)) {
      builder.append(DELIMITER);
      builder.append(atomicEnergyMarkings.toUpperCase());
    }
  }

  private void appendFgi(StringBuilder builder, ISM ism) {
    String fgiSourceOpen = ism.getFgIsourceOpen();
    String fgiSourceProtected = ism.getFgIsourceProtected();

    boolean hasFgiSources = !isEmpty(fgiSourceOpen) || !isEmpty(fgiSourceProtected);

    if (hasFgiSources) {
      builder.append(DELIMITER);
      builder.append("FGI ");

      if (!isEmpty(fgiSourceOpen)) {
        builder.append(fgiSourceOpen.toUpperCase());
      }

      if (!isEmpty(fgiSourceProtected)) {
        builder.append(SPACE);
        builder.append(fgiSourceProtected.toUpperCase());
      }
    }
  }

  private void appendDisseminationControls(StringBuilder builder, ISM ism) {
    String releasableTo = ism.getReleasableTo();
    String displayOnlyTo = ism.getDisplayOnlyTo();
    String disseminationControls = ism.getDisseminationControls();

    boolean appendedDelimiter = false; // flag to keep track of whether a delimiter was added or not

    if (!isEmpty(releasableTo)) {
      builder.append(DELIMITER);
      builder.append("REL TO ");
      builder.append(releasableTo.toUpperCase());

      appendedDelimiter = true;
    }

    if (!isEmpty(displayOnlyTo)) {
      if (appendedDelimiter) {
        builder.append(SUB_DELIMITER);
      } else {
        builder.append(DELIMITER);
        appendedDelimiter = true;
      }
      builder.append("DISPLAY ONLY ");
      builder.append(displayOnlyTo.toUpperCase());
    }

    if (!isEmpty(disseminationControls)) {
      if (appendedDelimiter) {
        builder.append(SUB_DELIMITER);
      } else {
        builder.append(DELIMITER);
      }
      builder.append(disseminationControls.toUpperCase());
    }
  }

  private void appendOtherDisseminationControls(StringBuilder builder, ISM ism) {
    String nonIcMarkings = ism.getNonICmarkings();

    if (!isEmpty(nonIcMarkings)) {
      builder.append(DELIMITER);
      builder.append(nonIcMarkings.toUpperCase());
    }
  }

  public ISM createIsmFromBanner(String banner) {
    if (isEmpty(banner)) {
      throw new IllegalArgumentException("BannerUtil received null or empty banner.");
    }

    // if leading delimiter is found, set flag and remove leading delimiter from banner
    boolean leadingDelimiter = banner.startsWith(DELIMITER);
    if (leadingDelimiter) {
      banner = banner.substring(DELIMITER.length()).trim();
    }

    List<String> bannerSegments = new LinkedList<>(Arrays.asList(banner.split(DELIMITER)));
    // if no delimiters were found, treat the entire banner as one segment
    if (bannerSegments.isEmpty()) {
      bannerSegments.add(banner);
    }

    ISM ism = new ISM();

    String classificationSegment = bannerSegments.remove(0).trim().toUpperCase();
    parseClassification(classificationSegment, leadingDelimiter , ism);

    for (String bannerSegment : bannerSegments) {
      bannerSegment = bannerSegment.trim().toUpperCase();

      if (parseSap(bannerSegment, ism)) {
        continue;
      }
      if (parseAea(bannerSegment, ism)) {
        continue;
      }
      if (parseUsFgi(bannerSegment, ism)) {
        continue;
      }
      if (parseDisseminationControls(bannerSegment, ism)) {
        continue;
      }
      if (parseOtherDisseminationControls(bannerSegment, ism)) {
        continue;
      }

      if (!parseSciControls(bannerSegment, ism)) {
        throw new IllegalArgumentException(String.format("Unknown banner segment found: %s.", bannerSegment));
      }
    }

    return ism;
  }

  private boolean parseClassification(String bannerSegment, boolean leadingDelimiter, ISM ism) {
    // US classification
    if (!leadingDelimiter) {
      ism.setOwnerProducer("USA");
      ism.setClassification(bannerSegment);
      return true;
    }

    // JOINT classification
    if (bannerSegment.toUpperCase().startsWith(JOINT)) {
      String[] classificationAndOwnerProducer = bannerSegment.substring(JOINT.length()).trim().split(SPACE, 2);
      String classification = safeGetAndTrim(classificationAndOwnerProducer, 0);
      String ownerProducer = safeGetAndTrim(classificationAndOwnerProducer, 1);

      if (isEmpty(classification)) {
        throw new IllegalArgumentException("JOINT classification requires a Classification level.");
      }

      if (isEmpty(ownerProducer)) {
        throw new IllegalArgumentException("JOINT classification requires Owner Producers.");
      }

      ism.setJoint("true");
      ism.setClassification(classification);
      ism.setOwnerProducer(ownerProducer);
      return true;
    }

    // FGI classification
    String[] fgiAuthorityAndClassification = bannerSegment.split(SPACE, 2);
    String fgiAuthority = safeGetAndTrim(fgiAuthorityAndClassification, 0);
    String classification = safeGetAndTrim(fgiAuthorityAndClassification, 1);

    if (isEmpty(classification)) {
      if ("NU".equals(fgiAuthority) || "NATO UNCLASSIFIED".equals(fgiAuthority)) {
        ism.setFgIsourceOpen(NATO_FGI);
        ism.setClassification("U");
        return true;
      }
      if ("NR".equals(fgiAuthority) || "NATO RESTRICTED".equals(fgiAuthority)) {
        ism.setFgIsourceOpen(NATO_FGI);
        ism.setClassification("R");
        return true;
      }
      if ("NC".equals(fgiAuthority) || "NATO CLASSIFIED".equals(fgiAuthority)) {
        ism.setFgIsourceOpen(NATO_FGI);
        ism.setClassification("C");
        return true;
      }
      if ("NS".equals(fgiAuthority) || "NATO SECRET".equals(fgiAuthority)) {
        ism.setFgIsourceOpen(NATO_FGI);
        ism.setClassification("S");
        return true;
      }
      if ("CTS".equals(fgiAuthority) || "COSMIC TOP SECRET".equals(fgiAuthority)) {
        ism.setFgIsourceOpen(COSMIC_FGI);
        ism.setClassification("TS");
        return true;
      }

      throw new IllegalArgumentException("Non-US classification needs FGI Authority, or a special NATO classification.");
    } else {
      ism.setFgIsourceOpen(fgiAuthority);
      ism.setClassification(classification);
      return true;
    }
  }

  private boolean parseUsFgi(String bannerSegment, ISM ism) {
    if (!bannerSegment.toUpperCase().startsWith("FGI")
            && !bannerSegment.toUpperCase().startsWith("FOREIGN GOVERNMENT INFORMATION")) {
      return false;
    }

    String fgiSources = null;
    if (bannerSegment.startsWith("FGI")) {
      fgiSources = bannerSegment.substring("FGI".length()).trim();

    } else if (bannerSegment.startsWith("FOREIGN GOVERNMENT INFORMATION")) {
      fgiSources = bannerSegment.substring("FOREIGN GOVERNMENT INFORMATION".length()).trim();
    }

    ism.setFgIsourceOpen(fgiSources);

    return true;
  }

  private boolean parseSap(String bannerSegment, ISM ism) {
    if (!isEmpty(ism.getSaRIdentifier()) ||
        (!bannerSegment.startsWith("SAR-")
        && !bannerSegment.startsWith("SPECIAL ACCESS REQUIRED-")
        && !bannerSegment.equals("HVSACO"))) {
      return false;
    }

    if (bannerSegment.equals("HVSACO")) {
      ism.setSaRIdentifier(bannerSegment);
    } else {
      ism.setSaRIdentifier(safeGetAndTrim(bannerSegment.split("-", 2), 1));
    }
    return true;
  }

  private boolean parseAea(String bannerSegment, ISM ism) {
    if (!isEmpty(ism.getAtomicEnergyMarkings())
        || !AEA_SET.contains(bannerSegment)) {
      return false;
    }

    ism.setAtomicEnergyMarkings(bannerSegment);
    return true;
  }

  private boolean parseDisseminationControls(String bannerSegment, ISM ism) {
    String[] bannerSubSegments;
    if (bannerSegment.contains(SUB_DELIMITER)) {
      bannerSubSegments = bannerSegment.split(SUB_DELIMITER);
    } else {
      bannerSubSegments = new String[] { bannerSegment };
    }

    String firstBannerSubSegment = safeGetAndTrim(bannerSubSegments, 0);

    if (!firstBannerSubSegment.startsWith("REL TO")
        && !firstBannerSubSegment.startsWith("DISPLAY ONLY")
        && !DISSEMINATION_CONTROLS_SET.contains(firstBannerSubSegment)) {
      return false;
    }

    Set<String> disseminationControls = new HashSet<>();

    for (String bannerSubSegment : bannerSubSegments) {
      bannerSubSegment = bannerSubSegment.trim();

      if (bannerSubSegment.startsWith("REL TO")) {
        ism.setReleasableTo(bannerSubSegment.substring("REL TO".length()).trim());
      } else if (bannerSubSegment.startsWith("DISPLAY ONLY")) {
        ism.setDisplayOnlyTo(bannerSubSegment.substring("DISPLAY ONLY".length()).trim());
      } else {
        disseminationControls.add(bannerSubSegment);
      }
    }

    if (!disseminationControls.isEmpty()) {
      ism.setDisseminationControls(String.join(SUB_DELIMITER, disseminationControls));
    }

    return true;
  }

  private boolean parseOtherDisseminationControls(String bannerSegment, ISM ism) {
    String[] bannerSubSegments = bannerSegment.split(SUB_DELIMITER);

    if (ism.getNonICmarkings() != null || !OTHER_DISSEMINATION_CONTROLS_SET.contains(safeGetAndTrim(bannerSubSegments, 0))) {
      return false;
    }

    ism.setNonICmarkings(bannerSegment);

    return true;
  }

  private boolean parseSciControls(String bannerSegment, ISM ism) {
    if (!isEmpty(ism.getScIControls())) {
      return false;
    }

    ism.setScIControls(bannerSegment);

    return true;
  }

  private String safeGetAndTrim(String[] array, int index) {
    if (index >= array.length) {
      return "";
    }
    return array[index].trim();
  }
}
