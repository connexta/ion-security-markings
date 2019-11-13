package com.connexta.security.markings.util;

import com.connexta.security.markings.api.openapi.models.ISM;
import com.connexta.security.markings.api.util.IsmConstants;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.connexta.security.markings.util.StringUtils.isEmpty;

public class IsmUtils {

  public static ISM createIsmFromMap(Map<String, String> ismMap) {
    ISM ism = new ISM();

    ism.setAtomicEnergyMarkings(ismMap.get(IsmConstants.ATOMIC_ENERGY_MARKINGS));
    ism.setClassification(ismMap.get(IsmConstants.CLASSIFICATION));
    ism.setClassificationReason(ismMap.get(IsmConstants.CLASSIFICATION_REASON));
    ism.setClassifiedBy(ismMap.get(IsmConstants.CLASSIFIED_BY));
    ism.setCompilationReason(ismMap.get(IsmConstants.COMPILATION_REASON));
    ism.setCompliesWith(ismMap.get(IsmConstants.COMPLIES_WITH));
    ism.setCreateDate(ismMap.get(IsmConstants.CREATE_DATE));
    ism.setDeclassDate(ismMap.get(IsmConstants.DECLASS_DATE));
    ism.setDeclassEvent(ismMap.get(IsmConstants.DECLASS_EVENT));
    ism.setDeclassException(ismMap.get(IsmConstants.DECLASS_EXCEPTION));
    ism.setDerivativelyClassifiedBy(ismMap.get(IsmConstants.DERIVATIVELY_CLASSIFIED_BY));
    ism.setDerivedFrom(ismMap.get(IsmConstants.DERIVED_FROM));
    ism.setDeSVersion(ismMap.get(IsmConstants.DES_VERSION));
    ism.setDisplayOnlyTo(ismMap.get(IsmConstants.DISPLAY_ONLY_TO));
    ism.setDisseminationControls(ismMap.get(IsmConstants.DISSEMINATION_CONTROLS));
    ism.setExcludeFromRollup(ismMap.get(IsmConstants.EXCLUDE_FROM_ROLLUP));
    ism.setExemptFrom(ismMap.get(IsmConstants.EXEMPT_FROM));
    ism.setExternalNotice(ismMap.get(IsmConstants.EXTERNAL_NOTICE));
    ism.setFgIsourceOpen(ismMap.get(IsmConstants.FGI_SOURCE_OPEN));
    ism.setFgIsourceProtected(ismMap.get(IsmConstants.FGI_SOURCE_PROTECTED));
    ism.setHasApproximateMarkings(ismMap.get(IsmConstants.HAS_APPROXIMATE_MARKINGS));
    ism.setId(ismMap.get(IsmConstants.ID));
    ism.setIdReference(ismMap.get(IsmConstants.ID_REFERENCE));
    ism.setIsMCATCESVersion(ismMap.get(IsmConstants.ISM_CAT_CES_VERSION));
    ism.setNoAggregation(ismMap.get(IsmConstants.NO_AGGREGATION));
    ism.setNonICmarkings(ismMap.get(IsmConstants.NON_IC_MARKINGS));
    ism.setNonUSControls(ismMap.get(IsmConstants.NON_US_CONTROLS));
    ism.setNoticeDate(ismMap.get(IsmConstants.NOTICE_DATE));
    ism.setNoticeReason(ismMap.get(IsmConstants.NOTICE_REASON));
    ism.setNoticeType(ismMap.get(IsmConstants.NOTICE_TYPE));
    ism.setOwnerProducer(ismMap.get(IsmConstants.OWNER_PRODUCER));
    ism.setJoint(ismMap.get(IsmConstants.JOINT));
    ism.setPocType(ismMap.get(IsmConstants.POC_TYPE));
    ism.setQualifier(ismMap.get(IsmConstants.QUALIFIER));
    ism.setReleasableTo(ismMap.get(IsmConstants.RELEASABLE_TO));
    ism.setResourceElement(ismMap.get(IsmConstants.RESOURCE_ELEMENT));
    ism.setSaRIdentifier(ismMap.get(IsmConstants.SAR_IDENTIFIER));
    ism.setScIControls(ismMap.get(IsmConstants.SCI_CONTROLS));
    ism.setTypeOfExemptedSource(ismMap.get(IsmConstants.TYPE_OF_EXEMPTED_SOURCE));
    ism.setUnregisteredNoticeType(ismMap.get(IsmConstants.UNREGISTERED_NOTICE_TYPE));

    return ism;
  }

