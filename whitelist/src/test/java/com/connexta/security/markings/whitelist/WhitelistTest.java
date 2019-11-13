package com.connexta.security.markings.whitelist;

import com.connexta.security.markings.api.openapi.models.ISM;
import com.connexta.security.markings.container.MarkingsContainer;
import com.connexta.security.markings.test.resources.data.ValidIsm;
import com.connexta.security.markings.util.IsmUtils;
import com.connexta.security.markings.whitelist.storage.WhitelistStorage;
import com.connexta.security.markings.whitelist.valueset.StringValueSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WhitelistTest {
  private static final ISM DEFAULT_ISM = ValidIsm.SECRET_LETTER.getIsm();
  private static final Map<String, String> DEFAULT_ISM_MAP = IsmUtils.createMapFromIsm(DEFAULT_ISM);

  private Whitelist whitelist;
  private Whitelist.WhitelistMap whitelistMap = new Whitelist.WhitelistMap();;
  private WhitelistStorage whitelistStorageMock = mock(WhitelistStorage.class);
  private MarkingsContainer markingsContainerMock = mock(MarkingsContainer.class);

  @BeforeEach
  public void setup() {
    when(markingsContainerMock.getCorrectedIsmMap()).thenReturn(DEFAULT_ISM_MAP);

    when(whitelistStorageMock.getWhitelistMap()).thenReturn(whitelistMap);

    whitelist = new Whitelist(whitelistStorageMock);
  }

  @Test
  public void testConstructWithNullStorage() {
    WhitelistStorage nullWhitelistStorage = null;

    assertThrows(IllegalArgumentException.class, () -> new Whitelist(nullWhitelistStorage));
  }

  @Test
  public void testIsWhitelistedWithNullWhitelistMap() {
    whitelistMap = null;

    assertFalse(whitelist.getNonWhitelistedMarkings(markingsContainerMock).isEmpty());
  }

  @Test
  public void testIsWhitelistedWithEmptyWhitelistMap() {
    assertFalse(whitelist.getNonWhitelistedMarkings(markingsContainerMock).isEmpty());
  }

  @Test
  public void testIsWhitelistedSuccess() {
    whitelistMap.put(IsmUtils.CLASSIFICATION, new StringValueSet(Set.of(DEFAULT_ISM.getClassification())));
    whitelistMap.put(IsmUtils.OWNER_PRODUCER, new StringValueSet(Set.of(DEFAULT_ISM.getOwnerProducer())));
    whitelistMap.put(IsmUtils.DECLASS_DATE, new StringValueSet(Set.of(DEFAULT_ISM.getDeclassDate())));
    whitelistMap.put(IsmUtils.CLASSIFIED_BY, new StringValueSet(Set.of(DEFAULT_ISM.getClassifiedBy())));
    whitelistMap.put(IsmUtils.DERIVED_FROM, new StringValueSet(Set.of(DEFAULT_ISM.getDerivedFrom())));

    assertTrue(whitelist.getNonWhitelistedMarkings(markingsContainerMock).isEmpty());
  }

  @Test
  public void testIsWhitelistedDifferentCapitalization() {
    whitelistMap.put(IsmUtils.CLASSIFICATION, new StringValueSet(Set.of(DEFAULT_ISM.getClassification().toLowerCase())));
    whitelistMap.put(IsmUtils.OWNER_PRODUCER, new StringValueSet(Set.of(DEFAULT_ISM.getOwnerProducer().toLowerCase())));
    whitelistMap.put(IsmUtils.DECLASS_DATE, new StringValueSet(Set.of(DEFAULT_ISM.getDeclassDate())));
    whitelistMap.put(IsmUtils.CLASSIFIED_BY, new StringValueSet(Set.of(DEFAULT_ISM.getClassifiedBy().toUpperCase())));
    whitelistMap.put(IsmUtils.DERIVED_FROM, new StringValueSet(Set.of(DEFAULT_ISM.getDerivedFrom().toUpperCase())));

    assertTrue(whitelist.getNonWhitelistedMarkings(markingsContainerMock).isEmpty());
  }

  @Test
  public void testIsWhitelistedNoCorrespondingEntry() {
    whitelistMap.put(IsmUtils.CLASSIFICATION, new StringValueSet(Set.of(DEFAULT_ISM.getClassification())));
    whitelistMap.put(IsmUtils.OWNER_PRODUCER, new StringValueSet(Set.of(DEFAULT_ISM.getOwnerProducer())));
    whitelistMap.put(IsmUtils.DECLASS_DATE, new StringValueSet(Set.of(DEFAULT_ISM.getDeclassDate())));
    whitelistMap.put(IsmUtils.CLASSIFIED_BY, new StringValueSet(Set.of(DEFAULT_ISM.getClassifiedBy())));

    assertFalse(whitelist.getNonWhitelistedMarkings(markingsContainerMock).isEmpty());
  }

  @Test
  public void testIsWhitelistedIncorrectCorrespondingEntry() {
    whitelistMap.put(IsmUtils.CLASSIFICATION, new StringValueSet(Set.of(DEFAULT_ISM.getClassification())));
    whitelistMap.put(IsmUtils.OWNER_PRODUCER, new StringValueSet(Set.of(DEFAULT_ISM.getOwnerProducer())));
    whitelistMap.put(IsmUtils.DECLASS_DATE, new StringValueSet(Set.of(DEFAULT_ISM.getDeclassDate())));
    whitelistMap.put(IsmUtils.CLASSIFIED_BY, new StringValueSet(Set.of(DEFAULT_ISM.getClassifiedBy())));
    whitelistMap.put(IsmUtils.DERIVED_FROM, new StringValueSet(Set.of("x^3")));

    assertFalse(whitelist.getNonWhitelistedMarkings(markingsContainerMock).isEmpty());
  }
}
