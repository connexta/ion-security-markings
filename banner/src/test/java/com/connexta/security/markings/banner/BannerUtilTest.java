/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.banner;

import com.connexta.security.markings.api.openapi.models.ISM;
import com.connexta.security.markings.test.resources.data.ValidIsm;
import com.connexta.security.markings.util.IsmUtils;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class BannerUtilTest {
  private BannerUtil bannerUtil = new BannerUtil();

  @Test
  public void testCreateBannerGivenNullIsm() {
    ISM nullIsm = null;

    assertThrows(IllegalArgumentException.class, () -> bannerUtil.createBannerFromIsm(nullIsm));
  }

  @Test
  public void testCreateBannerGivenEmptyIsm() {
    ISM emptyIsm = new ISM();

    assertThrows(IllegalArgumentException.class, () -> bannerUtil.createBannerFromIsm(emptyIsm));
  }

  @ParameterizedTest
  @EnumSource(value = ValidIsm.class)
  public void testCreateBannerGivenValidIsm(ValidIsm validIsm) {
    log.info("ISM: {}.", validIsm.name());

    ISM ism = validIsm.getIsm();
    String banner = validIsm.getBanner();

    log.info("Banner: {}", banner);

    assertEquals(banner, bannerUtil.createBannerFromIsm(ism));
  }

  @ParameterizedTest
  @NullAndEmptySource
  public void testCreateIsmGivenNullAndEmptyBanner(String banner) {
    assertThrows(IllegalArgumentException.class, () -> bannerUtil.createIsmFromBanner(banner));
  }

  @ParameterizedTest
  @EnumSource(value = ValidIsm.class)
  public void testCreateIsmGivenValidBanner(ValidIsm validIsm) {
    log.info("ISM: {}.", validIsm.name());

    ISM ism = validIsm.getIsm();
    String banner = validIsm.getBanner();

    log.info("Banner: {}", banner);

    assertEquals(filterNonBannerAttributes(ism), bannerUtil.createIsmFromBanner(banner));
  }

  private ISM filterNonBannerAttributes(ISM ism) {
    Map<String, String> ismMap = IsmUtils.createMapFromIsm(ism);

    Map<String, String> bannerRelevantIsmMap = ismMap.entrySet().stream()
        .filter(entry -> BannerUtil.BANNER_RELEVANT_ISM_ATTRIBUTE_SET.contains(entry.getKey()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    return IsmUtils.createIsmFromMap(bannerRelevantIsmMap);
  }

  // don't want to create given invalid ism, because it will just throw values into the banner,
  // and the invalid ism enum doesn't have invalid banners associated with it
}
