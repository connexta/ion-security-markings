/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.jblocks.factory;

import com.connexta.security.markings.api.openapi.models.ISM;
import com.connexta.security.markings.test.resources.data.InvalidIsm;
import com.connexta.security.markings.test.resources.data.ValidIsm;
import com.connexta.security.markings.test.resources.jblocks.JBlocksTestObjects;
import java.util.Optional;
import jblocks.dataheaders.classification.CaBlock;
import jblocks.dataheaders.classification.MarkingsDefinition;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
public class CaBlockFactoryTest {
  private static final MarkingsDefinition MARKINGS_DEFINITION_TEST_INSTANCE = JBlocksTestObjects.getMarkingsDefinition();
  private static final CaBlock CA_BLOCK_TEST_INSTANCE = JBlocksTestObjects.getCaBlock();

  private CaBlock.Builder builderMock = mock(CaBlock.Builder.class);

  @Test
  public void testConstructGivenNullMarkingsDefinition() {
    MarkingsDefinition nullMarkingsDefinition = null;

    assertThrows(IllegalArgumentException.class, () -> new CaBlockFactory(nullMarkingsDefinition));
  }

  @Test
  public void testCreateGivenNullIsmWrapper() {
    ISM nullIsm = null;

    CaBlockFactory caBlockFactory =
        new CaBlockFactoryWithCustomBuilder(MARKINGS_DEFINITION_TEST_INSTANCE, builderMock);

    assertThrows(IllegalArgumentException.class, () -> caBlockFactory.create(nullIsm));
  }

  @Test
  public void testCreateGivenIsmWithNullClassificationLevel() {
    ISM ism = InvalidIsm.NO_CLASSIFICATION.getIsm();

    CaBlockFactory caBlockFactory =
        new CaBlockFactoryWithCustomBuilder(MARKINGS_DEFINITION_TEST_INSTANCE, builderMock);

    assertTrue(caBlockFactory.create(ism).isEmpty());
  }

  @Test
  public void testCreateGivenIsmWithUnclassifiedClassificationLevel() {
    ISM ism = ValidIsm.UNCLASSIFIED.getIsm();

    CaBlockFactory caBlockFactory =
        new CaBlockFactoryWithCustomBuilder(MARKINGS_DEFINITION_TEST_INSTANCE, builderMock);

    assertTrue(caBlockFactory.create(ism).isEmpty());
  }

  @Test
  public void testCreateGivenIsmWithRelevantFields() {
    ISM ism = ValidIsm.CABLOCK.getIsm();

    when(builderMock.build()).thenReturn(CA_BLOCK_TEST_INSTANCE);

    CaBlockFactory caBlockFactory =
        new CaBlockFactoryWithCustomBuilder(MARKINGS_DEFINITION_TEST_INSTANCE, builderMock);

    Optional<CaBlock> optionalCaBlock = caBlockFactory.create(ism);

    assertEquals(CA_BLOCK_TEST_INSTANCE, optionalCaBlock.get());

    verify(builderMock, times(1)).setClassificationReason(ism.getClassificationReason());
    verify(builderMock, times(1)).setClassifiedBy(ism.getClassifiedBy());
    verify(builderMock, times(1)).setDeclassifyOn(ism.getDeclassDate());
    verify(builderMock, times(1)).setDerivedFrom(ism.getDerivedFrom());
    verify(builderMock, times(1)).build();
  }

  /** Have to make this test class in order to work around having to re-create a CaBlock builder. */
  private static class CaBlockFactoryWithCustomBuilder extends CaBlockFactory {
    private CaBlock.Builder builder;

    public CaBlockFactoryWithCustomBuilder(
        MarkingsDefinition markingsDefinition, CaBlock.Builder builder) {
      super(markingsDefinition);
      this.builder = builder;
    }

    @Override
    protected CaBlock.Builder createBuilder() {
      return builder;
    }
  }
}
