/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.util.banner;

import com.connexta.security.markings.api.rest.models.ISM;
import com.connexta.security.markings.test.resources.data.InvalidIsm;
import com.connexta.security.markings.test.resources.data.ValidIsm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class BannerCreatorTest {

  @ParameterizedTest
  @EnumSource(
      value = InvalidIsm.class,
      names = {"NULL", "EMPTY"},
      mode = EnumSource.Mode.INCLUDE)
  public void testCreateGivenNullAndEmptyIsm(InvalidIsm invalidIsm) {
    log.info("ISM: {}.", invalidIsm.name());

    ISM ism = invalidIsm.getIsm();

    assertThrows(IllegalArgumentException.class, () -> new BannerCreator().create(ism));
  }

  @ParameterizedTest
  @EnumSource(
      value = InvalidIsm.class,
      names = {"NO_CLASSIFICATION", "SECRET_NO_OWNERPRODUCER"},
      mode = EnumSource.Mode.INCLUDE)
  public void testCreateGivenIsmWithNoClassificationAndIsmWithNoOwnerProducer(
      InvalidIsm invalidIsm) {
    log.info("ISM: {}.", invalidIsm.name());

    ISM ism = invalidIsm.getIsm();

    assertThrows(IllegalArgumentException.class, () -> new BannerCreator().create(ism));
  }

  @ParameterizedTest
  @EnumSource(value = ValidIsm.class)
  public void testCreateGivenValidIsm(ValidIsm validIsm) {
    log.info("ISM: {}.", validIsm.name());

    ISM ism = validIsm.getIsm();
    String banner = validIsm.getValidBanner().getBanner();

    log.info("Banner: {}", banner);

    assertEquals(banner, new BannerCreator().create(ism));
  }

  // don't want to create given invalid ism, because it will just throw values into the banner,
  // and the invalid ism enum doesn't have invalid banners associated with it
}
