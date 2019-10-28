/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.jblocks;

import static org.springframework.util.StringUtils.isEmpty;

import com.connexta.security.markings.api.rest.models.ISM;
import com.connexta.security.markings.banner.BannerCreator;
import com.connexta.security.markings.common.exceptions.DetailedResponseStatusException;
import com.connexta.security.markings.util.InputValidator;
import com.google.common.annotations.VisibleForTesting;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import jblocks.dataheaders.classification.AccessControls;
import jblocks.dataheaders.classification.CaBlock;
import jblocks.dataheaders.classification.Classification;
import jblocks.dataheaders.classification.MarkingsDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class ClassificationFactory {

  private String systemHighBannerMarkings;
  private MarkingsDefinition markingsDefinition;
  private CaBlockFactory caBlockFactory;
  private AccessControlsFactory accessControlsFactory;
  private BannerCreator bannerCreator;

  public ClassificationFactory(
      MarkingsDefinition markingsDefinition,
      String systemHighBannerMarkings,
      BannerCreator bannerCreator) {
    this(markingsDefinition, systemHighBannerMarkings, bannerCreator, null, null);
  }

  @VisibleForTesting
  ClassificationFactory(
      MarkingsDefinition markingsDefinition,
      String systemHighBannerMarkings,
      BannerCreator bannerCreator,
      @Nullable AccessControlsFactory accessControlsFactory,
      @Nullable CaBlockFactory caBlockFactory) {
    if (isEmpty(systemHighBannerMarkings)) {
      String msg =
          "Received null or blank System High Banner Markings in ClassificationFactory constructor. Cannot continue";
      log.error(msg);
      throw new IllegalArgumentException(msg);
    }
    if (markingsDefinition == null) {
      String msg =
          "Received null MarkingsDefinition in ClassificationFactory constructor. Cannot continue";
      log.error(msg);
      throw new IllegalArgumentException(msg);
    }

    this.systemHighBannerMarkings = systemHighBannerMarkings;
    this.markingsDefinition = markingsDefinition;
    this.bannerCreator = bannerCreator;
    this.accessControlsFactory =
        Objects.requireNonNullElseGet(
            accessControlsFactory, () -> new AccessControlsFactory(markingsDefinition));
    this.caBlockFactory =
        Objects.requireNonNullElseGet(caBlockFactory, () -> new CaBlockFactory(markingsDefinition));
  }

  public Optional<Classification> create(ISM ism) {
    log.trace("Entering create method given ISM.");

    log.trace("Input validation on ISM.");
    InputValidator.validateIsm(ism);

    Classification.Builder builder = createBuilder();

    log.trace("Create Banner Markings from given ISM or use System High Banner.");
    String bannerMarkings = createBannerOrDefaultToSystemHighBanner(ism);

    log.trace("Parse banner.");
    builder.parse(bannerMarkings);

    Optional<CaBlock> caBlock = caBlockFactory.create(ism);
    if (caBlock.isPresent()) {
      builder.setCaBlock(caBlock.get());
    } else {
      log.debug("CaBlockFactory returned empty optional. Not adding to Classification builder.");
    }

    Optional<AccessControls> accessControls = accessControlsFactory.create(ism);
    if (accessControls.isPresent()) {
      builder.setAccessControls(accessControls.get());
    } else {
      log.debug(
          "AccessControlsFactory returned empty optional. Not adding to Classification builder.");
    }

    log.trace("Exiting create method given ISM.");
    return Optional.of(builder.build());
  }

  public Optional<Classification> create(String bannerMarkings) {
    log.trace("Entering create method given Banner Markings.");

    log.trace("Input validation on Banner Markings");
    InputValidator.validateBanner(bannerMarkings);

    Classification.Builder builder = createBuilder();

    builder.parse(bannerMarkings);

    log.trace("Exiting create method given Banner Markings.");
    return Optional.of(builder.build());
  }

  private String createBannerOrDefaultToSystemHighBanner(ISM ism) {
    String bannerMarkings = null;
    try {
      bannerMarkings = bannerCreator.create(ism);
    } catch (IllegalArgumentException e) {
      log.info("Could not create banner from passed in ISM.", e);
    }

    if (isEmpty(bannerMarkings)) {
      log.info("Defaulting to the system high banner.");
      bannerMarkings = systemHighBannerMarkings;
    }

    if (isEmpty(
        bannerMarkings)) { // this should never happen; the system high banner should always be
      // populated
      String msg =
          "Could not create banner from passed in ISM, and system high banner was null or blank. Cannot create classification.";
      log.warn(msg);
      throw new DetailedResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.UNPROCESSABLE_ENTITY.value(), msg);
    }

    return bannerMarkings;
  }

  protected Classification.Builder createBuilder() {
    return Classification.newBuilder(markingsDefinition);
  }
}