  public static Map<String, String> createMapFromIsm(ISM ism) {
    Map<String, String> ismMap = new HashMap<>();

    ismMap.put(IsmConstants.ATOMIC_ENERGY_MARKINGS, ism.getAtomicEnergyMarkings());
    ismMap.put(IsmConstants.CLASSIFICATION, ism.getClassification());
    ismMap.put(IsmConstants.CLASSIFICATION_REASON, ism.getClassificationReason());
    ismMap.put(IsmConstants.CLASSIFIED_BY, ism.getClassifiedBy());
    ismMap.put(IsmConstants.COMPILATION_REASON, ism.getCompilationReason());
    ismMap.put(IsmConstants.COMPLIES_WITH, ism.getCompliesWith());
    ismMap.put(IsmConstants.CREATE_DATE, ism.getCreateDate());
    ismMap.put(IsmConstants.DECLASS_DATE, ism.getDeclassDate());
    ismMap.put(IsmConstants.DECLASS_EVENT, ism.getDeclassEvent());
    ismMap.put(IsmConstants.DECLASS_EXCEPTION, ism.getDeclassException());
    ismMap.put(IsmConstants.DERIVATIVELY_CLASSIFIED_BY, ism.getDerivativelyClassifiedBy());
    ismMap.put(IsmConstants.DERIVED_FROM, ism.getDerivedFrom());
    ismMap.put(IsmConstants.DES_VERSION, ism.getDeSVersion());
    ismMap.put(IsmConstants.DISPLAY_ONLY_TO, ism.getDisplayOnlyTo());
    ismMap.put(IsmConstants.DISSEMINATION_CONTROLS, ism.getDisseminationControls());
    ismMap.put(IsmConstants.EXCLUDE_FROM_ROLLUP, ism.getExcludeFromRollup());
    ismMap.put(IsmConstants.EXEMPT_FROM, ism.getExemptFrom());
    ismMap.put(IsmConstants.EXTERNAL_NOTICE, ism.getExternalNotice());
    ismMap.put(IsmConstants.FGI_SOURCE_OPEN, ism.getFgIsourceOpen());
    ismMap.put(IsmConstants.FGI_SOURCE_PROTECTED, ism.getFgIsourceProtected());
    ismMap.put(IsmConstants.HAS_APPROXIMATE_MARKINGS, ism.getHasApproximateMarkings());
    ismMap.put(IsmConstants.ID, ism.getId());
    ismMap.put(IsmConstants.ID_REFERENCE, ism.getIdReference());
    ismMap.put(IsmConstants.ISM_CAT_CES_VERSION, ism.getIsMCATCESVersion());
    ismMap.put(IsmConstants.NO_AGGREGATION, ism.getNoAggregation());
    ismMap.put(IsmConstants.NON_IC_MARKINGS, ism.getNonICmarkings());
    ismMap.put(IsmConstants.NON_US_CONTROLS, ism.getNonUSControls());
    ismMap.put(IsmConstants.NOTICE_DATE, ism.getNoticeDate());
    ismMap.put(IsmConstants.NOTICE_REASON, ism.getNoticeReason());
    ismMap.put(IsmConstants.NOTICE_TYPE, ism.getNoticeType());
    ismMap.put(IsmConstants.OWNER_PRODUCER, ism.getOwnerProducer());
    ismMap.put(IsmConstants.JOINT, ism.getJoint());
    ismMap.put(IsmConstants.POC_TYPE, ism.getPocType());
    ismMap.put(IsmConstants.QUALIFIER, ism.getQualifier());
    ismMap.put(IsmConstants.RELEASABLE_TO, ism.getReleasableTo());
    ismMap.put(IsmConstants.RESOURCE_ELEMENT, ism.getResourceElement());
    ismMap.put(IsmConstants.SAR_IDENTIFIER, ism.getSaRIdentifier());
    ismMap.put(IsmConstants.SCI_CONTROLS, ism.getScIControls());
    ismMap.put(IsmConstants.TYPE_OF_EXEMPTED_SOURCE, ism.getTypeOfExemptedSource());
    ismMap.put(IsmConstants.UNREGISTERED_NOTICE_TYPE, ism.getUnregisteredNoticeType());

    ismMap = ismMap.entrySet()
        .stream()
        .filter(entry -> !isEmpty(entry.getValue()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    return ismMap;
  }
}
