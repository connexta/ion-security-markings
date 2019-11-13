/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.test.resources.data;

import com.connexta.security.markings.api.rest.models.ISM;

public enum ValidIsm {
  UNCLASSIFIED(
      new ISM().classification("UNCLASSIFIED").ownerProducer("USA"), ValidBanner.UNCLASSIFIED),
  UNCLASSIFIED_FOUO(
      new ISM().classification("UNCLASSIFIED").ownerProducer("USA").disseminationControls("FOUO"),
      ValidBanner.UNCLASSIFIED_FOUO),
  UNCLASSIFIED_RELTO(
      new ISM().classification("UNCLASSIFIED").ownerProducer("USA").releasableTo("USA, CAN, GBR"),
      ValidBanner.UNCLASSIFIED_RELTO),
  UNCLASSIFIED_DISPLAYONLYTO(
      new ISM().classification("UNCLASSIFIED").ownerProducer("USA").displayOnlyTo("USA, CAN"),
      ValidBanner.UNCLASSIFIED_DISPLAYONLYTO),
  UNCLASSIFIED_RELTO_DISPLAYONLYTO(
      new ISM()
          .classification("UNCLASSIFIED")
          .ownerProducer("USA")
          .releasableTo("USA, CAN, GBR")
          .displayOnlyTo("USA, CAN"),
      ValidBanner.UNCLASSIFIED_RELTO_DISPLAYONLYTO),

  SECRET(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA")
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.SECRET),
  SECRET_CABLOCK(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA")
          .classificationReason("Important Stuff")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2")
          .declassDate("19991230"),
      ValidBanner.SECRET),
  SECRET_FOUO(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA")
          .declassDate("19991230")
          .disseminationControls("FOUO")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.SECRET_FOUO),
  SECRET_RELTO(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA")
          .declassDate("19991230")
          .releasableTo("USA, CAN, GBR")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.SECRET_RELTO),
  SECRET_DISPLAYONLYTO(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA")
          .declassDate("19991230")
          .displayOnlyTo("USA, CAN")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.SECRET_DISPLAYONLYTO),
  SECRET_RELTO_DISPLAYONLYTO(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA")
          .declassDate("19991230")
          .releasableTo("USA, CAN, GBR")
          .displayOnlyTo("USA, CAN")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.SECRET_RELTO_DISPLAYONLYTO),
  SECRET_NOFORN_ORCON(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA")
          .declassDate("19991230")
          .disseminationControls("NOFORN/ORCON")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.SECRET_NOFORN_ORCON),

  TOP_SECRET(
      new ISM()
          .classification("TOP SECRET")
          .ownerProducer("USA")
          .declassDate("19991230")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.TOP_SECRET),
  TOP_SECRET_FOUO(
      new ISM()
          .classification("TOP SECRET")
          .ownerProducer("USA")
          .declassDate("19991230")
          .disseminationControls("FOUO")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.TOP_SECRET_FOUO),
  TOP_SECRET_RELTO(
      new ISM()
          .classification("TOP SECRET")
          .ownerProducer("USA")
          .declassDate("19991230")
          .releasableTo("USA, CAN, GBR")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.TOP_SECRET_RELTO),
  TOP_SECRET_DISPLAYONLYTO(
      new ISM()
          .classification("TOP SECRET")
          .ownerProducer("USA")
          .declassDate("19991230")
          .displayOnlyTo("USA, CAN")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.TOP_SECRET_DISPLAYONLYTO),
  TOP_SECRET_RELTO_DISPLAYONLYTO(
      new ISM()
          .classification("TOP SECRET")
          .ownerProducer("USA")
          .declassDate("19991230")
          .releasableTo("USA, CAN, GBR")
          .displayOnlyTo("USA, CAN")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.TOP_SECRET_RELTO_DISPLAYONLYTO),
  TOP_SECRET_NOFORN_ORCON(
      new ISM()
          .classification("TOP SECRET")
          .ownerProducer("USA")
          .declassDate("19991230")
          .disseminationControls("NOFORN/ORCON")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.TOP_SECRET_NOFORN_ORCON),

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
  JOINT_SECRET_FOUO(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA, CAN")
          .releasableTo("USA, CAN")
          .joint(true)
          .declassDate("19991230")
          .disseminationControls("FOUO")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.JOINT_SECRET_FOUO),
  JOINT_SECRET_DISPLAYONLYTO(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA, CAN")
          .releasableTo("USA, CAN")
          .joint(true)
          .declassDate("19991230")
          .displayOnlyTo("USA, CAN")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.JOINT_SECRET_DISPLAYONLYTO),
  JOINT_SECRET_RELTO_DISPLAYONLYTO(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA, CAN")
          .releasableTo("USA, CAN")
          .joint(true)
          .declassDate("19991230")
          .releasableTo("USA, CAN, GBR")
          .displayOnlyTo("USA, CAN")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2"),
      ValidBanner.JOINT_SECRET_RELTO_DISPLAYONLYTO),
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
