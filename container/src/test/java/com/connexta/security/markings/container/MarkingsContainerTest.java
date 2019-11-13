package com.connexta.security.markings.container;

import com.connexta.security.markings.api.openapi.models.ISM;
import com.connexta.security.markings.banner.BannerUtil;
import com.connexta.security.markings.jblocks.factory.ClassificationFactory;
import com.connexta.security.markings.test.resources.data.ValidIsm;
import com.connexta.security.markings.test.resources.jblocks.JBlocksTestObjects;
import com.connexta.security.markings.util.IsmUtils;
import java.util.Map;
import java.util.Optional;
import jblocks.dataheaders.classification.Classification;
import jblocks.dataheaders.classification.MarkingsDefinition;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
public class MarkingsContainerTest {
  private static final MarkingsDefinition DEFAULT_MARKINGS_DEFINITION = JBlocksTestObjects.getMarkingsDefinition();
  private static final ISM DEFAULT_ISM = ValidIsm.SECRET.getIsm();
  private static final Map<String, String> DEFAULT_ISM_MAP = IsmUtils.createMapFromIsm(DEFAULT_ISM);
  private static final String DEFAULT_BANNER = ValidIsm.SECRET.getBanner();
  private static final ISM DEFAULT_CORRECTED_ISM = ValidIsm.FOUO.getIsm();
  private static final Map<String, String> DEFAULT_CORRECTED_ISM_MAP = IsmUtils.createMapFromIsm(DEFAULT_CORRECTED_ISM);
  private static final String DEFAULT_CORRECTED_BANNER = ValidIsm.FOUO.getBanner();

  private BannerUtil bannerUtilMock = mock(BannerUtil.class);
  private ClassificationFactory classificationFactoryMock = mock (ClassificationFactory.class);
  private Classification classificationMock = mock(Classification.class);

  private MarkingsContainer markingsContainer;

  @BeforeEach
  public void setup() {
    when(bannerUtilMock.createBannerFromIsm(DEFAULT_ISM)).thenReturn(DEFAULT_BANNER);
    when(bannerUtilMock.createIsmFromBanner(DEFAULT_CORRECTED_BANNER)).thenReturn(DEFAULT_CORRECTED_ISM);
    when(classificationFactoryMock.create(DEFAULT_ISM, DEFAULT_BANNER)).thenReturn(Optional.of(classificationMock));
    when(classificationMock.toBanner()).thenReturn(DEFAULT_CORRECTED_BANNER);

    markingsContainer = new MarkingsContainer(DEFAULT_MARKINGS_DEFINITION, DEFAULT_ISM, bannerUtilMock, classificationFactoryMock);
  }

  @Test
  public void testConstructWithNullMarkingsDefinition() {
    MarkingsDefinition nullMarkingsDefinition = null;

    assertThrows(IllegalArgumentException.class, () -> new MarkingsContainer(nullMarkingsDefinition, DEFAULT_ISM, bannerUtilMock, classificationFactoryMock));
  }

  @Test
  public void testConstructWithNullIsm() {
    ISM nullIsm = null;

    assertThrows(IllegalArgumentException.class, () -> new MarkingsContainer(DEFAULT_MARKINGS_DEFINITION, nullIsm, bannerUtilMock, classificationFactoryMock));
  }

  @Test
  public void testConstructWithNullBannerUtil() {
    BannerUtil nullBannerUtil = null;

    assertThrows(IllegalArgumentException.class, () -> new MarkingsContainer(DEFAULT_MARKINGS_DEFINITION, DEFAULT_ISM, nullBannerUtil, classificationFactoryMock));
  }

  @Test
  public void testConstructWithNullClassificationFactory() {
    ClassificationFactory nullClassificationFactory = null;

    assertThrows(IllegalArgumentException.class, () -> new MarkingsContainer(DEFAULT_MARKINGS_DEFINITION, DEFAULT_ISM, bannerUtilMock, nullClassificationFactory));
  }

  @Test
  public void testGetIsmMap() {
    assertEquals(DEFAULT_ISM_MAP, markingsContainer.getIsmMap());
  }

  @Test
  public void testGetIsmMapThrowsException() {
    when(bannerUtilMock.createBannerFromIsm(DEFAULT_ISM)).thenThrow(new IllegalArgumentException());

    assertThrows(IllegalArgumentException.class, () -> markingsContainer.getBanner());
  }

  @Test
  public void testGetBanner() {
    assertEquals(DEFAULT_BANNER, markingsContainer.getBanner());
  }

  @Test
  public void testGetBannerThrowsException() {
    when(bannerUtilMock.createBannerFromIsm(DEFAULT_ISM)).thenThrow(new IllegalArgumentException());

    assertThrows(IllegalArgumentException.class, () -> markingsContainer.getBanner());
  }

  @Test
  public void testGetClassification() {
    assertEquals(classificationMock, markingsContainer.getClassification());
  }

  @Test
  public void testGetClassificationEmptyOptional() {
    when(classificationFactoryMock.create(DEFAULT_ISM, DEFAULT_BANNER)).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class, () -> markingsContainer.getClassification());
  }

  @Test
  public void testGetClassificationThrowsException() {
    when(classificationFactoryMock.create(DEFAULT_ISM, DEFAULT_BANNER)).thenThrow(new IllegalArgumentException());

    assertThrows(IllegalArgumentException.class, () -> markingsContainer.getClassification());
  }

  @Test
  public void testGetCorrectedBanner() {
    assertEquals(DEFAULT_BANNER, markingsContainer.getBanner());
  }

  @Test
  public void testGetCorrectedBannerThrowsException() {
    when(classificationMock.toBanner()).thenThrow(new IllegalArgumentException());

    assertThrows(IllegalArgumentException.class, () -> markingsContainer.getCorrectedBanner());
  }

  @Test
  public void testGetCorrectedIsmAndMap() {
    assertEquals(DEFAULT_CORRECTED_ISM, markingsContainer.getCorrectedIsm());
    assertEquals(DEFAULT_CORRECTED_ISM_MAP, markingsContainer.getCorrectedIsmMap());
  }

  @Test
  public void testGetCorrectedIsmAndMapThrowsException() {
    when(bannerUtilMock.createIsmFromBanner(DEFAULT_CORRECTED_BANNER)).thenThrow(new IllegalArgumentException());

    assertThrows(IllegalArgumentException.class, () -> markingsContainer.getCorrectedIsm());
    assertThrows(IllegalArgumentException.class, () -> markingsContainer.getCorrectedIsmMap());
  }
}
