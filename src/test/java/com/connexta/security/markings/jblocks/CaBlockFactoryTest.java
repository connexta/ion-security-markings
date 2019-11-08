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
import com.connexta.security.markings.common.exceptions.DetailedResponseStatusException;
import com.connexta.security.markings.configuration.JBlocksTestConfiguration;
import com.connexta.security.markings.data.InvalidIsm;
import com.connexta.security.markings.data.ValidIsm;
import java.util.Optional;
import jblocks.dataheaders.classification.CaBlock;
import jblocks.dataheaders.classification.MarkingsDefinition;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@Import(JBlocksTestConfiguration.class)
@Slf4j
public class CaBlockFactoryTest {
  private MarkingsDefinition markingsDefinitionTestInstance;
  private CaBlock caBlockTestInstance;

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

    assertThrows(DetailedResponseStatusException.class, () -> caBlockFactory.create(ism));
  }

  @Test
  public void testCreateGivenIsmWithNullClassificationLevel() {
    ISM ism = InvalidIsm.EMPTY.getIsm();

    CaBlockFactory caBlockFactory =
        new CaBlockFactoryWithCustomBuilder(markingsDefinitionTestInstance, builderMock);

    assertThrows(DetailedResponseStatusException.class, () -> caBlockFactory.create(ism));
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
    ISM ism = ValidIsm.CABLOCK.getIsm();

    when(builderMock.build()).thenReturn(caBlockTestInstance);

    CaBlockFactory caBlockFactory =
        new CaBlockFactoryWithCustomBuilder(markingsDefinitionTestInstance, builderMock);

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

  @Autowired
  public void setMarkingsDefinitionTestInstance(MarkingsDefinition markingsDefinitionTestInstance) {
    this.markingsDefinitionTestInstance = markingsDefinitionTestInstance;
  }

  @Autowired
  public void setCaBlockTestInstance(CaBlock caBlockTestInstance) {
    this.caBlockTestInstance = caBlockTestInstance;
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
