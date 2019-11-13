/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.util.validation;

import com.connexta.security.markings.api.rest.models.ISM;
import com.connexta.security.markings.api.rest.models.SecurityMarkings;
import com.connexta.security.markings.test.resources.data.InvalidBanner;
import com.connexta.security.markings.test.resources.data.InvalidIsm;
import com.connexta.security.markings.test.resources.data.ValidBanner;
import com.connexta.security.markings.test.resources.data.ValidIsm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
        IllegalArgumentException.class,
        () -> InputValidator.validateIsm(invalidIsm.getIsm()));
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
  public void testValidateGivenNullAndEmptyBanner(InvalidBanner invalidBanner) {
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

  @Test
  public void testValidateGivenValidSecurityMarkings() {
    SecurityMarkings securityMarkings = new SecurityMarkings();
    securityMarkings.setISM(new ISM());

    InputValidator.validateSecurityMarkings(securityMarkings);
  }

  @Test
  public void testValidateGivenNullAndEmptySecurityMarkings() {
    SecurityMarkings securityMarkingsNull = null;
    SecurityMarkings securityMarkingsEmpty = new SecurityMarkings();

    assertThrows(
        IllegalArgumentException.class,
        () -> InputValidator.validateSecurityMarkings(securityMarkingsNull));
    assertThrows(
        IllegalArgumentException.class,
        () -> InputValidator.validateSecurityMarkings(securityMarkingsEmpty));
  }
}
