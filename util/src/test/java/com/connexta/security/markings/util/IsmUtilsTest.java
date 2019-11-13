package com.connexta.security.markings.util;

import com.connexta.security.markings.api.openapi.models.ISM;
import com.connexta.security.markings.test.resources.data.ValidIsm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class IsmUtilsTest {

  @ParameterizedTest
  @EnumSource(value = ValidIsm.class)
  public void testIsmToMapAndBack(ValidIsm validIsm) {
    log.info("Valid ISM: {}.", validIsm.name());

    ISM ism = validIsm.getIsm();

    assertEquals(ism, IsmUtils.createIsmFromMap(IsmUtils.createMapFromIsm(ism)));
  }
}
