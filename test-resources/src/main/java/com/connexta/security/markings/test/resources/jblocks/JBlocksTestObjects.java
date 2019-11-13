package com.connexta.security.markings.test.resources.jblocks;

import java.io.IOException;
import java.io.InputStream;
import jblocks.dataheaders.classification.AccessControls;
import jblocks.dataheaders.classification.CaBlock;
import jblocks.dataheaders.classification.MarkingsDefinition;

public class JBlocksTestObjects {
  private static final MarkingsDefinition MARKINGS_DEFINITION;
  private static final AccessControls ACCESS_CONTROLS;
  private static final CaBlock CA_BLOCK;

  static {
    InputStream inputStream =
        JBlocksTestObjects.class.getClassLoader().getResourceAsStream("test-markings-definition-extension.txt");

    MarkingsDefinition.Builder markingsDefinitionBuilder = MarkingsDefinition.loadLatest();
    try {
      markingsDefinitionBuilder.parse(inputStream);
    } catch (IOException e) {
      throw new RuntimeException("Could not create Markings Definition test instance.");
    }

    MARKINGS_DEFINITION = markingsDefinitionBuilder.build();
    ACCESS_CONTROLS = new AccessControls(MARKINGS_DEFINITION);
    CA_BLOCK = CaBlock.newBuilder(MARKINGS_DEFINITION).build();
  }

  public static MarkingsDefinition getMarkingsDefinition() {
    return MARKINGS_DEFINITION;
  }

  public static AccessControls getAccessControls() {
    return ACCESS_CONTROLS;
  }

  public static CaBlock getCaBlock() {
    return CA_BLOCK;
  }
}
