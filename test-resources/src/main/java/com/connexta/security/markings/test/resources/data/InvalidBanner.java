/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.test.resources.data;

public enum InvalidBanner {
  NULL(null),
  EMPTY(""),

  // out of order
  FOUO_UNCLASSIFIED("FOUO//UNCLASSIFIED"),
  RELTO_SECRET("REL TO USA, CAN, GBR//SECRET"),
  SAP_TOP_SECRET("SPECIAL ACCESS REQUIRED-TWISTED FEATHER//TOP SECRET"),
  NOFORN_ORCON_JOINT_SECRET("NOFORN/ORCON//JOINT SECRET USA, CAN"),

  // incomplete
  FOUO("FOUO"),
  DELIMITER_NOFORN_ORCON("//NOFORN/ORCON"),
  SECRET_RELTO_NO_COUNTRIES("SECRET//REL TO"),
  JOINT_SECRET_NO_COUNTRIES("JOINT SECRET"),

  // contradictory
  UNCLASSIFIED_NOFORN_ORCON("UNCLASSIFIED/NOFORN/ORCON"),
  TOP_SECRET_NOFORN_RELEASABLETO("TOP SECRET//REL TO USA, CAN//NOFORN"),

  // close
  UNCLASS("UNCLASS"),
  SECRET_FOUOUO("SECRET//FOUOUO"),
  TOP_SUB_DELIMITER_SECRET("TOP/SECRET"),

  // gibberish
  GIBBERISH("ALDNASJ22YI"),
  UNCLASSIFIED_GIBBERISH("UNCLASSIFIED//KANALDOLEN"),
  SECRET_GIBBERISH_GIBBERISH("SECRET//NAOSIDU//ANENWNWN/POWNENH"),
  DELIMITER("//"),
  SUPER_POWERS("SUPER//POWERS"),
  ;

  private String banner;

  InvalidBanner(String banner) {
    this.banner = banner;
  }

  public String getBanner() {
    return banner;
  }
}
