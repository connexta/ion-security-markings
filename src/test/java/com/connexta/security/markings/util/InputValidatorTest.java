/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.util;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.connexta.security.markings.data.InvalidBanner;
import com.connexta.security.markings.data.InvalidIsm;
import com.connexta.security.markings.data.ValidBanner;
import com.connexta.security.markings.data.ValidIsm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@Slf4j
public class InputValidatorTest {
  @ParameterizedTest
  @EnumSource(
      value = InvalidIsm.class,
      names = {"NULL", "EMPTY", "NO_CLASSIFICATION", "SECRET_NO_OWNERPRODUCER"},
      mode = EnumSource.Mode.INCLUDE)
  public void testValidateGivenInvalidIsm(InvalidIsm invalidIsm) {
    log.info("ISM: {}.", invalidIsm.name());

    assertThrows(
        IllegalArgumentException.class, () -> InputValidator.validateIsm(invalidIsm.getIsm()));
  }

  @ParameterizedTest
  @EnumSource(ValidIsm.class)
  public void testValidateGivenValidIsm(ValidIsm validIsm) {
    log.info("ISM: {}.", validIsm.name());

    InputValidator.validateIsm(validIsm.getIsm());
  }

  @ParameterizedTest
  @EnumSource(
      value = InvalidBanner.class,
      names = {"NULL", "EMPTY"},
      mode = EnumSource.Mode.INCLUDE)
  public void testValidateGivenInvalidBanner(InvalidBanner invalidBanner) {
    log.info("Banner: {}", invalidBanner.name());

    assertThrows(
        IllegalArgumentException.class,
        () -> InputValidator.validateBanner(invalidBanner.getBanner()));
  }

  @ParameterizedTest
  @EnumSource(ValidBanner.class)
  public void testValidateGivenInvalidBanner(ValidBanner validBanner) {
    log.info("Banner: {}", validBanner.name());

    InputValidator.validateBanner(validBanner.getBanner());
  }
}
