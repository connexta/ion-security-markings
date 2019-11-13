package com.connexta.security.markings.container;

import com.connexta.security.markings.api.openapi.models.ISM;
import com.connexta.security.markings.banner.BannerUtil;
import com.connexta.security.markings.jblocks.factory.ClassificationFactory;
import com.connexta.security.markings.util.IsmUtils;
import com.google.common.annotations.VisibleForTesting;
import java.util.Map;
import java.util.Optional;
import jblocks.dataheaders.classification.AccessControls;
import jblocks.dataheaders.classification.CaBlock;
import jblocks.dataheaders.classification.Classification;
import jblocks.dataheaders.classification.MarkingsDefinition;
import lombok.extern.slf4j.Slf4j;

import static com.connexta.security.markings.util.StringUtils.isEmpty;

@Slf4j
public class MarkingsContainer {

  private static void validateIsm(ISM ism) {
    if (ism == null) {
      throw new IllegalArgumentException("MarkingsContainer constructor received null ISM.");
    }

    if (isEmpty(ism.getClassification())) {
      throw new IllegalArgumentException("MarkingsContainer constructor received ISM with null or empty classification level.");
    }

    if (isEmpty(ism.getOwnerProducer())) {
      throw new IllegalArgumentException("MarkingsContainer constructor received ISM with null or empty owner producer..");
    }
  }

  private MarkingsDefinition markingsDefinition;
  private ISM ism;
  private BannerUtil bannerUtil;
  private ClassificationFactory classificationFactory;

  // generated when accessed
  private Map<String, String> ismMap;
  private String banner;
  private Classification classification;
  private String correctedBanner;
  private ISM correctedIsm;
  private Map<String, String> correctedIsmMap;

  public MarkingsContainer(MarkingsDefinition markingsDefinition, ISM ism) {
    this(markingsDefinition, ism, new BannerUtil(), new ClassificationFactory(markingsDefinition));
  }

  @VisibleForTesting
  MarkingsContainer(MarkingsDefinition markingsDefinition, ISM ism, BannerUtil bannerUtil, ClassificationFactory classificationFactory) {
    if (markingsDefinition == null) {
      throw new IllegalArgumentException("MarkingsContainer constructor received null MarkingsDefinition.");
    }
    validateIsm(ism);
    if (bannerUtil == null) {
      throw new IllegalArgumentException("MarkingsContainer constructor received null BannerUtil.");
    }
    if (classificationFactory == null) {
      throw new IllegalArgumentException("MarkingsContainer constructor received null ClassificationFactory.");
    }

    this.markingsDefinition = markingsDefinition;
    this.ism = ism;
    this.bannerUtil = bannerUtil;
    this.classificationFactory = classificationFactory;
  }

  public ISM getIsm() {
    return ism;
  }

  public Map<String, String> getIsmMap() {
    if (ismMap == null) {
      generateIsmMap();
    }

    return ismMap;
  }

  public String getBanner() {
    if (isEmpty(banner)) {
      generateBanner();
    }

    return banner;
  }

  public Classification getClassification() {
    if (classification == null) {
      generateClassification();
    }

    return classification;
  }

  public String getCorrectedBanner() {
    if (isEmpty(correctedBanner)) {
      generateCorrectedBanner();
    }

    return correctedBanner;
  }

  public ISM getCorrectedIsm() {
    if (correctedIsm == null) {
      generateCorrectedIsmAndIsmMap();
    }

    return correctedIsm;
  }

  public Map<String, String> getCorrectedIsmMap() {
    if (correctedIsmMap == null) {
      generateCorrectedIsmAndIsmMap();
    }

    return correctedIsmMap;
  }

  private void generateIsmMap() {
    ismMap = IsmUtils.createMapFromIsm(ism);
  }

  private void generateBanner() {
    banner = bannerUtil.createBannerFromIsm(ism);
  }

  private void generateClassification() {
    if (isEmpty(banner)) {
      generateBanner();
    }

    Optional<Classification> optionalClassification = classificationFactory.create(ism, banner);

    if (optionalClassification.isEmpty()) {
      throw new IllegalArgumentException("Problem transforming ISM to classification.");
    }

    classification = optionalClassification.get();
  }

  private void generateCorrectedBanner() {
    if (classification == null) {
      generateClassification();
    }

    correctedBanner = classification.toBanner();
  }

  private void generateCorrectedIsmAndIsmMap() {
    if (isEmpty(correctedBanner)) {
      generateCorrectedBanner();
    }

    correctedIsm = bannerUtil.createIsmFromBanner(correctedBanner);

    AccessControls accessControls = classification.getAccessControls();
    if (accessControls != null) {
      // no-op
    }

    CaBlock caBlock = classification.getCaBlock();
    if (caBlock != null) {
      correctedIsm.setClassificationReason(caBlock.getClassificationReason());
      correctedIsm.setClassifiedBy(caBlock.getClassifiedBy());
      correctedIsm.setDerivedFrom(caBlock.getDerivedFrom());
      correctedIsm.setDeclassDate(caBlock.getDeclassifyOn());
    }

    // fill in missing values from original ism
    correctedIsmMap = IsmUtils.createMapFromIsm(correctedIsm);
    getIsmMap().forEach((key, value) -> {
      correctedIsmMap.putIfAbsent(key, value);
    });

    correctedIsm = IsmUtils.createIsmFromMap(correctedIsmMap);
  }
}
