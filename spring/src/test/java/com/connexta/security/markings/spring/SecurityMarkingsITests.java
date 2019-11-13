/*
 * Copyright (c) 2019 Connexta, LLC
 *
 * Released under the GNU Lesser General Public License version 3; see
 * https://www.gnu.org/licenses/lgpl-3.0.html
 */
package com.connexta.security.markings.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.connexta.security.markings.api.rest.models.ISM;
import com.connexta.security.markings.api.rest.models.SecurityMarkings;
import com.connexta.security.markings.test.resources.data.InvalidIsm;
import com.connexta.security.markings.test.resources.data.ValidIsm;
import com.connexta.security.markings.test.resources.data.MediaType;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = SecurityMarkingsApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Slf4j
public class SecurityMarkingsITests {
  private static final String DEFAULT_ACCEPT_VERSION = "0.1.0-SNAPSHOT";
  private static final String DEFAULT_MEDIA_TYPE = MediaType.JSON_UTF_8.getValue();

  private static final HttpHeaders DEFAULT_HEADERS = new HttpHeaders();
  private static final ISM DEFAULT_ISM = ValidIsm.SECRET_FOUO.getIsm();
  private static final SecurityMarkings DEFAULT_SECURITY_MARKINGS = new SecurityMarkings();

  @LocalServerPort private int port;
  private URI validateUri;
  private TestRestTemplate restTemplate = new TestRestTemplate();
  private HttpHeaders headers = new HttpHeaders();
  private SecurityMarkings securityMarkings = new SecurityMarkings();

  @BeforeAll
  public static void setupClass() {
    DEFAULT_SECURITY_MARKINGS.setISM(DEFAULT_ISM);

    DEFAULT_HEADERS.add("Accept-Version", DEFAULT_ACCEPT_VERSION);
    DEFAULT_HEADERS.add("Content-Type", DEFAULT_MEDIA_TYPE);
  }

  @BeforeEach
  public void setup() throws Exception {
    validateUri = new URI("http://localhost:" + port + "/validation/validate");
  }

  // Media Type tests
  @Test
  public void testValidateGivenNoMediaType() {
    headers.add("Accept-Version", DEFAULT_ACCEPT_VERSION);

    // omitting body because of a "cannot convert body into content type" error
    RequestEntity<SecurityMarkings> request =
        new RequestEntity<>(headers, HttpMethod.POST, validateUri);

    ResponseEntity<String> response = restTemplate.exchange(request, String.class);

    log.info("Response status code: {}.", response.getStatusCode());

    assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
  }

  @ParameterizedTest
  @NullAndEmptySource
  public void testValidateGivenNullAndEmptyMediaType(String mediaType) {
    headers.add("Accept-Version", DEFAULT_ACCEPT_VERSION);
    headers.add("Content-Type", mediaType);

    // omitting body because of a "cannot convert body into content type" error
    RequestEntity<SecurityMarkings> request =
        new RequestEntity<>(headers, HttpMethod.POST, validateUri);

    ResponseEntity<String> response = restTemplate.exchange(request, String.class);

    log.info("Response status code: {}.", response.getStatusCode());

    assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
  }

  @ParameterizedTest
  @EnumSource(
      value = MediaType.class,
      names = {
        "JSON_UTF_8",
        "ANY_TYPE",
        "ANY_TEXT_TYPE",
        "ANY_IMAGE_TYPE",
        "ANY_AUDIO_TYPE",
        "ANY_VIDEO_TYPE",
        "ANY_APPLICATION_TYPE"
      },
      mode = EnumSource.Mode.EXCLUDE)
  public void testValidateGivenInvalidMediaType(MediaType mediaType) {
    headers.add("Accept-Version", DEFAULT_ACCEPT_VERSION);
    headers.add("Content-Type", mediaType.getValue());

    // omitting body because of a "cannot convert body into content type" error
    RequestEntity<SecurityMarkings> request =
        new RequestEntity<>(headers, HttpMethod.POST, validateUri);

    ResponseEntity<String> response = restTemplate.exchange(request, String.class);

    log.info("Response status code: {}.", response.getStatusCode());

    assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
  }

