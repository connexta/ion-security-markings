/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.test.resources.data;

import com.connexta.security.markings.api.openapi.models.ISM;

public enum ValidIsm {
  // classifications
  UNCLASSIFIED(
      new ISM().classification("UNCLASSIFIED").ownerProducer("USA"), "UNCLASSIFIED"),
  UNCLASSIFIED_LETTER(
      new ISM().classification("U").ownerProducer("USA"), "U"),

  CONFIDENTIAL(
      new ISM()
          .classification("CONFIDENTIAL")
          .ownerProducer("USA")
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      "CONFIDENTIAL"),
  CONFIDENTIAL_LETTER(
      new ISM()
          .classification("C")
          .ownerProducer("USA")
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      "C"),

  RESTRICTED(
      new ISM()
          .classification("RESTRICTED")
          .ownerProducer("USA")
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      "RESTRICTED"),
  RESTRICTED_LETTER(
      new ISM()
          .classification("R")
          .ownerProducer("USA")
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      "R"),

  SECRET(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA")
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      "SECRET"),
  SECRET_LETTER(
      new ISM()
          .classification("S")
          .ownerProducer("USA")
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      "S"),

  TOP_SECRET(
      new ISM()
          .classification("TOP SECRET")
          .ownerProducer("USA")
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      "TOP SECRET"),
  TOP_SECRET_LETTER(
      new ISM()
          .classification("TS")
          .ownerProducer("USA")
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      "TS"),

  JOINT_SECRET(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA, CAN")
          .releasableTo("USA, CAN")
          .joint("true")
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      "//JOINT SECRET USA, CAN//REL TO USA, CAN"),
  JOINT_SECRET_LETTER(
      new ISM()
          .classification("S")
          .ownerProducer("USA, CAN")
          .releasableTo("USA, CAN")
          .joint("true")
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      "//JOINT S USA, CAN//REL TO USA, CAN"),

  // dissemination controls
  FOUO(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA")
          .declassDate("19991230")
          .disseminationControls("FOUO")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      "SECRET//FOUO"),

  CABLOCK(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA")
          .classificationReason("Important Stuff")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2")
          .declassDate("19991230"),
      "SECRET"),
  ;

  private ISM ism;
  private String banner;

  ValidIsm(ISM ism, String banner) {
    this.ism = ism;
    this.banner = banner;
  }

  public ISM getIsm() {
    return ism;
  }

  public String getBanner() {
    return banner;
  }
}
