/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.spring.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.connexta.security.markings.api.openapi.models.ISM;
import com.connexta.security.markings.container.MarkingsContainer;
import com.connexta.security.markings.container.factory.MarkingsContainerFactory;
import com.connexta.security.markings.spring.exceptions.DetailedResponseStatusException;
import com.connexta.security.markings.test.resources.data.ValidIsm;
import com.connexta.security.markings.test.resources.jblocks.JBlocksTestObjects;
import com.connexta.security.markings.whitelist.Whitelist;
import java.util.Collections;
import java.util.Map;
import jblocks.dataheaders.classification.Classification;
import jblocks.dataheaders.classification.MarkingsDefinition;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public class ValidationServiceTest {
  private static final MarkingsDefinition MARKINGS_DEFINITION_TEST_INSTANCE = JBlocksTestObjects.getMarkingsDefinition();
  private static final ISM SYSTEM_HIGH_ISM = ValidIsm.FOUO.getIsm();
  private static final ISM DEFAULT_ISM = ValidIsm.SECRET.getIsm();

  private MarkingsContainerFactory markingsContainerFactoryMock = mock(MarkingsContainerFactory.class);
  private MarkingsContainer systemHighMarkingsContainerMock = mock(MarkingsContainer.class);
  private MarkingsContainer markingsContainerMock = mock(MarkingsContainer.class);
  private Classification systemHighClassificationMock = mock(Classification.class);
  private Classification classificationMock = mock(Classification.class);
  private Whitelist whitelistMock = mock(Whitelist.class);
  private ValidationService validationService;

  @BeforeEach
  public void setup() {
    when(markingsContainerFactoryMock.create(SYSTEM_HIGH_ISM))
        .thenReturn(systemHighMarkingsContainerMock);
    when(markingsContainerFactoryMock.create(DEFAULT_ISM)).thenReturn(markingsContainerMock);

    when(systemHighMarkingsContainerMock.getClassification()).thenReturn(systemHighClassificationMock);
    when(markingsContainerMock.getClassification()).thenReturn(classificationMock);

    when(systemHighClassificationMock.covers(classificationMock)).thenReturn(true);

    when(whitelistMock.getNonWhitelistedMarkings(markingsContainerMock)).thenReturn(Collections.emptyMap());

    validationService =
        new ValidationService(
            MARKINGS_DEFINITION_TEST_INSTANCE,
            SYSTEM_HIGH_ISM,
            whitelistMock,
            markingsContainerFactoryMock);
  }

  @Test
  public void testConstructWithNullMarkingsDefinition() {
    MarkingsDefinition nullMarkingsDefinition = null;

    assertThrows(
        IllegalArgumentException.class,
        () -> new ValidationService(nullMarkingsDefinition, SYSTEM_HIGH_ISM, whitelistMock));
  }

  @Test
  public void testConstructWithNullSystemHighIsm() {
    ISM nullSystemHighIsm = null;

    assertThrows(
        IllegalArgumentException.class,
        () -> new ValidationService(MARKINGS_DEFINITION_TEST_INSTANCE, nullSystemHighIsm, whitelistMock));
  }

  @Test
  public void testConstructWhenClassificationFactoryCannotCreateSystemHighClassification() {
    when(markingsContainerFactoryMock.create(SYSTEM_HIGH_ISM)).thenThrow(IllegalArgumentException.class);

    assertThrows(
        IllegalArgumentException.class,
        () ->
            new ValidationService(
                MARKINGS_DEFINITION_TEST_INSTANCE,
                SYSTEM_HIGH_ISM,
                whitelistMock,
                markingsContainerFactoryMock));
  }

  @Test
  public void testValidateGivenNullIsm() {
    ISM nullIsm = null;
    when(markingsContainerFactoryMock.create(nullIsm)).thenThrow(IllegalArgumentException.class);

    assertThrows(
        DetailedResponseStatusException.class,
        () -> validationService.validate(nullIsm));
  }

  @Test
  public void testValidateWhenMarkingsContainerFactoryCannotCreateClassification() {
    when(markingsContainerFactoryMock.create(DEFAULT_ISM)).thenThrow(IllegalArgumentException.class);

    assertThrows(
        DetailedResponseStatusException.class,
        () -> validationService.validate(DEFAULT_ISM));
  }

  @Test
  public void testSuccessfulValidation() {
    // this not throwing errors is the only verification needed
    validationService.validate(DEFAULT_ISM);
  }

  @Test
  public void testValidateWhenNotCoveredBySystemHigh() {
    when(systemHighClassificationMock.covers(classificationMock)).thenReturn(false);

    assertThrows(
        DetailedResponseStatusException.class,
        () -> validationService.validate(DEFAULT_ISM));
  }

  @Test
  public void testValidateWhenNotWhitelisted() {
    when(whitelistMock.getNonWhitelistedMarkings(any())).thenReturn(Map.of("classification", "STS"));

    assertThrows(
        DetailedResponseStatusException.class,
        () -> validationService.validate(DEFAULT_ISM));
  }
}
