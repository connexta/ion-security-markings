package com.connexta.security.markings.container.factory;

import com.connexta.security.markings.api.openapi.models.ISM;
import com.connexta.security.markings.container.MarkingsContainer;
import jblocks.dataheaders.classification.MarkingsDefinition;

public class MarkingsContainerFactory {
  private MarkingsDefinition markingsDefinition;

  public MarkingsContainerFactory(MarkingsDefinition markingsDefinition) {
    if (markingsDefinition == null) {
      throw new IllegalArgumentException("MarkingsContainerFactory received null MarkingsDefinition in its constructor");
    }

    this.markingsDefinition = markingsDefinition;
  }

  public MarkingsContainer create(ISM ism) {
    return new MarkingsContainer(markingsDefinition, ism);
  }
}
