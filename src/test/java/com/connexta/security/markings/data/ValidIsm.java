/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.data;

import com.connexta.security.markings.api.rest.models.ISM;

public enum ValidIsm {
  // classifications
  UNCLASSIFIED(
      new ISM().classification("UNCLASSIFIED").ownerProducer("USA"), ValidBanner.UNCLASSIFIED),
  UNCLASSIFIED_LETTER(
      new ISM().classification("U").ownerProducer("USA"), ValidBanner.UNCLASSIFIED_LETTER),

  CONFIDENTIAL(
      new ISM()
          .classification("CONFIDENTIAL")
          .ownerProducer("USA")
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.CONFIDENTIAL),
  CONFIDENTIAL_LETTER(
      new ISM()
          .classification("C")
          .ownerProducer("USA")
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.CONFIDENTIAL_LETTER),

  RESTRICTED(
      new ISM()
          .classification("RESTRICTED")
          .ownerProducer("USA")
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.RESTRICTED),
  RESTRICTED_LETTER(
      new ISM()
          .classification("R")
          .ownerProducer("USA")
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.RESTRICTED_LETTER),

  SECRET(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA")
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.SECRET),
  SECRET_LETTER(
      new ISM()
          .classification("S")
          .ownerProducer("USA")
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.SECRET_LETTER),

  TOP_SECRET(
      new ISM()
          .classification("TOP SECRET")
          .ownerProducer("USA")
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.TOP_SECRET),
  TOP_SECRET_LETTER(
      new ISM()
          .classification("TS")
          .ownerProducer("USA")
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.TOP_SECRET_LETTER),

  JOINT_SECRET(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA, CAN")
          .releasableTo("USA, CAN")
          .joint(true)
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.JOINT_SECRET),
  JOINT_SECRET_LETTER(
      new ISM()
          .classification("S")
          .ownerProducer("USA, CAN")
          .releasableTo("USA, CAN")
          .joint(true)
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.JOINT_SECRET_LETTER),

  // dissemination controls
  FOUO(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA")
          .declassDate("19991230")
          .disseminationControls("FOUO")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.FOUO),

  CABLOCK(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA")
          .classificationReason("Important Stuff")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2")
          .declassDate("19991230"),
      ValidBanner.SECRET),
  ;

  private ISM ism;
  private ValidBanner validBanner;

  ValidIsm(ISM ism, ValidBanner validBanner) {
    this.ism = ism;
    this.validBanner = validBanner;
  }

  public ISM getIsm() {
    return ism;
  }

  public ValidBanner getValidBanner() {
    return validBanner;
  }
}
