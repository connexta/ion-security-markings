/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.connexta.security.markings.configuration.JBlocksTestConfiguration;
import com.connexta.security.markings.data.ValidBanner;
import com.connexta.security.markings.data.ValidIsm;
import com.connexta.security.markings.jblocks.ClassificationFactory;
import com.connexta.security.markings.rest.models.ISM;
import com.connexta.security.markings.rest.models.SecurityMarkings;
import java.util.Optional;
import jblocks.dataheaders.classification.Classification;
import jblocks.dataheaders.classification.MarkingsDefinition;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@Import(JBlocksTestConfiguration.class)
@Slf4j
public class SecurityMarkingsServiceImplTest {
  private static final String SYSTEM_HIGH_BANNER = ValidBanner.SECRET.getBanner();
  private static final ISM DEFAULT_ISM = ValidIsm.SECRET.getIsm();
  private static final SecurityMarkings DEFAULT_SECURITY_MARKINGS =
      new SecurityMarkings().ISM(DEFAULT_ISM);

  private MarkingsDefinition markingsDefinitionTestInstance;

  private ClassificationFactory classificationFactoryMock = mock(ClassificationFactory.class);
  private Classification unclassifiedClassificationMock = mock(Classification.class);
  private Classification secretClassificationMock = mock(Classification.class);
  private Classification topSecretClassificationMock = mock(Classification.class);
  private SecurityMarkingsServiceImpl securityMarkingsServiceImpl;

  @BeforeEach
  public void setup() {
    when(unclassifiedClassificationMock.covers(unclassifiedClassificationMock)).thenReturn(true);
    when(unclassifiedClassificationMock.covers(secretClassificationMock)).thenReturn(false);
    when(unclassifiedClassificationMock.covers(topSecretClassificationMock)).thenReturn(false);
    when(secretClassificationMock.covers(unclassifiedClassificationMock)).thenReturn(true);
    when(secretClassificationMock.covers(secretClassificationMock)).thenReturn(true);
    when(secretClassificationMock.covers(topSecretClassificationMock)).thenReturn(false);
    when(topSecretClassificationMock.covers(any())).thenReturn(true);

    when(classificationFactoryMock.create(SYSTEM_HIGH_BANNER))
        .thenReturn(Optional.of(secretClassificationMock));

    securityMarkingsServiceImpl =
        new SecurityMarkingsServiceImpl(
            markingsDefinitionTestInstance, SYSTEM_HIGH_BANNER, classificationFactoryMock);
  }

  @Test
  public void testConstructWithNullMarkingsDefinition() {
    MarkingsDefinition markingsDefinition = null;

    assertThrows(
        IllegalArgumentException.class,
        () -> new SecurityMarkingsServiceImpl(markingsDefinition, SYSTEM_HIGH_BANNER));
  }

  @ParameterizedTest
  @NullAndEmptySource
  public void testConstructWithNullAndEmptyBanner(String banner) {
    log.info("Banner: {}.", banner);

    assertThrows(
        IllegalArgumentException.class,
        () -> new SecurityMarkingsServiceImpl(markingsDefinitionTestInstance, banner));
  }

  @Test
  public void testConstructWhenClassificationFactoryCannotCreateSystemHighClassification() {
    when(classificationFactoryMock.create(SYSTEM_HIGH_BANNER)).thenReturn(Optional.empty());

    assertThrows(
        IllegalArgumentException.class,
        () ->
            new SecurityMarkingsServiceImpl(
                markingsDefinitionTestInstance, SYSTEM_HIGH_BANNER, classificationFactoryMock));
  }

  @Test
  public void testValidateGivenNullSecurityMarkings() {
    SecurityMarkings securityMarkings = null;

    assertThrows(
        IllegalArgumentException.class,
        () -> securityMarkingsServiceImpl.validate(securityMarkings));
  }

  @Test
  public void testValidateGivenSecurityMarkingsWithNullIsm() {
    ISM ism = null;
    SecurityMarkings securityMarkings = new SecurityMarkings();
    securityMarkings.setISM(ism);

    assertThrows(
        IllegalArgumentException.class,
        () -> securityMarkingsServiceImpl.validate(securityMarkings));
  }

  @Test
  public void testValidateWhenClassificationFactoryCannotCreateClassification() {
    when(classificationFactoryMock.create(DEFAULT_ISM)).thenReturn(Optional.empty());

    assertThrows(
        RuntimeException.class,
        () -> securityMarkingsServiceImpl.validate(DEFAULT_SECURITY_MARKINGS));
  }

  @Test
  public void testValidateWhenCoveredBySystemHigh() {
    when(classificationFactoryMock.create(DEFAULT_ISM))
        .thenReturn(Optional.of(unclassifiedClassificationMock));

    // this not throwing errors is the only verification needed
    securityMarkingsServiceImpl.validate(DEFAULT_SECURITY_MARKINGS);
  }

  @Test
  public void testValidateWhenNotCoveredBySystemHigh() {
    when(classificationFactoryMock.create(DEFAULT_ISM))
        .thenReturn(Optional.of(topSecretClassificationMock));

    assertThrows(
        RuntimeException.class,
        () -> securityMarkingsServiceImpl.validate(DEFAULT_SECURITY_MARKINGS));
  }

  @Autowired
  public void setMarkingsDefinitionTestInstance(MarkingsDefinition markingsDefinitionTestInstance) {
    this.markingsDefinitionTestInstance = markingsDefinitionTestInstance;
  }
}
