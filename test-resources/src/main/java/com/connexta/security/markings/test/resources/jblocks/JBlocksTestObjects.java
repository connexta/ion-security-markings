package com.connexta.security.markings.test.resources.jblocks;

import jblocks.dataheaders.classification.CaBlock;
import jblocks.dataheaders.classification.MarkingsDefinition;

public class JBlocksTestObjects {
  private static final MarkingsDefinition MARKINGS_DEFINITION;
  private static final CaBlock CA_BLOCK;

  static {
    MARKINGS_DEFINITION = MarkingsDefinition.buildLatest();
    CA_BLOCK = CaBlock.newBuilder(MARKINGS_DEFINITION).build();
  }

  public static MarkingsDefinition getMarkingsDefinition() {
    return MARKINGS_DEFINITION;
  }

  public static CaBlock getCaBlock() {
    return CA_BLOCK;
  }
}
