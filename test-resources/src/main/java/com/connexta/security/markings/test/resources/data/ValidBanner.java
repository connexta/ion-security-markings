/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.test.resources.data;

public enum ValidBanner {
  UNCLASSIFIED("UNCLASSIFIED"),
  UNCLASSIFIED_FOUO("UNCLASSIFIED//FOUO"),
  UNCLASSIFIED_RELTO("UNCLASSIFIED//REL TO USA, CAN, GBR"),
  UNCLASSIFIED_DISPLAYONLYTO("UNCLASSIFIED//DISPLAY ONLY USA, CAN"),
  UNCLASSIFIED_RELTO_DISPLAYONLYTO("UNCLASSIFIED//REL TO USA, CAN, GBR/DISPLAY ONLY USA, CAN"),

  SECRET("SECRET"),
  SECRET_FOUO("SECRET//FOUO"),
  SECRET_RELTO("SECRET//REL TO USA, CAN, GBR"),
  SECRET_DISPLAYONLYTO("SECRET//DISPLAY ONLY USA, CAN"),
  SECRET_RELTO_DISPLAYONLYTO("SECRET//REL TO USA, CAN, GBR/DISPLAY ONLY USA, CAN"),
  SECRET_NOFORN_ORCON("SECRET//NOFORN/ORCON"),

  TOP_SECRET("TOP SECRET"),
  TOP_SECRET_FOUO("TOP SECRET//FOUO"),
  TOP_SECRET_RELTO("TOP SECRET//REL TO USA, CAN, GBR"),
  TOP_SECRET_DISPLAYONLYTO("TOP SECRET//DISPLAY ONLY USA, CAN"),
  TOP_SECRET_RELTO_DISPLAYONLYTO("TOP SECRET//REL TO USA, CAN, GBR/DISPLAY ONLY USA, CAN"),
  TOP_SECRET_NOFORN_ORCON("TOP SECRET//NOFORN/ORCON"),

  JOINT_SECRET("//JOINT SECRET USA, CAN//REL TO USA, CAN"),
  JOINT_SECRET_FOUO("//JOINT SECRET USA, CAN//REL TO USA, CAN//FOUO"),
  JOINT_SECRET_DISPLAYONLYTO("//JOINT SECRET USA, CAN//REL TO USA, CAN/DISPLAY ONLY USA, CAN"),
  JOINT_SECRET_RELTO_DISPLAYONLYTO(
      "//JOINT SECRET USA, CAN//REL TO USA, CAN, GBR/DISPLAY ONLY USA, CAN"),
  ;

  private String banner;

  ValidBanner(String banner) {
    this.banner = banner;
  }

  public String getBanner() {
    return banner;
  }
}