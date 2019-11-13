/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.jblocks.factory;

import com.connexta.security.markings.api.openapi.models.ISM;
import com.connexta.security.markings.test.resources.data.ValidIsm;
import com.connexta.security.markings.test.resources.jblocks.JBlocksTestObjects;
import java.util.Optional;
import jblocks.dataheaders.classification.AccessControls;
import jblocks.dataheaders.classification.CaBlock;
import jblocks.dataheaders.classification.Classification;
import jblocks.dataheaders.classification.MarkingsDefinition;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
public class ClassificationFactoryTest {
  private static final ISM DEFAULT_ISM = ValidIsm.SECRET.getIsm();
  private static final String DEFAULT_BANNER = ValidIsm.SECRET.getBanner();
  private static final MarkingsDefinition MARKINGS_DEFINITION_TEST_INSTANCE = JBlocksTestObjects.getMarkingsDefinition();
  private static final AccessControls ACCESS_CONTROLS_TEST_INSTANCE = JBlocksTestObjects.getAccessControls();
  private static final CaBlock CA_BLOCK_TEST_INSTANCE = JBlocksTestObjects.getCaBlock();


  private ClassificationFactoryWithCustomBuilder classificationFactory;
  private AccessControlsFactory accessControlsFactoryMock = mock(AccessControlsFactory.class);
  private CaBlockFactory caBlockFactoryMock = mock(CaBlockFactory.class);
  private Classification.Builder builderMock = mock(Classification.Builder.class);
  private Classification classificationMock = mock(Classification.class);

  @Test
  public void testConstructWithNullMarkingsDefinition() {
    MarkingsDefinition nullMarkingsDefinition = null;
    assertThrows(
        IllegalArgumentException.class,
        () -> new ClassificationFactory(nullMarkingsDefinition));
  }

  @Test
  public void testCreateGivenNullIsm() {
    ISM nullIsm = null;

    classificationFactory =
        new ClassificationFactoryWithCustomBuilder(
            MARKINGS_DEFINITION_TEST_INSTANCE,
            accessControlsFactoryMock,
            caBlockFactoryMock,
            builderMock);

    assertThrows(IllegalArgumentException.class, () -> classificationFactory.create(nullIsm, DEFAULT_BANNER));
  }

  @Test
  public void testCreateGivenNullBanner() {
    String nullBanner = null;

    classificationFactory =
        new ClassificationFactoryWithCustomBuilder(
            MARKINGS_DEFINITION_TEST_INSTANCE,
            accessControlsFactoryMock,
            caBlockFactoryMock,
            builderMock);

    assertThrows(IllegalArgumentException.class, () -> classificationFactory.create(DEFAULT_ISM, nullBanner));
  }

  @Test
  public void testCreateGivenEmptyBanner() {
    String emptyBanner = "";

    classificationFactory =
        new ClassificationFactoryWithCustomBuilder(
            MARKINGS_DEFINITION_TEST_INSTANCE,
            accessControlsFactoryMock,
            caBlockFactoryMock,
            builderMock);

    assertThrows(IllegalArgumentException.class, () -> classificationFactory.create(DEFAULT_ISM, emptyBanner));
  }

  @Test
  public void testCreateWhenFactoriesReturnNothing() {
    when(accessControlsFactoryMock.create(any(ISM.class))).thenReturn(Optional.empty());
    when(caBlockFactoryMock.create(any(ISM.class))).thenReturn(Optional.empty());
    when(builderMock.build()).thenReturn(classificationMock);

    classificationFactory =
        new ClassificationFactoryWithCustomBuilder(
            MARKINGS_DEFINITION_TEST_INSTANCE,
            accessControlsFactoryMock,
            caBlockFactoryMock,
            builderMock);

    Optional<Classification> optionalClassification = classificationFactory.create(DEFAULT_ISM, DEFAULT_BANNER);

    assertEquals(classificationMock, optionalClassification.get());

    verify(accessControlsFactoryMock, times(1)).create(DEFAULT_ISM);
    verify(caBlockFactoryMock, times(1)).create(DEFAULT_ISM);

    verify(builderMock, times(1)).parse(DEFAULT_BANNER);
    verify(builderMock, times(0)).setAccessControls(any(AccessControls.class));
    verify(builderMock, times(0)).setCaBlock(any(CaBlock.class));
    verify(builderMock, times(1)).build();
  }

  @Test
  public void testCreateWhenFactoriesReturnSomething() {
    when(accessControlsFactoryMock.create(any(ISM.class))).thenReturn(Optional.of(ACCESS_CONTROLS_TEST_INSTANCE));
    when(caBlockFactoryMock.create(any(ISM.class))).thenReturn(Optional.of(CA_BLOCK_TEST_INSTANCE));
    when(builderMock.build()).thenReturn(classificationMock);

    classificationFactory =
        new ClassificationFactoryWithCustomBuilder(
            MARKINGS_DEFINITION_TEST_INSTANCE,
            accessControlsFactoryMock,
            caBlockFactoryMock,
            builderMock);

    Optional<Classification> optionalClassification = classificationFactory.create(DEFAULT_ISM, DEFAULT_BANNER);

    assertEquals(classificationMock, optionalClassification.get());

    verify(accessControlsFactoryMock, times(1)).create(DEFAULT_ISM);
    verify(caBlockFactoryMock, times(1)).create(DEFAULT_ISM);

    verify(builderMock, times(1)).parse(DEFAULT_BANNER);
    verify(builderMock, times(1)).setAccessControls(ACCESS_CONTROLS_TEST_INSTANCE);
    verify(builderMock, times(1)).setCaBlock(CA_BLOCK_TEST_INSTANCE);
    verify(builderMock, times(1)).build();
  }

  /** Have to make this test class in order to work around having to re-create a CaBlock builder. */
  private static class ClassificationFactoryWithCustomBuilder extends ClassificationFactory {
    private Classification.Builder builder;

    public ClassificationFactoryWithCustomBuilder(
        MarkingsDefinition markingsDefinition,
        AccessControlsFactory accessControlsFactory,
        CaBlockFactory caBlockFactory,
        Classification.Builder builder) {
      super(
          markingsDefinition,
          accessControlsFactory,
          caBlockFactory);
      this.builder = builder;
    }

    @Override
    protected Classification.Builder createBuilder() {
      return builder;
    }
  }
}