  @ParameterizedTest
  @EnumSource(
      value = MediaType.class,
      names = {"JSON_UTF_8"},
      mode = EnumSource.Mode.INCLUDE)
  public void testValidateGivenValidMediaType(MediaType mediaType) {
    headers.add("Accept-Version", DEFAULT_ACCEPT_VERSION);
    headers.add("Content-Type", mediaType.getValue());

    RequestEntity<SecurityMarkings> request =
        new RequestEntity<>(DEFAULT_SECURITY_MARKINGS, headers, HttpMethod.POST, validateUri);

    ResponseEntity<String> response = restTemplate.exchange(request, String.class);

    log.info("Response status code: {}.", response.getStatusCode());

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  // Accept Version tests
  @Test
  public void testValidateGivenNoAcceptVersion() {
    headers.add("Content-Type", DEFAULT_MEDIA_TYPE);

    RequestEntity<SecurityMarkings> request =
        new RequestEntity<>(DEFAULT_SECURITY_MARKINGS, headers, HttpMethod.POST, validateUri);

    ResponseEntity<String> response = restTemplate.exchange(request, String.class);

    log.info("Response status code: {}.", response.getStatusCode());

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Disabled("Disabled until strict Accept-Versioning is implemented.")
  @ParameterizedTest
  @NullAndEmptySource
  public void testValidateGivenNullAndEmptyAcceptVersion(String acceptVersion) {
    headers.add("Accept-Version", acceptVersion);
    headers.add("Content-Type", DEFAULT_MEDIA_TYPE);

    RequestEntity<SecurityMarkings> request =
        new RequestEntity<>(DEFAULT_SECURITY_MARKINGS, headers, HttpMethod.POST, validateUri);

    ResponseEntity<String> response = restTemplate.exchange(request, String.class);

    log.info("Response status code: {}.", response.getStatusCode());

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"0.1.1", "0.2.0-SNAPSHOT", "1.0.0-SNAPSHOT"})
  public void testValidateGivenInvalidAcceptVersion(String acceptVersion) {
    headers.add("Accept-Version", acceptVersion);
    headers.add("Content-Type", DEFAULT_MEDIA_TYPE);

    RequestEntity<SecurityMarkings> request =
        new RequestEntity<>(DEFAULT_SECURITY_MARKINGS, headers, HttpMethod.POST, validateUri);

    ResponseEntity<String> response = restTemplate.exchange(request, String.class);

    log.info("Response status code: {}.", response.getStatusCode());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"0.0.0-SNAPSHOT", DEFAULT_ACCEPT_VERSION})
  public void testValidateGivenValidAcceptVersion(String acceptVersion) {
    headers.add("Accept-Version", acceptVersion);
    headers.add("Content-Type", DEFAULT_MEDIA_TYPE);

    RequestEntity<SecurityMarkings> request =
        new RequestEntity<>(DEFAULT_SECURITY_MARKINGS, headers, HttpMethod.POST, validateUri);

    ResponseEntity<String> response = restTemplate.exchange(request, String.class);

    log.info("Response status code: {}.", response.getStatusCode());

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  // security markings tests
  @Test
  public void testValidateGivenNullSecurityMarkings() {
    securityMarkings = null;

    RequestEntity<SecurityMarkings> request =
        new RequestEntity<>(securityMarkings, DEFAULT_HEADERS, HttpMethod.POST, validateUri);

    ResponseEntity<String> response = restTemplate.exchange(request, String.class);

    log.info("Response status code: {}.", response.getStatusCode());

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void testValidateGivenEmptySecurityMarkings() {
    RequestEntity<SecurityMarkings> request =
        new RequestEntity<>(securityMarkings, DEFAULT_HEADERS, HttpMethod.POST, validateUri);

    ResponseEntity<String> response = restTemplate.exchange(request, String.class);

    log.info("Response status code: {}.", response.getStatusCode());

    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
  }

  // security markings with ism tests
  @Test
  public void testValidateGivenSecurityMarkingsWithNullIsm() {
    securityMarkings.setISM(null);

    RequestEntity<SecurityMarkings> request =
        new RequestEntity<>(securityMarkings, DEFAULT_HEADERS, HttpMethod.POST, validateUri);

    ResponseEntity<String> response = restTemplate.exchange(request, String.class);

    log.info("Response status code: {}.", response.getStatusCode());

    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
  }

  @Test
  public void testValidateGivenSecurityMarkingsWithEmptyIsm() {
    securityMarkings.setISM(new ISM());

    RequestEntity<SecurityMarkings> request =
        new RequestEntity<>(securityMarkings, DEFAULT_HEADERS, HttpMethod.POST, validateUri);

    ResponseEntity<String> response = restTemplate.exchange(request, String.class);

    log.info("Response status code: {}.", response.getStatusCode());

    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
  }

  @ParameterizedTest
  @EnumSource(
      value = ValidIsm.class,
      names = {
        "SECRET_NOFORN_ORCON",
        "TOP_SECRET",
        "TOP_SECRET_FOUO",
        "TOP_SECRET_RELTO",
        "TOP_SECRET_DISPLAYONLYTO",
        "TOP_SECRET_RELTO_DISPLAYONLYTO",
        "TOP_SECRET_NOFORN_ORCON",
        "JOINT_SECRET",
        "JOINT_SECRET_FOUO",
        "JOINT_SECRET_DISPLAYONLYTO",
        "JOINT_SECRET_RELTO_DISPLAYONLYTO",
      },
      mode = EnumSource.Mode.EXCLUDE)
  public void testValidateGivenSecurityMarkingsWithValidIsmCoveredBySystemHigh(ValidIsm validIsm) {
    log.info("Valid ISM: {}.", validIsm.getIsm());

    securityMarkings.setISM(validIsm.getIsm());

    RequestEntity<SecurityMarkings> request =
        new RequestEntity<>(securityMarkings, DEFAULT_HEADERS, HttpMethod.POST, validateUri);

    ResponseEntity<String> response = restTemplate.exchange(request, String.class);

    log.info("Response status code: {}.", response.getStatusCode());

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  @ParameterizedTest
  @EnumSource(
      value = ValidIsm.class,
      names = {
        "SECRET_NOFORN_ORCON",
        "TOP_SECRET",
        "TOP_SECRET_FOUO",
        "TOP_SECRET_RELTO",
        "TOP_SECRET_DISPLAYONLYTO",
        "TOP_SECRET_RELTO_DISPLAYONLYTO",
        "TOP_SECRET_NOFORN_ORCON",
        "JOINT_SECRET",
        "JOINT_SECRET_FOUO",
        "JOINT_SECRET_DISPLAYONLYTO",
        "JOINT_SECRET_RELTO_DISPLAYONLYTO"
      },
      mode = EnumSource.Mode.INCLUDE)
  public void testValidateGivenSecurityMarkingsWithValidIsmNotCoveredBySystemHigh(
      ValidIsm validIsm) {
    log.info("Valid ISM: {}.", validIsm.getIsm());

    securityMarkings.setISM(validIsm.getIsm());

    RequestEntity<SecurityMarkings> request =
        new RequestEntity<>(securityMarkings, DEFAULT_HEADERS, HttpMethod.POST, validateUri);

    ResponseEntity<String> response = restTemplate.exchange(request, String.class);

    log.info("Response status code: {}.", response.getStatusCode());

    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
  }

  @ParameterizedTest
  @EnumSource(value = InvalidIsm.class)
  public void testValidateGivenSecurityMarkingsWithInvalidIsm(InvalidIsm invalidIsm) {
    log.info("ISM: {}.", invalidIsm.name());

    securityMarkings.setISM(invalidIsm.getIsm());

    RequestEntity<SecurityMarkings> request =
        new RequestEntity<>(securityMarkings, DEFAULT_HEADERS, HttpMethod.POST, validateUri);

    ResponseEntity<String> response = restTemplate.exchange(request, String.class);

    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
  }
}
