/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.connexta.security.markings.service.api.SecurityMarkingsService;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

public class SystemMarkingsControllerTests {

  @Test
  public void systemMarkings() throws IOException {
    SecurityMarkingsService service = mock(SecurityMarkingsService.class);
    MultipartFile multipartFile = mock(MultipartFile.class);
    Mockito.doThrow(new IOException()).when(multipartFile).getInputStream();
    SystemMarkingsController controller = new SystemMarkingsController();
    assertEquals(controller.systemMarkings("nothing").getStatusCode(), HttpStatus.OK);
  }
}
