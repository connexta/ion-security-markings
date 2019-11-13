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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.connexta.security.markings.api.rest.models.ISM;
import com.connexta.security.markings.test.resources.data.InvalidIsm;
import com.connexta.security.markings.test.resources.data.ValidIsm;
import com.connexta.security.markings.test.resources.jblocks.JBlocksTestObjects;
import java.util.Optional;
import jblocks.dataheaders.classification.CaBlock;
import jblocks.dataheaders.classification.MarkingsDefinition;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class CaBlockFactoryTest {
  private MarkingsDefinition markingsDefinitionTestInstance = JBlocksTestObjects.getMarkingsDefinition();
  private CaBlock caBlockTestInstance = JBlocksTestObjects.getCaBlock();

  private CaBlock.Builder builderMock = mock(CaBlock.Builder.class);

  @Test
  public void testContructGivenNullMarkingsDefinition() {
    assertThrows(IllegalArgumentException.class, () -> new CaBlockFactory(null));
  }

  @Test
  public void testCreateGivenNullIsm() {
    ISM ism = InvalidIsm.NULL.getIsm();

    CaBlockFactory caBlockFactory =
        new CaBlockFactoryWithCustomBuilder(markingsDefinitionTestInstance, builderMock);

    assertThrows(IllegalArgumentException.class, () -> caBlockFactory.create(ism));
  }

  @Test
  public void testCreateGivenIsmWithNullClassificationLevel() {
    ISM ism = InvalidIsm.EMPTY.getIsm();

    CaBlockFactory caBlockFactory =
        new CaBlockFactoryWithCustomBuilder(markingsDefinitionTestInstance, builderMock);

    assertThrows(IllegalArgumentException.class, () -> caBlockFactory.create(ism));
  }

  @Test
  public void testCreateGivenIsmWithUnclassifiedClassificationLevel() {
    ISM ism = ValidIsm.UNCLASSIFIED.getIsm();

    CaBlockFactory caBlockFactory =
        new CaBlockFactoryWithCustomBuilder(markingsDefinitionTestInstance, builderMock);

    assertTrue(caBlockFactory.create(ism).isEmpty());
  }

  @Test
  public void testCreateGivenIsmWithRelevantFields() {
    ISM ism = ValidIsm.SECRET_CABLOCK.getIsm();

    when(builderMock.build()).thenReturn(caBlockTestInstance);

    CaBlockFactory caBlockFactory =
        new CaBlockFactoryWithCustomBuilder(markingsDefinitionTestInstance, builderMock);

    // throws a null pointer becuase the builderMock.build() returns null by default, and we can't
    // mock a CaBlock
    Optional<CaBlock> caBlockOptional = caBlockFactory.create(ism);
    assertTrue(caBlockOptional.isPresent());
    CaBlock caBlock = caBlockOptional.get();
    assertEquals(caBlockTestInstance, caBlock);

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
