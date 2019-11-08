/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.data;

public enum ValidBanner {
  // classifications
  UNCLASSIFIED("UNCLASSIFIED"),
  UNCLASSIFIED_LETTER("U"),

  CONFIDENTIAL("CONFIDENTIAL"),
  CONFIDENTIAL_LETTER("C"),

  RESTRICTED("RESTRICTED"),
  RESTRICTED_LETTER("R"),

  SECRET("SECRET"),
  SECRET_LETTER("S"),

  TOP_SECRET("TOP SECRET"),
  TOP_SECRET_LETTER("TS"),

  JOINT_SECRET("//JOINT SECRET USA, CAN//REL TO USA, CAN"),
  JOINT_SECRET_LETTER("//JOINT S USA, CAN//REL TO USA, CAN"),

  FOUO("SECRET//FOUO"),
  ;

  private String banner;

  ValidBanner(String banner) {
    this.banner = banner;
  }

  public String getBanner() {
    return banner;
  }
}
