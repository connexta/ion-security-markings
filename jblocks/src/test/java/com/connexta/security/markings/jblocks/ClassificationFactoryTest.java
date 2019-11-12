/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.jblocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.connexta.security.markings.api.rest.models.ISM;
import com.connexta.security.markings.test.resources.jblocks.JBlocksTestObjects;
import com.connexta.security.markings.util.banner.BannerCreator;
import com.connexta.security.markings.test.resources.data.InvalidIsm;
import com.connexta.security.markings.test.resources.data.ValidBanner;
import com.connexta.security.markings.test.resources.data.ValidIsm;
import java.util.Optional;
import jblocks.dataheaders.classification.AccessControls;
import jblocks.dataheaders.classification.CaBlock;
import jblocks.dataheaders.classification.Classification;
import jblocks.dataheaders.classification.MarkingsDefinition;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.ArgumentMatchers;

@Slf4j
public class ClassificationFactoryTest {
  private static final String SYSTEM_HIGH_BANNER = ValidBanner.SECRET_FOUO.getBanner();
  private static final String DEFAULT_BANNER = ValidBanner.SECRET.getBanner();
  private static final ISM DEFAULT_ISM = ValidIsm.SECRET.getIsm();

  private MarkingsDefinition markingsDefinitionTestInstance = JBlocksTestObjects.getMarkingsDefinition();
  private CaBlock caBlockTestInstance = JBlocksTestObjects.getCaBlock();

  private ClassificationFactoryWithCustomBuilder classificationFactory;
  private BannerCreator bannerCreatorMock = mock(BannerCreator.class);
  private AccessControlsFactory accessControlsFactoryMock = mock(AccessControlsFactory.class);
  private CaBlockFactory caBlockFactoryMock = mock(CaBlockFactory.class);
  private Classification.Builder builderMock = mock(Classification.Builder.class);
  private Classification classificationMock = mock(Classification.class);

  @BeforeEach
  public void setup() {
    classificationFactory =
        new ClassificationFactoryWithCustomBuilder(
            markingsDefinitionTestInstance,
            SYSTEM_HIGH_BANNER,
            bannerCreatorMock,
            accessControlsFactoryMock,
            caBlockFactoryMock,
            builderMock);
  }

  @Test()
  public void testConstructWithNullMarkingsDefinition() {
    MarkingsDefinition markingsDefinition = null;
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          new ClassificationFactory(markingsDefinition, SYSTEM_HIGH_BANNER, bannerCreatorMock);
        });
  }

  @ParameterizedTest
  @NullAndEmptySource
  public void testConstructWithNullAndEmptyBanner(String banner) {
    log.info("Banner: {}.", banner);

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          new ClassificationFactory(markingsDefinitionTestInstance, banner, bannerCreatorMock);
        });
  }

  @ParameterizedTest
  @EnumSource(
      value = InvalidIsm.class,
      names = {"NULL", "EMPTY"},
      mode = EnumSource.Mode.INCLUDE)
  public void testCreateGivenNullAndEmptyIsm(InvalidIsm invalidIsm) {
    log.info("ISM: {}.", invalidIsm.name());

    ISM ism = invalidIsm.getIsm();

    assertThrows(IllegalArgumentException.class, () -> classificationFactory.create(ism));
  }

  @Test
  public void testCreateWhenCannotCreateBannerFromIsmAndFactoriesReturnNothing() {
    when(bannerCreatorMock.create(any(ISM.class))).thenReturn(null);
    when(accessControlsFactoryMock.create(any(ISM.class))).thenReturn(Optional.empty());
    when(caBlockFactoryMock.create(any(ISM.class))).thenReturn(Optional.empty());
    when(builderMock.build()).thenReturn(classificationMock);

    Optional<Classification> classificationOptional = classificationFactory.create(DEFAULT_ISM);

    assertTrue(classificationOptional.isPresent());
    Classification classification = classificationOptional.get();
    assertEquals(classificationMock, classification);

    verify(bannerCreatorMock, times(1)).create(DEFAULT_ISM);

    verify(accessControlsFactoryMock, times(1)).create(DEFAULT_ISM);
    verify(caBlockFactoryMock, times(1)).create(DEFAULT_ISM);

    verify(builderMock, times(1)).parse(SYSTEM_HIGH_BANNER);
    verify(builderMock, times(0)).setAccessControls(ArgumentMatchers.any(AccessControls.class));
    verify(builderMock, times(0)).setCaBlock(ArgumentMatchers.any(CaBlock.class));
    verify(builderMock, times(1)).build();
  }

  @Test
  public void testCreateWhenCanCreateBannerFromIsmAndFactoriesReturnSomething() {
    when(bannerCreatorMock.create(ArgumentMatchers.any(ISM.class))).thenReturn(DEFAULT_BANNER);
    when(accessControlsFactoryMock.create(ArgumentMatchers.any(ISM.class))).thenReturn(Optional.empty());
    when(caBlockFactoryMock.create(ArgumentMatchers.any(ISM.class))).thenReturn(Optional.of(caBlockTestInstance));
    when(builderMock.build()).thenReturn(classificationMock);

    Optional<Classification> classificationOptional = classificationFactory.create(DEFAULT_ISM);

    assertTrue(classificationOptional.isPresent());
    Classification classification = classificationOptional.get();
    assertEquals(classificationMock, classification);

    verify(bannerCreatorMock, times(1)).create(DEFAULT_ISM);

    verify(accessControlsFactoryMock, times(1)).create(DEFAULT_ISM);
    verify(caBlockFactoryMock, times(1)).create(DEFAULT_ISM);

    verify(builderMock, times(1)).parse(DEFAULT_BANNER);
    verify(builderMock, times(0)).setAccessControls(ArgumentMatchers.any(AccessControls.class));
    verify(builderMock, times(1)).setCaBlock(caBlockTestInstance);
    verify(builderMock, times(1)).build();
  }

  /** Have to make this test class in order to work around having to re-create a CaBlock builder. */
  private static class ClassificationFactoryWithCustomBuilder extends ClassificationFactory {
    Classification.Builder builder;

    public ClassificationFactoryWithCustomBuilder(
        MarkingsDefinition markingsDefinition,
        String systemHighBannerMarkings,
        BannerCreator bannerCreator,
        AccessControlsFactory accessControlsFactory,
        CaBlockFactory caBlockFactory,
        Classification.Builder builder) {
      super(
          markingsDefinition,
          systemHighBannerMarkings,
          bannerCreator,
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
