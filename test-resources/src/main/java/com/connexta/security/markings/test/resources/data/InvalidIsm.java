/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.test.resources.data;

import com.connexta.security.markings.api.openapi.models.ISM;

public enum InvalidIsm {

  // incomplete
  NO_CLASSIFICATION(new ISM().ownerProducer("USA")),
  NO_OWNERPRODUCER(
      new ISM().classification("SECRET").classifiedBy("Kernel Sanders").derivedFrom("x^2")),
  JOINT_SECRET_NO_RELEASABLETO(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA, CAN")
          .joint("true")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2")),
  JOINT_SECRET_NO_OWNERPRODUCER(
      new ISM()
          .classification("SECRET")
          .releasableTo("USA")
          .joint("true")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2")),
  JOINT_SECRET_ONE_OWNERPRODUCER(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA")
          .releasableTo("USA")
          .joint("true")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2")),

  // contradictory
  UNCLASSIFIED_NOFORNORCON(
      new ISM()
          .classification("UNCLASSIFIED")
          .ownerProducer("USA")
          .disseminationControls("NOFORN/ORCON")),
  TOP_SECRET_NOFORN_RELEASABLETO(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA")
          .releasableTo("USA, CAN")
          .disseminationControls("NOFORN")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2")),
  JOINT_SECRET_RELEASABLETO_DOESNOTCONTAIN_OWNERPRODUCER(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA, CAN")
          .releasableTo("CAN, GER")
          .joint("true")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2")),

  // close
  UNCLASS(new ISM().classification("UNCLASS").ownerProducer("USA")),
  TOP_S(new ISM().classification("TOP S").ownerProducer("USA")),
  RES(new ISM().classification("RES").ownerProducer("USA")),
  SECRET_FOUOUO(
      new ISM()
          .classification("SECRET")
          .ownerProducer("USA")
          .disseminationControls("FOUOUO")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2")),
  TOP(
      new ISM()
          .classification("TOP")
          .ownerProducer("USA")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2")),
  BAD_DATE_FORMAT(new ISM()
      .classification("SECRET")
      .ownerProducer("USA")
      .declassDate("03-18-1995")
      .classifiedBy("Kernel Sanders")
      .derivedFrom("x^2")),

  // gibberish
  GIBBERISH_CLASSIFICATION(
      new ISM()
          .classification("NADNFOA")
          .ownerProducer("USA")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2")),
  GIBBERISH_OWNERPRODUCER(
      new ISM()
          .classification("SECRET")
          .ownerProducer("NAOUSUS")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2")),
  GIBBERISH_CLASSIFICATION_OWNERPRODUCER(
      new ISM()
          .classification("ANSDOUWNE")
          .ownerProducer("ALKSDNFAE")
          .classifiedBy("Kernel Sanders")
          .derivedFrom("x^2")),
  ;

  private ISM ism;

  InvalidIsm(ISM ism) {
    this.ism = ism;
  }

  public ISM getIsm() {
    return ism;
  }
}
